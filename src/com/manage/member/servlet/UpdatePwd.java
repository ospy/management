package com.manage.member.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.entity.MemberInfo;
import com.manage.utils.DBPool;

/**
 * Servlet implementation class UpdatePsw
 */
@WebServlet("/UpdatePwd")
public class UpdatePwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String uid  = request.getParameter("UID");
		String pwd  = request.getParameter("PWD");
		PrintWriter out = response.getWriter();
		String result="0";
		 Connection conn = DBPool.getInstance().getConnection(); 
			String sql1 = "{call updatepwd(?,?)}";
			CallableStatement call1;
			try {
				call1 = conn.prepareCall(sql1);
				call1.setString(1,uid);
				call1.setString(2,pwd);
				call1.execute();
				call1.close();
				
				 result="1";
				
			    conn.close();
			} catch (SQLException e) {
				result=e.toString();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(result);	
	}

}
