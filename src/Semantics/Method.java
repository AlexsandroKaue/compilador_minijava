package Semantics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AST.Type;

public class Method {
	public String name;
    public Type type;
    public List<Variable> params;
    public Map<String, Variable> vars;
    
    public Method(String name, Type type){
    	this.name = name;
    	this.type = type;
    	this.params = new ArrayList<Variable>();
    	this.vars = new HashMap<String, Variable>();
    }
    
    public boolean addVar(String id, Type type) {
        if (vars.containsKey(id)) return false;
        vars.put(id, new Variable(id, type));
        return true;
    }

}
