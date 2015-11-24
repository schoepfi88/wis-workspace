package models;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlRootElement
public class Category {

	private int id;
	private String name;
	private String description;
	private ArrayList<Item> items;
	
	public Category() {
	
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setItems(ArrayList<Item> items){
		this.items = items;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	// Override .equals for logical mockito matcher
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(name).
            append(description).
            append(id).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Category))
            return false;
        if (obj == this)
            return true;
        Category rhs = (Category) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(name, rhs.name).
            append(description, rhs.description).
            append(id, rhs.id).
            isEquals();
    }
}
