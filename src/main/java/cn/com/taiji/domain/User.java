package cn.com.taiji.domain;

import java.io.Serializable;

import lombok.Data;
@Data
public class User implements Serializable{
	private static final long serializable =1L;
	private String username;
	private  String password;
	private int id;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, int id) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
	}
	
}
