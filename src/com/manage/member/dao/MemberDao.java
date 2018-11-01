package com.manage.member.dao;

import java.awt.ActiveEvent;
import java.awt.List;
import java.awt.Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.sound.midi.MetaEventListener;

import com.manage.entity.Activate;
import com.manage.entity.BookList;
import com.manage.entity.Member;
import com.manage.entity.MemberInfo;
import com.manage.utils.DBPool;
import com.manage.utils.DatabaseTools;

public class MemberDao {
	
	/**
	 * 鐧诲綍楠岃瘉
	 * @param uid
	 * @return
	 */
	public static ArrayList<Member> userLogin(String uid,String pwd){
		Member member = new Member();
		String sql = "select * from cc_member where s_loginid='"+uid+"' and s_password ='"+pwd+"'";
		Connection conn = DBPool.getInstance().getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		ArrayList<Member> list=new ArrayList<Member>();
		try {
			stmt = conn.createStatement();
			 rs = stmt.executeQuery(sql);
			 if(rs.next()){
					
				 member.setUid(rs.getString("i_uid"));
				 member.setEmail(rs.getString("s_mail"));
				 member.setLoginid(rs.getString("s_loginid"));
				 member.setPassword(rs.getString("s_password"));
				 member.setState(rs.getInt("i_state"));
				 member.setOnline(rs.getInt("i_online"));
			 }
			 list.add(member); 
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
		return list;
	}
	

	/**
	 * 鏍规嵁uid鏌ユ壘Member
	 * @param uid
	 * @return
	 */
		public static Member findMemberByID(String uid){
			Member member = new Member();
			String sql = "select * from cc_member where i_uid='"+uid+"'";
			Connection conn = DBPool.getInstance().getConnection();
			Statement stmt;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				 rs = stmt.executeQuery(sql);
				 if(rs.next()){
					 member.setUid(rs.getString("i_uid"));
					 member.setEmail(rs.getString("s_mail"));
					 member.setLoginid(rs.getString("s_loginid"));
					 member.setPassword(rs.getString("s_password"));
					 member.setState(rs.getInt("i_state"));
					 member.setOnline(rs.getInt("i_online"));
				 }else{
					 return null;
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return member;
		}
	
		/**
		 * 鏍规嵁email鏌ユ壘Member
		 * @param email
		 * @return
		 */
	public static Member findMemberByEmail(String email){
		Member member = new Member();
		String sql = "select * from cc_member where s_mail='"+email+"'";
		Connection conn = DBPool.getInstance().getConnection();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			 rs = stmt.executeQuery(sql);
			 rs.next();
			 member.setUid(rs.getString("i_uid"));
			 member.setEmail(rs.getString("s_mail"));
			 member.setLoginid(rs.getString("s_loginid"));
			 member.setPassword(rs.getString("s_password"));
			 member.setState(rs.getInt("i_state"));
			 member.setOnline(rs.getInt("i_online"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	
	/**
	 * 鏇存柊Member
	 * @param member
	 */
	public static void updateMember(Member member){
		String sql = "update cc_member set s_mail=?,s_password=?,i_state=?,i_online=? where i_uid=?";
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement ptst = null;
		try {
			ptst = conn.prepareStatement(sql);
			ptst.setString(1, member.getEmail());
			ptst.setString(2, member.getPassword());
			ptst.setInt(3, member.getState());
			ptst.setInt(4, member.getOnline());
			ptst.setString(5, member.getUid());
			ptst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DatabaseTools.closeStatement(ptst);
			DatabaseTools.closeConnection(conn);
		}
	}
	
	
	
	/**
	 * 鏍规嵁email鏌ユ壘Active
	 * @param email
	 * @return
	 */
	public static Activate findActiveByEmail(String email){
		Activate activate = new Activate();
		String sql = "select * from cc_activate where s_mail='"+email+"' order by i_id desc";
		Connection conn = DBPool.getInstance().getConnection();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			 rs = stmt.executeQuery(sql);
			 rs.next();
			 activate.setId(rs.getLong("i_id"));
			 activate.setCreateTime(rs.getString("s_create_time"));
			 activate.setCode(rs.getString("s_act_code"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activate;
	}
	
	
	/**
	 * 鏍规嵁uid鏌ユ壘Active
	 * @param uid
	 * @return
	 */
		public static Activate findActiveByUid(String uid){
			Activate activate = new Activate();
			String sql = "select * from cc_activate where i_uid='"+uid+"' order by i_id desc";
			Connection conn = DBPool.getInstance().getConnection();
			Statement stmt;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				 rs = stmt.executeQuery(sql);
				 rs.next();
				 activate.setId(rs.getLong("i_id"));
				 activate.setCreateTime(rs.getString("s_create_time"));
				 activate.setCode(rs.getString("s_act_code"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return activate;
		}
		
		/**
		 * 鏍规嵁SQL鏌ユ壘Active
		 * @param uid
		 * @return
		 */
		public static Activate findActiveBySQL(String sql){
			Activate activate = new Activate();
			Connection conn = DBPool.getInstance().getConnection();
			Statement stmt;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				 rs = stmt.executeQuery(sql);
				 rs.next();
				 activate.setId(rs.getLong("i_id"));
				 activate.setCreateTime(rs.getString("s_create_time"));
				 activate.setCode(rs.getString("s_act_code"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return activate;
		}
		
		/**
		 * 淇濆瓨activate
		 * @param activate
		 */
		public static void saveActivate(Activate activate){
			Member member = activate.getMember();
			String sql = "insert into cc_activate(i_uid,s_loginid,s_act_code,s_create_time,s_type,"
					+ "s_name,s_dest,b_overdue,b_send,b_deleted) values(?,?,?,?,?,?,?,?,?,?)";
			Connection conn = DBPool.getInstance().getConnection();
			PreparedStatement ptst = null;
			try {
				ptst = conn.prepareStatement(sql);
				ptst.setString(1, member.getUid());
				ptst.setString(2, member.getLoginid());
				ptst.setString(3, activate.getCode());
				ptst.setString(4, activate.getCreateTime());
				ptst.setString(5, activate.getStype());
				ptst.setString(6, activate.getSname());
				ptst.setString(7, activate.getSdest());
				ptst.setInt(8, activate.getBoverdue());
				ptst.setInt(9, activate.getBsend());
				ptst.setInt(10, activate.getBdeleted());
				ptst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DatabaseTools.closeStatement(ptst);
				DatabaseTools.closeConnection(conn);
			}
			
		}
		
		
		/**
		 * 淇濆瓨MemberInfo
		 * @param MemberInfo
		 */
		public static void saveMemberInfo(MemberInfo memberInfo){
			Member member = memberInfo.getMember();
			String sql = "insert into cc_member_info(i_uid,s_occupation,s_name,s_mobile,s_address,"
					+ "s_capacity,s_spec,s_education,s_create_time) values(?,?,?,?,?,?,?,?,?)";
			Connection conn = DBPool.getInstance().getConnection();
			PreparedStatement ptst = null;
			try {
				ptst = conn.prepareStatement(sql);
				ptst.setString(1, member.getUid());
				ptst.setString(2, memberInfo.getOccupation());
				ptst.setString(3, memberInfo.getName());
				ptst.setString(4, memberInfo.getMobile());
				ptst.setString(5, memberInfo.getAddress());
				ptst.setString(6, memberInfo.getCapacity());
				ptst.setString(7, memberInfo.getSpeciality());
				ptst.setString(8, memberInfo.getEducation());
				ptst.setString(9, memberInfo.getCreatetime());
				ptst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DatabaseTools.closeStatement(ptst);
				DatabaseTools.closeConnection(conn);
			}
			
		}
		
		
}