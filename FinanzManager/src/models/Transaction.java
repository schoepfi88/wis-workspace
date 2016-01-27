package models;


import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.gson.Gson;
@XmlRootElement
public class Transaction {
	private int id;
	private String sender;			// account number
	private String receiver;		// account number
	private float value;
	private String subject;
	private String time;
	
	
	public Transaction(){
		
	}
	
	public Transaction(int id, String sender, String receiver, float value) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Transaction))
			return false;
		if (obj == this)
			return true;

		Transaction t = (Transaction) obj;
		return t.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + this.id;
	    hash = 53 * hash + this.id;
	    return hash;
	}
}