package db;

import java.sql.*;
import java.util.ArrayList;
import javax.sql.RowSet;
import models.Category;
import models.Comment;
import models.Item;
import models.User;
import resources.Resource;

import org.sqlite.SQLiteJDBCLoader;

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
	
	public ArrayList<Category> getCategories(){
		ArrayList<Category> categories = new ArrayList<>();
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM category;");
			while (rs.next()) {
				Category cat = new Category();
				cat.setId(rs.getInt("id"));
				cat.setName(rs.getString("name"));
				cat.setDescription(rs.getString("description"));
				PreparedStatement pstmt = c.prepareStatement("SELECT * FROM item WHERE category like ?");
				pstmt.setString(1, cat.getName());
				ResultSet rs2 = pstmt.executeQuery();
				ArrayList<Item> tmpItems = new ArrayList<>();
				while (rs2.next()){
					Item tmp = new Item();
					tmp.setAuthor(rs2.getString("author"));
					tmp.setDescription(rs2.getString("description"));
					tmp.setTitle(rs2.getString("title1"));
					tmp.setCategory(cat.getName());
					tmp.setPrice(rs2.getFloat("price"));
					tmp.setId(rs2.getInt("id"));
					tmp.setCreatedAt(rs2.getString("created_at"));
					tmpItems.add(tmp);
				}
				cat.setItems(tmpItems);
				categories.add(cat);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return categories;
	}
	
	public Category getCategory(int id){
		Category cat = new Category();
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

			PreparedStatement pstmt = c.prepareStatement("SELECT * FROM category WHERE id like ?");
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			cat.setId(rs.getInt("id"));
			cat.setDescription(rs.getString("description"));
			cat.setName(rs.getString("name"));
			
			rs.close();
			pstmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return cat;
	}
	
	public ArrayList<Item> getItems() throws ClassNotFoundException {
		ArrayList<Item> items = new ArrayList<>();
		Class.forName("org.sqlite.JDBC");
		try {
			// create a database connection
			Connection c = DriverManager
					.getConnection(dbPath);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM item;");
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setTitle(rs.getString("title1"));
				item.setAuthor(rs.getString("author"));
				item.setDescription(rs.getString("description"));
				item.setCreatedAt(rs.getString("created_at"));
				item.setPrice(rs.getFloat("price"));
				items.add(item);
			}

			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");

		return items;
	}

	public Item getItem(int id) throws ClassNotFoundException {

		Item item = new Item();
		Class.forName("org.sqlite.JDBC");
		try {
			// create a database connection
			Connection c = DriverManager
					.getConnection(dbPath);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			
			PreparedStatement pstmt = c.prepareStatement("SELECT * FROM item WHERE id like ?");
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			item.setId(rs.getInt("id"));
			item.setTitle(rs.getString("title1"));
			item.setAuthor(rs.getString("author"));
			item.setDescription(rs.getString("description"));
			item.setPrice(rs.getFloat("price"));

			rs.close();
			pstmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return item;
	}

	public boolean createItem(Item item) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		try {
			boolean initialize = SQLiteJDBCLoader.initialize();
			SQLiteDataSource dataSource = new SQLiteDataSource();
			dataSource.setUrl(dbPath);
			Connection c = dataSource.getConnection();
			c.setAutoCommit(false);
			item.setCategory(item.getCategory().replace("\n", ""));		// remove newline from category
			System.out.println("Opened database successfully");
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO item (title1, description, author, category, price) ";
			String values = "VALUES ('" + item.getTitle() + "','" + item.getDescription() + "', '" + item.getAuthor() + "','" + item.getCategory() + "','" + item.getPrice()
					+ "');";
			stmt.executeUpdate(sql + values);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public int deleteItem(int id) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		int rowsAffected = 0;
		try {
			// create a database connection
			Connection c = DriverManager.getConnection(dbPath);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			PreparedStatement pstmt = c.prepareStatement("DELETE FROM item WHERE id like ?");
			
			pstmt.setInt(1, id);

			rowsAffected = pstmt.executeUpdate();
			c.commit();

			pstmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		if (rowsAffected > 0) {
			System.out.println("Operation done successfully");
		}
		System.out.println("ROWS AFFECTED:" + rowsAffected);
		return rowsAffected;
	}
	
	public boolean createCategory(Category cat) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		boolean success = false;
		try {
			boolean initialize = SQLiteJDBCLoader.initialize();
			SQLiteDataSource dataSource = new SQLiteDataSource();
			dataSource.setUrl(dbPath);
			Connection c = dataSource.getConnection();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO category (name, description) ";
			String values = "VALUES ('" + cat.getName() + "','" + cat.getDescription() + "');";
			stmt.executeUpdate(sql + values);
			stmt.close();
			c.commit();
			c.close();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			return success;
		}
		return success;
	}
	
	public int deleteCategory(int id) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		int rowsAffected = 0;
		try {
			// create a database connection
			Connection c = DriverManager.getConnection(dbPath);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			System.out.println(id);
			PreparedStatement pstmt = c.prepareStatement("DELETE FROM category WHERE id like ?");
			
			pstmt.setInt(1, id);

			rowsAffected = pstmt.executeUpdate();
			c.commit();

			pstmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		if (rowsAffected > 0) {
			System.out.println("Operation done successfully");
		}
		System.out.println("ROWS AFFECTED:" + rowsAffected);
		return rowsAffected;
	}
	
	public int createComment(Comment comment) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		int createdID = -1;
		try {
			boolean initialize = SQLiteJDBCLoader.initialize();
			SQLiteDataSource dataSource = new SQLiteDataSource();
			dataSource.setUrl(dbPath);
			Connection c = dataSource.getConnection();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO comment (author, message, title1, item_id) ";
			String values = "VALUES ('" + comment.getAuthor() + "','" + comment.getMessage() + "','" + comment.getTitle() + "','" + comment.getItemID() + "');";
			stmt.executeUpdate(sql + values);
			stmt.close();
			String query = "SELECT MAX(ID) AS LAST FROM comment";
			PreparedStatement pst1 = c.prepareStatement(query);
            ResultSet  rs1 = pst1.executeQuery();
            String maxId=  rs1.getString("LAST");
            createdID =(Integer.parseInt(maxId));
            pst1.execute();
            pst1.close();
            rs1.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createdID;
	}
	
	public int deleteComment(int itemID, int cID) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		int rowsAffected = 0;
		try {
			// create a database connection
			Connection c = DriverManager.getConnection(dbPath);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			PreparedStatement pstmt = c.prepareStatement("DELETE FROM comment WHERE item_id like ? and id like ?");
			
			pstmt.setInt(1, itemID);
			pstmt.setInt(2, cID);

			rowsAffected = pstmt.executeUpdate();
			c.commit();

			pstmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		if (rowsAffected > 0) {
			System.out.println("Operation done successfully");
		}
		System.out.println("ROWS AFFECTED:" + rowsAffected);
		return rowsAffected;
	}
	
	public Comment getComment(int itemID, int commID){
		Comment comment = new Comment();
		try {
			// create a database connection
			Connection c = DriverManager.getConnection(dbPath);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			PreparedStatement pstmt = c.prepareStatement("Select * FROM comment WHERE item_id like ? and id like ?");
			
			pstmt.setInt(1, itemID);
			pstmt.setInt(2, commID);

			ResultSet rs = pstmt.executeQuery();
			comment.setId(rs.getInt("id"));
			comment.setTitle(rs.getString("title1"));
			comment.setAuthor(rs.getString("author"));
			comment.setMessage(rs.getString("message"));
			c.commit();
			pstmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return comment;
	}
	
	public ArrayList<Comment> getComments(int id){
		ArrayList<Comment> comments = new ArrayList<>();
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
			PreparedStatement pstmt = c.prepareStatement("Select * FROM comment WHERE item_id like ?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setAuthor(rs.getString("author"));
				comment.setMessage(rs.getString("message"));
				comment.setTitle(rs.getString("title1"));
				comment.setCreatedAt(rs.getString("created_at"));
				comment.setItemID(rs.getInt("item_id"));
				comments.add(comment);
			}
			rs.close();
			pstmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return comments;
	}
	
	public ArrayList<String> login(String name, String password) throws ClassNotFoundException, SQLException{
		ArrayList<String> userData = new ArrayList<>();
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager
				.getConnection(dbPath);
		PreparedStatement pstmt = connection
				.prepareStatement("Select * from user where user_name like ? and password like ?;");
		pstmt.setString(1, name);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			userData.add(Integer.toString(rs.getInt(1)));
			userData.add(rs.getString(2));
			userData.add(Integer.toString(rs.getInt(4)));
		} 
		return userData;
	}
	
	public int register(String username, String password) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");

		Connection connection = DriverManager.getConnection(dbPath);
		
		PreparedStatement pstmt = connection
				.prepareStatement("INSERT INTO user (user_name, password, privilege) VALUES (?, ?, ?)");
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		pstmt.setInt(3, 7);

		int ctrl = pstmt.executeUpdate();
		return ctrl;
	}
}
