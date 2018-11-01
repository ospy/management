package com.manage.member.servlet;



/**
 * Servlet implementation class UpdateMemberInfo
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.manage.constant.Constant;
import com.manage.entity.Member;
import com.manage.entity.MemberInfo;
import com.manage.member.dao.MemberDao;
import com.manage.utils.DBPool;
import com.manage.utils.DateUtils;
import com.mchange.v2.cfg.PropertiesConfigSource.Parse;


@WebServlet("/M_UpdateMemberInfo")
public class M_UpdateMemberInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String delid  = request.getParameter("delid");
		if(delid!=""){
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		//MemberInfo memberInfo = new MemberInfo();

		String occupation  = request.getParameter("occupation");
		String username = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("address");
		String capacity = request.getParameter("capacity");
		String speciality = request.getParameter("speciality");
		String education = request.getParameter("education");
		String uid=request.getParameter("id");		
        String result="";
			
			Connection conn = DBPool.getInstance().getConnection(); 
			  //修改下载文件信息
			  try {
			 
						String sql1 = "{call UpdateMemberInfo(?,?,?,?,?,?,?,?)}";
						CallableStatement call1= conn.prepareCall(sql1);
						//一次给存储过程传递参数，插入书目信息
						call1.setString(1,uid);
						call1.setString(2,occupation);
						call1.setString(3,username);
						call1.setString(4,mobile);
						call1.setString(5,address);
						call1.setString(6,capacity);
						call1.setString(7,speciality);
						call1.setString(8,education);
						
						call1.execute();
						call1.close();
					    conn.close();
					 result="success";
			       
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		
		PrintWriter out = response.getWriter();
		out.print(result);
		//request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
