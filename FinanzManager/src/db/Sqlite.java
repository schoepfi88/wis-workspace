package db;

import java.sql.*;
import java.util.ArrayList;
import models.Account;
import models.AccountType;
import models.Transaction;
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
	
	public Account createAccount(String json, int owner){
		JsonObject jObj = new Gson().fromJson(json, JsonObject.class);
		System.out.println(json);
		Account account = new Account();
		account.setName(jObj.get("name").getAsString());
		account.setNumber(jObj.get("number").getAsString());
		account.setOwner(owner);
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
			System.out.println("Opened database successfully");

			System.out.println("Opened database successfully");
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO account (name, number, type, user_id) ";
			String values = "VALUES ('" + account.getName() + "','" + account.getNumber() + "','" + account.getType().toString() + "', " + owner + ");";
			System.out.println(sql + values);
			stmt.executeUpdate(sql + values);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return account;
	
	}
	
	public ArrayList<Account> getAccounts(int owner){
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
			System.out.println("Opened database successfully");

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM account;");
			while (rs.next()) {
				Account acc = new Account();
				acc.setId(rs.getInt("id"));
				acc.setName(rs.getString("name"));
				acc.setNumber(rs.getString("number"));
				if (rs.getString("type").equals("GIRO"))
					acc.setType(AccountType.GIRO);
				else 
					acc.setType(AccountType.CREDIT);
				acc.setOwner(owner);
				/* transactions
				PreparedStatement pstmt = c.prepareStatement("SELECT * FROM account WHERE user_id like ?");
				pstmt.set(1, owner);
				ResultSet rs2 = pstmt.executeQuery();
				ArrayList<Item> tmpItems = new ArrayList<>();
				while (rs2.next()){
					Item tmp = new Item();
					tmp.setAuthor(rs2.getString("author"));
					tmp.setDescription(rs2.getString("description"));
					tmp.setTitle(rs2.getString("title1"));
					tmp.setCategory(acc.getName());
					tmp.setPrice(rs2.getFloat("price"));
					tmp.setId(rs2.getInt("id"));
					tmp.setCreatedAt(rs2.getString("created_at"));
					tmpItems.add(tmp);
				}
				acc.setItems(tmpItems);
				categories.add(acc);*/
				accounts.add(acc);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return accounts;
	}
	
}