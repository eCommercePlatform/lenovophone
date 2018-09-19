package org.bb.bean;

import java.util.Date;

/**
 * @author Administrator 映射原则：属性名和数据库字段名保持一致【dbutils中不区分大小写】
 * 约定：表达的name属性值和数据库的字段名保持一致【大小写敏感】
 */
public class AdminInfoBean {
	private Integer adminid;
	private String adminname;
	private String adminpsw;
	private String adminaddress;
	private String admintel;
	private Date regDate;
	private String adminimg;

	public Integer getAdminid() {
		return adminid;
	}

	public void setAdminid(Integer adminid) {
		this.adminid = adminid;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public String getAdminpsw() {
		return adminpsw;
	}

	public void setAdminpsw(String adminpsw) {
		this.adminpsw = adminpsw;
	}

	public String getAdminaddress() {
		return adminaddress;
	}

	public void setAdminaddress(String adminaddress) {
		this.adminaddress = adminaddress;
	}

	public String getAdmintel() {
		return admintel;
	}

	public void setAdmintel(String admintel) {
		this.admintel = admintel;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getAdminimg() {
		return adminimg;
	}

	public void setAdminimg(String adminimg) {
		this.adminimg = adminimg;
	}

	@Override
	public String toString() {
		return "AdminInfoBean [adminid=" + adminid + ", adminname=" + adminname + ", adminpsw=" + adminpsw
				+ ", adminaddress=" + adminaddress + ", admintel=" + admintel + ", regDate=" + regDate + ", adminimg="
				+ adminimg + "]";
	}


	

}
