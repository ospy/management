package com.manage.member.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.utils.DBPool;

/**
 * Servlet implementation class M_DelMember
 */
@WebServlet("/M_DelMember")
public class M_DelMember extends HttpServlet {
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

		//MemberInfo memberInfo = new MemberInfo();
		String delid  = request.getParameter("DelId");
			
        String result="";
			
			Connection conn = DBPool.getInstance().getConnection(); 
			  //修改下载文件信息
			  try {
			 
						String sql1 = "update cc_member set b_deleted=1  where i_uid="+delid;
						String sql2 = "update cc_member_info set b_deleted=1  where i_uid="+delid;
						Statement stmt=null;
						
						
						stmt = conn.createStatement();
					  stmt.executeUpdate(sql1);
					  stmt.executeUpdate(sql2);
					    conn.close();
					 result="success";
			       
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		
		PrintWriter out = response.getWriter();
		out.print(result);
		//request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
