package Semantics;

import java.util.HashMap;
import java.util.Map;

import AST.Identifier;
import AST.IdentifierType;
import AST.Type;

public class Classe {
	public String name;
    public Map<String, Method> methods;
    public Map<String, Variable> globals;
    public String parent;
    public Type type;
    
    public Classe(String name, String parent){
    	this.name = name;
    	this.parent = parent;
    	this.methods = new HashMap<String, Method>();
    	this.globals = new HashMap<String, Variable>();
    	this.type = new IdentifierType(this.name, 0);
    }
    
    public boolean addMethod(String name, Type type){
    	if(methods.containsKey(name)) return false;
    	methods.put(name, new Method(name, type));
    	return true;
    }
    
    public boolean addVar(String id, Type type) {
        if (globals.containsKey(id)) return false;
        globals.put(id, new Variable(id, type));
        return true;
    }
}
