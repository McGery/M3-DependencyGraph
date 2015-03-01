package terna.dependency.ui.application;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListOutput extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DefaultListModel objectsModel;
	private static DefaultListModel actionsModel;
	
	public ListOutput() {
		createListView();
	}
	
	private void createListView() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBackground(Color.WHITE);
		
		objectsModel = new DefaultListModel();
		actionsModel = new DefaultListModel();
		
		JList objects = new JList(objectsModel);
		JList actions = new JList(actionsModel);
		
		JPanel objectView = new JPanel();
		objectView.setLayout(new BoxLayout(objectView, BoxLayout.Y_AXIS));
		objectView.setBackground(Color.WHITE);
		JLabel objectText = new JLabel("Objects:");
		objectView.add(objectText);
		JScrollPane objectScrollPane = new JScrollPane(objects);
		objectView.add(objectScrollPane);
		this.add(objectView);
		this.add(Box.createHorizontalStrut(20));
		
		JPanel actionView = new JPanel();
		actionView.setLayout(new BoxLayout(actionView, BoxLayout.Y_AXIS));
		actionView.setBackground(Color.WHITE);
		JLabel actionText = new JLabel("Actions:");
		actionView.add(actionText);
		JScrollPane actionScrollPane = new JScrollPane(actions);
		actionView.add(actionScrollPane);
		this.add(actionView);
	}
	
	public static DefaultListModel getObjectsModel() {
		return objectsModel;
	}

	public static DefaultListModel getActionsModel() {
		return actionsModel;
	}
}
