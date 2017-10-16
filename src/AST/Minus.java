package AST;
import AST.Visitor.Visitor;

public class Minus extends Exp implements BinaryIntegerOperation {
  public Exp e1,e2;
  
  public Minus(Exp ae1, Exp ae2, int ln) {
    super(ln);
    e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

	public Exp getExpression1() {
		// TODO Auto-generated method stub
		return e1;
	}
	
	public Exp getExpression2() {
		// TODO Auto-generated method stub
		return e2;
	}
	
	public Class<? extends Type> getExpType(){
		return IntegerType.class;
	}
}
