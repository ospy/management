package com.manage.member.servlet;

import com.manage.constant.Constant;
import com.manage.entity.BookList;
import com.manage.entity.Member;
import com.manage.entity.MemberInfo;
import com.manage.member.dao.MemberDao;
import com.manage.utils.DBPool;
import com.manage.utils.DateUtils;
import com.mchange.v2.cfg.PropertiesConfigSource.Parse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class GetUserList
 */
@WebServlet("/GetUserList")
public class GetUserList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserList() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		 /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
    	String loginid = request.getParameter("Loginid");    
    	String uname = request.getParameter("UName");
    	String uid = request.getParameter("UID");
    	String starttime = request.getParameter("StartTime");
    	String endtime = request.getParameter("EndTime");
    	//String pageIndex =  request.getParameter("pageNumber");
    	//String pageListSize = request.getParameter("pageSize");
    	
    	String pageIndex = request.getParameter("page");
    	String pageListSize = request.getParameter("rows");
    	
    	
    	String cond="";
    	String time="";
        if(loginid!=""&&uid==""&&uname==""){
    		cond="where s_loginid like '%"+loginid+"%'";
    	}	
    	else if(loginid==""&&uid!=""&&uname==""){
    		cond="where i_uid like '%"+uid+"%'";
    	} 
    	else if(loginid==""&&uid==""&&uname!=""){
    		cond="where s_name like '%"+uname+"%'";
    	};

    	
    	if(cond!=""){
    		time=" and ";
    		if(starttime!=""&&endtime!=""){
    			time+="'"+ starttime+"'"+ "< s_create_time and s_create_time"+"<"+"'"+ endtime+"'";	
    			}
    		else if(starttime!=""&&endtime==""){
    			time+= starttime+ "< s_create_time ";
    			}
    		else if(starttime==""&&endtime!=""){
    			time+= " s_create_time "+"<"+ endtime;
    			}
    		else{
    			time="";
    		}
    	}
    	else {
    		time=" where ";
    		if(starttime!=""&&endtime!=""){
    			time+= "'"+ starttime+"'"+  "< s_create_time and s_create_time"+"<"+"'"+endtime+"'" ;	
    			}
    		else if(starttime!=""&&endtime==""){
    			time+="'"+starttime+"'"+ "< s_create_time ";
    			}
    		else if(starttime==""&&endtime!=""){
    			time+= " s_create_time "+"<"+"'"+ endtime+"'";
    			}
    		else{
    			time="";
    		}
    	};
    	int rowCount = 0; 
        ArrayList<MemberInfo> list=new ArrayList<MemberInfo>();
        try {
        int	startIndex=(Integer.parseInt(pageIndex)-1)*10;	
   		 Connection conn = DBPool.getInstance().getConnection(); 
   		   String sql0 = "SELECT count(*) FROM M_UserList "+cond+time;
   			String sql1 = "SELECT * FROM M_UserList "+cond+time+" LIMIT "+ startIndex+","+ pageListSize;
   			Statement stmt=null;
   			ResultSet rs0 = null;
   			ResultSet rs = null;
   			
   			stmt = conn.createStatement();
   			rs0 = stmt.executeQuery(sql0);
   			   
   			if(rs0.next())    
   			{    
   			    rowCount=rs0.getInt(1);    
   			} 
   			 rs = stmt.executeQuery(sql1);
   			while(rs.next()){
				MemberInfo userlist=new MemberInfo();
				userlist.setId(rs.getString("i_uid"));
				userlist.setLoginid(rs.getString("s_loginid"));
				userlist.setName(rs.getString("s_name"));
				userlist.setEmail(rs.getString("s_mail"));
				userlist.setMobile(rs.getString("s_mobile"));
				userlist.setOccupation(rs.getString("s_occupation"));
				userlist.setAddress(rs.getString("s_address"));
				userlist.setCapacity(rs.getString("s_capacity"));
				userlist.setSpeciality(rs.getString("s_spec"));
				userlist.setEducation(rs.getString("s_education"));
				userlist.setCreatetime(rs.getString("s_create_time"));
				userlist.setActivetime(rs.getString("activetime"));
			list.add(userlist); 
   			}
   		conn.close();
   	} catch (SQLException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		String result = jsonArray.toString();
        result="{\"total\":"+rowCount+",\"rows\":"+result+"}";
		PrintWriter out = response.getWriter();
		out.print(result);
	}

}
