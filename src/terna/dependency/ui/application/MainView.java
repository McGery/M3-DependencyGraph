package terna.dependency.ui.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;
import org.jgraph.JGraph;

import terna.dependency.logic.ActionNumber;
import terna.dependency.logic.GraphVisualizer;
import terna.dependency.logic.M3Object;

public class MainView {
 
	private GraphVisualizer gVisualizer;
	static JComboBox combo;
	private List<String> components;
	static DefaultListModel objectsModel;
	static DefaultListModel actionsModel;
	private String cmp;
	private String query;
	private JFrame frame;
	final JPanel pan;
	private ListOutput listOutput;
	private TableView tableView;
	
	public MainView(GraphVisualizer gVisualizer, String cmp, String query) {
		this.gVisualizer = gVisualizer;
		this.cmp = cmp;
		this.query = query;
		this.components = getDistinctComponents(this.gVisualizer.getRawData().getAllActionNumbers());
		this.frame = new JFrame("M3 Dependecy");
		this.pan = new JPanel(new BorderLayout());
		this.listOutput = new ListOutput();
		this.tableView = new TableView(this);

	}
	
	public MainView(GraphVisualizer gVisualizer) {
		this(gVisualizer, "", "");
	}
	
	public MainView(ViewConsoleStartParameters sp) {
		this(sp.getInputObjects(), sp.getComponent(), sp.getQuery());
	}
	
	public void drawFrame() throws Exception {
		buildGraph(query, cmp);
		initTableView();
		
		pan.setBackground(Color.WHITE);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
	    
		JPanel entryPanel = new JPanel();
	    entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.LINE_AXIS));
	    entryPanel.add(Box.createHorizontalStrut(5));
	    entryPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    	    
	    entryPanel.add(new JLabel("Obj or A/N:"));
	    final JTextField textField = new JTextField(query);
	    Dimension d = new Dimension(200, 20);
	    textField.setPreferredSize(d);
	    entryPanel.add(textField);
	    
	    final JComboBox cmpSelection = new JComboBox(components.toArray());
	    entryPanel.add(cmpSelection);
	    cmpSelection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cmp = cmpSelection.getSelectedItem().toString();
				query = textField.getText();
				combo.setSelectedItem(query + cmp);
				
				buildGraph(query, cmp);
			}
		}); 
	    
	    JButton button = new JButton("Enter");
	    button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				query = textField.getText().trim();
				combo.setSelectedItem(query + cmp);
				buildGraph(query, cmp);
			}
		});
	    entryPanel.add(button);

	    entryPanel.add(Box.createHorizontalStrut(5));
	    entryPanel.add(new JSeparator(SwingConstants.VERTICAL));
	    entryPanel.add(Box.createHorizontalStrut(5));
	    
	    combo = new JComboBox(gVisualizer.getRawData().listAll());
	    entryPanel.add(combo);
	    combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String rawSelection = combo.getSelectedItem().toString();
				query = gVisualizer.getRawData().findNode(rawSelection).getName();
				cmp = gVisualizer.getRawData().findNode(rawSelection).getComponent();
				textField.setText(query);
				cmpSelection.setSelectedItem(cmp);
				buildGraph(query, cmp);
			}
		});  
	    
	    JPanel colorPanel = new JPanel();
	    colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
	    //colorPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	    
	    JPanel colorAN = new JPanel();
	    colorAN.setLayout(new BoxLayout(colorAN, BoxLayout.LINE_AXIS));
	    //colorAN.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    colorAN.add(Box.createHorizontalStrut(5));
	    colorAN.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    JPanel colorObject = new JPanel();
	    colorObject.setLayout(new BoxLayout(colorObject, BoxLayout.LINE_AXIS));
	    //colorObject.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    colorObject.add(Box.createHorizontalStrut(5));
	    colorObject.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    colorAN.add(new JLabel("AN Dev:"));
	    colorAN.add(drawColorRectangle("#0000FF"));
	    colorAN.add(Box.createHorizontalStrut(5));
	    colorAN.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    colorAN.add(new JLabel("AN Test:"));
	    colorAN.add(drawColorRectangle(GraphVisualizer.COLOR_AN_TEST));
	    colorAN.add(Box.createHorizontalStrut(5));
	    colorAN.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    colorAN.add(new JLabel("AN Approved:"));
	    colorAN.add(drawColorRectangle(GraphVisualizer.COLOR_AN_TEST));
	    colorAN.add(Box.createHorizontalStrut(5));
	    colorAN.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    colorAN.add(new JLabel("AN Exported:"));
	    colorAN.add(drawColorRectangle(GraphVisualizer.COLOR_AN_TEST));
	    colorAN.add(Box.createHorizontalStrut(5));
	    colorAN.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	   
	    colorObject.add(new JLabel("Object Dev:"));
	    colorObject.add(drawColorRectangle("#FFC800"));
	    colorObject.add(Box.createHorizontalStrut(5));
	    colorObject.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    colorObject.add(new JLabel("Object Test:"));
	    colorObject.add(drawColorRectangle(GraphVisualizer.COLOR_OBJECT_TEST));
	    colorObject.add(Box.createHorizontalStrut(5));
	    colorObject.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    colorObject.add(new JLabel("Object Approved:"));
	    colorObject.add(drawColorRectangle(GraphVisualizer.COLOR_OBJECT_TEST));
	    colorObject.add(Box.createHorizontalStrut(5));
	    colorObject.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    colorObject.add(new JLabel("Object Exported:"));
	    colorObject.add(drawColorRectangle(GraphVisualizer.COLOR_OBJECT_TEST));
	    colorObject.add(Box.createHorizontalStrut(5));
	    colorObject.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    colorPanel.add(colorAN);
	    colorPanel.add(colorObject);
	    topPanel.add(entryPanel);
	    topPanel.add(colorPanel);
	    
		JScrollPane scrollpane = new JScrollPane(pan);
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.add("Graph", scrollpane);
		tabPane.addTab("All Objects/ANs", this.listOutput);
		tabPane.addTab("Action Numbers", this.tableView);
		
	    frame.getContentPane().add(topPanel, BorderLayout.NORTH);
	    frame.getContentPane().add(tabPane, BorderLayout.CENTER);
	    frame.setSize(800, 600);
	    frame.setVisible(true); 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	private JPanel drawColorRectangle(String color) {
		Color c = Color.decode(color);
		JPanel colorPane = new JPanel();
		colorPane.setPreferredSize(new Dimension(20,20));
		colorPane.setMaximumSize(new Dimension(20,20));
		colorPane.setBackground(c);
		return colorPane;
	}
	
	private List<String> getDistinctComponents(
			HashMap<String, ActionNumber> actionNumbers) {
		List<String> components = new ArrayList<String>();
		for (ActionNumber number : actionNumbers.values()) {
			if (!components.contains(number.getComponent())) {
				components.add(number.getComponent());
			}
		}
		return components;
	}
	
	private void buildGraph(String query, String component) {
		pan.removeAll();
		if (query.trim().equals(""))
			return;
		
		JGraph g = null;
		try {
			g = gVisualizer.buildGraph(query+component);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
		// List output
		ListOutput.getActionsModel().clear();
		for (ActionNumber a : gVisualizer.getDrawnActionNumers()) {
			String output = String.format("%s, %s, %s", a.getName(), a.getComponent(), a.getMakStatus());
			ListOutput.getActionsModel().addElement(output);
		}
		ListOutput.getObjectsModel().clear();
		for (M3Object o : gVisualizer.getDrawnM3Objects()) {
			String output = String.format("%s, %s, %s", o.getName(), o.getComponent(), o.getMakStatus());
			ListOutput.getObjectsModel().addElement(output);
		}
		
		pan.add(g);
		pan.validate();
		pan.repaint();
	}
	
	public void refreshTableView(MakStatusFilter filter) {
		tableView.clear();

		try {
			Map<ActionNumber, List<ActionNumber>> actionNumberDependencyMap = gVisualizer.getActionNumberDependencyGraphs(filter);
			for (Map.Entry<ActionNumber, List<ActionNumber>> a : actionNumberDependencyMap.entrySet()) {
				ActionNumber baseAction = a.getKey();
				List<String> dependentActionsList = new ArrayList<String>();
				for (ActionNumber depAction : a.getValue()) {
					dependentActionsList.add(String.format("%s (%s)", depAction.getName(), depAction.getMakStatus()));
				}
				String dependencies = StringUtils.join(dependentActionsList, ", ");

				String baseActionName = baseAction.getName();
				String cmp = baseAction.getComponent();
				String status = baseAction.getMakStatus();

				tableView.addRow(baseActionName, status, cmp, dependencies);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void initTableView() {
		MakStatusFilter filter = new MakStatusFilter();
		filter.setShowDevelopmentNumbers(true);
		filter.setShowTestNumbers(true);
		filter.setShowApprovedNumbers(false);
		filter.setShowExportedNumbers(false);
		tableView.applyMakStatusFilter(filter);
		refreshTableView(filter);
	}
}
