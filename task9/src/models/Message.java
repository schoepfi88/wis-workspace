package models;

public class Message {
	private int id;
	private String sender;
	private String text;
	
	public Message (String sender, String text){
		this.sender = sender;
		this.text = text;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public String getSender(){
		return sender;
	}
	
	public String getText(){
		return text;
	}

}
