package com.manage.entity;

public class Activate {
	
	private long id;
	private  Member member;
	private String stype;
	private String sname;
	private String sloginid;
	private String sdest;
	private int boverdue;
	private int bsend;
	private int bdeleted;
	private String code;
	private  String createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSloginid() {
		return sloginid;
	}
	public void setSloginid(String sloginid) {
		this.sloginid = sloginid;
	}
	public String getSdest() {
		return sdest;
	}
	public void setSdest(String sdest) {
		this.sdest = sdest;
	}
	public int getBoverdue() {
		return boverdue;
	}
	public void setBoverdue(int boverdue) {
		this.boverdue = boverdue;
	}
	public int getBsend() {
		return bsend;
	}
	public void setBsend(int bsend) {
		this.bsend = bsend;
	}
	public int getBdeleted() {
		return bdeleted;
	}
	public void setBdeleted(int bdeleted) {
		this.bdeleted = bdeleted;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
