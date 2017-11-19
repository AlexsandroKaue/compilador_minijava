package AST;
import AST.Visitor.Visitor;

public class IdentifierExp extends Exp{
  public String s;
  private Type type;
  
  public IdentifierExp(String as, int ln) { 
    super(ln);
    s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
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
