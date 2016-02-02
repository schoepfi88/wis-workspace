package db;

public class DatabasePath {
	private String balthiPath = "jdbc:sqlite:/Users/balthazur/Programming/workspace/wis-workspace/FinanzManager/src/db/finanz.db";
						
	private String chriPath = "jdbc:sqlite:/home/csl/workspace/FinanzManager/src/db/finanz.db";
	private String relPath = "jdbc:sqlite:finanz.db";//worked noch nicht bei mir

	public String getPath(){
		return balthiPath;
	}
}
