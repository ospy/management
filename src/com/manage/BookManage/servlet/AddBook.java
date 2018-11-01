package com.manage.BookManage.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.entity.BookAbstract;
import com.manage.utils.DBPool;
import com.mchange.v2.cfg.PropertiesConfigSource.Parse;

import java.sql.CallableStatement; 
import java.lang.reflect.Field; 
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method; 

/**
 * Servlet implementation class Detail
 */
@WebServlet("/AddBook")
public class AddBook extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String s_desc=request.getParameter("title");
		String s_img_url=request.getParameter("img_url");
		
		BookAbstract bookabstract = new BookAbstract();
	    
		bookabstract.setBookTitle(request.getParameter("title").toString().trim());
		bookabstract.setAuthorIntro(request.getParameter("AuthorIntro")); 
		bookabstract.setContentIntro(request.getParameter("ContentIntro"));
		bookabstract.setContentIndex(request.getParameter("Index"));		
		bookabstract.setSeriesName(request.getParameter("Series").toString().trim());
		bookabstract.setPrice(request.getParameter("Price").toString().trim()); 
		bookabstract.setAuthorName(request.getParameter("Author").toString().trim());
		bookabstract.setPressName(request.getParameter("Press").toString().trim());
		bookabstract.setPublishTime(request.getParameter("PublishTime").toString().trim());
		bookabstract.setEdition(request.getParameter("Edition").toString().trim());
		bookabstract.setPages(request.getParameter("Pages").toString().trim()); 
		bookabstract.setWordNumber(request.getParameter("WordNumber").toString().trim()); 
		bookabstract.setPrintingTime(request.getParameter("PrintingTime").toString().trim());
		bookabstract.setBookSize(request.getParameter("BookSize").toString().trim()); 
		bookabstract.setPaper(request.getParameter("Paper").toString().trim());
		 
		bookabstract.setISBN(request.getParameter("ISBN").toString().trim());
		bookabstract.setPack(request.getParameter("Package").toString().trim());
        
		String spids=request.getParameter("spids");
		String specs=request.getParameter("specs");
		
		int price=20;
		int uid=0;
		String username="";

		int s_filesize=0;
		String s_filename="";
		 /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
		String result="";
		PrintWriter out = response.getWriter();
		
		if( request.getSession().getAttribute("uid")!=null&&request.getSession().getAttribute("username")!=null){
	     uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
		 username = request.getSession().getAttribute("username").toString();
		}else{
			result="请先登录！";
		
			out.print(result);
			return;}
		//文件信息
		String s_path=request.getParameter("s_path");
		String key=request.getParameter("key");
		String s_filetype=request.getParameter("filetype");
		
		if(request.getParameter("filesize")!=null&&request.getParameter("filesize")!=""){
		   s_filesize=Integer.valueOf(request.getParameter("filesize").toString());
		}
		else{
			result="文件大小不能为空！";
			out.print(result);
			return;}
		if(request.getParameter("filename")!=null&&request.getParameter("filename")!=""){
			   s_filename=request.getParameter("filename").toString();
			}
			else{
				out.print("文件名称不能为空！");
				return;}

		try {
			
			Connection conn = DBPool.getInstance().getConnection();
            
			String sql = "{call book_file(?,?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement call= conn.prepareCall(sql);
			//一次给存储过程传递参数，插入书目信息
			call.setString(1,s_desc);
			call.setString(2,s_img_url);
			call.setString(3,s_filetype);
			call.setInt(4,price);
			call.setInt(5,uid);
			call.setString(6,username);
			call.setString(7,s_path);
			call.setString(8,key);
			call.setString(9,s_filename);
			call.setInt(10,s_filesize);
			call.registerOutParameter(11,Types.INTEGER); 
			call.execute();
			int discuid=call.getInt(11); 	
			call.close(); 
			// region插入学科分类
			
			
			String[] spid=spids.split(",");
			 String[] spec=specs.split(",");
			  
			  
			if(discuid!=0){ 
			  try {
					
					for(int i=0;i<spid.length;i++)
					  {						 
						String sql2 = "{call InsertSpec(?,?,?)}";
						CallableStatement call2= conn.prepareCall(sql2);
						//一次给存储过程传递参数，插入书目信息
						call2.setInt(1,discuid);
						call2.setInt(2,Integer.parseInt(spid[i]));
						call2.setString(3,spec[i]);					
						call2.execute();
						call2.close();
					  }
					 
			       
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  Field[] field = bookabstract.getClass().getDeclaredFields(); //获取实体类的所有属性，返回Field数组
				 // 遍历所有属性  
		        for (int j = 0; j < field.length; j++) {  
		        	int ptid=0;
		            // 获取属性的名字  
		            String name = field[j].getName();  
		            // 将属性的首字符大写，方便构造get，set方法  
		           name = name.substring(0, 1).toUpperCase() + name.substring(1);
		           
		           switch (name) {
					case "SeriesName":ptid=56;
						break;
					case "AuthorName":ptid=51;
					     break;
					case "AuthorIntro":ptid=52;
					break;
					case "ContentIntro":ptid=17;
					break;
					case "ContentIndex":ptid=16;
					break;
					
					case "PublishTime":ptid=18;
					break;
					case "Price":ptid=31;
					break;
					case "PressName":ptid=57;
							break;
					case "Pages":ptid=59;
							break;
					case "WordNumber":ptid=60;
							break;
					case "Edition":ptid=61;
							break;
					case "PrintingTime":ptid=62;
							break;
					case "Impression":ptid=63;
							break;
					case "BookSize":ptid=64;
						break;
					case "Pack":ptid=65;
						break;
					case "Paper":ptid=66;
						break;
					case "ISBN":ptid=67;
						break;
					default:
						break;
					}		           
		            // 获取属性的类型  
		            //String type = field[j].getGenericType().toString();  
		            // 如果type是类类型，则前面包含"class "，后面跟类名  
		           
		            Method m = null;
					try {
						m = bookabstract.getClass().getMethod("get" + name);
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
		            // 调用getter方法获取属性值  
		            String txt = "";
					try {
						txt = (String) m.invoke(bookabstract);
						
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
			            if (ptid != 0 && txt !=""&& txt !=null) { 
			            	String sql3 = "{call InsertAbstract(?,?,?)}";
							CallableStatement call3= conn.prepareCall(sql3);
							//一次给存储过程传递参数，插入书目信息
							call3.setInt(1,discuid);
							call3.setInt(2,ptid);
							call3.setString(3,txt);					
							call3.execute();
							call3.close();
			                System.out.println("属性值为：" + txt);  
			            } else {  
			                System.out.println("属性值为：空");  
			                  
			            }  
				
		        }
			// endregion
	        conn.close(); 
	        result="发布成功！";
	        out.print(result);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 /*设置字符集为'UTF-8'*/
      
		
			   
			   
			
	}

}