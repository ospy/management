package com.manage.member.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.manage.entity.BookList;
import com.manage.utils.DBPool;

/**
 * Servlet implementation class GetUserListCount
 */
@WebServlet("/GetUserListCount")
public class GetUserListCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserListCount() {
        super();
        // TODO Auto-generated constructor stub
    }

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
	String loginid = request.getParameter("Loginid");    
	String uname = request.getParameter("UName");
	String uid = request.getParameter("UID");
	String starttime = request.getParameter("StartTime");
	String endtime = request.getParameter("EndTime");
	
	String cond="";
	String time="";
    if(loginid!=""&&uid==""&&uname==""){
		cond="where s_loginid like '%"+loginid+"%'";
	}	
	else if(loginid==""&&uid!=""&&uname==""){
		cond="where i_uid like '%"+uid+"%'";
	} 
	else if(loginid==""&&uid==""&&uname!=""){
		cond="where s_name like '%"+uname+"%'";
	};

	
	if(cond!=""){
		time=" and ";
		if(starttime!=""&&endtime!=""){
			time+= starttime+ "< s_create_time "+"<"+ endtime;	
			}
		else if(starttime!=""&&endtime==""){
			time+= starttime+ "< s_create_time ";
			}
		else if(starttime==""&&endtime!=""){
			time+= " s_create_time "+"<"+ endtime;
			}
		else{
			time="";
		}
	}
	else {
		time=" where ";
		if(starttime!=""&&endtime!=""){
			time+= starttime+ "< s_create_time "+"<"+ endtime;	
			}
		if(starttime!=""&&endtime==""){
			time+= starttime+ "< s_create_time ";
			}
		if(starttime==""&&endtime!=""){
			time+= " s_create_time "+"<"+ endtime;
			}
		else{
			time="";
		}
	};
	
	int UserCount=0;
	

	 /*设置字符集为'UTF-8'*/
    response.setCharacterEncoding("UTF-8"); 
   
    ArrayList<BookList> list=new ArrayList<BookList>();
	
	try {
		 Connection conn = DBPool.getInstance().getConnection(); 
			String sql1 = "SELECT count(i_uid) FROM M_UserList "+cond+time;
			Statement stmt=null;
			ResultSet rs = null;
			
			stmt = conn.createStatement();
			 rs = stmt.executeQuery(sql1);
			 if(rs.first()) {  
				 UserCount=rs.getInt(1);
		        }   
		
		conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	

	PrintWriter out = response.getWriter();
	out.print(UserCount);
	}



}
