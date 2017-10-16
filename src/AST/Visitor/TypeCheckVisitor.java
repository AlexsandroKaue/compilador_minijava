package AST.Visitor;


import Semantics.Method;
import Semantics.ClassSymbolTable;
import Semantics.Classe;
import Semantics.Variable;
import AST.*;
import AST.Visitor.Visitor;


public class TypeCheckVisitor implements Visitor{
	private ClassSymbolTable table;
	private Classe lastClass;
	private Method lastMethod;

	public TypeCheckVisitor(ClassSymbolTable table) {
		this.table = table;
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
		this.lastClass = this.table.classes.get(n.getClassName());
		n.i1.accept(this);
		n.i2.accept(this);
		n.s.accept(this);
		this.lastClass = null;
	}

	public void visit(ClassDeclSimple n) {
		this.lastClass = this.table.classes.get(n.getClassName());
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
		this.lastClass = this.table.classes.get(n.getClassName());
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
		n.t.accept(this);
		n.i.accept(this);
	}

	public void visit(MethodDecl n) {
		this.lastMethod = this.lastClass.methods.get(n.getMethodName());
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
		}else if( !(n.e.getExpType().getClass().equals(BooleanType.class)) ) 
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
		}else if( !(n.e.getExpType().getClass().equals(BooleanType.class)) ) 
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
			if( !(  n.e.getExpType().equals(IntegerType.class)) )
				throw new IllegalArgumentException("Illegal type assign");
		}
		else if( identifier.type instanceof BooleanType ) {
			if( !(n.e.getExpType().equals(BooleanType.class)) )
				throw new IllegalArgumentException("Illegal type assign");			
		}
		else if( identifier.type instanceof IdentifierType ) {
			if( !( n.e.getExpType().equals(IdentifierType.class)) )
				throw new IllegalArgumentException("Illegal type assign");
			if( !( ((IdentifierType)identifier.type).s.equals( ((NewObject)n.e).i.s )) )
				throw new IllegalArgumentException("Illegal type assign");
		}
		else if( identifier.type instanceof IntArrayType )
			if( !(n.e.getExpType().equals(IntArrayType.class)) )
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
		
		if( n.getExpression1() instanceof IdentifierExp ) {
			if((expression1 = this.lastClass.globals.get( ((IdentifierExp)n.e1).s )) == null)
				if((expression1 = this.lastMethod.vars.get(((IdentifierExp)n.e1).s)) == null)
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e1).s + " is not declared");
			
			if(!expression1.type.getClass().equals(IntegerType.class))
				throw new IllegalArgumentException("Illegal type assign");
		}
		else if( !(n.getExpression1().getExpType().equals(IntegerType.class)) )
				throw new IllegalArgumentException("Illegal type assign");
		
		if( n.getExpression2() instanceof IdentifierExp ) {
			if((expression2 = this.lastClass.globals.get( ((IdentifierExp)n.e2).s )) == null)
				if((expression2 = this.lastMethod.vars.get(((IdentifierExp)n.e2).s)) == null)
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e2).s + " is not declared");
			
			if(!identifier.type.getClass().equals(expression2.type.getClass()))
				throw new IllegalArgumentException("Illegal type assign");
		}
		else 
			if( !(n.getExpression2().getExpType().equals(IntegerType.class)) )
				throw new IllegalArgumentException("Illegal type assign");
		
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(And n) {
		Variable variable;
		
		if( !( n.getExpression1().getExpType().equals(BooleanType.class)) )
			throw new IllegalArgumentException("Illegal operation between non boolean and boolean types");
		
		if( !(  n.getExpression2().getExpType().equals(BooleanType.class)) )
			throw new IllegalArgumentException("Illegal operation between non boolean and boolean types");
		
		if( n.getExpression1() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression1()).s + " is not declared");
				
			if( !variable.type.getClass().equals(BooleanType.class) )
				throw new IllegalArgumentException("Illegal operation between non boolean and boolean types");
		}
		
		if( n.getExpression2() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression2()).s + " is not declared");
				
			if( !variable.type.getClass().equals(BooleanType.class) )
				throw new IllegalArgumentException("Illegal operation between non boolean and boolean types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
	}

	public void visit(LessThan n) {
		Variable variable;
		
		if( !( n.getExpression1().getExpType().equals(IntegerType.class)) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( !(  n.getExpression2().getExpType().equals(IntegerType.class)) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.getExpression1() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression1()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( n.getExpression2() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression2()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
	}
	
	public void visit(Plus n) {
		//binaryIntegerOperationCheck(n);
		Variable variable;
		
		if( !( n.getExpression1().getExpType().equals(IntegerType.class)) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( !(  n.getExpression2().getExpType().equals(IntegerType.class)) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.getExpression1() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression1()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( n.getExpression2() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression2()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
		
	}

	public void visit(Minus n) {
		Variable variable;
		
		if( !( n.getExpression1().getExpType().equals(IntegerType.class)) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( !(  n.getExpression2().getExpType().equals(IntegerType.class)) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.getExpression1() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression1()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( n.getExpression2() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression2()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
		
	}

	public void visit(Times n) {
		Variable variable;
		
		if( !( n.getExpression1().getExpType().equals(IntegerType.class)) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( !(  n.getExpression2().getExpType().equals(IntegerType.class)) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.getExpression1() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression1()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( n.getExpression2() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression2()).s + " is not declared");
				
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

	public void visit(Call  n) {
		Variable variable;
		Classe classe;
		Method method;
		
		/*if( !(n.e.getExpType().equals(IdentifierType.class) ||
			  n.e instanceof Call ||
			  n.e instanceof This) )
			throw new IllegalArgumentException("Illegal operation for this type");
		
		if( n.e instanceof IdentifierExp){
			if(this.lastMethod != null)
				variable = this.lastMethod.vars.get( ((IdentifierExp)n.e).s ); 
			else
				variable = this.lastClass.globals.get( ((IdentifierExp)n.e).s );
			
			if(variable == null)
				throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e).s + " is not declared");
			
			if( (classe = this.table.classes.get( ((IdentifierType)variable.type).s )) == null )
				throw new IllegalArgumentException("Type " + ((IdentifierType)variable.type).s + " not declared");
		}
		
		
		if( (method = classe.methods.get(n.i.s)) == null ) 
			throw new IllegalArgumentException("Method "+ n.i.s + " not declared");
		
		if( !(method.params.size() == n.el.size()) ) {
			System.out.println(method.params.size());
			throw new IllegalArgumentException("Number of params invalid");
		}
		
		for(int i = 0; i < method.params.size(); i++) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.el.elementAt(i)).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.el.elementAt(i)).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.el.elementAt(i)).s + " is not declared");
			
			if( !(method.params.get(i).type.getClass().equals( variable.type.getClass() )) )
				throw new IllegalArgumentException("Illegal arguments passed");
		}*/
		
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
		Variable variable;
		
		if( !( n.e.getExpType().equals(BooleanType.class)) )
			throw new IllegalArgumentException("Illegal operation with non boolean type");
		
		if( n.e instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.e).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.e).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e).s + " is not declared");
				
			if( !variable.type.getClass().equals(BooleanType.class) )
				throw new IllegalArgumentException("Illegal operation with non boolean type");
		}
		n.e.accept(this);
	}

	public void visit(Identifier n) {
		// TODO Auto-generated method stub
		
	}
	
	public void binaryIntegerOperationCheck(BinaryIntegerOperation n) {
		Variable variable;
		
		if( !(  n.getExpression1() instanceof BinaryIntegerOperation ||
				n.getExpression1() instanceof IntegerLiteral ||
				n.getExpression1() instanceof IdentifierExp ||
				n.getExpression1() instanceof ArrayLength) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.getExpression1() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression1()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression1()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( !(  n.getExpression2() instanceof BinaryIntegerOperation ||
				n.getExpression2() instanceof IntegerLiteral ||
				n.getExpression2() instanceof IdentifierExp ||
				n.getExpression2() instanceof ArrayLength) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.getExpression2() instanceof IdentifierExp ) {
			if( (variable = this.lastClass.globals.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
				if( (variable = this.lastMethod.vars.get( ((IdentifierExp)n.getExpression2()).s ) ) == null )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.getExpression2()).s + " is not declared");
				
			if( !variable.type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
	}
	
	private boolean isTypeEqual(Type t1, Type t2){
		return t1.getClass() == t2.getClass();
	}
}
