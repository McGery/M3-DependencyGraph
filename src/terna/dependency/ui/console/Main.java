package terna.dependency.ui.console;

import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.cli.ParseException;

import terna.dependency.load.InputObject;

public class Main {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		try {
			if (args.length > 0) {
				ConsoleStarter cs = new ConsoleStarter();
				List<InputObject> inputObjects = cs.parseParameters(args);
				if (inputObjects != null) {
					cs.drawGraph(inputObjects);
				}
			} else {
				UiStarter us = new UiStarter();
				List<InputObject> inputObjects = us.loadInputObjects();
				if (inputObjects != null) {
					us.drawGraph(inputObjects);
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			System.out.println(ex.getStackTrace());
		}
	}
}
