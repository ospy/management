package com.manage.account.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;





import com.manage.utils.DBPool;

public class Account {
    
	//获取账户余额
	public static String Balance(String uid) {

		String sql = "SELECT  i_new_value  from cc_integral  where i_uid ="+ uid+ " and b_deleted=0 ORDER BY s_create_time DESC limit 1";
		Connection conn = DBPool.getInstance().getConnection();
		
		Statement stmt=null;
		ResultSet rs1 = null;
	    String result="";
	    
	   
		try {

			stmt = conn.createStatement();
			rs1 = stmt.executeQuery(sql);
			 
			
			while(rs1.next()){
				
				result = rs1.getString("i_new_value");
				

			}
			
			
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}

	       finally{
	      	 
	    	   try {
	    		rs1.close();
				
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
		return result;

	    }
	//查询该用户是否下载过该图书
	public static String IsDownload(String uid,String bookid) {

		String sql = "SELECT Count(*) count  from cc_integral  where i_uid ="+ uid+ " and i_discuid="+ bookid +" and b_deleted=0 ";
		Connection conn = DBPool.getInstance().getConnection();
		
		Statement stmt=null;
		ResultSet rs = null;
	    String result="";
	    
	   
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			 
			while(rs.next()){
			if(rs.getString("count")!="0"){
				result = rs.getString("count");
				
			}
			else{
				result = "0";
			}
		 }
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}

	       finally{
	      	 
	    	   try {
	    		rs.close();
				
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
		 return	result;

	    }
	
}
