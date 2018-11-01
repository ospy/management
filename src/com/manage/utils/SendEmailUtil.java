package com.manage.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import com.manage.constant.Constant;
import com.manage.entity.Activate;
import com.manage.entity.Member;
import com.manage.member.dao.MemberDao;

import freemarker.template.TemplateException;


public class SendEmailUtil {
	private static final String CHECK_CODE = "checkCode";
	
	public static void sendEmail(String email,HttpServletRequest request) throws IOException, TemplateException{
		Member member = MemberDao.findMemberByEmail(email);
		String loginid = member.getLoginid();
		String uid = member.getUid();
		String sql = "select count(1) from cc_activate where i_uid="+uid+" and b_deleted=0";
		int count = DatabaseTools.getCount(sql);
		if(count>0){//将b_deleted置为1
			sql = "update cc_activate set b_deleted=1 where i_uid="+uid+" and b_deleted=0";
			DatabaseTools.update(sql);
		}
		//插入新记录		
		Activate activate = new Activate();
		activate.setCreateTime(DateUtils.format(""));
		activate.setMember(member);
		activate.setStype("MAIL");
		activate.setBdeleted(0);
		activate.setBoverdue(0);
		activate.setBsend(1);
		activate.setSdest(member.getEmail());
		activate.setSloginid(loginid);
		activate.setSname("");
		// 邮箱激活验证码
		activate.setCode(StringUtil.getvalidcode());
		String checkCode = Md5Util.execute(member.getUid()+ ":"+ activate.getCode());
		// 发送邮件链接地址
		String url =  getServiceHostnew(request)+"activateAccount?activationid="   
		        + member.getUid() + "&" + CHECK_CODE + "=" + checkCode; 
		LOG.info("邮箱激活连接："+url);
		
		//保存验证码
		LOG.info("保存验证码");
		MemberDao.saveActivate(activate);
		//发送内容
		Map<String, String> map = new HashMap<String, String>();
        if (loginid == null || loginid.equals("")) {
            map.put("name", "亲爱的用户");
        } else {
            map.put("name", loginid);
        }
        map.put("emailActiveUrl", url);
        String[] msg = getEmailResources("account-activate.ftl",map);
        String subject = msg[0];
        String context = msg[1];
        String []address = {email};
        // 发送邮件
        final Properties props = PropertiesUtils.loadProps("/config/user/emailInfo.properties");
        final String addressFrom = props.getProperty("mailname");
       Session session = Session.getInstance(props, new Authenticator() {
           @Override
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(addressFrom, props.getProperty("mailpassword"));
           }
       });
       
       String type ="text/html;charset="+ Constant.CHARSET_DEFAULT;
       try {
           subject = MimeUtility.encodeText(subject, Constant.CHARSET_DEFAULT,"B");
       } catch (UnsupportedEncodingException e) {
           LOG.error("邮件主题转码失败！", e);
       }
       EmailUtils.sendEmail(session, new Date(), addressFrom, subject, context, type, null, null, address);
       
       //发送之后，将cc_member表i_state置为1
        member.setState(1);
        //更新member
        MemberDao.updateMember(member);
	}
	
	 /**
     * 获取服务器的域名（包含端口号）
     * 
     * @param request   请求
     * @return          域名
     */
    public static String getServiceHostnew(HttpServletRequest request) {
        String serverPort = "";
        if (request.getServerPort() != 80) {
            serverPort = ":" + request.getServerPort();
        }
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + serverPort + path + "/";
        return basePath;
    }
    
 
    
    /**
     * 得到邮件资源
     * 
     * @param modelName
     *            资源名称
     * @param map
     *            待填充字符
     * @return 数组[主题][内容]
     * @throws IOException
     *             文件读取异常
     * @throws TemplateException
     *             文件解析异常
     */
    public static String[] getEmailResources(String modelName, Map map)
            throws IOException, TemplateException {

        String msg = FreeMarkerUtil.execute("config/marker/email/" + modelName,
                Constant.CHARSET_DEFAULT, map);

        int index = msg.indexOf("\n");

        return new String[] { msg.substring(0, index - 1),
                msg.substring(index + 1) };
    }

}
