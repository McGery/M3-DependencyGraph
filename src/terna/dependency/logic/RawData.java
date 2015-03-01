package terna.dependency.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import terna.dependency.load.InputObject;

public class RawData {

	private List<InputObject>  rawData;
	private HashMap<String,M3Object> allM3Objects;
	private HashMap<String,ActionNumber> allActionNumbers;
	M3ObjectFactory objectFactory;
	ActionNumberFactory anFactory;
	
	public RawData(List<InputObject> inputObjects) {
		this.rawData = inputObjects;
		this.allM3Objects = new HashMap<String,M3Object>();
		this.allActionNumbers = new HashMap<String,ActionNumber>();
		this.objectFactory = new M3ObjectFactory();
		this.anFactory = new ActionNumberFactory();
		buildObjectsFromImput();
	}
	
	private void buildObjectsFromImput() {
		for (InputObject record : rawData) {
			M3Object m3Object = objectFactory.generate(record);
			ActionNumber actionNumber = anFactory.generate(record);
			
			if (m3Object.getType() == M3ObjectType.Language)
				continue;
			
			if(!allM3Objects.containsKey(m3Object.getId())) {
				allM3Objects.put(m3Object.getId(), m3Object);
			} else {
				m3Object = allM3Objects.get(m3Object.getId());
			}
			
			if(!allActionNumbers.containsKey(actionNumber.getId())) {
				allActionNumbers.put(actionNumber.getId(), actionNumber);
			} else {
				actionNumber = allActionNumbers.get(actionNumber.getId());
			}
			
			m3Object.getActionNumbers().add(actionNumber);
			actionNumber.getM3Objects().add(m3Object);
			
		}
	}
	
	public String[] listAll() {
		ArrayList<String> all = new ArrayList<String>();
		all.addAll(allM3Objects.keySet());
		all.addAll(allActionNumbers.keySet());
		Collections.sort(all);
		return all.toArray(new String[all.size()]);
	}
	
	public GraphNode findNode(String query) {
		if (allActionNumbers.containsKey(query)) {
			return allActionNumbers.get(query);
		} else {
			return allM3Objects.get(query);
		}
	}
	
	public HashMap<String,M3Object> getAllM3Objects() {
		return allM3Objects;
	}
	
	public HashMap<String, ActionNumber> getAllActionNumbers() {
		return allActionNumbers;
	}
}
