package models;


import javax.xml.bind.annotation.XmlRootElement;
import com.google.gson.Gson;
@XmlRootElement
public class Item {
	private int id;
	private String title;
	private String description;
	private String author;
	private String category;
	private float price;
	private String created_at;
	
	public Item (){
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String name) {
		this.title = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String itemToJSON(){
		return new Gson().toJson(this);
	}
	
	public void setCreatedAt(String time){
		created_at = time;
	}
	
	public String getCreatedAt(){
		return created_at;
	}
	
	public void setCategory(String category){
		this.category = category;
	}
	
	public String getCategory(){
		return this.category;
	}

}
