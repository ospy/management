
package com.manage.list.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.list.dao.ListDao;




@WebServlet("/GetByScid")
public class GetByScid extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	
		String scid = request.getParameter("scid");
	
		
		if(scid!=""&&scid!=null){
			scid=" where i_scid="+scid;	
		}
		
		
		
		
        /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
		String result = ListDao.getClass(scid);
		
	
			
			try {
				PrintWriter out = response.getWriter();
				out.print(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	

		
	
	
	}

}
