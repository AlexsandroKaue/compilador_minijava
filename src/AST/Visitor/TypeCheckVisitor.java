package AST.Visitor;

import Semantics.Method;
import Semantics.ClassSymbolTable;
import Semantics.Classe;
import Semantics.Variable;
import Throwables.SemanticsException;

import java.util.ArrayList;
import java.util.List;

import AST.*;
import AST.Visitor.Visitor;


public class TypeCheckVisitor implements Visitor{
	private ClassSymbolTable table;
	private Classe lastClass;
	private Method lastMethod;
	private String errors;

	public TypeCheckVisitor(ClassSymbolTable table) {
		this.table = table;
		this.errors = new String();
	}

	public void visit(Return n) {
		n.e.accept(this);
	}

	public void visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		
		if(!errors.equals(""))
			throw new SemanticsException(errors);
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

	public void visit(VarDecl n)  {
		n.t.accept(this);
		n.i.accept(this);
		
		//Constroi a tabela de símbolos local
		if(this.lastMethod != null) {
			if(n.t instanceof IdentifierType)
		  		if( this.table.classes.get( ((IdentifierType)n.t).s) == null )
		  			throw new SemanticsException("Classe não declarada: "+ ((IdentifierType)n.t).s, n.line_number);
			
			if(!this.lastMethod.addVar(n.i.s, n.t)) {
				errors = errors.concat("linha: "+n.line_number+". Variável já declarada: " + n.i.s+"\n");
				//throw new SemanticsException("Variável já declarada: " + n.i.s, n.line_number);
			}
		}
	}

	public void visit(MethodDecl n) {
		Variable var;
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
		
		if( n.e instanceof IdentifierExp) {
			if((var = getVariavel( ((IdentifierExp)n.e).s )) == null) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: :  " +((IdentifierExp)n.e).s+"\n");
				return ;
				//throw new SemanticsException("Variável não declarada: :  " +((IdentifierExp)n.e).s, n.line_number);
			}
			((IdentifierExp)n.e).setType(var.type);
		}
		
		if(!isTypeEqual(this.lastMethod.type, n.e.getType())) {
			errors = errors.concat("linha: "+ n.e.line_number +". Tipo ilegal de retorno.\n");
			return ;
			//throw new SemanticsException("Tipo ilegal de retorno: ", n.e.line_number);
		}
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
		
		Variable identifier;
		
		if( n.e instanceof IdentifierExp) {
			if((identifier = getVariavel( ((IdentifierExp)n.e).s )) == null) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: :  " +((IdentifierExp)n.e).s+"\n");
				return ;//throw new SemanticsException("Variável não declarada: :  " +((IdentifierExp)n.e).s, n.line_number);
				//throw new SemanticsException("Variável " + ((IdentifierExp)n.e).s + " não foi declarada");
			}
			if( !(identifier.type instanceof BooleanType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo de condição ilegal para if.\n");
				return ;//throw new SemanticsException("Tipo de condição ilegal para if. ", n.line_number);
			}
			((IdentifierExp)n.e).setType(identifier.type);
		}
		
		if( !(n.e.getType() instanceof BooleanType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo de condição ilegal para if.\n");
			//throw new SemanticsException("Tipo de condição ilegal para if. ", n.line_number);
		
	}

	public void visit(While n) {
		n.e.accept(this);
		n.s.accept(this);
		
		Variable identifier;
		
		if( n.e instanceof IdentifierExp) {
			if((identifier = getVariavel( ((IdentifierExp)n.e).s )) == null) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: :  " +((IdentifierExp)n.e).s+"\n");
				return ;
				//throw new SemanticsException("Variável não declarada: :  "+ ((IdentifierExp)n.e).s, n.line_number);
			}
			if( !(identifier.type instanceof BooleanType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo de condição ilegal para while.\n");
				return ;
				//throw new SemanticsException("Tipo de condição ilegal para while. "+ n.toString() , n.line_number);
			}
			((IdentifierExp)n.e).setType(identifier.type);
		}
		
		if( !(n.e.getType() instanceof BooleanType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo de condição ilegal para while.\n");
			//throw new SemanticsException("Tipo de condição ilegal para while. ", n.line_number);
	}

	public void visit(Print n) {
		n.e.accept(this);
	}

	public void visit(Assign n) {
		n.i.accept(this);
		n.e.accept(this);
		
		Variable var;
		
		if(getVariavel(n.i.s) == null) {
			errors = errors.concat("linha: "+n.line_number+". Variável não declarada: :  "+ n.i.s+"\n");
			return ;//throw new SemanticsException("Variável não declarada: :  "+ n.i.s, n.line_number);
		}
		if(n.e instanceof IdentifierExp) {
			if( (var = getVariavel(((IdentifierExp)n.e).s)) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: :  "+ ((IdentifierExp)n.e).s+"\n");
				return ;//throw new SemanticsException("Variável não declarada: :  "+ ((IdentifierExp)n.e).s, n.line_number);
			}
			((IdentifierExp)n.e).setType(var.type);
		}
		
		if(!isTypeEqual(getVariavel(n.i.s).type, n.e.getType()))
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a atribuição.\n");
			//throw new SemanticsException("Tipo ilegal para a atribuição. ", n.line_number);
		
	}

	public void visit(ArrayAssign n) {
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
		
		Variable identifier;
		Variable var;
		
		if((identifier = getVariavel(n.i.s)) == null) {
			errors = errors.concat("linha: "+n.line_number+". Variável não declarada: :  "+ n.i.s+"\n");
			return ;//throw new SemanticsException("Variável não declarada: :  " + n.i.s, n.line_number);
		}
		if(!(identifier.type instanceof IntArrayType)) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para ser tratado como vetor.\n");
			return ;//throw new SemanticsException("Tipo ilegal para ser tratado como vetor. ", n.line_number);
		}
		if( n.e1 instanceof IdentifierExp ) {
			if((var = getVariavel( ((IdentifierExp)n.e1).s )) == null) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: :  "+ ((IdentifierExp)n.e1).s+"\n");
				return ;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e1).s, n.line_number);
			}
			if(!(var.type instanceof IntegerType)) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para o índice.\n");
				return ;//throw new SemanticsException("Tipo ilegal para o índice. ",n.line_number);
			}
			((IdentifierExp)n.e1).setType(var.type);
		}
		
		if( !(n.e1.getType() instanceof IntegerType) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para o índice.\n");
			return ;//throw new SemanticsException("Tipo ilegal para o índice. ",n.line_number);
		}
		if( n.e2 instanceof IdentifierExp ) {
			if((var = getVariavel( ((IdentifierExp)n.e2).s )) == null) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: :  "+ ((IdentifierExp)n.e2).s+"\n");
				return ;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e2).s, n.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a atribuição.\n");
				return ;//throw new SemanticsException("Tipo ilegal para a atribuição. ", n.line_number);
			}
			((IdentifierExp)n.e2).setType(var.type);
		} 
		
		if( !(n.e2.getType() instanceof IntegerType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a atribuição.\n");
			//throw new SemanticsException("Tipo ilegal para a atribuição. ", n.line_number);
		
	}

	public void visit(And n) {
		n.e1.accept(this);
		n.e2.accept(this);
		
		Variable var;
		
		if( n.e1 instanceof IdentifierExp ) {
			
			if( (var = getVariavel( ((IdentifierExp)n.e1).s )) == null) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e1).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: " + ((IdentifierExp)n.e1).s, n.line_number);
			}
			if( !(var.type instanceof BooleanType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação &&\n");
				return;//throw new SemanticsException("Tipo ilegal para a operação &&. ", n.line_number);
			}
			((IdentifierExp)n.e1).setType(var.type);
		}
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e2).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e2).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e2).s, n.line_number);
			}
			if( !(var.type instanceof BooleanType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação &&\n");
				return;//throw new SemanticsException("Tipo ilegal para a operação &&. ", n.line_number);
			}
			((IdentifierExp)n.e2).setType(var.type);
		}
		
		if( !( n.e1.getType() instanceof BooleanType) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação &&\n");
			return; //throw new SemanticsException("Tipo ilegal para a operação &&. ", n.line_number);
		}
		if( !(  n.e2.getType() instanceof BooleanType) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação &&\n");
			//throw new SemanticsException("Tipo ilegal para a operação &&. ", n.line_number);
		}
	}

	public void visit(LessThan n) {
		n.e1.accept(this);
		n.e2.accept(this);
		
		Variable var;
		
		if( n.e1 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e1).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e1).s+"\n");
				//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e1).s, n.line_number);
				return;
			}	
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação <\n");
				return;//throw new SemanticsException("Tipo ilegal para operação < ", n.line_number);
			}
			((IdentifierExp)n.e1).setType(var.type);
		}
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e2).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e2).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e2).s, n.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação <\n");
				return; //throw new SemanticsException("Tipo ilegal para operação <", n.line_number);
			}
			((IdentifierExp)n.e2).setType(var.type);
		}
		
		if( !( n.e1.getType() instanceof IntegerType) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação <\n");
			return; //throw new SemanticsException("Tipo ilegal para operação <", n.line_number);
		}
		if( !(  n.e2.getType() instanceof IntegerType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação <\n");
			//throw new SemanticsException("Tipo ilegal para operação <", n.line_number);
		
	}
	
	public void visit(Plus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		
		Variable var;
		
		if( n.e1 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e1).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e1).s+"\n");
				return;//throw new SemanticsException("Variable não declarada: " + ((IdentifierExp)n.e1).s, n.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação +\n");
				return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
			}
			((IdentifierExp)n.e1).setType(var.type);
		}
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e2).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e2).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e2).s, n.e2.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação +\n");
				return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
			}
			((IdentifierExp)n.e2).setType(var.type);
		}
		
		if( !( n.e1.getType() instanceof IntegerType) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação +\n");
			return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
		}
		if( !(  n.e2.getType() instanceof IntegerType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação +\n");
			//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
		
	}

	public void visit(Minus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		
		Variable var;
		
		if( n.e1 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e1).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e1).s+"\n");
				return;//throw new SemanticsException("Variable não declarada: " + ((IdentifierExp)n.e1).s, n.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação -\n");
				return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
			}
			((IdentifierExp)n.e1).setType(var.type);
		}
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e2).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e2).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e2).s, n.e2.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação -\n");
				return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
			}
			((IdentifierExp)n.e2).setType(var.type);
		}
		
		if( !( n.e1.getType() instanceof IntegerType) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação -\n");
			return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
		}
		if( !(  n.e2.getType() instanceof IntegerType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação -\n");
			//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
		
		
		
	}

	public void visit(Times n) {
		n.e1.accept(this);
		n.e2.accept(this);
		
		Variable var;
		
		if( n.e1 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e1).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e1).s+"\n");
				return;//throw new SemanticsException("Variable não declarada: " + ((IdentifierExp)n.e1).s, n.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação *\n");
				return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
			}
			((IdentifierExp)n.e1).setType(var.type);
		}
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e2).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e2).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e2).s, n.e2.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação *\n");
				return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
			}
			((IdentifierExp)n.e2).setType(var.type);
		}
		
		if( !( n.e1.getType() instanceof IntegerType) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação *\n");
			return;//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
		}
		if( !(  n.e2.getType() instanceof IntegerType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação *\n");
			//throw new SemanticsException("Tipo ilegal para operação + ", n.line_number);
		
		
		
	}

	public void visit(ArrayLookup n) {
		n.e1.accept(this);
		n.e2.accept(this);
		
		Variable var;
		if( n.e1 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e1).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e1).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e1).s, n.e1.line_number);
			}
			if( !(var.type instanceof IntArrayType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para ser tratado como vetor.\n");
				return;//throw new SemanticsException("Tipo ilegal para ser tratado como vetor. ", n.e1.line_number);
			}
			((IdentifierExp)n.e1).setType(var.type);
		}
		
		if( n.e2 instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e2).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e2).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e2).s, n.e2.line_number);
			}
			if( !(var.type instanceof IntegerType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para ser tratado como índice.\n");
				return;//throw new SemanticsException("Tipo ilegal para ser tratado como índice ", n.e2.line_number);
			}
			((IdentifierExp)n.e2).setType(var.type);
		}
		
		if( !( n.e1.getType() instanceof IntArrayType) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para ser tratado como vetor.\n");
			return;//throw new SemanticsException("Tipo ilegal para ser tratado como vetor. ", n.e1.line_number);
		}
		if( !( n.e2.getType() instanceof IntegerType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para ser tratado como índice.\n");
			//throw new SemanticsException("Tipo ilegal para ser tratado como índice ", n.e2.line_number);
	}

	public void visit(ArrayLength n) {
		n.e.accept(this);
		
		Variable var;
		
		if( !(n.e instanceof IdentifierExp) ) {
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação length\n");
			return;//throw new SemanticsException("Tipo ilegal para a operação length ", n.e.line_number);
		}
		if( (var = getVariavel( ((IdentifierExp)n.e).s )) == null ) {
			errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e).s+"\n");
			return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e).s, n.e.line_number);
		}
		if( !(var.type instanceof IntArrayType))
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para a operação length\n");
			//throw new SemanticsException("Tipo ilegal para a operação length ", n.e.line_number);
		
	}

	public void visit(Call  n) {
		n.e.accept(this);
	    n.i.accept(this);
	    for ( int i = 0; i < n.el.size(); i++ ) {
	        n.el.elementAt(i).accept(this);
	    }
	    
	    String classeName;
	    Classe classe;
	    Method metodo;
	    Variable var;
	    Type returnType;
	    
	    if(n.e instanceof IdentifierExp) {
	    	if(( var = getVariavel(((IdentifierExp)n.e).s )) == null) {
	    		errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  "+ ((IdentifierExp)n.e).s, n.e.line_number);
	    	}
	    	returnType = var.type;
	    } else {
	    	returnType = n.e.getType();
	    }
	    
	    if(!(returnType instanceof IdentifierType)) {
	    	errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para essa expressão\n");
	    	return;//throw new SemanticsException("Tipo ilegal para essa expressão. ", n.e.line_number);
	    }
	    
	    classeName = ((IdentifierType)returnType).s;
	    if((classe = this.table.classes.get(classeName)) == null) {
	    	errors = errors.concat("linha: "+n.line_number+". Classe não declarada: "+classeName+"\n");
	    	return;//throw new SemanticsException("Classe não declarada: "+classeName, n.e.line_number);
	    }
	    if((metodo = classe.methods.get(n.i.s)) == null) {
	    	if(classe.parent != null) {
	    		metodo = this.table.classes.get(classe.parent).methods.get(n.i.s);
	    	}
	    	if(metodo == null) {
	    		errors = errors.concat("linha: "+n.line_number+". Método não declarado: "+n.i.s+"\n");
	    		return;//throw new SemanticsException("Método não declarado: "+n.i.s, n.i.line_number);
	    	}
	    }
		
		if( metodo.params.size() != n.el.size()){
			errors = errors.concat("linha: "+n.line_number+". Diferente número de argumentos: "+metodo.name+"\n");
			return;//throw new SemanticsException("Diferente número de argumentos: "+metodo.name, n.el.line_number);
		}
		
		for(int i=0; i<metodo.params.size(); i++) {
			if(n.el.elementAt(i) instanceof IdentifierExp){
				if(( var = getVariavel(((IdentifierExp)n.el.elementAt(i)).s )) == null) {
					errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.el.elementAt(i)).s+"\n");
					return;//throw new SemanticsException("Variável não declarada: :  "+((IdentifierExp)n.el.elementAt(i)).s, n.el.elementAt(i).line_number);
				}
				((IdentifierExp)n.el.elementAt(i)).setType(var.type);
			}
			
			if( !isTypeEqual(metodo.params.get(i).type, n.el.elementAt(i).getType()) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para os parâmetros do método: "+metodo.name+"\n");
				//throw new SemanticsException("Tipo ilegal para os parâmetros do método: "+metodo.name, n.el.elementAt(i).line_number);
			}
		}
		
		n.setType(metodo.type);
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
		if(this.lastClass == null) {
			errors = errors.concat("linha: "+n.line_number+". Chamada de this fora de class\n");
			return;//throw new SemanticsException("Chamada de this fora de classe");
		}
		n.setType(this.lastClass.type);
	}

	public void visit(NewArray n) {
		n.e.accept(this);
		
	}

	public void visit(NewObject n) {
		// TODO Auto-generated method stub
		Classe classe;
		if( (classe = this.table.classes.get(n.i.s)) == null ) {
			errors = errors.concat("linha: "+n.line_number+". Classe não declarada:"+n.i.s+"\n");
			return;//throw new SemanticsException("Classe não declarada: "+n.i.s, n.i.line_number);
		}
		n.setType(classe.type);
		
	}

	public void visit(Not n) {
		n.e.accept(this);
		
		Variable var;
		
		if( n.e instanceof IdentifierExp ) {
			if( (var = getVariavel( ((IdentifierExp)n.e).s ) ) == null ) {
				errors = errors.concat("linha: "+n.line_number+". Variável não declarada: " + ((IdentifierExp)n.e).s+"\n");
				return;//throw new SemanticsException("Variável não declarada: :  " + ((IdentifierExp)n.e).s, n.e.line_number);
			}
			if( !(var.type instanceof BooleanType) ) {
				errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para operação !\n");
				return;//throw new SemanticsException("Tipo ilegal para operação ! ", n.e.line_number);
			}
			((IdentifierExp)n.e).setType(var.type);
		}
		
		if( !( n.e.getType() instanceof BooleanType) )
			errors = errors.concat("linha: "+n.line_number+". Tipo ilegal para operação !\n");
			//throw new SemanticsException("Tipo ilegal para operação ! ", n.e.line_number);
		
		
		
	}

	public void visit(Identifier n) {
		// TODO Auto-generated method stub
	}
	
	private boolean isTypeEqual(Type t1, Type t2){
		Classe classe1, classe2;
		
		if( t1.getClass().equals(t2.getClass()) ){
			if(t1 instanceof IdentifierType ) {
				classe1 = this.table.classes.get(((IdentifierType)t1).s);
				classe2 = this.table.classes.get(((IdentifierType)t2).s);
				if( classe1.name.equals(classe2.name) || classe1.name.equals(classe2.parent))
					return true;
			}else 
				return true;
		}
		
		return false;
	}
	
	private Variable getVariavel(String varName) {
		Variable id = null;
		
		// Verifica se a variável foi declarada no escopo local
		if(this.lastMethod != null) { 
			id = this.lastMethod.vars.get(varName);
			if(id == null) {
				id = this.lastMethod.getParam(varName);
			}
		}
		
		// Verifica se a variável foi declarada no escopo global
		if(id == null) {
			id = this.lastClass.globals.get(varName);
			// Verifica se a variável foi herdada da classe parent
			if(id == null && this.lastClass.parent != null) {
				id = this.table.classes.get(this.lastClass.parent).globals.get(varName);
			}
		}
		
		return id;
	}

	/*final static String VARIAVEL_NAO_DECLARADA = "Variável não declarada: ";
	final static String VARIAVEL_JA_DECLARADA = "Variável já existe: ";
	final static String TIPO_ERRADO = "Tipo errado para essa expressão: ";*/
}

 
