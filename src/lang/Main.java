package lang;

import lang.ir.BlockOfStatements;
import lang.parser.LangParser;
import lang.parser.ParseException;
import lang.utils.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private final List<String> lines = new ArrayList<>();
	private final InputStream is;

	public Main(InputStream in) throws IOException {
		is = load(in);
	}

	private InputStream load(InputStream in) throws IOException {
		InputStream is;BufferedReader r = new BufferedReader(new InputStreamReader(in));
		String line;
		StringBuilder b = new StringBuilder();
		while((line = r.readLine()) != null) {
			lines.add(line);
			b.append(line);
			b.append("\n");
		}

		is = new ByteArrayInputStream(b.toString().getBytes());
		return is;
	}

	public boolean compile() throws IOException {
		LangParser p = new LangParser(is);
		BlockOfStatements program;

		try {
			program = p.Program();
		} catch(ParseException e) {
			printError(e.getMessage(), e.currentToken.beginLine, e.currentToken.beginColumn);
			return false;
		}


		TypeChecking checking = new TypeChecking();
		program.accept(checking);

		for(lang.utils.Error error: checking.getErrors()) {
			printError(error.toString(), error.getNode().getLine(), error.getNode().getColumn());
		}

		if(!checking.isValid()) {
			return false;
		}

		File f = new File("a.out");
		f.createNewFile();
		f.setExecutable(true);

		FileOutputStream stream = new FileOutputStream(f.getPath());
		generateCode(program, stream);
		return true;
	}

	private void printError(String error, int line, int column) {
		for(int i = Math.max(line - 5, 0); i <= line - 1; i++) {
			System.out.println(
					String.format("%3d", i + 1) +
							"  " +
							lines.get(i)
			);
		}


		for (int i = -5; i < column - 1; i++) {
			System.out.print(' ');
		}
		System.out.println("^---- " + error + "\n");
	}

	private void generateCode(BlockOfStatements program, FileOutputStream stream) throws IOException {
		stream.write("#!./run\n".getBytes());
		ByteCodeGenerator gen = new ByteCodeGenerator(stream);
		program.accept(gen);
		stream.close();
	}

	public static void main(String[] args) throws Exception {
		InputStream is;
		if(args.length < 1) {
			is = System.in;
		} else {
			is = new FileInputStream(args[0]);
		}

		if(!new Main(is).compile()) {
			System.exit(1);
		}
	}
}
