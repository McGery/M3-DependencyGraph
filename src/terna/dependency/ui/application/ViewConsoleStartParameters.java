package terna.dependency.ui.application;

import terna.dependency.logic.GraphVisualizer;

public class ViewConsoleStartParameters {
	private String component;
	private String query;
	private GraphVisualizer gVisualizer;
	
	public ViewConsoleStartParameters (GraphVisualizer gVisualizer, String component, String query) {
		this.component = component;
		this.query = query;
		this.gVisualizer = gVisualizer;
	}

	public String getComponent() {
		return component;
	}

	public String getQuery() {
		return query;
	}

	public GraphVisualizer  getInputObjects() {
		return gVisualizer;
	}
}
