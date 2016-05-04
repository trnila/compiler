package lang.utils;

import lang.ir.Node;

public class Error {
	private String message;
	private Node node;

	public Error(String message, Node node) {
		this.message = message;
		this.node = node;
	}

	public String getMessage() {
		return message;
	}

	public Node getNode() {
		return node;
	}
}
