package models;

import java.util.ArrayList;

public class User {
	private int id;
	private String username;
	private String password;
	private String token;
	private ArrayList<Account> accounts;
	
	public User(int id, String username, String password, String token, ArrayList<Account> accounts) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.token = token;
		this.accounts = accounts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	

}
