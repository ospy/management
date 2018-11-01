package com.manage.list.servlet;

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
 * Servlet implementation class GetSpecByDpid
 */
@WebServlet("/GetSpecByClassid")
public class GetSpecByClassid extends HttpServlet {
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
		// TODO Auto-generated method stub
		 response.setCharacterEncoding("UTF-8"); 
		String spid=request.getParameter("classid");
		 String result="";
		 if(spid.equals("0")||spid==""){
			 result="全部";
		 }
		 else if(spid.equals("1")){
			 result="临床医学";
		 }
		 else if(spid.equals("2")){
			 result="基础医学";
		 }
		 else if(spid.equals("3")){
			 result="药学";
		 }
		 else{
		  try {
				
			  Connection conn = DBPool.getInstance().getConnection(); 		 
					String sql1 = "{call GetSpecBySpid(?,?)}";
					CallableStatement call1= conn.prepareCall(sql1);
					//一次给存储过程传递参数，插入书目信息
					
					call1.registerOutParameter(2,Types.INTEGER); 
					call1.setString(1,spid);
					//一次给存储过程传递参数，插入书目信息

					call1.execute();
					result=call1.getString(2); 				
					
					call1.close();
				    conn.close();
				 
		       
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		  PrintWriter out = response.getWriter();
		  out.print(result);	
	}

}
