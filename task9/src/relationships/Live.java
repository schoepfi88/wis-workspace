package relationships;

import org.neo4j.graphdb.RelationshipType;

public enum Live implements RelationshipType{
	LIVES_IN, BELONG_TO;
}
