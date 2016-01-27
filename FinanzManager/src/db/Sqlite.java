package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import models.Account;
import models.AccountType;
import models.Transaction;
import models.User;

import org.sqlite.SQLiteJDBCLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.sqlite.SQLiteDataSource;

public class Sqlite {
	private static Sqlite instance;
	private String dbPath = new DatabasePath().getPath();

	private Sqlite() {

	}

	public static Sqlite getInstance() {
		if (Sqlite.instance == null) {
			Sqlite.instance = new Sqlite();
		}
		return Sqlite.instance;
	}
	
	public boolean saveUser(String userJsonString){
		JsonObject userJson = new Gson().fromJson(userJsonString, JsonObject.class);
		
		User user = new User();
		user.setFbID(userJson.get("id").getAsString());
		user.setUsername(userJson.get("name").getAsString());
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// create a database connection
			Connection c = DriverManager
					.getConnection(dbPath);
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			// insert or ignore into user (name , fbID) values ( 'hans', '2384293');
			String sql = "INSERT OR IGNORE INTO user (name, fbID) ";
			String values = "VALUES ('" + user.getUsername() + "','" + user.getFbID() + "');";
			stmt.executeUpdate(sql + values);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		return true;
	}
	
	public Account createAccount(String json, String fbID){
		JsonObject jObj = new Gson().fromJson(json, JsonObject.class);
		Account account = new Account();
		account.setName(jObj.get("name").getAsString());
		account.setNumber(jObj.get("number").getAsString());
		account.setOwner(fbID);
		if (jObj.get("type").getAsString().equals("Giro"))
			account.setType(AccountType.GIRO);
		else 
			account.setType(AccountType.CREDIT);
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// create a database connection
			Connection c = DriverManager
					.getConnection(dbPath);
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO account (name, number, type, fbID, balance) ";
			String values = "VALUES ('" + account.getName() + "','" + account.getNumber() + "','" + account.getType().toString() + "', '" + fbID + "'," + 0 + ");";
			stmt.executeUpdate(sql + values);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		return account;
	
	}
	
	public Transaction createTransaction(String json){
		JsonObject jObj = new Gson().fromJson(json, JsonObject.class);
		Transaction transaction = new Transaction();
		transaction.setSender(jObj.get("sender").getAsString());
		transaction.setReceiver(jObj.get("receiver").getAsString());
		transaction.setValue(jObj.get("value").getAsFloat());
		transaction.setSubject(jObj.get("subject").getAsString());
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// create a database connection
			Connection c = DriverManager
					.getConnection(dbPath);
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO money_transaction (sender, receiver, value, subject) ";
			String values = "VALUES ('" + transaction.getSender() + "','" + 
										transaction.getReceiver() + "','" + 
										transaction.getValue() + "','" + 
										transaction.getSubject() + "');";
			stmt.executeUpdate(sql + values);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		return transaction;
	
	}
	
	public ArrayList<Account> getAccounts(String owner){
		ArrayList<Account> accounts = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// create a database connection
			Connection c = DriverManager
					.getConnection(dbPath);
			c.setAutoCommit(false);

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM account;");
			while (rs.next()) {
				Account acc = new Account();
				acc.setId(rs.getInt("id"));
				acc.setName(rs.getString("name"));
				acc.setNumber(rs.getString("number"));
				acc.setBalance(rs.getFloat("balance"));
				if (rs.getString("type").equals("GIRO"))
					acc.setType(AccountType.GIRO);
				else 
					acc.setType(AccountType.CREDIT);
				acc.setOwner(owner);
				accounts.add(acc);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return accounts;
	}
	
	public ArrayList<Transaction> getTransactions(String user_id){
		ArrayList<Transaction> transactions = new ArrayList<>();
		ArrayList<Account> accounts = getAccounts(user_id);
		for (Account acc : accounts){
			ArrayList<Transaction> tmpTrans = getTransactionsOfAccount(acc);
			for (Transaction t : tmpTrans){
				if (!transactions.contains(t)){
					transactions.add(t);
				}
			}
		}
		return transactions;
	}
	
	public ArrayList<Transaction> getTransactionsOfAccount(Account acc){
		ArrayList<Transaction> transactions = new ArrayList<>();
		float earnings = 0;
		float expenses = 0;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// create a database connection
			Connection c = DriverManager
					.getConnection(dbPath);
			c.setAutoCommit(false);
			
			// expenses
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM money_transaction where sender like '" + acc.getNumber() + "';");
			while (rs.next()) {
				Transaction trans = new Transaction();
				trans.setId(rs.getInt("id"));
				trans.setSender(rs.getString("sender"));
				trans.setReceiver(rs.getString("receiver"));
				trans.setValue(rs.getFloat("value") * -1);
				trans.setSubject(rs.getString("subject"));
				trans.setTime(rs.getString("created_at"));
				transactions.add(trans);
				expenses += trans.getValue();
			}
			rs.close();
			stmt.close();
			c.close();
			
			// create a database connection
			Connection c2 = DriverManager
					.getConnection(dbPath);
			c2.setAutoCommit(false);
			
			// earnings
			Statement stmt2 = c2.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM money_transaction where receiver like '" + acc.getNumber() + "';");
			while (rs2.next()) {
				Transaction trans = new Transaction();
				trans.setId(rs2.getInt("id"));
				trans.setSender(rs2.getString("sender"));
				trans.setReceiver(rs2.getString("receiver"));
				trans.setValue(rs2.getFloat("value"));
				trans.setSubject(rs2.getString("subject"));
				trans.setTime(rs2.getString("created_at"));
				transactions.add(trans);
				earnings += trans.getValue();
			}
			rs2.close();
			stmt2.close();
			c2.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		// update balance of account
		float balance = earnings + expenses;
		updateBalance(acc, balance);
		return getDistinct(transactions);
	}
	
	private void updateBalance(Account acc, float newBalance){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// create a database connection
			Connection c = DriverManager
					.getConnection(dbPath);
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			//UPDATE COMPANY SET ADDRESS = 'Texas' WHERE ID = 6;
			String sql = "update account set balance = " + newBalance + " ";
			String values = "where number like '" + acc.getNumber() + "';";
			stmt.executeUpdate(sql + values);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private ArrayList<Transaction> getDistinct(ArrayList<Transaction> list){
		ArrayList<Transaction> distinct = new ArrayList<>();
		Iterator<Transaction> iterator = list.iterator();

        while (iterator.hasNext()){
            Transaction t = (Transaction) iterator.next();
            if(!distinct.contains(t)) distinct.add(t);
        }
        return distinct;
	}
	
}