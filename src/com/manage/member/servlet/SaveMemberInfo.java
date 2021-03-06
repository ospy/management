package com.manage.member.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.manage.utils.DateUtils;


@WebServlet("/SaveMemberInfo")
public class SaveMemberInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		MemberInfo memberInfo = new MemberInfo();
		String occupation  = request.getParameter("ocu");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("unit");
		String capacity = request.getParameter("level");
		String speciality = request.getParameter("spe");
		String education = request.getParameter("edu");
		HttpSession session = request.getSession();
		Member member= (Member) session.getAttribute(Constant.SESSION_USER);
		memberInfo.setAddress(address);
		memberInfo.setCapacity(capacity);
		memberInfo.setEducation(education);
		memberInfo.setMember(member);
		memberInfo.setMobile(mobile);
		memberInfo.setName(name);
		memberInfo.setSpeciality(speciality);
		memberInfo.setOccupation(occupation);
		memberInfo.setCreatetime(DateUtils.format(null));
		MemberDao.saveMemberInfo(memberInfo);
		
		PrintWriter out = response.getWriter();
		out.print(true);
		//request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}