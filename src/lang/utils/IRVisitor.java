/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lang.utils;


import lang.ir.*;

/**
 *
 * @author beh01
 */
public interface  IRVisitor {
    void visit(AssignmentStatement st);
    void visit(BinaryExpression exp);
    void visit(BlockOfStatements st);
    void visit(TernaryExpression exp);
    void visit(IfStatement st);
    void visit(Constant exp);
    void visit(WriteStatement st);
    void visit(ReadStatement st);
    void visit(UnaryExpression exp);
    void visit(Variable exp);
    void visit(WhileStatement st);
	void visit(VariableDeclaration decl);
}
