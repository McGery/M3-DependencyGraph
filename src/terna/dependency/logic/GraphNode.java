package terna.dependency.logic;

public abstract class GraphNode {

	private String id = "";
	protected String name = "";
	protected String component = "";
	protected String makStatus = "";
	
	public GraphNode(String name, String component, String makStatus) {
		this.name = name;
		this.component = component;
		this.id = name + component;
		this.makStatus = makStatus;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getComponent() {
		return component;
	}
	
	public String getMakStatus() {
		return makStatus;
	}
}
