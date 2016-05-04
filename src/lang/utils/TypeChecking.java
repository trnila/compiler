package lang.utils;
import lang.ir.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TypeChecking implements IRVisitor {
	public boolean valid = true;
	private SymbolTable symbols = new SymbolTable();
	private List<Error> errors = new ArrayList<>();

    @Override
    public void visit(AssignmentExpression st) {
	    Variable var = st.getVariable();

		if(!symbols.exists(var.getName())) {
			error(new VariableError("variable not exists", var));
			return;
		}

	    Type varType = symbols.load(var.getName()).getType();
	    if(!areSame(varType, st.getExpression().getType())) {
		    error("could not assign");
		    return;
	    }

	    st.setType(varType);
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
			    } else {
				    error(
						    new BadTypeError(
								    exp.getOp(),
								    exp,
								    new Type[] {Type.INT, Type.FLOAT},
								    right
						    )
				    );
			    }
			    return;
		    case "%":
			    if(left == Type.INT && left.equals(right)) {
				    exp.setType(left);
			    } else {
				    error(new BadTypeError(
							exp.getOp(),
							exp,
							new Type[] {Type.INT},
						    left == Type.INT ? right : left
				    ));
			    }
				return;
		    case ".":
			    if(left == Type.STRING && left.equals(right)) {
				    exp.setType(Type.STRING);
			    } else {
				    error(new BadTypeError(
						    exp.getOp(),
						    exp,
						    new Type[] {Type.STRING},
						    left == Type.STRING ? right : left
				    ));
			    }
			    return;

		    case "<":
		    case "<=":
		    case ">":
		    case ">=":
		    case "==":
		    case "!=":
			    if(Type.isValid(left) && areSame(left, right)) {
				    exp.setType(Type.BOOLEAN);
			    } else {
				    error(new BadTypeError(
						    exp.getOp(),
						    exp,
						    new Type[]{left},
						    right
				    ));
			    }

			    return;
		    case "&&":
		    case "||":
			    if(left == Type.BOOLEAN && left == right) {
				    exp.setType(Type.BOOLEAN);
			    } else {
				    error(new BadTypeError(
						    exp.getOp(),
						    exp,
						    new Type[] {Type.BOOLEAN},
						    left == Type.BOOLEAN ? right : left
				    ));
			    }
			    return;
	    }

	    throw new RuntimeException("unreachable code!");
    }

	@Override
    public void visit(BlockOfStatements st) {
	}

    @Override
    public void visit(TernaryExpression exp) {
	    Type left = exp.getLeftPart().getType();
	    Type right = exp.getRightPart().getType();

	    boolean failed = false;
	    if(exp.getCondition().getType() != Type.BOOLEAN) {
		    failed = true;
		    error(new BadTypeError(
				    "ternary",
				    exp,
				    new Type[]{Type.BOOLEAN},
				    exp.getCondition().getType()
			));
	    }

	    if(!areSame(left, right)) {
		    failed = true;
		    error(new BadTypeError(
				    "ternary return value",
				    exp,
				    new Type[]{left},
				    right
		    ));
	    }

	    exp.setType(failed ? Type.ERROR : resultingType(left, right));
    }

	@Override
    public void visit(IfStatement st) {
		//TODO: this is bad!
		st.getCondition().accept(this);
		st.getThenPart().accept(this);
		if(st.getElsePart() != null) {
			st.getElsePart().accept(this);
		}

		if(st.getCondition().getType() != Type.BOOLEAN) {
			error(new BadTypeError(
					"if",
					st.getCondition(),
					new Type[]{Type.BOOLEAN},
					st.getCondition().getType()
			));
		}
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
			    } else {
				    error(new BadTypeError(
						    "unary",
						    exp,
						    new Type[] {Type.INT, Type.FLOAT},
						    target
				    ));
			    }
			    return;
		    case "!":
			    if(target == Type.BOOLEAN) {
					exp.setType(Type.BOOLEAN);
			    } else {
				    error(new BadTypeError(
						    "unary",
						    exp,
						    new Type[] {Type.BOOLEAN},
						    target
				    ));
			    }
			    return;
	    }

		//assert(0, "unreachable code");
    }

    @Override
    public void visit(Variable exp) {
		if(!symbols.exists(exp.getName())) {
			error("variable does not exists!");
			exp.setType(Type.ERROR);
		} else {
			exp.setType(symbols.load(exp.getName()).getType());
		}
    }

    @Override
    public void visit(ForStatement st) {
	    st.getCondition().accept(this);

		if(st.getCondition().getType() != Type.BOOLEAN) {
			error("expected boolean in for");
		}

	    if(st.getBody() != null) {
		    st.getBody().accept(this);
	    }
    }

    @Override
    public void visit(VariableDeclaration decl) {
	    for(Variable var: decl.getVariables()) {
		    if(symbols.exists(var.getName())) {
			    error("Variable already exists!");
		    } else {
			    symbols.save(var);
		    }
	    }
    }

	private boolean areSame(Type left, Type right) {
		if(left == right) {
			return true;
		}

		return (left == Type.FLOAT && right == Type.INT) ||
				(left == Type.INT && right == Type.FLOAT);
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

	private void error(String message) {
		System.out.println(message);
		valid = false;
	}

	private void error(Error error) {
		errors.add(error);
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public List<Error> getErrors() {
		return errors;
	}
}
