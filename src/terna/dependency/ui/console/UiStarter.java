package terna.dependency.ui.console;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import terna.dependency.load.ILoader;
import terna.dependency.load.InputObject;
import terna.dependency.load.csv.CsvLoader;
import terna.dependency.logic.GraphVisualizer;
import terna.dependency.logic.RawData;
import terna.dependency.ui.application.MainView;

public class UiStarter {

	private ILoader objectLoader;
	
	public UiStarter() {
		objectLoader = new CsvLoader();
	}
	
	public List<InputObject> loadInputObjects() {
		String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
		JFileChooser fileChooser = new JFileChooser(userDir);
		fileChooser.showOpenDialog(null);
		if (fileChooser.getSelectedFile() != null) {
			String csvPath = fileChooser.getSelectedFile().getPath();
			return objectLoader.load(csvPath);
		}
		return null;
	}

	public void drawGraph(List<InputObject> inputObjects) throws Exception {
		RawData rawData = new RawData(inputObjects);
		GraphVisualizer gVisualizer = new GraphVisualizer(rawData);
		MainView main = new MainView(gVisualizer);
		main.drawFrame();	
	}
}
