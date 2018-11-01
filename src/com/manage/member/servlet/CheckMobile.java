package com.manage.member.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.member.LoginRegisterService;

@WebServlet("/CheckMobile")
public class CheckMobile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mobileNum = request.getParameter("Mobile");
		boolean result = LoginRegisterService.checkMobile(mobileNum);
		PrintWriter out  = response.getWriter();
		out.print(result);
	}

}
