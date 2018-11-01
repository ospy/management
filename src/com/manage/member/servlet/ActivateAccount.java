package com.manage.member.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.constant.Constant;
import com.manage.entity.Activate;
import com.manage.entity.Member;
import com.manage.member.dao.MemberDao;
import com.manage.utils.DateUtils;
import com.manage.utils.LOG;
import com.manage.utils.Md5Util;


@WebServlet(name = "activateAccount", urlPatterns = { "/activateAccount" })
public class ActivateAccount extends HttpServlet {
	private static final String CHECK_CODE = "checkCode";
	private static final long serialVersionUID = 1L;

	//閭婵�娲婚獙璇�
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String checkCode1  = request.getParameter(CHECK_CODE);
		String uid = request.getParameter("activationid");
		String sql = "select * from cc_activate where i_uid="+uid+" and b_deleted=0";
		Member member = MemberDao.findMemberByID(uid);
		String path = request.getContextPath(); 
		if(member ==null){
			request.getSession().setAttribute("checkResult", "涓嶅瓨鍦ㄦ婵�娲婚摼鎺�");
//			response.sendRedirect("Member/userinfo.jsp");
			request.getRequestDispatcher("/Member/userinfo.jsp").forward(request, response);
			
		}else{
			request.getSession().setAttribute(Constant.SESSION_USER, member);
			Activate activate = MemberDao.findActiveBySQL(sql);
			String checkCode2 = Md5Util.execute(member.getUid() + ":"+ activate.getCode());
			String format = "yyyy-MM-dd HH:mm:ss"; 
			Calendar todayStart = Calendar.getInstance();  
			todayStart.set(Calendar.HOUR_OF_DAY, 0);  
			todayStart.set(Calendar.MINUTE, 0);  
			todayStart.set(Calendar.SECOND, 0);  
			todayStart.set(Calendar.MILLISECOND, 0); 
			if(member.getState()>1){//宸叉縺娲�
				request.getSession().setAttribute("checkResult", "宸叉縺娲�");
				response.sendRedirect("Member/userinfo.jsp");
			}else {//鏈縺娲�
				try {//褰撳ぉ鏈夋晥锛宼rue涓哄け鏁�
					if(DateUtils.isBefore(format, activate.getCreateTime(), format, DateUtils.format(todayStart.getTime(), format))){
						request.getSession().setAttribute("checkResult", "閾炬帴宸插け鏁�");
						response.sendRedirect("Member/userinfo.jsp");
					}else { //鏈夋晥
						if(checkCode1.equals(checkCode2)){
							//婵�娲绘垚鍔�,鏇存柊鐘舵��
							LOG.info("ID:"+member.getUid()+"婵�娲绘垚鍔燂紒");
							member.setState(2);
							MemberDao.updateMember(member);
							request.getSession().setAttribute("checkResult", "婵�娲绘垚鍔�");
//							response.sendRedirect("Member/userinfo.jsp");
    					request.getRequestDispatcher("Member/userinfo.jsp").forward(request, response);
						}else {//婵�娲诲け璐�
							request.getSession().setAttribute("checkResult", "婵�娲诲け璐�");
//							response.sendRedirect("Member/userinfo.jsp");
							request.getRequestDispatcher("/Member/userinfo.jsp").forward(request, response);
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}//
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}