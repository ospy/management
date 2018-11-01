package com.manage.detail.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.manage.account.dao.Account;
import com.manage.detail.dao.DetailDao;
import com.manage.utils.DBPool;

@WebServlet("/Download")
public class Download extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookid = request.getParameter("bookid");
		String uid = request.getParameter("uid");
		 /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
        String price="";
		String result = DetailDao.getDownload(bookid);
		JSONArray jsonArray = JSONArray.fromObject(result);
		JSONObject jsonObject = null;
		
        jsonObject = jsonArray.getJSONObject(0);  
        price= jsonObject.getString("i_base_price");  
            
              
       
		 
		String balance = Account.Balance(uid);
		
	    if(Integer.parseInt(balance)>=Integer.parseInt(price)){
		int newbalance = Integer.parseInt(balance)-Integer.parseInt(price);
		String newvalue = String.valueOf(newbalance);
		HttpSession session = request.getSession(); 
		session.setAttribute("account",newvalue);
		jsonObject.put("newbalance", newvalue);
		jsonArray.set(0,jsonObject);
		result=jsonArray.toString();
			try {
				DetailDao.insertAccount(bookid, uid, price, balance, newvalue);				
				PrintWriter out = response.getWriter();
				out.print(result);
				//下载次数加1
				Connection conn = DBPool.getInstance().getConnection();
				String sql = "{call AddDownTimes(?)}";
				CallableStatement call= conn.prepareCall(sql);
				//一次给存储过程传递参数，插入书目信息
				call.setInt(1,Integer.parseInt(bookid));
			
				call.execute();
					
				call.close(); 
				conn.close(); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else{
	    	PrintWriter out = response.getWriter();
			out.print("余额不足，请充值！");
	    }
	}
}
