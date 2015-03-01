package terna.dependency.load;

import java.util.List;

public interface ILoader {
	public List<InputObject> load(String path);
}
