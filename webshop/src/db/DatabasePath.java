package db;

public class DatabasePath {
	private String balthiPath = "jdbc:sqlite:/Users/balthazur/Programming/workspace/wis-workspace/webshop/src/db/shop.db";
	private String chriPath = "jdbc:sqlite:/home/csl/workspace/webshop/src/db/shop.db";
	private String relPath = "jdbc:sqlite:shop.db";//worked noch nicht bei mir

	public String getPath(){
		return balthiPath;
	}
}
