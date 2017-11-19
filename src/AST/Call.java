package AST;
import AST.Visitor.Visitor;

public class Call extends Exp {
  public Exp e;
  public Identifier i;
  public ExpList el;
  private Type type;
  
  public Call(Exp ae, Identifier ai, ExpList ael, int ln) {
    super(ln);
    e=ae; i=ai; el=ael;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public Exp getExpression() {
	  return e;
  }

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public void setType(Type type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

}
