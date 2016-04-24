package lang.interpreter;

import lang.ir.Type;

public class Value {
	private Type type;
	private String value;

	public Value(Type type, String value) {
		this.type = type;
		this.value = value;
	}

	public Value(Type type, int value) {
		this(type, Integer.toString(value));
	}

	public int getInt() {
		return Integer.parseInt(value);
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return value;
	}

	public boolean getBool() {
		return Boolean.parseBoolean(value) || value.equals("1");
	}
}
