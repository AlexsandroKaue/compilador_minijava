package Semantics;

import java.util.HashMap;
import java.util.Map;

import AST.Type;

public class Block {
	
	public Map<String, Variable> vars;
	
	public Block(){
    	this.vars = new HashMap<String, Variable>();
    }
    
    public boolean addVar(String id, Type type) {
        if (vars.containsKey(id)) return false;
        vars.put(id, new Variable(id, type));
        return true;
    }
}
