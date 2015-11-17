package db;

public class DatabasePath {
	private String balthiPath = "jdbc:sqlite:/Users/balthazur/Programming/workspace_mars/webshop/src/db/shop.db";
	private String chriPath = "jdbc:sqlite:/home/csl/workspace/webshop/src/db/shop.db";
	
	public String getPath(){
		return chriPath;
	}
}
