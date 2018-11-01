package com.manage.member.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.manage.entity.BookAbstract;
import com.manage.entity.Member;
import com.manage.entity.MemberInfo;
import com.manage.utils.DBPool;

/**
 * Servlet implementation class GetMemberInfo
 * 个人信息/MyCenter/Myinfo.jsp?uid=490042
 */
@WebServlet("/GetMemberInfo")

public class GetMemberInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String uid=request.getParameter("uid");
		 /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
		String result="";
		PrintWriter out = response.getWriter();
        MemberInfo memberinfo = new MemberInfo();
        if( request.getSession().getAttribute("uid")!=null){	
			  //修改下载文件信息
			  try {
			     
				  
				  Connection conn = DBPool.getInstance().getConnection(); 
						String sql1 = "{call GetMemberInfo(?,?)}";
						CallableStatement call1= conn.prepareCall(sql1);
						call1.registerOutParameter(2,Types.INTEGER); 
						call1.setString(1, uid);
						//一次给存储过程传递参数，插入书目信息
						ResultSet rs = call1.executeQuery();
						
						while(rs.next()){
						memberinfo.setId(rs.getString("i_uid"));
						memberinfo.setLoginid(rs.getString("s_loginid"));
						memberinfo.setEmail(rs.getString("s_mail"));
						memberinfo.setOccupation(rs.getString("s_occupation"));
						memberinfo.setName(rs.getString("s_name"));
						memberinfo.setMobile(rs.getString("s_mobile"));
						memberinfo.setAddress(rs.getString("s_address"));
						memberinfo.setCapacity(rs.getString("s_capacity"));
						memberinfo.setSpeciality(rs.getString("s_spec"));
						memberinfo.setEducation(rs.getString("s_education"));
						memberinfo.setCreatetime(rs.getString("s_create_time"));
						}
						call1.execute();
						String email=call1.getString(2); 
						memberinfo.setEmail(email);
						call1.close();
					    conn.close();
					    
			       
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  ArrayList<MemberInfo> list=new ArrayList<MemberInfo>();
			  list.add(memberinfo); 
				JSONArray jsonArray = JSONArray.fromObject(list);
				
				result = jsonArray.toString();
				out.print(result);
		}
		else{
			result="请登录";
			out.print(result);	
			}
			
		}		
			
	
}
