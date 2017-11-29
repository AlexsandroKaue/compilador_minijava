package AST.Visitor;

import AST.*;
import Semantics.Classe;
import Semantics.Method;
import Semantics.ClassSymbolTable;
import Semantics.Variable;
import Throwables.SemanticsException;

public class BuildClassSymbolTable implements Visitor{
	private ClassSymbolTable tableClass;
	private Method lastMethod;
	private Classe lastClass;
	
	public BuildClassSymbolTable(ClassSymbolTable table) {
		this.tableClass = table;
	}


	// Return added for the toy example language---they are subsumed in the MiniJava AST by the MethodDecl nodes.
	  // Exp e;
	  public void visit(Return n) {
	    n.e.accept(this); 
	  }
	  
	  
	  // MainClass m;
	  // ClassDeclList cl;
	  public void visit(Program n) {
	    n.m.accept(this);
	    for ( int i = 0; i < n.cl.size(); i++ ) {
	        n.cl.elementAt(i).accept(this);
	    }
	  }
	  
	  // Identifier i1,i2;
	  // Statement s;
	  public void visit(MainClass n) {
		  this.lastClass = this.tableClass.classes.get(n.getClassName());
		  n.i1.accept(this);
		  n.i2.accept(this);
		  n.s.accept(this);
		  this.lastClass = null;
	  }

	  // Identifier i;
	  // VarDeclList vl;
	  // MethodDeclList ml;
	  public void visit(ClassDeclSimple n) {
			this.lastClass = this.tableClass.classes.get(n.getClassName());
			n.i.accept(this);
			for ( int i = 0; i < n.vl.size(); i++ ) {
				n.vl.elementAt(i).accept(this);
			}
			for ( int i = 0; i < n.ml.size(); i++ ) {
				n.ml.elementAt(i).accept(this);
			}
			this.lastClass = null;
	  }
	 
	  // Identifier i;
	  // Identifier j;
	  // VarDeclList vl;
	  // MethodDeclList ml;
	  public void visit(ClassDeclExtends n) {
		this.lastClass = this.tableClass.classes.get(n.getClassName());
		//this.lastClass.globals.putAll(this.tableClass.classes.get(this.lastClass.parent).globals);
		//this.lastClass.methods.putAll(this.tableClass.classes.get(this.lastClass.parent).methods);
	    n.i.accept(this);
	    n.j.accept(this);
	    for ( int i = 0; i < n.vl.size(); i++ ) {
	        n.vl.elementAt(i).accept(this);
	    }
	    for ( int i = 0; i < n.ml.size(); i++ ) {
	        n.ml.elementAt(i).accept(this);
	    }
	    this.lastClass = null;
	  }

	  // Type t;
	  // Identifier i;
	  public void visit(VarDecl n) {
		  n.t.accept(this);
		  n.i.accept(this);
		  
		  if(this.lastMethod == null) {
			  	if(n.t instanceof IdentifierType)
			  		if( tableClass.classes.get( ((IdentifierType)n.t).s) == null )
			  			throw new SemanticsException("Classe n�o declarada: "+ ((IdentifierType)n.t).s, n.t.line_number);
			  	
				if(!this.lastClass.addVar(n.i.s, n.t))
					throw new SemanticsException("Vari�vel j� existe: "+ ((IdentifierType)n.t).s, n.t.line_number);
		  }	
		  
	  }

	  // Type t;
	  // Identifier i;
	  // FormalList fl;
	  // VarDeclList vl;
	  // StatementList sl;
	  // Exp e;
	  public void visit(MethodDecl n) {
		  	if(!this.lastClass.addMethod(n.getMethodName(), n.getMethodType()))
		  		throw new SemanticsException("M�todo j� existe: "+n.i.s, n.i.line_number);
			this.lastMethod = this.lastClass.methods.get(n.getMethodName());
			
			for(int i = 0; i < n.fl.size(); i++) {
				this.lastMethod.params.add(new Variable(n.fl.elementAt(i).i.s, n.fl.elementAt(i).t));
			}
			
			n.t.accept(this);
		    n.i.accept(this);
		    for ( int i = 0; i < n.fl.size(); i++ ) {
		        n.fl.elementAt(i).accept(this);
		    }
		    for ( int i = 0; i < n.vl.size(); i++ ) {
		        n.vl.elementAt(i).accept(this);
		    }
		    for ( int i = 0; i < n.sl.size(); i++ ) {
		        n.sl.elementAt(i).accept(this);
		    }
		    n.e.accept(this);
		    this.lastMethod = null;
	  }

	  // Type t;
	  // Identifier i;
	  public void visit(Formal n) {
	    n.t.accept(this);
	    n.i.accept(this);
	  }

	  public void visit(IntArrayType n) {
	  }

	  public void visit(BooleanType n) {
	  }

	  public void visit(IntegerType n) {
	  }

	  // String s;
	  public void visit(IdentifierType n) {
	  }

	  // StatementList sl;
	  public void visit(Block n) {
	    for ( int i = 0; i < n.sl.size(); i++ ) {
	        n.sl.elementAt(i).accept(this);
	    }
	  }

	  // Exp e;
	  // Statement s1,s2;
	  public void visit(If n) {
	    n.e.accept(this);
	    n.s1.accept(this);
	    n.s2.accept(this);
	  }

	  // Exp e;
	  // Statement s;
	  public void visit(While n) {
	    n.e.accept(this);
	    n.s.accept(this);
	  }

	  // Exp e;
	  public void visit(Print n) {
	    n.e.accept(this);
	  }
	  
	  // Identifier i;
	  // Exp e;
	  public void visit(Assign n) {
	    n.i.accept(this);
	    n.e.accept(this);
	  }

	  // Identifier i;
	  // Exp e1,e2;
	  public void visit(ArrayAssign n) {
	    n.i.accept(this);
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(And n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(LessThan n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(Plus n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(Minus n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(Times n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e1,e2;
	  public void visit(ArrayLookup n) {
	    n.e1.accept(this);
	    n.e2.accept(this);
	  }

	  // Exp e;
	  public void visit(ArrayLength n) {
	    n.e.accept(this);
	  }

	  // Exp e;
	  // Identifier i;
	  // ExpList el;
	  public void visit(Call n) {
	    n.e.accept(this);
	    n.i.accept(this);
	    for ( int i = 0; i < n.el.size(); i++ ) {
	        n.el.elementAt(i).accept(this);
	    }
	  }

	  // int i;
	  public void visit(IntegerLiteral n) {
	  }

	  public void visit(True n) {
	  }

	  public void visit(False n) {
	  }

	  // String s;
	  public void visit(IdentifierExp n) {
	  }

	  public void visit(This n) {
	  }

	  // Exp e;
	  public void visit(NewArray n) {
	    n.e.accept(this);
	  }

	  // Identifier i;
	  public void visit(NewObject n) {
	    System.out.print(n.i.s);
	  }

	  // Exp e;
	  public void visit(Not n) {
	    n.e.accept(this);
	  }

	  // String s;
	  public void visit(Identifier n) {
	  }
}
