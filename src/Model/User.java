package Model;

import Helper.DBConnection;

public class User {
	DBConnection conn = new DBConnection();
	private int id;
	private String tcno, name, pasword, type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTcno() {
		return tcno;
	}

	public void setTcno(String tcno) {
		this.tcno = tcno;
	}

	public String getName() {
		return name;
	}

	public User() {

	}

	public User(int id, String tcno, String name, String pasword, String type) {
		super();
		this.id = id;
		this.tcno = tcno;
		this.name = name;
		this.pasword = pasword;
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
