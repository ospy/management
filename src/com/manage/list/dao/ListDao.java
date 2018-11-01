package com.manage.list.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.*;

import net.sf.json.JSONArray;

import com.manage.entity.BookList;
import com.manage.utils.DBPool;
import com.manage.utils.RsToJson;

public class ListDao {

	/**
	 * 鐧诲綍楠岃瘉
	 * 
	 * @param uid
	 * @return
	 */
	

	/**
	 * 鐧诲綍楠岃瘉
	 * 
	 * @param uid
	 * @return
	 */
	public static String getClass(String scid) {

		String sql = "SELECT i_spid,s_spec FROM med_speciality"+scid+" and b_deleted=0";
		Connection conn = DBPool.getInstance().getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		String result="";
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			result = RsToJson.resultSetToJson(rs);
           
				
		} catch (SQLException e) {
			e.printStackTrace();
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
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
	
	
	
	
	
	public static String getCount(String condition,String classid, String Time) {

		String sql = "SELECT count(*) as Count   from cc_discu a where i_discuid in (SELECT DISTINCT i_discuid from cc_speciality_link_discu b " + classid + ") "+condition+"" + Time + "";
		Connection conn = DBPool.getInstance().getConnection();
		Statement stmt=null;
		ResultSet rs0 = null;
		String result="";
		try {

			stmt = conn.createStatement();
			rs0 = stmt.executeQuery(sql);
			while(rs0.next()){
			result= rs0.getString("Count");

				} 
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
       finally{
    	 
    	   try {
			rs0.close();
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
//分类列表页查询	
	public static String getBookList(String condition,String classid, String Time,	int pageIndex, int pageListSize, String order) {

		String sql = "SELECT a.i_discuid,a.s_desc,a.i_discuPrice,a.i_click_times,a.i_download_times,a.s_filetypes,a.s_loginid,a.s_imgurl,a.s_create_time  from cc_discu a where i_discuid in (SELECT DISTINCT i_discuid from cc_speciality_link_discu b "	+ classid+ ") "+condition+"  "+ Time+ "  "+ order+ " LIMIT "+ pageIndex+","+ pageListSize +"";
		Connection conn = DBPool.getInstance().getConnection();
		
		Statement stmt=null;
		ResultSet rs1 = null;
        String result="";
        
       
        Statement stmtsub=null;
		ResultSet rssub = null;
        String resultsub="";
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
						}
						else{
							resultsub+= "<a href=\"/Classlist/classlist.jsp?classid="+rssub.getString("i_spid")+"\">"+rssub.getString("s_spec")+"</a>";
						}
						booklist.setS_spec(resultsub); 
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
	
	//最新资源--按分类
	public static String getNewByClass(String spid) {

		String sql = "SELECT  a.i_discuid,a.s_desc,a.i_discuPrice,a.s_imgurl,a.i_download_times  from cc_discu a where i_discuid in (SELECT DISTINCT i_discuid from cc_speciality_link_discu b "	+ spid+ ") order by a.s_create_time desc limit 10";
		Connection conn = DBPool.getInstance().getConnection();
		Statement stmt=null;
		ResultSet rs2 = null;
        String result="";
		try {

			stmt = conn.createStatement();
			rs2 = stmt.executeQuery(sql);
			result = RsToJson.resultSetToJson(rs2);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
	
	//下载次数最多--按分类
	public static String getHotByClass(String spid) {

		String sql = "SELECT  a.i_discuid,a.s_desc,a.i_discuPrice,a.s_imgurl,a.i_download_times  from cc_discu a where i_discuid in (SELECT DISTINCT i_discuid from cc_speciality_link_discu b "	+ spid+ ") order by a.i_download_times desc limit 10";
		Connection conn = DBPool.getInstance().getConnection();
		Statement stmt=null;
		ResultSet rs3 = null;
        String result="";
		try {

			stmt = conn.createStatement();
			rs3 = stmt.executeQuery(sql);
			result = RsToJson.resultSetToJson(rs3);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
	
	// 最近下载--按分类
	public static String LatestDownByClass(String spid) {

		String sql = "SELECT a.i_discuid,a.s_desc,a.i_discuPrice,a.s_imgurl,T.s_create_time from cc_discu a, (select distinct b.i_discuid,c.s_create_time from cc_speciality_link_discu b, cc_integral c where b.i_discuid=c.i_discuid and b.i_spid="+spid+" and c.s_type=‘下载文件’ ORDER BY c.s_create_time desc) T WHERE  T.i_discuid=a.i_discuid LIMIT 10";
		Connection conn = DBPool.getInstance().getConnection();
		Statement stmt=null;
		ResultSet rs4 = null;
        String result="";
		try {

			stmt = conn.createStatement();
			rs4 = stmt.executeQuery(sql);
			result = RsToJson.resultSetToJson(rs4);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       finally{
	      	 
	    	   try {
				rs4.close();
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
}