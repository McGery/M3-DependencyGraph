package terna.dependency.logic;

import java.util.*;

public class M3Object extends GraphNode {
	
	private List<ActionNumber> actionNumbers;
	private M3ObjectType type;
	
	public M3Object(String name, String component, String makStatus, M3ObjectType type) {
		super (name, component, makStatus);
		actionNumbers = new ArrayList<ActionNumber>();
		this.type = type;		
	}

	public List<ActionNumber> getActionNumbers() {
		return actionNumbers;
	}

	public M3ObjectType getType() {
		return type;
	}
	
	public String toString() {
		return String.format("%s %s", name, component);
	}
}
