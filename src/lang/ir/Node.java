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
    private int line;
	private int column;

	public Node(int line, int column) {
		this.line = line;
		this.column = column;
	}

	public abstract void accept(IRVisitor visitor);
   
    @Override
    public abstract String toString();

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
}
