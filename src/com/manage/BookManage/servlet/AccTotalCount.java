package com.manage.BookManage.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet("/AccTotalCount")
public class AccTotalCount extends HttpServlet {
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
	String selectType = request.getParameter("selectType");
	String addsub = request.getParameter("addsub");
	String loginid = request.getParameter("loginid");
	String memo = request.getParameter("memo");
	String startTime = request.getParameter("starttime");
	String endTime = request.getParameter("endtime");
	
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
		memo=memo.trim();
		condition+=" and memo like '%"+memo+"%'";
	}

	 if(startTime!=null&&startTime!="")
		{			
			Time +=" and s_create_time >'"+ startTime+"'";			
		}
		if(endTime!=null&&endTime!=""){
			Time +=" and s_create_time <'"+ endTime+"'";		
		}

	String sql = "SELECT count(*) as Count   from  myaccount "+condition + Time;
	Connection conn = DBPool.getInstance().getConnection();
	Statement stmt=null;
	ResultSet rs0 = null;
	String result="";
	try {

		stmt = conn.createStatement();
		rs0 = stmt.executeQuery(sql);
		while(rs0.next()){
		result= rs0.getString("Count");

			} 
	} catch (SQLException e) {
		e.printStackTrace();
		
	}
   finally{
	 
	   try {
		rs0.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
