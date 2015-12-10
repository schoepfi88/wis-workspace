package models;

import java.util.ArrayList;

public class ActiveUser {
	ArrayList<User> users;
	static private ActiveUser instance;
	
	private ActiveUser(){
		users = new ArrayList<>();
	}
	
	public static ActiveUser getInstance(){
		if (instance == null){
			instance = new ActiveUser();
		}
		return instance;
	}
	
	public void addUser(User user){
		users.add(user);
	}
	
	public void deleteUser(User user){
		users.remove(user);
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public boolean checkLoggedIn(User user){
		return users.contains(user);
	}
}
