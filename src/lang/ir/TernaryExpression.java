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
public class TernaryExpression extends Expression{
    private Expression condition, leftPart, rightPart;

    public TernaryExpression(int line, int column, Expression condition, Expression leftPart, Expression rightPart) {
        super(line, column);
        this.condition = condition;
        this.leftPart = leftPart;
        this.rightPart = rightPart;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getLeftPart() {
        return leftPart;
    }

    public Expression getRightPart() {
        return rightPart;
    }

    @Override
    public void accept(IRVisitor visitor) {
        condition.accept(visitor);
        leftPart.accept(visitor);
        rightPart.accept(visitor);

        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return "("+condition.toString()+")?"+leftPart.toString()+":"+rightPart.toString();
    }


}
