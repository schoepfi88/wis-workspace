package models;

import java.util.ArrayList;

public class User {
	private int id;
	private String username;
	private String fbID;
	private ArrayList<Account> accounts;
	
	public User(){
		accounts = new ArrayList<>();
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

	public String getFbID() {
		return fbID;
	}

	public void setFbID(String fbID) {
		this.fbID = fbID;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	

}
