package com.manage.account.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.account.dao.Account;
import com.manage.utils.DBPool;
import com.manage.utils.DateUtils;

/**
 * Servlet implementation class AccAdjust
 */
@WebServlet("/AccAdjust")
public class AccAdjust extends HttpServlet {
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
		String uid = request.getParameter("uid");
		String updatetype = request.getParameter("updatetype");
		String points = request.getParameter("points");
		String accmemo = request.getParameter("accmemo");
		
		String uname = (String) request.getSession().getAttribute("username");
		uname+=":"+accmemo;
		String balance = Account.Balance(uid);
		int newbalance=0;
		String date = DateUtils.format(null);
		if(updatetype.equals("+")){
			 newbalance = Integer.parseInt(balance)+Integer.parseInt(points);
		}
		if(updatetype.equals("-")){
			 newbalance = Integer.parseInt(balance)-Integer.parseInt(points);
		}
		String newvalue = String.valueOf(newbalance);
		String sql = "insert into cc_integral(i_uid,i_value,s_type,i_discuid,s_message,s_create_time,i_old_value,i_new_value) VALUES("+uid+","+updatetype+points+",'手动添加','','"+uname+"','"+date+"',"+balance+","+newvalue+")";
		Connection conn = DBPool.getInstance().getConnection();
		
		Statement stmt=null;
		
	    String result="";
	    
	   
		try {

			stmt = conn.createStatement();
			 stmt.executeUpdate(sql);
			result = "调整账户成功";
	  
		} catch (SQLException e) {
			e.printStackTrace();
		}

	       finally{
	      	 
	    	   try {
	    		  
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	   try {	    		 
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
