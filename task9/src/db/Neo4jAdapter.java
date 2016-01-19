package db;

import static db.GraphUtil.registerShutdownHook;

import java.util.Date;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import labels.Location;
import labels.Person;
import labels.Work;
import models.User;
import relationships.EmploymentRelationship;
import relationships.Live;

public class Neo4jAdapter {
	private static String DB_PATH = "workspace/neo4j-community-3.0.0-M02/data/graph.db";
//	private static String DB_PATH = "/Users/balthazur/Programming/workspace/wis-workspace/neo4j-community-3.0.0-M02/data/graph.db";
	GraphDatabaseService db;
	GraphDatabaseFactory dbFac;
	private static Neo4jAdapter instance = null;
	
	@SuppressWarnings("deprecation")
	private Neo4jAdapter(){
		dbFac = new GraphDatabaseFactory();
	}
	
	public static Neo4jAdapter getInstance(){
		if (instance == null){
			instance = new Neo4jAdapter();
		}
		return instance;
	}
	
	@SuppressWarnings("deprecation")
	public void createPerson(User u){
		Transaction tx = db.beginTx();
		try {
			Node person = db.createNode(Person.PERSON);
			person.setProperty("id", u.getId());
			person.setProperty("firstName", u.getFirstName());
			person.setProperty("lastName", u.getLastName());
			person.setProperty("gender", u.getGender());
			
			Node location = db.createNode(Location.LOCATION);
			location.setProperty("country", u.getCountry());
			location.setProperty("zip", u.getZip());
			location.setProperty("street", u.getStreet());
			
			Relationship relationship = person.createRelationshipTo
			(location,Live.LIVES_IN);
			relationship.setProperty("address", u.getCountry()+ ", " + u.getStreet());
			relationship.setProperty("since", new Date().toString());
			
			Relationship relationship2 = location.createRelationshipTo
			(person,Live.BELONG_TO);
			relationship2.setProperty("address", u.getCountry()+ ", " + u.getStreet());
			relationship2.setProperty("since", new Date().toString());
			
			Node job = db.createNode(Work.WORK);
			job.setProperty("id", u.getId());
			job.setProperty("name", u.getEmployment());
			
			Relationship relationship3 = person.createRelationshipTo
			(job,EmploymentRelationship.WORKS_AS);
			relationship.setProperty("id", u.getEmployment()+u.getId());
			relationship.setProperty("since", new Date().toString());
			tx.success();
		} finally {
			tx.finish();
		}
	}
	
	public void close(){
		db.shutdown();
	}
	
	public void open(){
		db = dbFac.newEmbeddedDatabase(DB_PATH);
		registerShutdownHook(db);
	}
	
	
	public static void main(String[] args) {
		User c = new User(0, "Chri", "Schöpf", "male", 24, "Austria", "Student", "4444", "weg 4");
		User b = new User(1, "Bal", "Huber", "male", 24, "Austria", "Student", "3333", "weg 3");
		Neo4jAdapter neo = Neo4jAdapter.getInstance();
		neo.createPerson(b);
		neo.createPerson(c);
	}

}
