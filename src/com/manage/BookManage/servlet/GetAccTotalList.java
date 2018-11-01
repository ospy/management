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
@WebServlet("/GetAccTotalList")
public class GetAccTotalList extends HttpServlet {
	private static final long serialVersionUID = 2L;
       
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
		String selectType = request.getParameter("selectType");
		String addsub = request.getParameter("addsub");
		String loginid = request.getParameter("loginid");
		String memo = request.getParameter("memo");
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		String pageIndex =  request.getParameter("pageIndex");
		String pageListSize =request.getParameter("pageListSize");
		
		String condition="where 1=1 ";
		String AcountCount="";
		String Time="";
		
		if(selectType!=""&&selectType!=null){
			selectType=selectType.trim();
			condition+=" and s_type ='"+selectType+"'";
		}
		if(addsub.equals("+")){
			addsub=addsub.trim();
			condition+=" and i_value > 0";
		}
		if(addsub.equals("-")){
			addsub=addsub.trim();
			condition+=" and i_value < 0";
		}
		if(loginid!=""&&loginid!=null){
			loginid=loginid.trim();
			condition+=" and s_loginid like '%"+loginid+"%'";
		}
		if(memo!=""&&memo!=null){
			selectType=selectType.trim();
			condition+="and memo like '%"+selectType+"%'";
		}

		 if(startTime!=null&&startTime!="")
			{			
				Time +=" and s_create_time >'"+ startTime+"'";			
			}
			if(endTime!=null&&endTime!=""){
				Time +=" and s_create_time <'"+ endTime+"'";		
			}
        /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
       
        ArrayList<Account> list=new ArrayList<Account>();
		
		try {
			 Connection conn = DBPool.getInstance().getConnection(); 
				String sql1 = "SELECT * FROM myaccount  "+condition+Time+" ORDER BY s_create_time desc LIMIT "+pageIndex+","+pageListSize;
				Statement stmt=null;
				ResultSet rs = null;
				
				stmt = conn.createStatement();
				 rs = stmt.executeQuery(sql1);
			while(rs.next()){
				Account account=new Account();
				account.setS_loginid(rs.getString("s_loginid"));
				account.setI_discuid(rs.getString("i_discuid"));
				account.setS_desc(rs.getString("s_desc"));
				account.setS_message(rs.getString("s_message"));
				account.setS_type(rs.getString("s_type"));
				account.setI_uid(rs.getString("i_uid"));
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
