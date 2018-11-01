package com.manage.list.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.list.dao.ListDao;


@WebServlet("/HotByClass")
public class HotByClass extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String spid = request.getParameter("spid");						
		
		if(spid.equals("0")){
			spid=" ";	
		}
		else if(spid.equals("1")){
			spid="where b.i_spid <= 5700";	
		}
		else if(spid.equals("2")){
			spid="where  b.i_spid <=7600 and i_spid >=6000";	
		}
		else if(spid.equals("3")){
			spid="where  b.i_spid <=9100 and i_spid >=9000";	
		}
		else if(spid==""){
			spid=" ";
		}
		else if(spid.contains(",")){
			spid="where b.i_spid in ("+spid+")";	
		}
		else{
			spid="where b.i_spid="+spid;	
		}	
        /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
		String result = ListDao.getHotByClass(spid);					
			try {
				PrintWriter out = response.getWriter();
				out.print(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	

	}

}