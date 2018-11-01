package com.manage.BookManage.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.utils.DBPool;

/**
 * Servlet implementation class AccountCount
 */
@WebServlet("/AccountCount")
public class AccountCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

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
		   /*设置字符集为'UTF-8'*/
	response.setCharacterEncoding("UTF-8"); 
	String uid = request.getParameter("Uid");
	String AcountCount="";
	

		try {
		
			Connection conn = DBPool.getInstance().getConnection(); 
			String sql1 = "{call GetAccount(?,?)}";
			CallableStatement call1= conn.prepareCall(sql1);
			call1.registerOutParameter(2,Types.INTEGER); 
			call1.setString(1,uid);
			//一次给存储过程传递参数，插入书目信息

			call1.execute();
			AcountCount=call1.getString(2); 
			
			call1.close();
		    conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.print(AcountCount);   
	}

}
