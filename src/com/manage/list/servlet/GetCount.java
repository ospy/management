package com.manage.list.servlet;


import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;
import org.json.JSONException;

import com.manage.entity.BookList;
import com.manage.list.dao.ListDao;
import com.manage.utils.ResultsetToList;
import com.manage.utils.RsToJson;


@WebServlet("/GetCount")
public class GetCount extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String classid="where  i_spid in(";
		String Time="";
		String condition="";
		
		String keyword =  request.getParameter("search");
		String spid = request.getParameter("spid");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
 
		if(keyword!=""&&keyword!=null){
			keyword=keyword.trim();
			String temp="";
			String [] searchArray = keyword.split("\\s+");
			for(String ss : searchArray){
				temp +=" \"%"+ss+"%\"";
			  }
			
			condition="and s_desc like "+temp;
		}
		else{
			condition=" ";
		}
		

		if(spid==null||spid==""||spid.equals("0")){
			classid=" ";	
		}
		else if(spid.equals("1")){
			classid="where b.i_spid <= 5700";	
		}
		else if(spid.equals("2")){
			classid="where  b.i_spid <=7600 and i_spid >=6000";	
		}
		else if(spid.equals("3")){
			classid="where  b.i_spid <=9100 and i_spid >=9000";	
		}
		else{
			spid=spid.trim();
			String spidarr="";
			String [] searchArray = spid.split(",");
			for(String ss : searchArray){
				spidarr +=ss+",";
			  }
			classid+=spidarr.substring(0,spidarr.length() - 1)+") ";
			
		};	
		

		 if(startTime!=null&&startTime!="")
		{			
			Time ="and s_create_time >'"+ startTime+"'";			
		}
		if(endTime!=null&&endTime!=""){
			Time +=" and s_create_time <'"+ endTime+"'";		
		}
		
        /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
       String rs0 = ListDao.getCount(condition,classid,Time);

			try {
			
				PrintWriter out = response.getWriter();
				out.print(rs0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}
}