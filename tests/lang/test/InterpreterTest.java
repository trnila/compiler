package lang.test;

import junit.framework.Assert;
import lang.interpreter.Env;
import lang.interpreter.Instructions.Loader.InstructionLoader;
import lang.interpreter.Program;
import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class InterpreterTest {
	private ByteArrayOutputStream out;
	private Env env;


	@Before
	public void setUp() throws Exception {
		out = new ByteArrayOutputStream();
		env = new Env();
		env.setOutputStream(out);
	}

	@Test
	public void writeConstant() throws Exception {
		loadProgram(
				"push Shello world\n" +
				"write 1"
		);

		Assert.assertEquals("hello world\n", out.toString());
	}

	@Test
	public void someMath() throws Exception {
		loadProgram(
				"push I1\n" +
				"push I4\n" +
				"push I5\n" +
				"mul\n" +
				"add\n" +
				"write 1"
		);

		Assert.assertEquals("21\n", out.toString());
	}

	@Test
	public void someAdvancedMath() throws Exception {
		loadProgram(
				"push I1\n" +
				"push I2\n" +
				"push I3\n" +
				"mul\n" +
				"add\n" +
				"push I4\n" +
				"push I1\n" +
				"push I8\n" +
				"add\n" +
				"push I10\n" +
				"sub\n" +
				"mul\n" +
				"push I1\n" +
				"push I3\n" +
				"push I1\n" +
				"div\n" +
				"add\n" +
				"mod\n" +
				"add\n" +
				"write 1"
		);

		Assert.assertEquals("8\n", out.toString());
	}

	@Test
	public void jumpTest() throws Exception {
		loadProgram(
				"jmp 10\n" +
				"push Serror\n" +
				"write 1\n" +
				"label 10\n" +
				"push Ssuccess\n" +
				"write 1\n"
		);

		Assert.assertEquals("success\n", out.toString());
	}

	@Test
	public void fjumpTest() throws Exception {
		loadProgram(
				"push B1\n" +
				"fjmp 10\n" +
				"push Ssuccess 1\n" +
				"write 1\n" +
				"label 10\n" +
				"push B0\n" +
				"fjmp 20\n" +
				"push Serror\n" +
				"write 1\n" +
				"label 20\n" +
				"push Ssuccess 2\n" +
				"write 1"
		);

		Assert.assertEquals("success 1\nsuccess 2\n", out.toString());
	}

	@Test
	public void loadSaveVariable() throws Exception {
		loadProgram(
				"push I5\n" +
				"save a\n" +
				"push I10\n" +
				"save b\n" +
				"load a\n" +
				"load b\n" +
				"add\n" +
				"save c\n" +
				"load a\n" +
				"push S+\n" +
				"load b\n" +
				"push S=\n" +
				"load c\n" +
				"write 5"
		);

		Assert.assertEquals("15=10+5\n", out.toString());
	}

	@Test
	public void read() throws Exception {
		env.setInputStream(new StringInputStream("18 daniel True 4.56"));
		loadProgram(
			"read I\n" +
			"read S\n" +
			"read B\n" +
			"read F\n" +
			"write 4"
		);

		Assert.assertEquals("4.56truedaniel18\n", out.toString());
	}


	private void loadProgram(String source) {
		StringInputStream i = new StringInputStream(source);

		Program program = InstructionLoader.load(i);
		program.execute(env);
	}

}