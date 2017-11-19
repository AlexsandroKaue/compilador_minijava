package AST;
import AST.Visitor.Visitor;

public class NewObject extends Exp {
  public Identifier i;
  private Type type;
  
  public NewObject(Identifier ai, int ln) {
    super(ln);
    i=ai;
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
		this.type = type;
	}
}
