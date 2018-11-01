package com.manage.BookManage.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.manage.entity.Account;
import com.manage.utils.DBPool;

/**
 * Servlet implementation class GetAccountList
 */
@WebServlet("/GetAccountList")
public class GetAccountList extends HttpServlet {
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
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("Uid");
		int pageIndex =  Integer.parseInt(request.getParameter("pageIndex"));
		int pageListSize =Integer.parseInt(request.getParameter("pageListSize"));
		
		
		
        /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
       
        ArrayList<Account> list=new ArrayList<Account>();
		
		try {
			 Connection conn = DBPool.getInstance().getConnection(); 
				String sql1 = "SELECT * FROM myaccount  where i_uid="+uid+" ORDER BY s_create_time desc LIMIT "+pageIndex+","+pageListSize+";";
				Statement stmt=null;
				ResultSet rs = null;
				
				stmt = conn.createStatement();
				 rs = stmt.executeQuery(sql1);
			while(rs.next()){
				Account account=new Account();
				account.setI_discuid(rs.getString("i_discuid"));
				account.setS_desc(rs.getString("s_desc"));
				account.setS_message(rs.getString("s_message"));
				account.setS_type(rs.getString("s_type"));
				account.setI_value(rs.getString("i_value"));
				account.setI_old_value(rs.getString("i_old_value"));
				account.setI_new_value(rs.getString("i_new_value"));
				account.setS_create_time(rs.getString("s_create_time"));
			list.add(account); 
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		String result = jsonArray.toString();

		PrintWriter out = response.getWriter();
		out.print(result);
	}

}
