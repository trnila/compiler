package lang.utils;
import lang.ir.*;

public class TypeChecking implements IRVisitor {
	public boolean valid = true;

	private void error(String message) {
		System.out.println(message);
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}



    @Override
    public void visit(AssignmentExpression st) {
    }

    @Override
    public void visit(BinaryExpression exp) {
	    Type left = exp.getLeft().getType();
	    Type right = exp.getRight().getType();

	    switch(exp.getOp()) {
		    case "+":
		    case "-":
		    case "*":
		    case "/":
			    if(left == Type.INT || left == Type.FLOAT) {
				    exp.setType(left == Type.FLOAT || right == Type.FLOAT ? Type.FLOAT : Type.INT);
				    return;
			    }
			    break;
		    case "%":
			    if(left == Type.INT && left.equals(right)) {
				    exp.setType(left);
				    return;
			    }
			    break;
		    case ".":
			    if(left == Type.STRING && left.equals(right)) {
				    exp.setType(Type.STRING);
				    return;
			    }
			    break;

		    case "<":
		    case "<=":
		    case ">":
		    case ">=":
		    case "==":
		    case "!=":
			    if(Type.isValid(left) && areSame(left, right)) {
				    exp.setType(Type.BOOLEAN);
				    return;
			    }
			    break;
		    case "&&":
		    case "||":
			    if(left == Type.BOOLEAN && left == right) {
				    exp.setType(Type.BOOLEAN);
				    return;
			    }
			    break;
	    }

	    error("unexpected " + exp.toString());
    }

	private boolean areSame(Type left, Type right) {
		if(left == right) {
			return true;
		}

		return (left == Type.FLOAT && right == Type.INT) ||
				(left == Type.INT && right == Type.FLOAT);
	}

	@Override
    public void visit(BlockOfStatements st) {

    }

    @Override
    public void visit(TernaryExpression exp) {
	    Type left = exp.getLeftPart().getType();
	    Type right = exp.getRightPart().getType();

		if(exp.getCondition().getType() == Type.BOOLEAN && areSame(left, right)) {
			exp.setType(resultingType(left, right));
			return;
		}

	    error("invalid ternary");
    }

	private Type resultingType(Type left, Type right) {
		if(areSame(left, right)) {
			if(left == Type.FLOAT || right == Type.FLOAT) {
				return Type.FLOAT;
			}
			return left;
		}

		return Type.ERROR;
	}

	@Override
    public void visit(IfStatement st) {

    }

    @Override
    public void visit(Constant exp) {

    }

    @Override
    public void visit(WriteStatement st) {

    }

    @Override
    public void visit(ReadStatement st) {

    }

    @Override
    public void visit(UnaryExpression exp) {
	    Type target = exp.getTarget().getType();

	    switch(exp.getOperator()) {
		    case "-":
			    if(target == Type.INT || target == Type.FLOAT) {
				    exp.setType(target);
				    return;
			    }
			    break;
		    case "!":
			    if(target == Type.BOOLEAN) {
				    exp.setType(Type.BOOLEAN);
				    return;
			    }
			    break;
	    }

		error("invalid unary expr");
    }

    @Override
    public void visit(Variable exp) {
    }

    @Override
    public void visit(ForStatement st) {
		if(st.getCondition().getType() != Type.BOOLEAN) {
			error("expected boolean in for");
		}
    }

    @Override
    public void visit(VariableDeclaration decl) {
    }
}
