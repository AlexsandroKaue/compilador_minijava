package AST.Visitor;

import Semantics.Method;
import Semantics.SymbolTable;
import Semantics.Class;
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
		n.e.accept(this);
		n.s1.accept(this);
		n.s2.accept(this);
	}

	public void visit(While n) {
		n.e.accept(this);
		n.s.accept(this);
	}

	public void visit(Print n) {
		n.e.accept(this);
	}

	public void visit(Assign n) {
		if(this.lastMethod == null || !this.lastMethod.vars.containsKey(n.i.s))
			if(!this.lastClass.globals.containsKey(n.i.s))
				throw new IllegalArgumentException("Variable " + n.i.s + " is not declared");
		n.i.accept(this);
		n.e.accept(this);
		
	}

	public void visit(ArrayAssign n) {
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
		if( !(  n.e1 instanceof IntegerLiteral ||
				n.e1 instanceof IdentifierExp ||
				n.e1 instanceof Plus ||
				n.e1 instanceof Minus ||
				n.e1 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e1 instanceof IdentifierExp ) {
			if( !this.lastClass.globals.containsKey( ((IdentifierExp)n.e1).s )) {
				if( !this.lastMethod.vars.containsKey( ((IdentifierExp)n.e1).s ) )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e1).s + " is not declared");
				if( !this.lastMethod.vars.get(((IdentifierExp)n.e1).s).type.getClass().equals(IntegerType.class) )
					throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
			}
			else if( !this.lastClass.globals.get(((IdentifierExp)n.e1).s).type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( !(  n.e2 instanceof IntegerLiteral ||
				n.e2 instanceof IdentifierExp ||
				n.e2 instanceof Plus ||
				n.e2 instanceof Minus ||
				n.e2 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e2 instanceof IdentifierExp ) {
			if( !this.lastClass.globals.containsKey( ((IdentifierExp)n.e2).s )) {
				if( !this.lastMethod.vars.containsKey( ((IdentifierExp)n.e2).s ) )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e2).s + " is not declared");
				if( !this.lastMethod.vars.get(((IdentifierExp)n.e2).s).type.getClass().equals(IntegerType.class) )
					throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
			}
			else if( !this.lastClass.globals.get(((IdentifierExp)n.e2).s).type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
		
	}

	public void visit(Minus n) {
		if( !(  n.e1 instanceof IntegerLiteral ||
				n.e1 instanceof IdentifierExp ||
				n.e1 instanceof Plus ||
				n.e1 instanceof Minus ||
				n.e1 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e1 instanceof IdentifierExp ) {
			if( !this.lastClass.globals.containsKey( ((IdentifierExp)n.e1).s )) {
				if( !this.lastMethod.vars.containsKey( ((IdentifierExp)n.e1).s ) )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e1).s + " is not declared");
				if( !this.lastMethod.vars.get(((IdentifierExp)n.e1).s).type.getClass().equals(IntegerType.class) )
					throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
			}
			else if( !this.lastClass.globals.get(((IdentifierExp)n.e1).s).type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( !(  n.e2 instanceof IntegerLiteral ||
				n.e2 instanceof IdentifierExp ||
				n.e2 instanceof Plus ||
				n.e2 instanceof Minus ||
				n.e2 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e2 instanceof IdentifierExp ) {
			if( !this.lastClass.globals.containsKey( ((IdentifierExp)n.e2).s )) {
				if( !this.lastMethod.vars.containsKey( ((IdentifierExp)n.e2).s ) )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e2).s + " is not declared");
				if( !this.lastMethod.vars.get(((IdentifierExp)n.e2).s).type.getClass().equals(IntegerType.class) )
					throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
			}
			else if( !this.lastClass.globals.get(((IdentifierExp)n.e2).s).type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		n.e1.accept(this);
		n.e2.accept(this);
		
	}

	public void visit(Times n) {
		if( !(  n.e1 instanceof IntegerLiteral ||
				n.e1 instanceof IdentifierExp ||
				n.e1 instanceof Plus ||
				n.e1 instanceof Minus ||
				n.e1 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e1 instanceof IdentifierExp ) {
			if( !this.lastClass.globals.containsKey( ((IdentifierExp)n.e1).s )) {
				if( !this.lastMethod.vars.containsKey( ((IdentifierExp)n.e1).s ) )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e1).s + " is not declared");
				if( !this.lastMethod.vars.get(((IdentifierExp)n.e1).s).type.getClass().equals(IntegerType.class) )
					throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
			}
			else if( !this.lastClass.globals.get(((IdentifierExp)n.e1).s).type.getClass().equals(IntegerType.class) )
				throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		}
		
		if( !(  n.e2 instanceof IntegerLiteral ||
				n.e2 instanceof IdentifierExp ||
				n.e2 instanceof Plus ||
				n.e2 instanceof Minus ||
				n.e2 instanceof Times ) )
			throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
		
		if( n.e2 instanceof IdentifierExp ) {
			if( !this.lastClass.globals.containsKey( ((IdentifierExp)n.e2).s )) {
				if( !this.lastMethod.vars.containsKey( ((IdentifierExp)n.e2).s ) )
					throw new IllegalArgumentException("Variable " + ((IdentifierExp)n.e2).s + " is not declared");
				if( !this.lastMethod.vars.get(((IdentifierExp)n.e2).s).type.getClass().equals(IntegerType.class) )
					throw new IllegalArgumentException("Illegal operation between noninteger and integer types");
			}
			else if( !this.lastClass.globals.get(((IdentifierExp)n.e2).s).type.getClass().equals(IntegerType.class) )
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
		n.e.accept(this);
	}

	public void visit(Call n) {
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
