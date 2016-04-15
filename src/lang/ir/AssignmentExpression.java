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
public class AssignmentExpression extends Expression {
    private Variable variable;
    private Expression expression;

    public AssignmentExpression(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public void accept(IRVisitor visitor) {
        variable.accept(visitor);
        expression.accept(visitor);

        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return "(" + variable.toString()+" = "+expression.toString()+ ")";
    }
}
