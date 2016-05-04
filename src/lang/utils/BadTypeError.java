package lang.utils;

import lang.ir.Node;
import lang.ir.Type;

import java.util.List;

public class BadTypeError extends Error {
	private Type[] required;
	private Type given;

	public BadTypeError(String message, Node node, Type[] required, Type given) {
		super(message, node);
		this.required = required;
		this.given = given;
	}

	@Override
	public String toString() {
		return getMessage() + " requires " + gen(required) + " but " + given + " given";
	}

	private String gen(Type[] arr) {
		StringBuilder builder = new StringBuilder();

		boolean first = true;
		for(Type item: arr) {
			if(!first) {
				builder.append(", ");
			} else {
				first = false;
			}

			builder.append(item);
		}

		return builder.toString();
	}


}
