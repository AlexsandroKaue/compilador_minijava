package AST;
import AST.Visitor.Visitor;

public class And extends Exp {
  public Exp e1,e2;
  
  public And(Exp ae1, Exp ae2, int ln) {
    super(ln);
    e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public Exp getExpression1(){
	  return e1;
  }
  
  public Exp getExpression2(){
	  return e2;
  }
  
  public Class<? extends Type> getExpType(){
	return BooleanType.class;
  }
}
