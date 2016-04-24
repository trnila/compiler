package lang.ir;

import lang.utils.IRVisitor;

public class ExpressionStatement extends Statement {
	private Expression expr;

	public ExpressionStatement(Expression expr) {
		this.expr = expr;
	}

	@Override
	public void accept(IRVisitor visitor) {
		expr.accept(visitor);
	}

	@Override
	public String toString() {
		return expr.toString();
	}

	public Expression getExpr() {
		return expr;
	}
}
