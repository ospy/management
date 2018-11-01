package com.manage.index;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.manage.utils.DBPool;
import com.manage.utils.DatabaseTools;
import com.manage.utils.ResultsetToList;
import com.manage.utils.RsToJson;

public class IndexService {

	//1.新书速递
	public static List getNewBooks(){ 
		Connection connection = DBPool.getInstance().getConnection();
		String sql="SELECT cc_discu.i_discuid,cc_discu.s_desc,cc_discu.s_imgurl,cc_discu.s_create_time FROM "+//
				"cc_discu where b_deleted=0   order by s_create_time desc limit 0, 15 ";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return ResultsetToList.convertList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{		
			DatabaseTools.closeConnection(connection);
		}
	}
	
	//2.热门下载
	public static List getHotDownload(){ 
		Connection connection = DBPool.getInstance().getConnection();
		String sql="SELECT cc_discu.i_discuid,cc_discu.s_desc,cc_discu.s_imgurl,cc_discu.i_download_times,"+//
				"cc_discu.s_create_time,cc_discu.b_deleted FROM "+//
				"cc_discu where b_deleted=0 order by i_download_times desc limit 0, 15";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return ResultsetToList.convertList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{			
			DatabaseTools.closeConnection(connection);
		}
	}
	//3.最近访问（实际是最近下载，避免未登陆人员查看）
	public static List getNewVisit(){ 
		Connection connection = DBPool.getInstance().getConnection();
		String sql="select cc_member.s_loginid,cc_discu.s_desc,cc_discu.i_discuid,cc_discu.s_imgurl,cc_integral.s_create_time from (cc_integral left join cc_member on cc_integral.i_uid=cc_member.i_uid) left join cc_discu on cc_integral.i_discuid=cc_discu.i_discuid where cc_integral.s_type='下载文件' order by cc_integral.s_create_time desc limit 0, 15";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return ResultsetToList.convertList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{			
			DatabaseTools.closeConnection(connection);
		}
	}
	
	//1.最新注册
		public static List getNewRegister(){ 
			Connection connection = DBPool.getInstance().getConnection();
			String sql="SELECT cc_member.i_uid,cc_member.s_loginid,cc_member.s_create_time,cc_member.b_deleted"+//
					" FROM cc_member where b_deleted=0 order by s_create_time desc limit 0, 10";
			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				return ResultsetToList.convertList(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally{			
				DatabaseTools.closeConnection(connection);
			}
		}
		//2.账户变化
		public static List getNewAccount(){ 
			Connection connection = DBPool.getInstance().getConnection();
			String sql="select cc_member.s_loginid,cc_integral.s_type,cc_integral.i_value from"+//
			"(cc_integral left join cc_member on cc_integral.i_uid=cc_member.i_uid)  order by cc_integral.s_create_time desc limit 0, 10";
			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				return ResultsetToList.convertList(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally{			
				DatabaseTools.closeConnection(connection);
			}
		}
		//3.下载排行
		public static List getTopDownload(){ 
			Connection connection = DBPool.getInstance().getConnection();
			String sql="SELECT cc_member.s_loginid,count(*) as count FROM cc_integral "//
					+ "LEFT JOIN cc_member on cc_integral.i_uid=cc_member.i_uid "//
					+ "where cc_integral.s_type='下载文件' group  BY cc_integral.i_uid order by COUNT(*) desc limit 0, 10 ";
			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				return ResultsetToList.convertList(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally{			
				DatabaseTools.closeConnection(connection);
			}
		}
	
}
