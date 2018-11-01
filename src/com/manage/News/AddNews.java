package com.manage.News;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.utils.DatabaseTools;
import com.manage.News.*;
import com.manage.utils.DateUtils;
import com.manage.utils.DBPool;

/**
 * Servlet implementation class AddNews
 */
@WebServlet("/AddNews")
public class AddNews extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */


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
		 /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
		String title = request.getParameter("title");
		String content  = request.getParameter("content");
		String datetime =  DateUtils.format(null);
		
		String sql = "insert into news(title,content,datetime,type,b_deleted) values(?,?,?,?,?)";
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement ptst = null;
		String result="";
		try {
			ptst = conn.prepareStatement(sql);
			ptst.setString(1,title);
			ptst.setString(2,content);
			ptst.setString(3,datetime);
			ptst.setInt(4,1);
			ptst.setInt(5,0);			
			ptst.executeUpdate();
			result="提交新闻成功！";
			ptst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			PrintWriter out = response.getWriter();
			out.print(result);
	
	}

}
