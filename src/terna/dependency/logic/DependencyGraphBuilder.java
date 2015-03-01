package terna.dependency.logic;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.Pseudograph;

public class DependencyGraphBuilder {
	
	private RawData rawData;
	
	public DependencyGraphBuilder(RawData rawData) {
		this.rawData = rawData;
	}
	
//	public DirectedGraph<M3Object, ActionNumber> getM3ObjectDependencies (String name) {
//		DirectedGraph<M3Object, ActionNumber> hrefGraph = new DirectedPseudograph<M3Object, ActionNumber>(ActionNumber.class);
//		M3Object firstObject = allM3Objects.get(name);
//		traverseAction(firstObject, hrefGraph);
//		return hrefGraph;
//	}
//
//	private void traverseAction(M3Object parentNode, DirectedGraph<M3Object, ActionNumber> hrefGraph) {
//		if(hrefGraph.containsVertex(parentNode))
//			return;	
//		
//		hrefGraph.addVertex(parentNode);
//		for (ActionNumber action : parentNode.getActionNumbers()) {
//			for(M3Object object : action.getM3Objects()) {
//				traverseAction(object, hrefGraph);
//				if(!parentNode.equals(object)) {
//					hrefGraph.addEdge(parentNode, object, ActionNumber.copy(action));
//				}
//			}
//		}
//	}
	
	public UndirectedGraph<Object, Object> getAllDependencies (String name) throws Exception {
		UndirectedGraph<Object, Object> hrefGraph = new Pseudograph<Object, Object>(Object.class);
		Object firstObject = rawData.getAllM3Objects().get(name);
		if (firstObject == null)
			firstObject = rawData.getAllActionNumbers().get(name);
		if (firstObject == null) {
			System.err.println("A/N or M3Objekt " + name + " nicht vorhanden");
			throw new Exception("A/N or M3Objekt " + name + " nicht vorhanden");
		}
		if (firstObject != null)
		 traverseAction(firstObject, hrefGraph);
		
		return hrefGraph;
	}

	private void traverseAction(Object parentNode, UndirectedGraph<Object, Object> hrefGraph) {
		if(hrefGraph.containsVertex(parentNode))
			return;	
		
		hrefGraph.addVertex(parentNode);
		if(parentNode instanceof M3Object) {
			for (ActionNumber action : ((M3Object) parentNode).getActionNumbers()) {
				traverseAction(action, hrefGraph);
				hrefGraph.addEdge(parentNode, action);
			}
		} else if (parentNode instanceof ActionNumber) {
			for (M3Object object : ((ActionNumber) parentNode).getM3Objects()) {
				traverseAction(object, hrefGraph);
				hrefGraph.addEdge(parentNode, object);
			}
		} else {
			System.err.println("Fehlerhafter Knoten " + parentNode);
		}
		
	}
}
