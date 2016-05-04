package lang.interpreter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Env {
	private int pc = 0;
	private Stack<Value> stack = new Stack<>();
	private PrintStream out = System.out;
	private Scanner in = new Scanner(System.in);
	private HashMap<String, Value> variables = new HashMap<>();

	public Stack<Value> getStack() {
		return stack;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.out = new PrintStream(outputStream);
	}

	public void write(String s) {
		out.print(s);
	}

	public void jump(int destination) {
		pc = destination;
	}

	public int getPc() {
		return pc;
	}

	public void nextInstruction() {
		pc++;
	}

	public void setInputStream(InputStream inputStream) {
		this.in = new Scanner(inputStream);
	}

	public void save(String key, Value value) {
		variables.put(key, value);
	}

	public Value load(String key) {
		return variables.get(key);
	}

	public Scanner getInputStream() {
		return in;
	}
}
