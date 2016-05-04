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
public class BinaryExpression extends Expression {

    private Expression left;
    private String op;
    private Expression right;

    public BinaryExpression(int line, int column, Expression left, String op, Expression right) {
        super(line, column);
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public String getOp() {
        return op;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left.toString() + op + right.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        left.accept(visitor);
        right.accept(visitor);

        visitor.visit(this);
    }

}
