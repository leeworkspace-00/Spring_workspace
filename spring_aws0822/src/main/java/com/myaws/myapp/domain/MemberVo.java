package com.myaws.myapp.domain;

public class MemberVo { // mybatis 가 사용할 값들을 가져와서 도메인 담아준다

	private int midx;
	private String memberid;
	private String memberpwd;
	private String membername;
	private String membergender;
	private String memberbirth;
	private String memberaddr;
	private String memberphone;
	private String memberemail;
	private String memberhobby;
	private String delyn;
	private String writeday;
	private String memberip;

	public int getMidx() {
		return midx;
	}

	public void setMidx(int midx) {
		this.midx = midx;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getMemberpwd() {
		return memberpwd;
	}

	public void setMemberpwd(String memberpwd) {
		this.memberpwd = memberpwd;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getMembergender() {
		return membergender;
	}

	public void setMembergender(String membergender) {
		this.membergender = membergender;
	}

	public String getMemberbirth() {
		return memberbirth;
	}

	public void setMemberbirth(String memberbirth) {
		this.memberbirth = memberbirth;
	}

	public String getMemberaddr() {
		return memberaddr;
	}

	public void setMemberaddr(String memberaddr) {
		this.memberaddr = memberaddr;
	}

	public String getMemberphone() {
		return memberphone;
	}

	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}

	public String getMemberemail() {
		return memberemail;
	}

	public void setMemberemail(String memberemail) {
		this.memberemail = memberemail;
	}

	public String getMemberhobby() {
		return memberhobby;
	}

	public void setMemberhobby(String memberhobby) {
		this.memberhobby = memberhobby;
	}

	public String getDelyn() {
		return delyn;
	}

	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}

	public String getWriteday() {
		return writeday;
	}

	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}

	public String getMemberip() {
		return memberip;
	}

	public void setMemberip(String memberip) {
		this.memberip = memberip;
	}
}