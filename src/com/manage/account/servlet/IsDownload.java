package com.manage.account.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.account.dao.Account;
import com.manage.detail.dao.DetailDao;


/**
 * Servlet implementation class Detail
 */
@WebServlet("/IsDownload")
public class IsDownload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("Uid");
		String bookid = request.getParameter("BookId");
		 /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 

			 String  result =Account.IsDownload(uid, bookid);  
			 if(!result.equals("0")){
				 result = DetailDao.getDownload(bookid);
				 }
			try {
				PrintWriter out = response.getWriter();
				out.print(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}