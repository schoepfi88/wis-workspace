package models;

public class User {
	private static User instance = null;
	private int id;
	private String username;
	private int privilege;
	private String password;
	
	
	private User(){
		this.id = 0;
		this.username = "guest";
		this.privilege = 1;
	}

	public static User getInstance(){
		if(instance == null){
			instance = new User();
		}
		return instance;
	}

	public void setUser(int id, String username, int privilege){
		this.id = id;
		this.username = username;
		this.privilege = privilege;
	}
	public void unsetUser(){
		this.id = 0;
		this.username = "guest";
		this.privilege = 1;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public int getPrivilege() {
		return privilege;
	}
	
	public String getPassword(){
		return password;
	}

}
