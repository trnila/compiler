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
public class ReadStatement extends Statement {
    private ArrayList<Variable> variables = new ArrayList<Variable>();

    public ReadStatement(int line, int column) {
        super(line, column);
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    @Override
    public void accept(IRVisitor visitor) {
        for(Variable v: variables) {
            v.accept(visitor);
        }
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("read ");
        for(Variable v : variables) {
            sb.append(v.toString()+", ");
        }
        return sb.toString()+"\n";
    }


}
