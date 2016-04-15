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
public abstract class Node {

    public abstract void accept(IRVisitor visitor);
   
    @Override
    public abstract String toString();

}
