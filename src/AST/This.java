package AST;
import AST.Visitor.Visitor;

public class This extends Exp {
	private Type type;
  public This(int ln) {
    super(ln);
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
