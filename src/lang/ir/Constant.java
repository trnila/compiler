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
public class Constant extends Expression{
    private Object value;

    public Constant(int line, int column, Object value, Type type) {
        super(line, column);
        this.value = value;
        setType(type);
    }

    public Object getValue() {
        return value;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
