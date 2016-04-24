package lang;

import lang.ir.BlockOfStatements;
import lang.parser.LangParser;
import lang.utils.ByteCodeGenerator;
import lang.utils.IRVisitor;
import lang.utils.NicePrintingVisitor;
import lang.utils.TypeChecking;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
	public static void main(String[] args) throws Exception {
		IRVisitor visitor = new NicePrintingVisitor();

		InputStream is;
		if(args.length < 1) {
			is = System.in;
		} else {
			is = new FileInputStream(args[0]);
		}

		LangParser p = new LangParser(is);
		BlockOfStatements program = p.Program();
		//program.accept(visitor);
		program.accept(new TypeChecking());

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ByteCodeGenerator gen = new ByteCodeGenerator(stream);
		program.accept(gen);
		System.out.println(stream);


	}
}
