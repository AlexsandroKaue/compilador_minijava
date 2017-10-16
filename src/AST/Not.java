package AST;
import AST.Visitor.Visitor;

public class Not extends Exp {
	private BooleanType type;
  public Exp e;
  
  public Not(Exp ae, int ln) {
    super(ln);
    e=ae; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public Class<? extends Type> getExpType() {
	  return BooleanType.class;
  }
}
