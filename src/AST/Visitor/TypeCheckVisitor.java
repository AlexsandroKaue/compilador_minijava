package AST.Visitor;

import sun.security.util.Length;

import com.sun.org.apache.xpath.internal.Expression;

import Semantics.Method;
import Semantics.SymbolTable;
import Semantics.Class;
import Semantics.Variable;
import AST.*;
import AST.Visitor.Visitor;


public class TypeCheckVisitor implements Visitor{
	private SymbolTable table;
	private Class lastClass;
	private Method lastMethod;

	public TypeCheckVisitor() {
		this.table = new SymbolTable();
	}

	public void visit(Return n) {
		n.e.accept(this);
	}

	public void visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
	}

	public void visit(MainClass n) {
		if (!this.table.addClass(n.i1.s, null))
			throw new IllegalArgumentException("Class " + n.i1.s + " already exists");
		this.lastClass = this.table.classes.get(n.i1.s);
		n.i1.accept(this);
		n.i2.accept(this);
		n.s.accept(this);
		this.lastClass = null;
	}

	public void visit(ClassDeclSimple n) {
		if (!this.table.addClass(n.i.s, null))
			throw new IllegalArgumentException("Class " + n.i.s + " already exists");
		this.lastClass = this.table.classes.get(n.i.s);
		n.i.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			if (n.ml != null)
				n.ml.elementAt(i).accept(this);
		}
		this.lastClass = null;
		
	}

	public void visit(ClassDeclExtends n) {
		if (!this.table.addClass(n.i.s, n.j.s))
			throw new IllegalArgumentException("Class " + n.i.s + " already exists");
		this.lastClass = this.table.classes.get(n.i.s);
		n.i.accept(this);
		n.j.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		this.lastClass = null;
		
	}

	public void visit(VarDecl n) {
		
		if(n.t instanceof IdentifierType)
			if( this.table.classes.get( ((IdentifierType)n.t ).s ) == null )
				throw new IllegalArgumentException("Type " + ((IdentifierType)n.t ).s + " not declared");
		
		if(this.lastMethod == null){
			if(!this.lastClass.addVar(n.i.s, n.t)){
				throw new IllegalArgumentException("Variable " + n.i.s + " already exists");
			}
		}else if(!this.lastMethod.addVar(n.i.s, n.t))
			throw new IllegalArgumentException("Variable " + n.i.s + " already exists");
		n.t.accept(this);
		n.i.accept(this);
	}

	public void visit(MethodDecl n) {
		if(!this.lastClass.addMethod(n.i.s, n.t))
			throw new IllegalArgumentException("Method " + n.i.s + " already exists");
		this.lastMethod = this.lastClass.methods.get(n.i.s);
		for(int i = 0; i < n.fl.size(); i++) {
			this.lastMethod.params.add(new Variable(n.fl.elementAt(i).i.s, n.fl.elementAt(i).t));
		}
		
		n.t.accept(this);
		n.i.accept(this);
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		n.e.accept(this);
		this.lastMethod = null;
		
	}

	public void visit(Formal n) {
		n.t.accept(this);
		n.i.accept(this);
		
	}

	public void visit(IntArrayType n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(BooleanType n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(IntegerType n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(IdentifierType n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Block n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
	}

	public void visit(If n) {
		Variable identifier;
		
		if( n.e instanceof IdentifierExp){
			if((identifier = this.lastClass.globals.get( ((IdentifierExp)n.e).s )) == null)
				if((identifier = this.lastMethod.vars.get(((IdentifierExp)n.e).s)) == null)
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e).s + " is not declared");
			
			if( !(identifier.type instanceof BooleanType) )
				throw new IllegalArgumentException("Illegal type in if expression");
		}else if( !(n.e instanceof True ||
					n.e instanceof False ||
					n.e instanceof LessThan) ) 
				throw new IllegalArgumentException("Illegal type in if expression");
		
		n.e.accept(this);
		n.s1.accept(this);
		n.s2.accept(this);
	}

	public void visit(While n) {
		Variable identifier;
		
		if( n.e instanceof IdentifierExp){
			if((identifier = this.lastClass.globals.get( ((IdentifierExp)n.e).s )) == null)
				if((identifier = this.lastMethod.vars.get(((IdentifierExp)n.e).s)) == null)
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e).s + " is not declared");
			
			if( !(identifier.type instanceof BooleanType) )
				throw new IllegalArgumentException("Illegal type in while expression");
		}else if( !(n.e instanceof True ||
					n.e instanceof False ||
					n.e instanceof LessThan) ) 
				throw new IllegalArgumentException("Illegal type in while expression");
		
		n.e.accept(this);
		n.s.accept(this);
	}

	public void visit(Print n) {
		n.e.accept(this);
	}

	public void visit(Assign n) {
		Variable identifier;
		Variable expression;
		
		if((identifier = this.lastClass.globals.get(n.i.s)) == null)
			if((identifier = this.lastMethod.vars.get(n.i.s)) == null)
				throw new IllegalArgumentException("Variable " + n.i.s + " is not declared");
		
		if( n.e instanceof IdentifierExp){
			
			if((expression = this.lastClass.globals.get( ((IdentifierExp)n.e).s )) == null)
				if((expression = this.lastMethod.vars.get(((IdentifierExp)n.e).s)) == null)
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e).s + " is not declared");
			
			if(!identifier.type.getClass().equals(expression.type.getClass()))
				throw new IllegalArgumentException("Illegal type assign");
		}
		else if( identifier.type instanceof IntegerType ) {
			if( !(  n.e instanceof IntegerLiteral ||
				n.e instanceof Plus ||
				n.e instanceof Minus ||
				n.e instanceof Times ||
				n.e instanceof ArrayLength) )
				throw new IllegalArgumentException("Illegal type assign");
		}
		else if( identifier.type instanceof BooleanType ) {
			if( !( n.e instanceof And ||
					n.e instanceof LessThan) )
				throw new IllegalArgumentException("Illegal type assign");			
		}
		else if( identifier.type instanceof IdentifierType ) {
			if( !( n.e instanceof NewObject) )
				throw new IllegalArgumentException("Illegal type assign");
			if( !( ((IdentifierType)identifier.type).s.equals( ((NewObject)n.e).i.s )) )
				throw new IllegalArgumentException("Illegal type assign");
		}
		else if( identifier.type instanceof IntArrayType )
			if( !(n.e instanceof NewArray))
				throw new IllegalArgumentException("Illegal type assign");
		
		n.i.accept(this);
		n.e.accept(this);
		
	}

	public void visit(ArrayAssign n) {
		Variable identifier;
		Variable expression1;
		Variable expression2;
		
		if((identifier = this.lastClass.globals.get(n.i.s)) == null)
			if((identifier = this.lastMethod.vars.get(n.i.s)) == null)
				throw new IllegalArgumentException("Variable " + n.i.s + " is not declared");
		
		if( n.e1 instanceof IdentifierExp ) {
			if((expression1 = this.lastClass.globals.get( ((IdentifierExp)n.e1).s )) == null)
				if((expression1 = this.lastMethod.vars.get(((IdentifierExp)n.e1).s)) == null)
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e1).s + " is not declared");
			
			if(!expression1.type.getClass().equals(IntegerType.class))
				throw new IllegalArgumentException("Illegal type assign");
		}
		else if( !(n.e1 instanceof IntegerLiteral ||
			n.e1 instanceof Plus ||
			n.e1 instanceof Minus ||
			n.e1 instanceof Times ||
			n.e1 instanceof ArrayLength) )
				throw new IllegalArgumentException("Illegal type assign");
		
		if( n.e2 instanceof IdentifierExp ) {
			if((expression2 = this.lastClass.globals.get( ((IdentifierExp)n.e2).s )) == null)
				if((expression2 = this.lastMethod.vars.get(((IdentifierExp)n.e2).s)) == null)
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e2).s + " is not declared");
			
			if(!identifier.type.getClass().equals(expression2.type.getClass()))
				throw new IllegalArgumentException("Illegal type assign");
		}
		else if( !(n.e2 instanceof IntegerLiteral ||
			n.e2 instanceof Plus ||
			n.e2 instanceof Minus ||
			n.e2 instanceof Times ||
			n.e2 instanceof ArrayLength) )
				throw new IllegalArgumentException("Illegal type assign");
		
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(And n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(LessThan n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}
	
	public void visit(Plus n) {
		Variable variable;
		
		if( !(  n.e1 instanceof IntegerLiteral ||
				n.e1 instanceof IdentifierExp ||
				n.e1 instanceof Plus ||
				n.e1 instanceof Minus ||
				n.e1 instanceof Times ) )
			throw new IllegalArgumentException("Illegal sum between noninteger and integer types");
		
		if( n.e1 instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e1).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.e1).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e1).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal sum between noninteger and integer types");
		}
		
		if( !(  n.e2 instanceof IntegerLiteral ||
				n.e2 instanceof IdentifierExp ||
				n.e2 instanceof Plus ||
				n.e2 instanceof Minus ||
				n.e2 instanceof Times ) )
			throw new IllegalArgumentException("Illegal sum between noninteger and integer types");
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e2).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.e2).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e2).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal sum between noninteger and integer types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
		
	}

	public void visit(Minus n) {
Variable variable;
		
		if( !(  n.e1 instanceof IntegerLiteral ||
				n.e1 instanceof IdentifierExp ||
				n.e1 instanceof Plus ||
				n.e1 instanceof Minus ||
				n.e1 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e1 instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e1).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.e1).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e1).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( !(  n.e2 instanceof IntegerLiteral ||
				n.e2 instanceof IdentifierExp ||
				n.e2 instanceof Plus ||
				n.e2 instanceof Minus ||
				n.e2 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e2).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.e2).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e2).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
		
	}

	public void visit(Times n) {
Variable variable;
		
		if( !(  n.e1 instanceof IntegerLiteral ||
				n.e1 instanceof IdentifierExp ||
				n.e1 instanceof Plus ||
				n.e1 instanceof Minus ||
				n.e1 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e1 instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e1).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.e1).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e1).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( !(  n.e2 instanceof IntegerLiteral ||
				n.e2 instanceof IdentifierExp ||
				n.e2 instanceof Plus ||
				n.e2 instanceof Minus ||
				n.e2 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e2).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.e2).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e2).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
		
	}


	public void visit(ArrayLookup n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(ArrayLength n) {
		Variable variable;
		
		if( !(n.e instanceof IdentifierExp) )
			throw new IllegalArgumentException("Illegal operation for this type");
		
		if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e).s )) == null )
			if( ( variable = this.lastMethod.vars.get( ((IdentifierExp)n.e).s )) == null )
				throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e).s + " is not declared");
		
		if( !variable.type.getClass().equals(IntArrayType.class))
			throw new IllegalArgumentException("Illegal operation for this type");
		
		n.e.accept(this);
	}

	public void visit(Call n) {
		Variable variable;
		Class classe;
		Method method;
		
		if( !(n.e instanceof IdentifierExp) )
			throw new IllegalArgumentException("Illegal operation for this type");
		
		if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e).s ) ) == null )
			if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.e).s ) ) == null )
				throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e).s + " is not declared");
		
		if( (classe = this.table.classes.get( ((IdentifierType)variable.type).s )) == null )
			throw new IllegalArgumentException("Type " + ((IdentifierType)variable.type).s + " not declared");
			
		if( (method = classe.methods.get( n.i.s )) == null)
			throw new IllegalArgumentException("Method "+ n.i.s +" not declared in class " + ((IdentifierType)variable.type).s);
		
		if( !(method.params.size() == n.el.size()) ) {
			System.out.println(method.params.size());
			throw new IllegalArgumentException("Number of params invalid");
		}
		
		/*Continuar desse ponto*/
		for(int i = 0; i < method.params.size(); i++) {
			if( !(method.params.get(i).type.getClass().equals( ((IdentifierExp)n.el.elementAt(i)).s ) )){
				System.out.println(method.params.get(i).type.getClass());
				System.out.println((n.el.elementAt(i)));
				throw new IllegalArgumentException("Illegal arguments passed");}
		}
		
		n.e.accept(this);
	    n.i.accept(this);
	    for ( int i = 0; i < n.el.size(); i++ ) {
	        n.el.elementAt(i).accept(this);
	    }
	}

	public void visit(IntegerLiteral n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(True n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(False n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(IdentifierExp n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(This n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(NewArray n) {
		n.e.accept(this);
	}

	public void visit(NewObject n) {
		// TODO Auto-generated method stub
		
	}

	public void visit(Not n) {
		n.e.accept(this);
	}

	public void visit(Identifier n) {
		// TODO Auto-generated method stub
		
	}
	
}
