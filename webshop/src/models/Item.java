package models;


import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(title).
            append(description).
            append(author).
            append(category).
            append(Integer.toString(id)).
            append(Float.toString(price)).
            append(created_at).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Item))
            return false;
        if (obj == this)
            return true;
        Item rhs = (Item) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(title, rhs.title).
            append(description, rhs.description).
            append(price, rhs.price).
            append(id, rhs.id).
            append(author, rhs.author).
            append(category, rhs.category).
            append(created_at, rhs.created_at).
            isEquals();
    }
}
