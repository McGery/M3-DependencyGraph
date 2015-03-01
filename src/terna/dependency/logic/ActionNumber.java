package terna.dependency.logic;

import java.util.*;

public class ActionNumber extends GraphNode {

	private String description;
	private List<M3Object> m3Objects;
	
	public ActionNumber (String name, String component, String makStatus, String description) {
		super (name, component, makStatus);
		this.description = description;
		m3Objects = new ArrayList<M3Object>();
	}

	public String getDescription() {
		return description;
	}

	public List<M3Object> getM3Objects() {
		return m3Objects;
	}

	public String toString() {
		return String.format("%s %s", name, component);
	}
}
