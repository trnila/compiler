package lang;

import lang.ir.BlockOfStatements;
import lang.parser.LangParser;
import lang.utils.ByteCodeGenerator;
import lang.utils.IRVisitor;
import lang.utils.NicePrintingVisitor;
import lang.utils.TypeChecking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
		IRVisitor visitor = new NicePrintingVisitor();

		InputStream is;
		if(args.length < 1) {
			is = System.in;
		} else {
			is = new FileInputStream(args[0]);
		}

		List<String> lines = new ArrayList<>();
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder b = new StringBuilder();
		while((line = r.readLine()) != null) {
			lines.add(line);
			b.append(line);
			b.append("\n");
		}

		is = new ByteArrayInputStream(b.toString().getBytes());

		LangParser p = new LangParser(is);
		BlockOfStatements program = p.Program();
		//program.accept(visitor);
		TypeChecking checking = new TypeChecking();
		program.accept(checking);


		for(lang.utils.Error error: checking.getErrors()) {
			int lin = error.getNode().getLine();

			for(int i = Math.max(lin - 3, 0); i <= lin - 1; i++) {
				System.out.println(
						String.format("%3d", i + 1) +
						"  " +
						lines.get(i)
				);
			}

			for (int i = -5; i < error.getNode().getColumn() - 1; i++) {
				System.out.print(' ');
			}
			System.out.println("^---- " + error + "\n");
		}


		File f = new File("a.out");
		f.createNewFile();
		f.setExecutable(true);

		FileOutputStream stream = new FileOutputStream(f.getPath());
		stream.write("#!./run\n".getBytes());
		ByteCodeGenerator gen = new ByteCodeGenerator(stream);
		program.accept(gen);
		//System.out.println(stream);
		stream.close();
	}
}
