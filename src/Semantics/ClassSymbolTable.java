package Semantics;

import java.util.HashMap;
import java.util.Map;

public class ClassSymbolTable {

	public Map<String, Classe> classes;

	public ClassSymbolTable() {
        classes = new HashMap<String, Classe>();
    }

	public boolean addClass(String id, String parent) {
		if (classes.containsKey(id)) return false;
		classes.put(id, new Classe(id, parent));
		return true;
	}
}
