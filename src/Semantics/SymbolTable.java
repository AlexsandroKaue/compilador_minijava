package Semantics;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

	public Map<String, Class> classes;

	public SymbolTable() {
        classes = new HashMap<String, Class>();
    }

	public boolean addClass(String id, String parent) {
		if (classes.containsKey(id)) return false;
		classes.put(id, new Class(id, parent));
		return true;
	}
}
