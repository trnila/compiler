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
public class BlockOfStatements  extends Statement{
    ArrayList<Statement> statements = new ArrayList<Statement>();

    public BlockOfStatements() {
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    @Override
    public void accept(IRVisitor visitor) {
        for(Statement s : statements) s.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Statement s : statements) {
            sb.append(s.toString());
        }
        return sb.toString();
    }


}
