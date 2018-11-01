package com.manage.member.servlet;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.manage.account.dao.Account;
import com.manage.entity.Member;
import com.manage.member.dao.MemberDao;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("uName");
		String pwd  = request.getParameter("pwd");		
		       pwd = DigestUtils.md5Hex(pwd);
		ArrayList<Member> result = MemberDao.userLogin(username, pwd);
		
		for (int i = 0; i < result.size(); i++) {
	        if(result.get(0).getLoginid()!=null){
	        HttpSession session = request.getSession(); 
	        session.setAttribute("username", result.get(0).getLoginid()); 
	        session.setAttribute("uid", result.get(0).getUid()); 
	        String balance = Account.Balance(result.get(0).getUid());
	        session.setAttribute("account", balance); 
			PrintWriter out = response.getWriter();
			out.print(1);
		}
	        else{
	        	PrintWriter out = response.getWriter();
				out.print(0);
	        }
		}
	}

}
