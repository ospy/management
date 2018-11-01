/**
 * 
 */
/**
 * @author admin
 *
 */
package com.manage.detail.dao;

import java.awt.List;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.manage.entity.BookAbstract;
import com.manage.entity.BookList;
import com.manage.entity.FileInfo;
import com.manage.utils.DBPool;
import com.manage.utils.DateUtils;
import com.manage.utils.RsToJson;



public class DetailDao {
public static String getBasicInfo(String bookid) {

	String sql = "SELECT i_discuid,s_desc,i_discuPrice,i_click_times,i_download_times,s_filetypes,s_loginid,s_imgurl,s_create_time  from cc_discu  where i_discuid ="+ bookid+ " and b_deleted=0";
	Connection conn = DBPool.getInstance().getConnection();
	
	Statement stmt=null;
	ResultSet rs1 = null;
    String result="";
    
   
    Statement stmtsub=null;
	ResultSet rssub = null;
    String resultsub="";
    String specid="";
    ArrayList<BookList> list=new ArrayList<BookList>();
	try {

		stmt = conn.createStatement();
		rs1 = stmt.executeQuery(sql);
		 
		
		while(rs1.next()){
			BookList booklist=new BookList();   
			booklist.setI_discuid(rs1.getInt("i_discuid"));    
			booklist.setS_desc(rs1.getString("s_desc"));    
			booklist.setS_imgurl(rs1.getString("s_imgurl"));    
			booklist.setS_filetypes(rs1.getString("s_filetypes"));    
			booklist.setS_create_time(rs1.getString("s_create_time").substring(0,10));
			booklist.setS_loginid(rs1.getString("s_loginid"));
			booklist.setI_discuPrice(rs1.getInt("i_discuPrice"));
			
			String discuid = rs1.getString("i_discuid");
			String sqlsub = "SELECT s_spec,i_spid FROM cc_speciality_link_discu where i_discuid="+discuid+" and b_deleted=0 " ;
			
		
				stmtsub = conn.createStatement();
				rssub = stmtsub.executeQuery(sqlsub);
				resultsub="";
				while(rssub.next()){	
					if(!rssub.isLast()){
					resultsub+= "<a href=\"/Classlist/classlist.jsp?classid="+rssub.getString("i_spid")+"\">"+rssub.getString("s_spec")+"</a>  |  ";
					specid+=rssub.getString("i_spid")+",";
					}
					else{
						resultsub+= "<a href=\"/Classlist/classlist.jsp?classid="+rssub.getString("i_spid")+"\">"+rssub.getString("s_spec")+"</a>";
						specid+=rssub.getString("i_spid");
					}
					booklist.setS_spec(resultsub); 
					booklist.setS_specid(specid);
		        }
				
			list.add(booklist); 

		}
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		result = jsonArray.toString();
		  
	} catch (SQLException e) {
		e.printStackTrace();
	}

       finally{
      	 
    	   try {
    		rssub.close();  
			rs1.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {
    		   stmtsub.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {	    		 
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }	
	return result;

    }
public static String getClassByBookid(int discuid) {

	String sql = "SELECT i_id,s_spec,i_spid FROM cc_speciality_link_discu where i_discuid="+discuid+" and b_deleted=0 ";
	Connection conn = DBPool.getInstance().getConnection();
	
	Statement stmt=null;
	ResultSet rs = null;
    String result=""; 
    JSONArray jsonarray = new JSONArray();  
	try {

		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);	
		while(rs.next()){
			JSONObject jsonObj = new JSONObject();
			 String i_id =rs.getString("i_id");
			 String i_spid =rs.getString("i_spid");  
	         String s_spec = rs.getString("s_spec");  
	        jsonObj.put("i_id",i_id); 
	        jsonObj.put("i_spid", i_spid); 
	        jsonObj.put("s_spec",s_spec); 
	        jsonarray.add(jsonObj); 
		        }
      
		
		result = jsonarray.toString();
		  
	} catch (SQLException e) {
		e.printStackTrace();
	}

       finally{
      	 
    	   try {
    		
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {
    		 
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {	    		 
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }	
	return result;

    }


public static String getExtraInfo(String bookid) throws UnsupportedEncodingException {

	String sql = "SELECT *  from cc_discu_content  where i_discuid ="+ bookid+ " and b_deleted=0";
	Connection conn = DBPool.getInstance().getConnection();
	
	Statement stmt=null;
	ResultSet rs2 = null;
    String result="";
    
    ArrayList<BookAbstract> list=new ArrayList<BookAbstract>();
    BookAbstract bookabstract = new BookAbstract();
	try {

		stmt = conn.createStatement();
		rs2 = stmt.executeQuery(sql);
		 
		JSONObject obj = new JSONObject();
		String ptids="";
		while(rs2.next()){
			   
//			bookabstract.setI_discuid(rs2.getInt("i_discuid"));    
//			bookabstract.setI_ptid(rs2.getInt("i_ptid"));    
//			bookabstract.setT_value(rs2.getString("t_value"));    
			
			String temp = rs2.getString("s_value");  
			
			
			
			switch (rs2.getInt("i_ptid")) {
			case 51:bookabstract.setAuthorName(temp);
			   
				break;
			case 52:bookabstract.setAuthorIntro(temp);
			
			     break;
			case 17:bookabstract.setContentIntro(temp);
			      
			     break;
			case 16:bookabstract.setContentIndex(temp);
			     
			break;
			case 18:bookabstract.setPublishTime(temp);
			   
			break;
			case 31:bookabstract.setPrice(temp);
					
			break;
			case 56:bookabstract.setSeriesName(temp);
				
					break;
			case 57:bookabstract.setPressName(temp);
				
					break;
			case 59:bookabstract.setPages(temp);
					
					break;
			case 60:bookabstract.setWordNumber(temp);
					
					break;
			case 61:bookabstract.setEdition(temp);
					
					break;
			case 62:bookabstract.setPrintingTime(temp);
					
					break;
			case 63:bookabstract.setImpression(temp);
					
					break;
			case 64:bookabstract.setBookSize(temp);
					
				break;
			case 65:bookabstract.setPack(temp);
				
				break;
			case 66:bookabstract.setPaper(temp);
					
				break;
			case 67:bookabstract.setISBN(temp);
					
				break;
			default:
				break;
			}
		        }
				
			list.add(bookabstract); 
 
			JSONArray jsonArray = JSONArray.fromObject(list);
//			ptids=ptids.substring(0,ptids.lastIndexOf(","));
//			obj.put("ptid",ptids);
//			jsonArray.add(obj); 
			
			
			result = jsonArray.toString();

		}
		
	  
	 catch (SQLException e) {
		e.printStackTrace();
	}

 finally{
      	 
    	   try {
    		
			rs2.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {
    		 
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {	    		 
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }	
	return result;

    }

public static String getDownload(String bookid) throws UnsupportedEncodingException {

	String sql = "SELECT *  from view_link_file  where i_key ="+ bookid+" ";
	Connection conn = DBPool.getInstance().getConnection();
	
	Statement stmt=null;
	ResultSet rs3 = null;
    String result="";
    
    ArrayList<FileInfo> list=new ArrayList<FileInfo>();
    
	try {

		stmt = conn.createStatement();
		rs3 = stmt.executeQuery(sql);
		 
		
		while(rs3.next()){
			FileInfo fileinfo = new FileInfo();   		
			fileinfo.setI_base_price(rs3.getString("i_discuPrice"));  
			fileinfo.setS_filename(rs3.getString("s_filename")); 
			fileinfo.setS_path(rs3.getString("s_path"));
			fileinfo.setS_password(rs3.getString("s_password")); 
			fileinfo.setFilesize(rs3.getString("i_size")); 
			fileinfo.setFiletype(rs3.getString("s_filetypes")); 
			list.add(fileinfo); 
		        }
		
		
        JSONArray jsonarray = JSONArray.fromObject(list);
		
		result = jsonarray.toString();
		}
		  
	 catch (SQLException e) {
		e.printStackTrace();
	}

 finally{
      	 
    	   try {
    		
			rs3.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {
    		 
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {	    		 
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }	
	return result;

    }

public static void insertAccount(String bookid,String uid,String price,String balance,String newvalue) throws UnsupportedEncodingException {
	String date = DateUtils.format(null);
	


	String sql = "insert into cc_integral(i_uid,i_value,s_type,i_discuid,s_create_time,i_old_value,i_new_value) VALUES("+uid+",-"+price+",'下载文件',"+bookid+",'"+date+"',"+balance+","+newvalue+")";
	Connection conn = DBPool.getInstance().getConnection();
	
	Statement stmt=null;
	
   
    
   
	try {

		stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		 

		}
		  
	 catch (SQLException e) {
		e.printStackTrace();
	}

 finally{
    	   try {
    		 
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   try {	    		 
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }	
	

    }
}