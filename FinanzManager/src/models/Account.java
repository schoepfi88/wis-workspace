package models;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlRootElement
public class Account {

	private int id;
	private String name;
	private AccountType type;
	private String number;
	private int owner;
	private ArrayList<Transaction> transactions;
	
	public Account(){
		this.transactions = new ArrayList<>();
	}
	
	public Account(int id, AccountType type, String number, int owner) {
		this.id = id;
		this.type = type;
		this.number = number;
		this.owner = owner;
		this.transactions = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}
	
	public void addTransaction(Transaction t){
		transactions.add(t);
	}
	
	public float getBalance(){
		float balance = 0;
		for(Transaction t : transactions){
			balance += t.getValue();
		}
		return balance;
	}
}
