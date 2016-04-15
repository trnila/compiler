package lang;

import lang.ir.*;
import lang.parser.LangParser;
import lang.utils.IRVisitor;
import lang.utils.NicePrintingVisitor;

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
		p.Program().accept(visitor);
		System.out.println(visitor.toString());
	}
}
