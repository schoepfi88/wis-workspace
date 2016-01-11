package models;

public class User {
	private int id;
	private String username;
	private int privilege;
	private String password;
	private String token;
	private String adress;
	
	
	public User(){
		this.id = 0;
		this.username = "guest";
		this.privilege = 1;
		this.adress = null;
	}

	public void setUser(int id, String username, int privilege, String token){
		this.id = id;
		this.username = username;
		this.privilege = privilege;
		this.token = token;
	}
	public void setUser(int id, String username, int privilege, String token, String adress){
		this.id = id;
		this.username = username;
		this.privilege = privilege;
		this.token = token;
		this.adress = adress;
	}
	public void unsetUser(){
		this.id = 0;
		this.username = "guest";
		this.privilege = 1;
		this.adress = null;
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
	
	public String getToken() {
		return token;
	}
	public String getAdress(){
		return adress;
	}
	
	
	@Override
    public boolean equals(Object object){
        boolean sameSame = false;

        if (object != null && object instanceof User){
        	return this.username.equals(((User) object).getUsername()) && this.privilege == ((User) object).getPrivilege();
        }

        return sameSame;
    }

}
