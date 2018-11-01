package com.manage.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.Request;

import com.manage.constant.Constant;
import com.manage.utils.DateUtils;
import com.manage.utils.EmailUtils;
import com.manage.utils.LOG;
import com.manage.utils.PropertiesUtils;
import com.manage.utils.SendEmailUtil;
import com.sun.mail.util.MailSSLSocketFactory;

public class TestSendEmail {

	public static void main(String[] args) {

	}
	
	@Test
	public void  dateUtils() throws ParseException {
		String format = "yyyy-MM-dd HH:mm:ss"; 
		Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        System.out.println(DateUtils.format(todayStart.getTime(), format));
        System.out.println(DateUtils.isBefore(format, "2017-02-24 08:51:52", format, DateUtils.format(todayStart.getTime(), format)));
	}
	
	@Test
	public void readProperties(){
//		SendEmailUtil.sendEmail("123@qq.com", HttpServletRequest Request);
		Properties props = PropertiesUtils.loadProps("/config/user/emailInfo.properties");
		Iterator<String> it=props.stringPropertyNames().iterator();
        while(it.hasNext()){
            String key=it.next();
            System.out.println(key+":"+props.getProperty(key));
        }
	}
	
	@Test
	public void sendEmail(){
		final Properties props = new Properties();     
        try{
            //读取属性文件a.properties
//            InputStream in = new BufferedInputStream (new FileInputStream("config/user/emailInfo.properties"));
            InputStream in = getClass().getResourceAsStream("emailInfo.properties");
            props.load(in);     ///加载属性列表
            Iterator<String> it=props.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                System.out.println(key+":"+props.getProperty(key));
            }
            in.close();
            
//            MailSSLSocketFactory sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//            props.put("mail.smtp.ssl.enable", "true");
//            props.put("mail.smtp.ssl.socketFactory", sf); 
            
            
            
           final String addressFrom = props.getProperty("mailname");
//           Session session = Session.getInstance(props);
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(addressFrom, props.getProperty("mailpassword"));
                }
            });
            
            
            String loginid ="zhangsan";
            String url = "http://mail.163.com";
            Map<String, String> map = new HashMap<String, String>();
            if (loginid == null || loginid.equals("")) {
                map.put("name", "亲爱的用户");
            } else {
                map.put("name", loginid);
            }
            map.put("emailActiveUrl", url);
            String[] msg = SendEmailUtil.getEmailResources("account-activate.ftl",map);
            String subject = msg[0];
            String context = msg[1];
//            LOG.info(subject);
//            LOG.info(context);
//            String address = "z8m8j82003@163.com";
            String []address = {"z8m8j82003@163.com"};
//            String []address = {"z8m8j82003@163.com"};
            String type ="text/html;charset="+ Constant.CHARSET_DEFAULT;
            try {
                subject = MimeUtility.encodeText(subject, Constant.CHARSET_DEFAULT,"B");
            } catch (UnsupportedEncodingException e) {
                LOG.error("邮件主题转码失败！", e);
            }
//            Address [] addresses = {new InternetAddress(address[0])};
//            EmailUtils.sendEmail(session, new Date(), addressFrom, subject, context, type, null,addresses );
            EmailUtils.sendEmail(session, new Date(), addressFrom, subject, context, type, null, null, address);
            
//          session.setDebug(true); //有他会打印一些调试信息。

//            MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象
//            message.setFrom(new InternetAddress(addressFrom));//设置发件人的地址
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));//设置收件人,并设置其接收类型为TO
//            message.setSubject(subject);//设置标题
//            //设置信件内容
////            message.setText(mailContent); //发送 纯文本 邮件 todo
//            message.setContent(context, "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富
//            message.setSentDate(new Date());//设置发信时间
//            message.saveChanges();//存储邮件信息
//
//            //发送邮件
////            Transport transport = session.getTransport("smtp");
//            Transport transport = session.getTransport();
//            LOG.info("开始连接。。。。。。。。。");
//            transport.connect(props.getProperty("mailname"), props.getProperty("mailpassword"));
//            LOG.info("开始发送。。。。。。。。。");
//            transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址
//            transport.close();
            
            
            LOG.info("发送成功。");
        }
        catch(Exception e){
            System.out.println(e);
        }
	}
	
	
	

}
