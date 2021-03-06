package AST.Visitor;

import AST.*;
import Semantics.ClassSymbolTable;
import Throwables.SemanticsException;
/*
 * Etapa responsavel por construir a tabela de simbolos que guarda 
 * as classes decladaras na entrada
 * 
 */
public class BuildGlobalSymbolTable implements Visitor{
	
	private ClassSymbolTable classSymbolTable;
	
	public BuildGlobalSymbolTable(ClassSymbolTable classSymbolTable) {
		this.classSymbolTable = classSymbolTable;
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
		  if (!this.classSymbolTable.addClass(n.getClassName(), null))
			  throw new SemanticsException("Classe j� declarada: "+ n.getClassName(), n.i1.line_number);
		  n.i1.accept(this);
		  n.i2.accept(this);
		  n.s.accept(this);
	  }

	  // Identifier i;
	  // VarDeclList vl;
	  // MethodDeclList ml;
	  public void visit(ClassDeclSimple n) {
			if (!this.classSymbolTable.addClass(n.getClassName(), null))
				throw new SemanticsException("Classe j� declarada: "+ n.i.s, n.i.line_number);
			
			n.i.accept(this);
			for ( int i = 0; i < n.vl.size(); i++ ) {
				n.vl.elementAt(i).accept(this);
			}
			for ( int i = 0; i < n.ml.size(); i++ ) {
				n.ml.elementAt(i).accept(this);
			}
	  }
	 
	  // Identifier i;
	  // Identifier j;
	  // VarDeclList vl;
	  // MethodDeclList ml;
	  public void visit(ClassDeclExtends n) {
		if (!this.classSymbolTable.addClass(n.getClassName(), n.getParentClassName()))
			throw new SemanticsException("Classe j� declarada: "+n.i.s, n.i.line_number);
		
	    n.i.accept(this);
	    n.j.accept(this);
	    for ( int i = 0; i < n.vl.size(); i++ ) {
	        n.vl.elementAt(i).accept(this);
	    }
	    for ( int i = 0; i < n.ml.size(); i++ ) {
	        n.ml.elementAt(i).accept(this);
	    }
	    
	  }

	  // Type t;
	  // Identifier i;
	  public void visit(VarDecl n) {
		  n.t.accept(this);
		  n.i.accept(this);
	  }

	  // Type t;
	  // Identifier i;
	  // FormalList fl;
	  // VarDeclList vl;
	  // StatementList sl;
	  // Exp e;
	  public void visit(MethodDecl n) {
		  
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
