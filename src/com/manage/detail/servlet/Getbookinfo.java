
package com.manage.detail.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.detail.dao.GetBookInfo;



/**
 * Servlet implementation class Detail
 */
@WebServlet("/Getbookinfo")
public class Getbookinfo extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		
		 /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
           //当当
            if(url.contains("dangdang")){String result = GetBookInfo.getDD(url);
        	
	        	try {
					PrintWriter out = response.getWriter();
					out.print(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  };	
			}
            //京东
            if(url.contains("jd.com")){
            	String result = GetBookInfo.getJD(url);
        	
	        	try {
					PrintWriter out = response.getWriter();
					out.print(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  };	
			}
            //亚马逊
            if(url.contains("amazon.cn")){
            	String result = GetBookInfo.getAMAZON(url);
        	
	        	try {
					PrintWriter out = response.getWriter();
					out.print(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  };	
			}
			
	}

}