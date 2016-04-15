package lang;

import lang.ir.*;
import lang.parser.LangParser;
import lang.utils.IRVisitor;
import lang.utils.NicePrintingVisitor;

import java.io.FileInputStream;

public class Main {
	public static void main(String[] args) throws Exception {
		IRVisitor visitor = new NicePrintingVisitor();

		//(new Constant(5, Type.INT)).accept(visitor);
		//LangParser p = new LangParser(new FileInputStream("tests/t1.txt"));
		LangParser p = new LangParser(new FileInputStream("x"));
		p.Program().accept(visitor);

		System.out.println(visitor.toString());

	}
}
