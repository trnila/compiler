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
		out.println("save " + st.getVariable().getName());
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
		opcodes.put("==", "eq");

		// TODO: maybe rewrite visitor?
		opcodes.put(">=",
				"save __y\n" +
				"save __x\n" +
				"load __x\n" +
				"load __y\n" +
				"gt\n" +
				"load __x\n" +
				"load __y\n" +
				"eq\n" +
				"or"
		);

		opcodes.put("<=",
				"save __y\n" +
				"save __x\n" +
				"load __x\n" +
				"load __y\n" +
				"lt\n" +
				"load __x\n" +
				"load __y\n" +
				"eq\n" +
				"or"
		);

		opcodes.put("!=", "eq\nnot");

		if(opcodes.get(exp.getOp()) != null) {
			out.println(opcodes.get(exp.getOp()));
		} else {
			throw new UnsupportedOperationException("unknown binary operator: " + exp.getOp());
		}
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
		out.println("fjmp " + elseLabel);
		st.getThenPart().accept(this);
		out.println("jmp " + endLabel);
		out.println("label " + elseLabel);
		//TODO: some optimalizations if else part is not provided
		if(st.getElsePart() != null) {
			st.getElsePart().accept(this);
		}
		out.println("label " + endLabel);
	}

	@Override
	public void visit(Constant exp) {
		out.print("push " + toChar(exp.getType()));

		if(exp.getType() == Type.STRING) {
			String value = exp.getValue().toString();
			if(value.length() - 2 > 1) {
				out.print(value.substring(1, value.length() - 1));
			}
			out.println();
		} else {
			out.println(exp.getValue());
		}

	}

	@Override
	public void visit(WriteStatement st) {
		out.println("write " + st.getExpressions().size());
	}

	@Override
	public void visit(ReadStatement st) {
		for(Variable var: st.getVariables()) {
			out.println("read " + var.getType());
			out.println("save " + var.getName());
		}
	}

	@Override
	public void visit(UnaryExpression exp) {
		if(exp.getOperator().equals("-")) {
			out.println("uminus");
		} else if(exp.getOperator().equals("!")) {
			out.println("not");
		} else {
			out.println("errr");
		}
	}

	@Override
	public void visit(Variable exp) {
		out.println("load " + exp.getName());
	}

	@Override
	public void visit(ForStatement st) {
		int loopLabel = label++;
		int endLabel = label++;

		//out.println("labelik");
		st.getInitialization().accept(this);

		out.println("label " + loopLabel);
		st.getCondition().accept(this);
		out.println("fjmp " + endLabel);

		st.getBody().accept(this);
		st.getAfterthought().accept(this);
		out.println("jmp " + loopLabel);
		out.println("label " + endLabel);
	}

	@Override
	public void visit(VariableDeclaration decl) {
		String val;
		switch(decl.getType()) {
			case INT:
				val = "I0";
				break;
			case FLOAT:
				val = "F0.0";
				break;
			case STRING:
				val = "S";
				break;
			case BOOLEAN:
				val = "B0";
				break;
			default:
				throw new UnsupportedOperationException("unknown type");
		}

		for(Variable var: decl.getVariables()){
			out.println("push " + val + "\nsave " + var.getName());
		}
	}

	private char toChar(Type type) {
		//TODO: move!
		switch(type) {
			case INT:
				return 'I';
			case FLOAT:
				return 'F';
			case STRING:
				return 'S';
			case BOOLEAN:
				return 'B';
			case ERROR:
				return 'E';// TODO: ?
		}

		return 'E';
	}
}
