/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lang.ir;

import lang.utils.IRVisitor;

/**
 *
 * @author beh01
 */
public class ForStatement extends Statement{
    private Expression initialization;
    private Expression condition;
	private Expression afterthought;
    private BlockOfStatements body;

	public ForStatement(Expression initialization, Expression condition, Expression afterthought, BlockOfStatements body) {
		this.initialization = initialization;
		this.condition = condition;
		this.afterthought = afterthought;
		this.body = body;
	}

	public Expression getInitialization() {
		return initialization;
	}

	public Expression getCondition() {
		return condition;
	}

	public Expression getAfterthought() {
		return afterthought;
	}

	public BlockOfStatements getBody() {
		return body;
	}

	@Override
    public void accept(IRVisitor visitor) {
		initialization.accept(visitor);
        condition.accept(visitor);
		afterthought.accept(visitor);
        body.accept(visitor);
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("for(" + initialization.toString() + ";" + condition.toString() + ";" + afterthought.toString() + ") {\n");
        sb.append(body.toString());
        sb.append("}");
        sb.append("\n");
        return sb.toString();
    }


}
