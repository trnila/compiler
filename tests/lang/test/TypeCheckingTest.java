package lang.test;

import lang.ir.BlockOfStatements;
import lang.ir.ExpressionStatement;
import lang.ir.Type;
import lang.parser.LangParser;
import lang.utils.BadTypeError;
import lang.utils.TypeChecking;
import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Assert;

public class TypeCheckingTest {
	private class Analyse {
		BlockOfStatements root;
		TypeChecking checking;

		public Analyse(BlockOfStatements root, TypeChecking checking) {
			this.root = root;
			this.checking = checking;
		}

		public boolean isValid() {
			return checking.isValid();
		}

		public lang.utils.Error getFirstError() {
			return checking.getErrors().get(0);
		}

		public Type getExprType() {
			return ((ExpressionStatement) root.getStatements().get(0)).getExpr().getType();
		}
	}

	@org.junit.Test
	public void testMathBinaryOperations() throws Exception {
		Analyse analyse = analyzeProgram("1+2;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.INT, analyse.getExprType());

		analyse = analyzeProgram("1+2+6;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.INT, analyse.getExprType());

		analyse = analyzeProgram("1+2-6*7/2;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.INT, analyse.getExprType());

		analyse = analyzeProgram("1.0+2.0-6.4*7.2/2.2;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.FLOAT, analyse.getExprType());

		analyse = analyzeProgram("true && False;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("\"ahoj\"*5.0;");
		Assert.assertFalse(analyse.checking.isValid());
	}

	@org.junit.Test
	public void testVariables() throws Exception {
		Analyse analyse = analyzeProgram("int a; a = 4; a = a + 2;");
		Assert.assertTrue(analyse.checking.isValid());

		analyse = analyzeProgram("float a; a = 4; a = a + 2;");
		Assert.assertTrue(analyse.checking.isValid());

		analyse = analyzeProgram("a = 4; a = a + 2;");
		Assert.assertFalse(analyse.checking.isValid());

		analyse = analyzeProgram("a = 4; int a; a = a + 2;");
		Assert.assertFalse(analyse.checking.isValid());

		analyse = analyzeProgram("boolean a; a = 4; a = a + 2;");
		Assert.assertFalse(analyse.checking.isValid());

		analyse = analyzeProgram("int a; a = 4; a = a + 2; int a;");
		Assert.assertFalse(analyse.checking.isValid());
	}


	@org.junit.Test
	public void testModuloInteger() throws Exception {
		Analyse analyse = analyzeProgram("1%2;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.INT, analyse.getExprType());
	}

	@org.junit.Test
	public void testModuloFloat() throws Exception {
		Analyse checking = analyzeProgram("1.0%2.0;");
		Assert.assertFalse(checking.isValid());
//TODO: dodelat
		checking = analyzeProgram("1%2.0;");
		Assert.assertFalse(checking.isValid());
	}

	@org.junit.Test
	public void testRetype() throws Exception {
		Analyse analyse = analyzeProgram("1+2.43;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.FLOAT, analyse.getExprType());

		analyse = analyzeProgram("1.43+2;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.FLOAT, analyse.getExprType());
	}

	@org.junit.Test
	public void testConcat() throws Exception {
		Analyse analyse = analyzeProgram("\"hello\".\"world\";");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.STRING, analyse.getExprType());
	}

	@org.junit.Test
	public void testRelational() throws Exception {
		Analyse analyse = analyzeProgram("1 > 2;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("True > False;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("1.0 > 2.45;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("\"hello\" < \"zello\" ;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("1 > 2.0;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("1 > 2 > 5;");
		Assert.assertFalse(analyse.checking.isValid());

		analyse = analyzeProgram("1 > \"test\";");
		Assert.assertFalse(analyse.checking.isValid());
	}

	@org.junit.Test
	public void testLogicalBinary() throws Exception {
		Analyse analyse = analyzeProgram("True || False;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("True && False;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("1 || False;");
		Assert.assertFalse(analyse.checking.isValid());
	}

	@org.junit.Test
	public void testUnaryOps() throws Exception {
		Analyse analyse = analyzeProgram("-12;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.INT, analyse.getExprType());

		analyse = analyzeProgram("-(1*3+4.4);");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.FLOAT, analyse.getExprType());

		analyse = analyzeProgram("!True;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("!(True && 1 > 5);");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("-True;");
		Assert.assertFalse(analyse.checking.isValid());
		Assert.assertEquals("unary requires INT, FLOAT but BOOLEAN given", analyse.getFirstError().toString());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getLine());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getColumn());

		analyse = analyzeProgram("-\"str\";");
		Assert.assertFalse(analyse.checking.isValid());
		Assert.assertEquals("unary requires INT, FLOAT but STRING given", analyse.getFirstError().toString());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getLine());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getColumn());

		analyse = analyzeProgram("!20;");
		Assert.assertFalse(analyse.checking.isValid());
		Assert.assertEquals("unary requires BOOLEAN but INT given", analyse.getFirstError().toString());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getLine());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getColumn());
	}

	@org.junit.Test
	public void testTernary() throws Exception {
		Analyse analyse = analyzeProgram("True ? 1 : 0;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.INT, analyse.getExprType());

		analyse = analyzeProgram("True ? 1 : 1.4;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.FLOAT, analyse.getExprType());

		analyse = analyzeProgram("!True || 1 < 3 ? \"test\" : \"hello\";");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.STRING, analyse.getExprType());

		analyse = analyzeProgram("True ? True ? False : True : False;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("True ? False < True : True > False || False;");
		Assert.assertTrue(analyse.checking.isValid());
		Assert.assertSame(Type.BOOLEAN, analyse.getExprType());

		analyse = analyzeProgram("1 ? 1 : 1;");
		Assert.assertFalse(analyse.checking.isValid());
		Assert.assertEquals("ternary requires BOOLEAN but INT given", analyse.getFirstError().toString());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getLine());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getColumn());

		analyse = analyzeProgram("True ? True : 34;");
		Assert.assertFalse(analyse.checking.isValid());
		Assert.assertEquals("ternary return value requires BOOLEAN but INT given", analyse.getFirstError().toString());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getLine());
		Assert.assertEquals(1, analyse.getFirstError().getNode().getColumn());
	}

	@org.junit.Test
	public void testFor() throws Exception {
		Analyse analyse = analyzeProgram("int i; for(i=0;i<5;i=i+1)begin ; end;");
		Assert.assertTrue(analyse.checking.isValid());
	}

	@org.junit.Test
	public void testIf() throws Exception {
		Analyse analyse = analyzeProgram("int i;\n if (1 > 5 && 1 > 5 || 5 + 1 < 4) then \n write 1; end;");
		Assert.assertTrue(analyse.checking.isValid());

		analyse = analyzeProgram("int i;\n if (1) then \n write 1; end;");
		Assert.assertFalse(analyse.checking.isValid());
		Assert.assertEquals("if requires BOOLEAN but INT given", analyse.getFirstError().toString());
		Assert.assertEquals(2, analyse.getFirstError().getNode().getLine());
		Assert.assertEquals(6, analyse.getFirstError().getNode().getColumn());
	}
	
	private Analyse analyzeProgram(String program) throws Exception {
		StringInputStream is = new StringInputStream(program);
		TypeChecking checking = new TypeChecking();

		LangParser p = new LangParser(is);
		BlockOfStatements prg = p.Program();
		prg.accept(checking);
		return new Analyse(prg, checking);
	}
}