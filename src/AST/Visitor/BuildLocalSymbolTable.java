package AST.Visitor;

import AST.And;
import AST.ArrayAssign;
import AST.ArrayLength;
import AST.ArrayLookup;
import AST.Assign;
import AST.BinaryIntegerOperation;
import AST.Block;
import AST.BooleanType;
import AST.Call;
import AST.ClassDeclExtends;
import AST.ClassDeclSimple;
import AST.False;
import AST.Formal;
import AST.Identifier;
import AST.IdentifierExp;
import AST.IdentifierType;
import AST.If;
import AST.IntArrayType;
import AST.IntegerLiteral;
import AST.IntegerType;
import AST.LessThan;
import AST.MainClass;
import AST.MethodDecl;
import AST.Minus;
import AST.NewArray;
import AST.NewObject;
import AST.Not;
import AST.Plus;
import AST.Print;
import AST.Program;
import AST.Return;
import AST.This;
import AST.Times;
import AST.True;
import AST.VarDecl;
import AST.While;
import Semantics.ClassSymbolTable;
import Semantics.Classe;
import Semantics.Method;
import Semantics.Variable;

public class BuildLocalSymbolTable implements Visitor{

	private ClassSymbolTable table;
	private Classe lastClass;
	private Method lastMethod;

	public BuildLocalSymbolTable(ClassSymbolTable table) {
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
		
		if(this.lastMethod != null)
			if(!this.lastMethod.addVar(n.getVarName(), n.getVarType()))
				throw new IllegalArgumentException("Variable " + n.getVarName() + " already exists");
			
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
		n.e1.accept(this);
		n.e2.accept(this);
		
	}

	public void visit(Minus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		
	}

	public void visit(Times n) {
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
}
