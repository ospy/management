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

import com.manage.utils.DBPool;

/**
 * Servlet implementation class CheckSelfEmail
 */
@WebServlet("/CheckSelfEmail")


public class CheckSelfEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("Email").toString();
		
		int uid = Integer.parseInt(request.getParameter("Uid").toString());
		
		String result = null;
		
			
			
			try {
				 Connection conn = DBPool.getInstance().getConnection(); 
					String sql1 = "{call CheckSelfEmail(?,?)}";
				CallableStatement call1= conn.prepareCall(sql1);
				call1.setString(1,email);
				call1.registerOutParameter(2,Types.INTEGER); 
				//一次给存储过程传递参数，插入书目信息

				call1.execute();
				int getuid=call1.getInt(2);
				if(getuid==0){
					result="0";
				}
				else if(uid==getuid){
					result="1";
				}
				else if(uid!=getuid){
					result="2";
				}
				call1.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		    
		PrintWriter out  = response.getWriter();
		
		out.print(result);
	}

}
