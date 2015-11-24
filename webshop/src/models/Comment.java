package models;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlRootElement
public class Comment {
	private int id;
	private String author;
	private String message;
	private String title1;
	private String createdAt;
	private int itemID;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getTitle() {
		return title1;
	}

	public void setTitle(String title) {
		this.title1 = title;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Comment(){
		
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(author).
            append(message).
            append(title1).
            append(createdAt).
            append(Integer.toString(id)).
            append(Integer.toString(itemID)).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Comment))
            return false;
        if (obj == this)
            return true;

        Comment rhs = (Comment) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(author, rhs.author).
            append(message, rhs.message).
            append(title1, rhs.title1).
            append(createdAt, rhs.createdAt).
            append(id, rhs.id).
            append(itemID, rhs.itemID).
            isEquals();
    }
	
	

}
