package terna.dependency.load.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import terna.dependency.load.ILoader;
import terna.dependency.load.InputObject;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CsvLoader implements ILoader {

	private CSVReader csvReader;

	public List<InputObject> load(String path) {
		char cvsSplitBy = ';';
		List<InputObject> inputObjects = new ArrayList<InputObject>();
		try {
			csvReader = new CSVReader(new FileReader(new File(path)), cvsSplitBy);
			HeaderColumnNameTranslateMappingStrategy<InputObject> strategy = new HeaderColumnNameTranslateMappingStrategy<InputObject>();
			strategy.setColumnMapping(generateColumnMapping());
			strategy.setType(InputObject.class);
			CsvToBean<InputObject> csvToBean = new CsvToBean<InputObject>();
			inputObjects = csvToBean.parse(strategy, csvReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputObjects;
	}

	private Hashtable<String, String> generateColumnMapping() {
		Hashtable<String, String> columnMapping = new Hashtable<String, String>();
		columnMapping.put("A/N", "actionNumber");
		columnMapping.put("Location", "location");
		columnMapping.put("Cmp", "component");
		columnMapping.put("MAK Status", "makStatus");
		columnMapping.put("User", "user");
		columnMapping.put("Description", "description");
		columnMapping.put("Name", "programName");
		columnMapping.put("Path", "typeDefinition");
		return columnMapping;
	}


}
