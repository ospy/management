package com.manage.spec.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.account.dao.Account;
import com.manage.list.dao.ListDao;

/**
 * Servlet implementation class spec
 */
@WebServlet("/GetSpec")
public class spec extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String scid=request.getParameter("scid");
		String result="{";
		String condition="";
		 /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 

			   
			   
			try {
		         for(int i=1;i<6;i++){
		        	condition=" where i_scid="+i;	
				   result +="区"+i+":"+ListDao.getClass(condition)+",";
		         }
		      
		        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = result.substring(0,result.length()-1); 
			result =result+"}";
			PrintWriter out = response.getWriter();
		    out.print(result); 
	}

}


