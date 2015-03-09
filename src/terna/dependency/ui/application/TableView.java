package terna.dependency.ui.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class TableView extends JPanel {

	private static final long serialVersionUID = 4604498861618486903L;
	private JTable table;
	private JCheckBox cbDev;
	private JCheckBox cbTest;
	private JCheckBox cbAppr;
	private JCheckBox cbExp;
	private MainView controller;
	
	public TableView(MainView controller) {
		super(new BorderLayout());
		setTableViewController(controller);
		initTopArea();
		
		DefaultTableModel m = new DefaultTableModel(new Object[]{"Action Number", "Status", "Component", "Dependencies"}, 0);
		table = new JTable(m);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setGridColor(Color.GRAY);
        table.getColumnModel().getColumn(3).setCellRenderer(new TextAreaRenderer());
        
        TableFilterHeader filterHeader = new TableFilterHeader(table, AutoChoices.ENABLED);
        filterHeader.setSelectionBackground(Color.WHITE);
        filterHeader.setSelectionForeground(Color.BLACK);
       
        setJTableColumnsWidth(table, 800, 15, 10, 10, 65);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Add the scroll pane to this panel.
        add(scrollPane, BorderLayout.CENTER);
	}
	
	private void initTopArea() {
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));

		// Development box
		cbDev = new JCheckBox("development");
		cbDev.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
                
            }
		});
		cbDev.setSelected(true);
		topPane.add(cbDev);
		
		// Test box
		cbTest = new JCheckBox("test");
		cbTest.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
                
            }
		});
		cbTest.setSelected(true);
		topPane.add(cbTest);
		
		// Approved box
		cbAppr = new JCheckBox("approved");
		cbAppr.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
                
            }
		});
		topPane.add(cbAppr);
		
		// Exported box
		cbExp = new JCheckBox("exported");
		cbExp.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
                
            }
		});
		topPane.add(cbExp);
		
		// Refresh button
		JButton button = new JButton("Refresh");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
                getTableViewController().refreshTableView();
            }
		});
		topPane.add(button);
		this.add(topPane, BorderLayout.PAGE_START);
	}

	public void setTableViewController(MainView controller) {
		this.controller = controller;
	}
	
	public MainView getTableViewController() {
		return this.controller;
	}
	
	private void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
	    double total = 0;
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	 
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int)
	                (tablePreferredWidth * (percentages[i] / total)));
	    }
	}

	public void addRow (String actionNumber, String status, String component, String dependencies) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(new Object[]{actionNumber, status, component, dependencies});
	}
	
	public void clear() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
	}
	
	private class TextAreaRenderer extends JTextArea implements TableCellRenderer {
		private static final long serialVersionUID = -641307675494925771L;

		public TextAreaRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object
		           value, boolean isSelected, boolean hasFocus, int row, int column) {
			setText((String)value);
		       setSize(table.getColumnModel().getColumn(column).getWidth(),
		               getPreferredSize().height);
		       if (table.getRowHeight(row) != getPreferredSize().height) {
		               table.setRowHeight(row, getPreferredSize().height);
		       }
		       
		       if (isSelected) {
		    	   setBackground(table.getSelectionBackground());
		    	   setForeground(table.getSelectionForeground());
		       } else {
		    	   setBackground(table.getBackground());
		    	   setForeground(table.getForeground());
		       }

		       return this;
		   }
	}
	
	public Boolean getDevelopmentSelected() {
		return cbDev.isSelected();
	}

	public Boolean getTestSelected() {
		return cbTest.isSelected();
	}

	public Boolean getApprovedSelected() {
		return cbAppr.isSelected();
	}

	public Boolean getExportedSelected() {
		return cbExp.isSelected();
	}
}
