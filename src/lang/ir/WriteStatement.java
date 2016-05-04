/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lang.ir;

import java.util.ArrayList;
import lang.utils.IRVisitor;

/**
 *
 * @author beh01
 */
public class WriteStatement extends Statement {

    ArrayList<Expression> expressions = new ArrayList<Expression>();

    public WriteStatement(int line, int column) {
        super(line, column);
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public void accept(IRVisitor visitor) {
        for (Expression e : expressions) {
            e.accept(visitor);
        }
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("write ");
        for (Expression e : expressions) {
            sb.append(e.toString() + ", ");
        }
        return sb.toString() + "\n";
    }

}
