package lang.utils;

import lang.ir.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;

public class ByteCodeGenerator implements IRVisitor {
	private PrintStream out;
	private Stack<String> data = new Stack<>();
	private int label = 0;

	public ByteCodeGenerator(OutputStream os) {
		out = new PrintStream(os);
	}

	@Override
	public void visit(AssignmentExpression st) {
		System.out.println("save " + st.getVariable().getName());
	}

	@Override
	public void visit(BinaryExpression exp) {
		HashMap<String, String> opcodes = new HashMap<>();
		opcodes.put("+", "add");
		opcodes.put("-", "sub");
		opcodes.put("*", "mul");
		opcodes.put("/", "div");
		opcodes.put("%", "mod");
		opcodes.put(".", "concat");
		opcodes.put("&&", "and");
		opcodes.put("||", "or");
		opcodes.put(">", "gt");
		opcodes.put("<", "lt");
		opcodes.put("=", "eq");

		System.out.println(opcodes.get(exp.getOp()));
	}

	@Override
	public void visit(BlockOfStatements st) {

	}

	@Override
	public void visit(TernaryExpression exp) {

	}

	@Override
	public void visit(IfStatement st) {
		int elseLabel = label++;
		int endLabel = label++;

		st.getCondition().accept(this);
		System.out.println("fjmp " + elseLabel);
		st.getThenPart().accept(this);
		System.out.println("jmp " + endLabel);
		System.out.println("label " + elseLabel);
		st.getElsePart().accept(this);
		System.out.println("label " + endLabel);
	}

	@Override
	public void visit(Constant exp) {
		System.out.println("push " + exp);
	}

	@Override
	public void visit(WriteStatement st) {
		System.out.println("print " + st.getExpressions().size());
	}

	@Override
	public void visit(ReadStatement st) {
		for(Variable var: st.getVariables()) {
			System.out.println("read " + var.getType());
			System.out.println("save " + var.getName());
		}
	}

	@Override
	public void visit(UnaryExpression exp) {
		if(exp.getOperator().equals("-")) {
			System.out.println("uminus");
		} else if(exp.getOperator().equals("!")) {
			System.out.println("not");
		} else {
			System.out.println("errr");
		}
	}

	@Override
	public void visit(Variable exp) {
		System.out.println("load " + exp.getName());
	}

	@Override
	public void visit(ForStatement st) {
		int loopLabel = label++;
		int endLabel = label++;

		//System.out.println("labelik");
		st.getInitialization().accept(this);

		System.out.println("label " + loopLabel);
		st.getCondition().accept(this);
		System.out.println("jmpf " + endLabel);

		st.getBody().accept(this);
		st.getAfterthought().accept(this);
		System.out.println("jmp " + loopLabel);
		System.out.println("label " + endLabel);
	}

	@Override
	public void visit(VariableDeclaration decl) {

	}
}
