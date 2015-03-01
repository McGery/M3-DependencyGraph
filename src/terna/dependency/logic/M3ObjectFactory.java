package terna.dependency.logic;

import terna.dependency.load.InputObject;

public class M3ObjectFactory {

	public final String TYPE_PROGRAM = "mvx/app/pgm";
	public final String TYPE_VIEW = "viewDef";
	public final String TYPE_DATASTRUCTURE = "mvx/app/ds";
	public final String TYPE_LANGUAGE = "language";
	public final String TYPE_DBINTERFACE_HFIX = "HFix";
	public final String TYPE_DBINTERFACE_VFIX = "VFix";
	public final String TYPE_DBINTERFACE_TFIX = "TFix";
	public final String TYPE_OUT = "mvx/out/obj";

	public M3Object generate(InputObject inputObject) {
		String type = inputObject.getTypeDefinition();
		String name = inputObject.getProgramName();
		String component = inputObject.getComponent();
		String makStatus = inputObject.getMakStatus();
		
		if (type.startsWith(TYPE_PROGRAM)) {
			return generateProgram(name, component, makStatus);
		} else if (type.equals(TYPE_VIEW)) {
			return generateView(name, component, makStatus);
		} else if (type.equals(TYPE_DATASTRUCTURE)) {
			return generateDataStructure(name, component, makStatus);
		} else if (type.equals(TYPE_LANGUAGE)) {
			return generateLanguage(name, component, makStatus);
		} else if (type.startsWith(TYPE_DBINTERFACE_VFIX) || type.startsWith(TYPE_DBINTERFACE_HFIX) || type.startsWith(TYPE_DBINTERFACE_TFIX)) {
			return generateDbInterface(name, component, makStatus);
		} else if (type.equals(TYPE_OUT)) {
			return generateOut(name, component, makStatus);
		} else {
			return generateUndefined(name, component, makStatus);
		}
	}

	private M3Object generateUndefined(String name, String component, String makStatus) {
		return new M3Object(name, component, makStatus, M3ObjectType.Undefined);
	}

	private M3Object generateOut(String name, String component, String makStatus) {
		return new M3Object(name + "OUT", component, makStatus, M3ObjectType.Out);
	}

	private M3Object generateDbInterface(String name, String component, String makStatus) {
		return new M3Object("d"+name, component, makStatus, M3ObjectType.DbInterface);
	}

	private M3Object generateLanguage(String name, String component, String makStatus) {
		return new M3Object(name, component, makStatus, M3ObjectType.Language);
	}

	private M3Object generateDataStructure(String name, String component, String makStatus) {
		return new M3Object(name, component, makStatus, M3ObjectType.Datastructure);
	}

	private M3Object generateView(String name, String component, String makStatus) {
		return new M3Object(name+"DSP", component, makStatus, M3ObjectType.View);
	}

	private M3Object generateProgram(String name, String component, String makStatus) {
		return new M3Object(name, component, makStatus, M3ObjectType.Program);
	}
}
