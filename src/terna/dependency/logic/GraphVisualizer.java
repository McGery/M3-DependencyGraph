package terna.dependency.logic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.tree.JGraphTreeLayout;

public class GraphVisualizer {

	private DependencyGraphBuilder dptBuilder;
	private RawData rawData;
	private List<M3Object> drawnM3Objects;

	private List<ActionNumber> drawnActionNumers;
	
	public GraphVisualizer(RawData rawData) {
		this.rawData = rawData;
		this.dptBuilder = new DependencyGraphBuilder(rawData);
		this.drawnM3Objects = new ArrayList<M3Object>();
		this.drawnActionNumers = new ArrayList<ActionNumber>();
	}

	public JGraph buildGraph(String query) throws Exception {
	    //DirectedGraph<M3Object, ActionNumber> root = dptBuilder.getM3ObjectDependencies("ARS115");
	    UndirectedGraph<Object, Object> root = dptBuilder.getAllDependencies(query);
	    
	    // create a visualization using JGraph, via the adapter
	    //JGraph jgraph = new JGraph( new JGraphModelAdapter<M3Object, ActionNumber>( root ) );
	    JGraph jgraph = new JGraph( new JGraphModelAdapter<Object, Object>( root ) );
	   
//	    final  JGraphHierarchicalLayout hir = new JGraphHierarchicalLayout();
//	    final JGraphFacade graphFacade = new JGraphFacade(jgraph);      
//	    hir.run(graphFacade);
//	    final Map nestedMap = graphFacade.createNestedMap(true, true);
//	    jgraph.getGraphLayoutCache().edit(nestedMap);
	    
//	    JGraphFacade facade = new JGraphFacade(jgraph); // Pass the facade the JGraph instance
//	    JGraphLayout layout = new JGraphFastOrganicLayout(); // Create an instance of the appropriate layout
//	    layout.run(facade); // Run the layout on the facade. Note that layouts do not implement the Runnable interface, to avoid confusion
//
//	     Map nested = facade.createNestedMap(true, true); // Obtain a mapof the resulting attribute changes from the facade
//
//	     jgraph.getGraphLayoutCache().edit(nested); 
	    
	    //Object roots = getRoots(); // replace getRoots with your own	    Object array of the cell tree roots. NOTE: these are the root cell(s) of the tree(s), not the roots of the graph model.

	    
	     JGraphFacade facade = new JGraphFacade(jgraph); // Pass thefacade the JGraph instance

	     JGraphTreeLayout layout = new JGraphTreeLayout(); // Create aninstance of the appropriate layout
//	     layout.setTreeDistance();
//	     layout.setLevelDistance(100);
//	     layout.setNodeDistance(150);
	     
	     removeEdgeLabels(jgraph);
	     setColors(jgraph, query); 

	     layout.run(facade); // Run the layout on the facade.

	     Map<?, ?> nested = facade.createNestedMap(true, true); // Obtain a mapof the resulting attribute changes from the facade
	     
	     jgraph.getGraphLayoutCache().edit(nested); // Apply the results tothe actual graph
	    
	     drawnActionNumers.clear();
	     drawnM3Objects.clear();
	     
	     for(Object obj : root.vertexSet()) {
	    	 if(obj instanceof M3Object) {
	    		 drawnM3Objects.add((M3Object) obj);
	    	 } else if(obj instanceof ActionNumber) {
	    		 drawnActionNumers.add((ActionNumber)obj);
	    	 }
	     }
	     
	     return jgraph;
	}
	
	public static void removeEdgeLabels(JGraph jgraph) {
		GraphLayoutCache cache = jgraph.getGraphLayoutCache();
		CellView[] cells = cache.getCellViews();
		for (CellView cell : cells) {
			if (cell instanceof EdgeView) {
				EdgeView ev = (EdgeView) cell;
				DefaultEdge eval = (DefaultEdge) ev.getCell();
				eval.setUserObject("");
			}
		}
		cache.reload();
		jgraph.repaint();
	}
	private final String ENVIRONMENT_TEST = "test";
	private final String ENVIRONMENT_APPROVED = "approved";
	private final String ENVIRONMENT_EXPORTED = "exported";
	
	public static final String COLOR_AN_TEST = "#0092ff";
	public static final String COLOR_OBJECT_TEST = "#df6500";
	
	private void setColors(JGraph jgraph, String query) {
		GraphLayoutCache cache = jgraph.getGraphLayoutCache();
		for (Object item : jgraph.getRoots()) {
			DefaultGraphCell cell = (DefaultGraphCell) item;
			
			CellView view1 = cache.getMapping(cell, true);
			AttributeMap map1 = view1.getAttributes();
			map1.applyValue(GraphConstants.AUTOSIZE, true);
			map1.applyValue(GraphConstants.EDITABLE, false);
			
			if (cell.getUserObject() instanceof ActionNumber) {
				CellView view = cache.getMapping(cell, true);
				AttributeMap map = view.getAttributes();
				ActionNumber currentAN = ((ActionNumber)cell.getUserObject());
				if (currentAN.getId().equals(query)) {
					map.applyValue(GraphConstants.BACKGROUND, Color.RED);
				} else if (currentAN.getMakStatus().equals(ENVIRONMENT_TEST)) {
					map.applyValue(GraphConstants.BACKGROUND, Color.decode(COLOR_AN_TEST));
				} else if (currentAN.getMakStatus().equals(ENVIRONMENT_APPROVED)) {
					map.applyValue(GraphConstants.BACKGROUND, Color.decode(COLOR_AN_TEST));
				} else if (currentAN.getMakStatus().equals(ENVIRONMENT_EXPORTED)) {
					map.applyValue(GraphConstants.BACKGROUND, Color.decode(COLOR_AN_TEST));
				} else {
					map.applyValue(GraphConstants.BACKGROUND, Color.BLUE);
				}
			} else if (cell.getUserObject() instanceof M3Object) {
				CellView view = cache.getMapping(cell, true);
				AttributeMap map = view.getAttributes();
				M3Object currentO = (M3Object)cell.getUserObject();
				if (currentO.getId().equals(query)) {
					map.applyValue(GraphConstants.BACKGROUND, Color.RED);
				} else if (currentO.getMakStatus().equals(ENVIRONMENT_TEST)) {
					map.applyValue(GraphConstants.BACKGROUND, Color.decode(COLOR_OBJECT_TEST));
				} else if (currentO.getMakStatus().equals(ENVIRONMENT_APPROVED)) {
					map.applyValue(GraphConstants.BACKGROUND, Color.decode(COLOR_OBJECT_TEST));
				} else if (currentO.getMakStatus().equals(ENVIRONMENT_EXPORTED)) {
					map.applyValue(GraphConstants.BACKGROUND, Color.decode(COLOR_OBJECT_TEST));
				}
				
			}
			
		}
		cache.reload();
		jgraph.repaint();
	}
	
	public Map<ActionNumber, List<ActionNumber>> getActionNumberDependencyGraphs() throws Exception {
		Map<ActionNumber, List<ActionNumber>> actionNumberGraphs = new HashMap<ActionNumber, List<ActionNumber>>();
		for (String action : rawData.getAllActionNumbers().keySet()) {
			ActionNumber baseAction = rawData.getAllActionNumbers().get(action);
			UndirectedGraph<Object, Object> root = dptBuilder.getAllDependencies(action);
			List<ActionNumber> dependentActions = new ArrayList<ActionNumber>();
			for(Object obj : root.vertexSet()) {
				if(obj instanceof ActionNumber) {
					ActionNumber subNumber = (ActionNumber)obj;
					if (subNumber.equals(baseAction))
						continue;
					dependentActions.add((ActionNumber)obj);
				}
			}
			actionNumberGraphs.put(baseAction, dependentActions);
		}
		return actionNumberGraphs;
	}
	
	public RawData getRawData() {
		return this.rawData;
	}
	
	public List<M3Object> getDrawnM3Objects() {
		return drawnM3Objects;
	}

	public List<ActionNumber> getDrawnActionNumers() {
		return drawnActionNumers;
	}
}
