package db;

import java.sql.*;
import java.util.ArrayList;
import javax.sql.RowSet;
import models.Category;
import models.Comment;
import models.Item;
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

	public void createItem(Item item) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		try {
			boolean initialize = SQLiteJDBCLoader.initialize();
			SQLiteDataSource dataSource = new SQLiteDataSource();
			dataSource.setUrl(dbPath);
			Connection c = dataSource.getConnection();
			c.setAutoCommit(false);
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

	}

	public int deleteItem(int id) throws ClassNotFoundException {
		System.out.println("im ind delte 1240492059090");
		System.out.println("id: "+id);
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
	
	public void createCategory(Category cat) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void createComment(Comment comment) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
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
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM comment;");
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
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return comments;
	}

	
}
