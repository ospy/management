package com.manage.detail.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.detail.dao.DetailDao;

/**
 * Servlet implementation class GetDownByBookId
 */
@WebServlet("/GetDownByBookId")
public class GetDownByBookId extends HttpServlet {

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
		// TODO Auto-generated method stub
		String bookid=request.getParameter("bookid").trim();
		
		try {
    		String url = DetailDao.getDownload(bookid);
    		 /*设置字符集为'UTF-8'*/
            response.setCharacterEncoding("UTF-8"); 
    		PrintWriter out = response.getWriter();
			out.print(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  };	
	}

}
