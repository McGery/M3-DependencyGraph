package terna.dependency.ui.console;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import terna.dependency.load.ILoader;
import terna.dependency.load.InputObject;
import terna.dependency.load.csv.CsvLoader;
import terna.dependency.logic.GraphVisualizer;
import terna.dependency.logic.RawData;
import terna.dependency.ui.application.MainView;
import terna.dependency.ui.application.ViewConsoleStartParameters;

public class ConsoleStarter {

	private String query = "";
	private String cmp = "";
	private String csvPath = "";
	private Options options;
	private CommandLineParser parser;
	private CommandLine cmd;
	private ILoader objectLoader;
	
	public ConsoleStarter() {
		options = new Options();
		options.addOption("csvPath", true, "path to MAK exproted CSV file");
		options.addOption("query", true, "Root M3 object name or A/N to query");
		options.addOption("cmp", true, "Lowest component to include in the graphs");
		parser = new GnuParser();
		objectLoader = new CsvLoader();
	}
	
	public List<InputObject> parseParameters(String[] args) throws ParseException {
		cmd = parser.parse(options, args);
		
		csvPath = cmd.getOptionValue("csvPath");
		query = cmd.getOptionValue("query");
		cmp = cmd.getOptionValue("cmp");
		return objectLoader.load(csvPath);
	}
	
	public void drawGraph(List<InputObject> inputObjects) throws Exception
	{
		RawData rawData = new RawData(inputObjects);
		GraphVisualizer gVisualizer = new GraphVisualizer(rawData);
		ViewConsoleStartParameters sp = new ViewConsoleStartParameters(gVisualizer, cmp, query);
		MainView main = new MainView(sp);
		main.drawFrame();
	}
}
