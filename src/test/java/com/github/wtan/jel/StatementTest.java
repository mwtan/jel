package com.github.wtan.jel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import com.github.wtan.jel.IExpression;
import com.github.wtan.jel.Statement;
import com.github.wtan.jel.StatementLexer;
import com.github.wtan.jel.exception.ExpressionException;

import junit.framework.TestCase;

/**
 * JUnit Tests for Statement class.
 * 
 * @author Will Tan
 */
public class StatementTest extends TestCase {
	private static final byte BYTE_0X16 = 0x16;
	private static final byte BYTE_0X16_NEG = -0x16;
	private static final char CHAR_A = 'a';
	private static final short SHORT_256 = 256;
	private static final int INT_21 = 21;
	private static final int INT_MINUS_21 = -21;
	private static final int INT_42 = 42;
	private static final int INT_54 = 54;
	private static final int INT_512 = 512;
	private static final int INT_554 = 554;
	private static final int INT_882 = 882;
	private static final long LONG_93000000 = 93000000L;
	private static final float FLOAT_PI = 3.142F;
	private static final double DOUBLE_PI = 3.141592653589D;
	private static final int ITER = 100000;
	private Map m;

	/**
	 * Standard constructor
	 */
	public StatementTest(String name) {
		super(name);
	}

	/**
	 * Set up test values in a Map.
	 */
	public void setUp() {
		m = new HashMap();
		m.put("rc", "21");
		m.put("id", "31");
		m.put("indicator_true", true);
		m.put("indicator_false", false);
		m.put("varbyte", new Byte(BYTE_0X16));
		m.put("varbyteneg", new Byte(BYTE_0X16_NEG));
		m.put("varchar", new Character(CHAR_A));
		m.put("varshort", new Short(SHORT_256));
		m.put("varint", new Integer(INT_512));
		m.put("varlong", new Long(LONG_93000000));
		m.put("dist", new Long(LONG_93000000));
		m.put("pi", new Float(FLOAT_PI));
		m.put("pidouble", new Double(DOUBLE_PI));
		m.put("description", "Ask not what your country can do for you...");
		m.put("myobj", new MyClass(INT_42, "hello"));
		m.put("myobj2", new Date());
	}

	public void test_parse_error_missing_semicolon() {
		try {
			IExpression cond = Statement.parse("message = \"hello\"");
			cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void test_parse_error_unexpected_token() {
		try {
			IExpression cond = Statement.parse("message = hi \"hello\";");
			cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void test_parse_error_bad_token() {
		try {
			IExpression cond = Statement.parse("message = hello\";");
			cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void test_parse_error_null_statement() {
		try {
			String str = null;
			IExpression cond = Statement.parse(str);
			cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Comments
	 */
	public void test_single_line_comment() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("// Comment \r\n");
			sb.append("message = \"hello\";");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals("hello", m.get("message"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_multi_line_comment() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("/* \r\n");
			sb.append(" * Comment \r\n");
			sb.append("\r\n");
			sb.append(" */\r\n");
			sb.append("message = \"hello\";");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals("hello", m.get("message"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Assignment statement.
	 */
	public void test_assign() {
		try {
			IExpression cond = Statement.parse("message = \"hello\";");
			cond.eval(m);
			assertEquals("hello", m.get("message"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_invalid_assign() {
		try {
			IExpression cond = Statement.parse("(1 + 2) = \"hello\";");
			cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (ExpressionException e) {
			e.getPrintStackTrace();
		}
	}

	/**
	 * Assignment statement.
	 */
	public void test_assign_int() {
		try {
			IExpression cond = Statement.parse("code = 512;");
			cond.eval(m);
			assertEquals(INT_512, ((Integer) m.get("code")).intValue());
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_assign_hex() {
		try {
			IExpression cond = Statement.parse("val = 0X11;");
			cond.eval(m);
			assertEquals(17, ((Integer) m.get("val")).intValue());
			cond = Statement.parse("val = 0xFF;");
			cond.eval(m);
			assertEquals(255, ((Integer) m.get("val")).intValue());
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_assign_octal() {
		try {
			IExpression cond = Statement.parse("val = 010;");
			cond.eval(m);
			assertEquals(8, ((Integer) m.get("val")).intValue());
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Assignment in if statement.
	 */
	public void test_assign_in_if() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  if (myobj != null && myobj.rc == 42) { ");
			sb.append("    message = \"hello\";");
			sb.append("  } ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals("hello", m.get("message"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Multiple assignments
	 */
	public void test_assign_multi() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  message = \"hello\";");
			sb.append("  message2 = \"hello\" + null;");
			sb.append("  message3 = null + \"hello\";");
			sb.append("  intcode = 42;");
			sb.append("  intcode2 = intcode + 12;");
			sb.append("  longcode = varlong + 42L;");
			sb.append("  floatcode = pi + 42F;");
			sb.append("  doublecode = pidouble + 42.0D;");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals("hello", m.get("message"));
			assertEquals("hello", m.get("message2"));
			assertEquals("hello", m.get("message3"));
			assertEquals(INT_42, ((Integer) m.get("intcode")).intValue());
			assertEquals(INT_54, ((Integer) m.get("intcode2")).intValue());
			assertEquals(LONG_93000000 + 42L, ((Long) m.get("longcode")).longValue());
			assertEquals(FLOAT_PI + 42F, ((Float) m.get("floatcode")).floatValue());
			assertEquals(DOUBLE_PI + 42.0D, ((Double) m.get("doublecode")).doubleValue());
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Multiple if statements
	 */
	public void test_assign_multi_if() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  if (myobj != null && myobj.rc == 42) { ");
			sb.append("    message = \"hello\";");
			sb.append("  } ");
			sb.append("  if (varint == 512) { ");
			sb.append("    message += \"world\";");
			sb.append("  } ");
			sb.append("  return true;");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			assertEquals("return true", true, ((Boolean) objeval).booleanValue());
			assertEquals("helloworld", m.get("message"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Overlay an existing value
	 */
	public void test_assign_overlay() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  message = \"hello\";");
			sb.append("  message = \"world\";");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals("world", m.get("message"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Multiple assignments with return
	 */
	public void test_assign_return() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  message = \"hello\";");
			sb.append("  /* This is a multi\n");
			sb.append("     line comment. */\n");
			sb.append("  return true;");
			sb.append("  // Should not execute statements below\n");
			sb.append("  intcode = 42;");
			sb.append("  intcode2 = intcode + 12;");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals("hello", m.get("message"));
			assertEquals(null, m.get("intcode"));
			assertEquals(null, m.get("intcode2"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Assignment of result from a method call.
	 */
	public void test_assign_from_method_call() {
		try {
			IExpression cond = Statement.parse("mytext = description.substring(18, 25);");
			cond.eval(m);
			assertEquals("country", m.get("mytext"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Multiple assignments in if statement
	 */
	public void test_assign_multi_in_if() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  if (myobj != null && myobj.rc == 42) { ");
			sb.append("    message = \"hello\";");
			sb.append("    intcode = 42;");
			sb.append("    intcode2 = intcode + 12;");
			sb.append("  } ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals("hello", m.get("message"));
			assertEquals(INT_42, ((Integer) m.get("intcode")).intValue());
			assertEquals(INT_54, ((Integer) m.get("intcode2")).intValue());
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * +=
	 */
	public void test_plusassign() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  intcode = 42;");
			sb.append("  intcode += varint;");
			sb.append("  intcode2 += varint;");
			sb.append("  longcode = 42L;");
			sb.append("  longcode += varlong;");
			sb.append("  floatcode = 0.01F;");
			sb.append("  floatcode += pi;");
			sb.append("  doublecode = 0.01D;");
			sb.append("  doublecode += pidouble;");
			sb.append("  mystr1 = \"Hello\";");
			sb.append("  mystr1 += \" Will\";");
			sb.append("  mystr2 += \"Will\";");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals(INT_554, ((Integer) m.get("intcode")).intValue());
			assertEquals(INT_512, ((Integer) m.get("intcode2")).intValue());
			assertEquals(93000042, ((Long) m.get("longcode")).longValue());
			assertEquals(3.152F, ((Float) m.get("floatcode")).floatValue());
			// assertEquals(3.151592653589D, ((Double) m.get("doublecode")).doubleValue());
			assertEquals("Hello Will", ((String) m.get("mystr1")));
			assertEquals("Will", ((String) m.get("mystr2")));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * -=
	 */
	public void test_minusassign() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  intcode = 42;");
			sb.append("  intcode -= rc;");
			sb.append("  intcode2 -= rc;");
			sb.append("  longcode = 42L;");
			sb.append("  longcode -= varlong;");
			sb.append("  floatcode = 123.01F;");
			sb.append("  floatcode -= pi;");
			sb.append("  doublecode = 123.01D;");
			sb.append("  doublecode -= pidouble;");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals(INT_21, ((Integer) m.get("intcode")).intValue());
			assertEquals(INT_MINUS_21, ((Integer) m.get("intcode2")).intValue());
			assertEquals(42L - LONG_93000000, ((Long) m.get("longcode")).longValue());
			assertEquals(123.01F - FLOAT_PI, ((Float) m.get("floatcode")).floatValue(), 0.01);
			assertEquals(123.01D - DOUBLE_PI, ((Double) m.get("doublecode")).doubleValue(), 0.01);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * *=
	 */
	public void test_multiplyassign() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  intcode = 42;");
			sb.append("  intcode *= rc;");
			sb.append("  intcode2 *= rc;");
			sb.append("  longcode = 42L;");
			sb.append("  longcode *= varlong;");
			sb.append("  floatcode = 42F;");
			sb.append("  floatcode *= pi;");
			sb.append("  doublecode = 42D;");
			sb.append("  doublecode *= pidouble;");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals(INT_882, ((Integer) m.get("intcode")).intValue());
			assertEquals(0, ((Integer) m.get("intcode2")).intValue());
			assertEquals(42L * LONG_93000000, ((Long) m.get("longcode")).longValue());
			assertEquals(42F * FLOAT_PI, ((Float) m.get("floatcode")).floatValue(), 0.01);
			assertEquals(42D * DOUBLE_PI, ((Double) m.get("doublecode")).doubleValue(), 0.01);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * /=
	 */
	public void test_divideassign() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  intcode = 42;");
			sb.append("  intcode /= rc;");
			sb.append("  intcode2 /= rc;");
			sb.append("  longcode = 42L;");
			sb.append("  longcode /= varlong;");
			sb.append("  floatcode = 42F;");
			sb.append("  floatcode /= pi;");
			sb.append("  doublecode = 42D;");
			sb.append("  doublecode /= pidouble;");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals(2, ((Integer) m.get("intcode")).intValue());
			assertEquals(0, ((Integer) m.get("intcode2")).intValue());
			assertEquals(42L / LONG_93000000, ((Long) m.get("longcode")).longValue());
			assertEquals(42F / FLOAT_PI, ((Float) m.get("floatcode")).floatValue(), 0.01);
			assertEquals(42D / DOUBLE_PI, ((Double) m.get("doublecode")).doubleValue(), 0.01);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * %=
	 */
	public void test_modulusassign() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			sb.append("  intcode = 45;");
			sb.append("  intcode %= rc;");
			sb.append("  intcode2 %= rc;");
			sb.append("  longcode = 45L;");
			sb.append("  longcode %= varlong;");
			sb.append("  floatcode = 45F;");
			sb.append("  floatcode %= pi;");
			sb.append("  doublecode = 45D;");
			sb.append("  doublecode %= pidouble;");
			sb.append("} ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals(3, ((Integer) m.get("intcode")).intValue());
			assertEquals(0, ((Integer) m.get("intcode2")).intValue());
			assertEquals(45L % LONG_93000000, ((Long) m.get("longcode")).longValue());
			assertEquals(45F % FLOAT_PI, ((Float) m.get("floatcode")).floatValue(), 0.01);
			assertEquals(45D % DOUBLE_PI, ((Double) m.get("doublecode")).doubleValue(), 0.01);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test returning a true
	 */
	public void test_return_true() {
		try {
			// return true;
			IExpression cond = Statement.parse("return true; return false;");
			Object objeval = cond.eval(null);
			assertEquals("return true", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test returning a false
	 */
	public void test_return_false() {
		try {
			// return false;
			IExpression cond = Statement.parse("return false;");
			Object objeval = cond.eval(null);
			if (objeval != null && objeval instanceof Boolean) {
				assertEquals("return false", false, ((Boolean) objeval).booleanValue());
			}
			else {
				fail("   FAILED - null condition");
			}
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test returning an object
	 */
	public void test_return_object() {
		try {
			IExpression cond = Statement.parse("return \"success\";");
			Object objeval = cond.eval(null);
			assertEquals("return object", "success", objeval);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_if_return() {
		try {
			IExpression cond = Statement.parse("if (rc == 21) return true; return false;");
			Object objeval = cond.eval(m);
			assertEquals("(rc == 21)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test equality and return true
	 */
	public void test_equal_1() {
		try {
			// Equal - true (rc is a String ("21") in the map)
			IExpression cond = Statement.parse("if (rc == 21) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(rc == 21)", true, ((Boolean) objeval).booleanValue());
			// character
			cond = Statement.parse("if ('A' == 65) return true;");
			objeval = cond.eval(m);
			assertEquals("('A' == 65)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test equality and return false
	 */
	public void test_equal_2() {
		try {
			// Equal - false
			IExpression cond = Statement.parse("if (rc == 21) return false;");
			Object objeval = cond.eval(m);
			assertEquals("(rc == 21)", false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test non-equality and return true
	 */
	public void test_equal_3() {
		try {
			// Not Equal
			IExpression cond = Statement.parse("if (rc == 22) return true;");
			Object objeval = cond.eval(m);
			assertNull("(rc == 22)", objeval);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_equal_boolean_invalid() {
		try {
			IExpression cond = Statement.parse("if (indicator_true == indicator_false) return false;");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/**
	 * Test byte equality and return true
	 */
	public void test_equal_4() {
		try {
			// Equal - byte
			IExpression cond = Statement.parse("if (varbyte == 22) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(varbyte == 22)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_equal_4a() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("if (varbyte == new Byte(\"22\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varbyte == new Byte(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test char equality and return true
	 */
	public void test_equal_5() {
		try {
			// Equal - char
			IExpression cond = Statement.parse("if (varchar == 'a') return true;");
			Object objeval = cond.eval(m);
			assertEquals("(varchar == 'a')", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test short equality and return true
	 */
	public void test_equal_6() {
		try {
			// Equal - short
			IExpression cond = Statement.parse("if (varshort == 256) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(varshort == 256)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_equal_6a() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Equal - short
			cond = Statement.parse("if (varshort == new Short(\"256\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varshort == new Short(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test int equality and return true
	 */
	public void test_equal_7() {
		try {
			// Equal - int
			IExpression cond = Statement.parse("if (varint == 512) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(varint == 512)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test long equality and return true
	 */
	public void test_equal_8() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Equal - long
			cond = Statement.parse("if (dist == 93000000) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (dist == 93000) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test float equality and return true
	 */
	public void test_equal_9() {
		try {
			// Equal - float 1
			IExpression cond = Statement.parse("if (pi == 3.142F) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(pi == 3.142F)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test float equality and return false
	 */
	public void test_equal_10() {
		try {
			// Equal - float 2
			IExpression cond = Statement.parse("if (pi == 3.141F) return false;");
			Object objeval = cond.eval(m);
			assertNull("(pi == 3.141F)", objeval);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test float equality with int
	 */
	public void test_equal_11() {
		try {
			// Equal - float 3
			IExpression cond = Statement.parse("if (pi == 3) return true;");
			Object objeval = cond.eval(m);
			assertNull("(pi == 3)", objeval);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test double equality and return false
	 */
	public void test_equal_12() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Equal - double
			cond = Statement.parse("if (pidouble == 3.141592653589D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pidouble == 1.141592653589D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test null object
	 */
	public void test_equal_13() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// null
			cond = Statement.parse("if (mmexception == null) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (abc == def) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (abc == null) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (null == def) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (null == null) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (myobj == null) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (null == myobj) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (myobj == myobj) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (myobj == myobj2) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test char equality
	 */
	public void test_equal_14() {
		try {
			// equal
			IExpression cond = Statement.parse("if (str.charAt(3) == '0') return true; else return false;");
			m.put("str", "i210_mmfghc");
			Object objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			// not equal
			cond = Statement.parse("if (str.charAt(2) == '0') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());

		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test byte non-equality
	 */
	public void test_notequal_byte() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Byte
			cond = Statement.parse("if (varbyte != new Byte(\"64\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varbyte != new Byte(\"22\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test char non-equality
	 */
	public void test_notequal_char() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Char
			cond = Statement.parse("if (varchar != 'z') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varchar != 'a') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test short non-equality
	 */
	public void test_notequal_short() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Short
			cond = Statement.parse("if (varshort != new Short(\"956\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varshort != new Short(\"256\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test int non-equality
	 */
	public void test_notequal_integer() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Integer
			cond = Statement.parse("if (rc != 99) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (rc != 21) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test long non-equality
	 */
	public void test_notequal_long() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Long
			cond = Statement.parse("if (varlong != 93000999L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varlong != 93000000L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test float non-equality
	 */
	public void test_notequal_float() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Float
			cond = Statement.parse("if (pi != 3.14F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pi != pi) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test double non-equality
	 */
	public void test_notequal_double() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Double
			cond = Statement.parse("if (pidouble != 3.14D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pidouble != pidouble) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test object nonequality
	 */
	public void test_notequal_object() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("if (mmexception != null) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (abc != def) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (abc != null) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (null != def) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (null != null) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (myobj != null) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (null != myobj) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (myobj != myobj) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (myobj != myobj2) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test or operator
	 */
	public void test_or() {
		try {
			// Equal - OR
			IExpression cond = Statement.parse("if (rc == 21 || rc == 22) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(rc == 21 || rc == 22)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test and operator
	 */
	public void test_and() {
		try {
			// Equal - AND
			IExpression cond = Statement.parse("if (rc == 21 && id == 31) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(rc == 21 && id == 31)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test not operator
	 */
	public void test_not_1() {
		try {
			// Not 1
			IExpression cond = Statement.parse("if (!(rc == 99)) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(!(rc == 99))", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test not operator
	 */
	public void test_not_2() {
		try {
			// Not of AND
			IExpression cond = Statement.parse("if (!(rc == 21 && id == 99)) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(!(rc == 21 && id == 99))", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test less than operator with byte
	 */
	public void test_less_than_byte() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Byte
			cond = Statement.parse("if (varbyte < new Byte(\"64\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varbyte < new Byte(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test less than operator with char
	 */
	public void test_less_than_char() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Char
			cond = Statement.parse("if (varchar < 'z') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varchar < 'A') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test less than operator with short
	 */
	public void test_less_than_short() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Short
			cond = Statement.parse("if (varshort < new Short(\"956\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varshort < new Short(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test less than operator with int
	 */
	public void test_less_than_integer() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Integer
			cond = Statement.parse("if (rc < 99) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (rc < 9) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test less than operator with long
	 */
	public void test_less_than_long() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Long
			cond = Statement.parse("if (varlong < 999999999L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varlong < 9L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test less than operator with float
	 */
	public void test_less_than_float() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Float
			cond = Statement.parse("if (pi < 3.20F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pi < 1.20F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test less than operator with double
	 */
	public void test_less_than_double() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Double
			cond = Statement.parse("if (pidouble < 3.2D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pidouble < 1.2D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test less than operator with object
	 */
	public void test_less_than_object() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("if (myobj < myobj2) return true; else return false;");
			objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/**
	 * Test greater than operator with byte
	 */
	public void test_greater_than_byte() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Integer
			cond = Statement.parse("if (varbyte > new Byte(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varbyte > new Byte(\"64\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test greater than operator with char
	 */
	public void test_greater_than_char() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Integer
			cond = Statement.parse("if (varchar > 'A') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varchar > 'z') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test greater than operator with short
	 */
	public void test_greater_than_short() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Integer
			cond = Statement.parse("if (varshort > new Short(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varshort > new Short(\"956\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test greater than operator with int
	 */
	public void test_greater_than_integer() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Integer
			cond = Statement.parse("if (rc > 20) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (rc > 999) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test greater than operator with long
	 */
	public void test_greater_than_long() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Long
			cond = Statement.parse("if (varlong > 99999L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varlong > 999999999L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test greater than operator with float
	 */
	public void test_greater_than_float() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Float
			cond = Statement.parse("if (pi > 3.01F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pi > 9.20F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test greater than operator with double
	 */
	public void test_greater_than_double() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Double
			cond = Statement.parse("if (pidouble > 3.14159D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pidouble > 9.2D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test greater than operator with object
	 */
	public void test_greater_than_object() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("if (myobj > myobj2) return true; else return false;");
			objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/**
	 * Test <= operator
	 */
	public void test_lessthanequal() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Byte
			cond = Statement.parse("if (varbyte <= new Byte(\"25\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varbyte <= new Byte(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Char
			cond = Statement.parse("if (varchar <= 'a') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varchar <= 'Z') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Short
			cond = Statement.parse("if (varshort <= new Short(\"256\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varshort <= new Short(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Integer
			cond = Statement.parse("if (rc <= 21) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (rc <= 31) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (rc <= 3) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Long
			cond = Statement.parse("if (varlong <= 94000000L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varlong <= 94L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Float
			cond = Statement.parse("if (pi <= 3.142F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pi <= 3.143F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pi <= 3.0F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Double
			cond = Statement.parse("if (pidouble <= 3.141592653589D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pidouble <= 3.0D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_lessthanequal_object() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("if (myobj <= myobj2) return true; else return false;");
			objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/**
	 * Test >= operator
	 */
	public void test_greaterthanequal() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Byte
			cond = Statement.parse("if (varbyte >= new Byte(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varbyte >= new Byte(\"25\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Char
			cond = Statement.parse("if (varchar >= 'Z') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varchar >= 'b') return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Short
			cond = Statement.parse("if (varshort >= new Short(\"2\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varshort >= new Short(\"2526\")) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Integer
			cond = Statement.parse("if (rc >= 21) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (rc >= 20) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (rc >= 200) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Long
			cond = Statement.parse("if (varlong >= 940L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (varlong >= 94000000L) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Float
			cond = Statement.parse("if (pi >= 3.142F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pi >= 3.01F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pi >= 9.01F) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
			// Double
			cond = Statement.parse("if (pidouble >= 3.141592653589D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(true, ((Boolean) objeval).booleanValue());
			cond = Statement.parse("if (pidouble >= 9.141592653589D) return true; else return false;");
			objeval = cond.eval(m);
			assertEquals(false, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_greaterthanequal_object() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("if (myobj >= myobj2) return true; else return false;");
			objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/**
	 * Test +
	 */
	public void test_plus_invalid_boolean() {
		try {
			IExpression cond = Statement.parse("val = indicator_true + indicator_false;");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_plus_invalid_boolean2() {
		try {
			IExpression cond = Statement.parse("val = indicator_true + null;");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_plus_invalid_boolean3() {
		try {
			IExpression cond = Statement.parse("val = null + indicator_true;");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_plus_invalid_object() {
		try {
			IExpression cond = Statement.parse("val = myobj + myobj2;");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_plus_byte() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("val = varbyte + new Byte(\"64\");");
			objeval = cond.eval(m);
			assertEquals(BYTE_0X16 + new Byte("64"), m.get("val"));
			cond = Statement.parse("val = null + new Byte(\"64\");");
			objeval = cond.eval(m);
			assertEquals(64, m.get("val"));
			cond = Statement.parse("val = new Byte(\"64\") + null;");
			objeval = cond.eval(m);
			assertEquals(64, m.get("val"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_plus_char() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("val = 'A' + 'a';");
			objeval = cond.eval(m);
			assertEquals('A' + 'a', m.get("val"));
			cond = Statement.parse("val = null + 'a';");
			objeval = cond.eval(m);
			assertEquals(0 + 'a', m.get("val"));
			cond = Statement.parse("val = 'a' + null;");
			objeval = cond.eval(m);
			assertEquals(0 + 'a', m.get("val"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_plus_short() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("val = varshort + new Short(\"2\");");
			objeval = cond.eval(m);
			assertEquals(SHORT_256 + new Short("2"), m.get("val"));
			cond = Statement.parse("val = varshort + null;");
			objeval = cond.eval(m);
			assertEquals(SHORT_256 + 0, m.get("val"));
			cond = Statement.parse("val = null + varshort;");
			objeval = cond.eval(m);
			assertEquals(SHORT_256 + 0, m.get("val"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_plus() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("val = varint + 2;");
			objeval = cond.eval(m);
			assertEquals(514, m.get("val"));
			cond = Statement.parse("val = varint + null;");
			objeval = cond.eval(m);
			assertEquals(512, m.get("val"));
			cond = Statement.parse("val = null + varint;");
			objeval = cond.eval(m);
			assertEquals(512, m.get("val"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_plus_int_long() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("val = varlong + 100L;");
			objeval = cond.eval(m);
			assertEquals(93000100L, m.get("val"));
			cond = Statement.parse("val = varlong + null;");
			objeval = cond.eval(m);
			assertEquals(93000000L, m.get("val"));
			cond = Statement.parse("val = null + varlong;");
			objeval = cond.eval(m);
			assertEquals(93000000L, m.get("val"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_plus_int_float() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("val = pi + 3.0F;");
			objeval = cond.eval(m);
			assertEquals(FLOAT_PI + 3.0F, m.get("val"));
			cond = Statement.parse("val = pi + null;");
			objeval = cond.eval(m);
			assertEquals(FLOAT_PI, m.get("val"));
			cond = Statement.parse("val = null + pi;");
			objeval = cond.eval(m);
			assertEquals(FLOAT_PI, m.get("val"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_plus_int_double() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("val = pidouble + 3.0D;");
			objeval = cond.eval(m);
			assertEquals(DOUBLE_PI + 3.0D, m.get("val"));
			cond = Statement.parse("val = pidouble + null;");
			objeval = cond.eval(m);
			assertEquals(DOUBLE_PI, m.get("val"));
			cond = Statement.parse("val = null + pidouble;");
			objeval = cond.eval(m);
			assertEquals(DOUBLE_PI, m.get("val"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_plus_invalid_variable() {
		try {
			IExpression cond = Statement.parse("num1 = 12 + bogus;");
			Object objeval = cond.eval(m);
			assertEquals(12, m.get("num1"));
			cond = Statement.parse("num2 = bogus + 23;");
			objeval = cond.eval(m);
			assertEquals(23, m.get("num2"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test + operator for string concat
	 */
	public void test_plus_string_concat() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  { ");
			sb.append("    message = \"hello\";");
			sb.append("    message = message + \"there\";");
			sb.append("    msg = \"You\" + \" cannot\" + \" be\" + \" serious.\";");
			sb.append("    text = \"Good \";");
			sb.append("    text += \"morning.\";");
			sb.append("    txt1 = \"Area \" + 51;");
			sb.append("    txt2 = \"hi\";");
			sb.append("    txt2 += 12;");
			sb.append("    var1 = \"varint=\" + varint;");
			sb.append("    var2 = \"dist=\" + dist;");
			sb.append("    var3 = \"pi=\" + pi;");
			sb.append("    nullconcat = \"Hello\" + bogus;");
			sb.append("  } ");
			IExpression cond = Statement.parse(sb.toString());
			cond.eval(m);
			assertEquals("hellothere", m.get("message"));
			assertEquals("You cannot be serious.", m.get("msg"));
			assertEquals("Good morning.", m.get("text"));
			assertEquals("Area 51", m.get("txt1"));
			assertEquals("hi12", m.get("txt2"));
			assertEquals("varint=512", m.get("var1"));
			assertEquals("dist=93000000", m.get("var2"));
			assertEquals("pi=3.142", m.get("var3"));
			assertEquals("Hello", m.get("nullconcat"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test - operator
	 */
	public void test_minus() {
		try {
			//
			IExpression cond = Statement.parse("if ((31 - 12) < 21) return true;");
			Object objeval = cond.eval(m);
			assertEquals("((31 - 12) < 21)", true, ((Boolean) objeval).booleanValue());
			// Minus long
			cond = Statement.parse("result = 31L - 12L;");
			objeval = cond.eval(m);
			assertEquals("31L - 12L", 31L - 12L, ((Long) m.get("result")).longValue());
			// Minus float
			cond = Statement.parse("result = 31F - 12F;");
			objeval = cond.eval(m);
			assertEquals("31F - 12F", 31F - 12F, ((Float) m.get("result")).floatValue(), 0.1);
			// Minus double
			cond = Statement.parse("result = 31D - 12D;");
			objeval = cond.eval(m);
			assertEquals("31D - 12D", 31D - 12D, ((Double) m.get("result")).doubleValue(), 0.1);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test / operator
	 */
	public void test_divide() {
		try {
			// Divide 1
			IExpression cond = Statement.parse("if ((144 / 12) < 21) return true;");
			Object objeval = cond.eval(m);
			assertEquals("((144 / 12) < 21)", true, ((Boolean) objeval).booleanValue());
			// Divide 2
			cond = Statement.parse("if ((4096 / 8) == 512) return true;");
			objeval = cond.eval(m);
			assertEquals("((4096 / 8) == 512)", true, ((Boolean) objeval).booleanValue());
			// Divide int
			cond = Statement.parse("result = 14 / 8;");
			objeval = cond.eval(m);
			assertEquals("14 / 8", 1, ((Integer) m.get("result")).intValue());
			// Divide long
			cond = Statement.parse("result = 14L / 8L;");
			objeval = cond.eval(m);
			assertEquals("14L / 8L", 14L / 8L, ((Long) m.get("result")).longValue());
			// Divide float
			cond = Statement.parse("result = 14F / 8F;");
			objeval = cond.eval(m);
			assertEquals("14F / 8F", 14F / 8F, ((Float) m.get("result")).floatValue(), 0.1);
			// Divide double
			cond = Statement.parse("result = 14D / 8D;");
			objeval = cond.eval(m);
			assertEquals("14D / 8D", 14D / 8D, ((Double) m.get("result")).doubleValue(), 0.1);
			// Divide 5
			cond = Statement.parse("overflow = 4 / 0;");
			try {
				objeval = cond.eval(m);
				fail("   FAILED. ArithmeticException NOT caught.");
			}
			catch (ArithmeticException ae) {
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test % operator
	 */
	public void test_modulus() {
		try {
			// Modulus 1
			IExpression cond = Statement.parse("if ((146 % 12) == 2) return true;");
			Object objeval = cond.eval(m);
			assertEquals("((146 % 12) == 2)", true, ((Boolean) objeval).booleanValue());
			// Modulus 2
			cond = Statement.parse("if ((149 % 12) == 5) return true;");
			objeval = cond.eval(m);
			assertEquals("((149 % 12) == 5)", true, ((Boolean) objeval).booleanValue());
			// Modulus long
			cond = Statement.parse("result = 149L % 12L;");
			objeval = cond.eval(m);
			assertEquals("149L % 12L", 149L % 12L, ((Long) m.get("result")).longValue());
			// Modulus float
			cond = Statement.parse("result = 149F % 12F;");
			objeval = cond.eval(m);
			assertEquals("149F % 12F", 149F % 12F, ((Float) m.get("result")).floatValue());
			// Modulus double
			cond = Statement.parse("result = 149D % 12D;");
			objeval = cond.eval(m);
			assertEquals("149D % 12D", 149D % 12D, ((Double) m.get("result")).doubleValue(), 0.01);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test * operator
	 */
	public void test_multiply() {
		try {
			// Multiply 1
			IExpression cond = Statement.parse("if ((6 * 3) < 21) return true;");
			Object objeval = cond.eval(m);
			assertEquals("((6 * 3) < 21)", true, ((Boolean) objeval).booleanValue());
			// Multiply 2
			cond = Statement.parse("if ((512 * 8) == 4096) return true;");
			objeval = cond.eval(m);
			assertEquals("((512 * 8) == 4096)", true, ((Boolean) objeval).booleanValue());
			// Multiply long
			cond = Statement.parse("result = 3L * 6L;");
			objeval = cond.eval(m);
			assertEquals("3L * 6L", 18, ((Long) m.get("result")).longValue());
			// Multiply float
			cond = Statement.parse("result = 3.2F * 6.0F;");
			objeval = cond.eval(m);
			assertEquals("3.2F * 6L", 19.2F, ((Float) m.get("result")).floatValue());
			// Multiply double
			cond = Statement.parse("result = 3.2D * 6.0D;");
			objeval = cond.eval(m);
			assertEquals("3.2D * 6.0D", 19.2D, ((Double) m.get("result")).doubleValue(), 0.01);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ prefix operator
	 */
	public void test_pre_inc() {
		try {
			// Inc 1
			IExpression cond = Statement.parse("if (++21 > 21) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(++21 > 21)", true, ((Boolean) objeval).booleanValue());
			// Inc 2
			cond = Statement.parse("if (++20 == 21) return true;");
			objeval = cond.eval(m);
			assertEquals("(++20 == 21)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ prefix operator for byte variable
	 */
	public void test_pre_inc_byte() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Byte(\"3\"); j=++i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=++i", 4, ((Byte) ival).byteValue());
			assertEquals("j=++i", 4, ((Byte) jval).byteValue());
			cond = Statement.parse("x=++(new java.lang.Byte(\"2\"));");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(3, ((Byte) x).byteValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ prefix operator for char variable
	 */
	public void test_pre_inc_char() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= 'a'; j=++i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals('b', ((Character) ival).charValue());
			assertEquals('b', ((Character) jval).charValue());
			cond = Statement.parse("x=++('a');");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals('b', ((Character) x).charValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ prefix operator for short variable
	 */
	public void test_pre_inc_short() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Short(\"3\"); j=++i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=++i", 4, ((Short) ival).shortValue());
			assertEquals("j=++i", 4, ((Short) jval).shortValue());
			cond = Statement.parse("x=++(new java.lang.Short(\"2\"));");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(3, ((Short) x).shortValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ prefix operator for integer variable
	 */
	public void test_pre_inc_integer() {
		try {
			// int i=3; int j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3; j=++i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=++i", 4, ((Integer) ival).intValue());
			assertEquals("j=++i", 4, ((Integer) jval).intValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ prefix operator for long variable
	 */
	public void test_pre_inc_long() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Long(\"3\"); j=++i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=++i", 4, ((Long) ival).longValue());
			assertEquals("j=++i", 4, ((Long) jval).longValue());
			cond = Statement.parse("x=++2L;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(3L, ((Long) x).longValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ prefix operator for float variable
	 */
	public void test_pre_inc_float() {
		try {
			// double i=3.14; double j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3.14F; j=++i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals(4.14F, ((Float) ival).floatValue(), 0.0001);
			assertEquals(4.14F, ((Float) jval).floatValue(), 0.0001);
			cond = Statement.parse("x=++2.5F;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(3.5F, ((Float) x).floatValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ prefix operator for double variable
	 */
	public void test_pre_inc_double() {
		try {
			// double i=3.14; double j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3.14D; j=++i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=++i", 4.14D, ((Double) ival).doubleValue(), 0.0001);
			assertEquals("j=++i", 4.14D, ((Double) jval).doubleValue(), 0.0001);
			cond = Statement.parse("x=++2.5D;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(3.5D, ((Double) x).doubleValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- prefix operator
	 */
	public void test_pre_dec() {
		try {
			// Dec 1
			IExpression cond = Statement.parse("if (--21 < 21) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(--21 < 21)", true, ((Boolean) objeval).booleanValue());
			// Dec 2
			cond = Statement.parse("if (--22 == 21) return true;");
			objeval = cond.eval(m);
			assertEquals("(--22 == 21)", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- prefix operator for byte variable
	 */
	public void test_pre_dec_byte() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Byte(\"3\"); j=--i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals(2, ((Byte) ival).byteValue());
			assertEquals(2, ((Byte) jval).byteValue());
			cond = Statement.parse("x=--(new java.lang.Byte(\"2\"));");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(1, ((Byte) x).byteValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- prefix operator for char variable
	 */
	public void test_pre_dec_char() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= 'b'; j=--i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals('a', ((Character) ival).charValue());
			assertEquals('a', ((Character) jval).charValue());
			cond = Statement.parse("x=--('b');");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals('a', ((Character) x).charValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- prefix operator for short variable
	 */
	public void test_pre_dec_short() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Short(\"3\"); j=--i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals(2, ((Short) ival).shortValue());
			assertEquals(2, ((Short) jval).shortValue());
			cond = Statement.parse("x=--(new java.lang.Short(\"2\"));");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(1, ((Short) x).shortValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- prefix operator for integer variable
	 */
	public void test_pre_dec_integer() {
		try {
			// int i=3; int j=--i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3; j=--i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=--i", 2, ((Integer) ival).intValue());
			assertEquals("j=--i", 2, ((Integer) jval).intValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- prefix operator for long variable
	 */
	public void test_pre_dec_long() {
		try {
			// long i=3; long j=--i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Long(\"3\"); j=--i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=--i", 2, ((Long) ival).longValue());
			assertEquals("j=--i", 2, ((Long) jval).longValue());
			cond = Statement.parse("x=--3L;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2L, ((Long) x).longValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- prefix operator for float variable
	 */
	public void test_pre_dec_float() {
		try {
			// double i=3.14; double j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3.14F; j=--i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals(2.14F, ((Float) ival).floatValue(), 0.0001);
			assertEquals(2.14F, ((Float) jval).floatValue(), 0.0001);
			cond = Statement.parse("x=--2.5F;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(1.5F, ((Float) x).floatValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- prefix operator for double variable
	 */
	public void test_pre_dec_double() {
		try {
			// double i=3.14; double j=--i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3.14D; j=--i; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=--i", 2.14D, ((Double) ival).doubleValue(), 0.0001);
			assertEquals("j=--i", 2.14D, ((Double) jval).doubleValue(), 0.0001);
			cond = Statement.parse("x=--2.5D;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(1.5D, ((Double) x).doubleValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ postfix operator
	 */
	public void test_post_inc() {
		try {
			// Inc 1
			IExpression cond = Statement.parse("if (21++ == 21) return true; else return false;");
			Object objeval = cond.eval(m);
			assertEquals("(++21 == 21)", true, ((Boolean) objeval).booleanValue());
			// Inc 2
			cond = Statement.parse("return 21++;");
			objeval = cond.eval(m);
			assertEquals("21++", 21, ((Integer) objeval).intValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ postfix operator for byte variable
	 */
	public void test_post_inc_byte() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Byte(\"3\"); j=i++; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=++i", 4, ((Byte) ival).byteValue());
			assertEquals("j=++i", 3, ((Byte) jval).byteValue());
			cond = Statement.parse("x=(new java.lang.Byte(\"2\"))++;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2, ((Byte) x).byteValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ postfix operator for char variable
	 */
	public void test_post_inc_char() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= 'a'; j=i++; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals('b', ((Character) ival).charValue());
			assertEquals('a', ((Character) jval).charValue());
			cond = Statement.parse("x=('a')++;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals('a', ((Character) x).charValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ postfix operator for short variable
	 */
	public void test_post_inc_short() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Short(\"3\"); j=i++; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=++i", 4, ((Short) ival).shortValue());
			assertEquals("j=++i", 3, ((Short) jval).shortValue());
			cond = Statement.parse("x=(new java.lang.Short(\"2\"))++;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2, ((Short) x).shortValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ postfix operator for integer variable
	 */
	public void test_post_inc_integer() {
		try {
			// int i=3; int j=i++; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3; j=i++; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=i++", 4, ((Integer) ival).intValue());
			assertEquals("j=i++", 3, ((Integer) jval).intValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ postfix operator for long variable
	 */
	public void test_post_inc_long() {
		try {
			// long i=3; long j=i++; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Long(\"3\"); j=i++; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=i++", 4, ((Long) ival).longValue());
			assertEquals("j=i++", 3, ((Long) jval).longValue());
			cond = Statement.parse("x=2L++;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2L, ((Long) x).longValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ postfix operator for float variable
	 */
	public void test_post_inc_float() {
		try {
			// double i=3.14; double j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3.14F; j=i++; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals(4.14F, ((Float) ival).floatValue(), 0.0001);
			assertEquals(3.14F, ((Float) jval).floatValue(), 0.0001);
			cond = Statement.parse("x=2.5F++;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2.5F, ((Float) x).floatValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test ++ postfix operator for double variable
	 */
	public void test_post_inc_double() {
		try {
			// double i=3.14; double j=i++; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3.14; j=i++; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=i++", 4.14D, ((Double) ival).doubleValue(), 0.0001);
			assertEquals("j=i++", 3.14D, ((Double) jval).doubleValue(), 0.0001);
			cond = Statement.parse("x=2.5D++;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2.5D, ((Double) x).doubleValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- postfix operator
	 */
	public void test_post_dec() {
		try {
			// Dec 1
			IExpression cond = Statement.parse("if (21-- == 21) return true; else return false;");
			Object objeval = cond.eval(m);
			assertEquals("(21-- == 21)", true, ((Boolean) objeval).booleanValue());
			// Dec 2
			cond = Statement.parse("return 21--;");
			objeval = cond.eval(m);
			assertEquals("21--", 21, ((Integer) objeval).intValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- postfix operator for byte variable
	 */
	public void test_post_dec_byte() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Byte(\"3\"); j=i--; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals(2, ((Byte) ival).byteValue());
			assertEquals(3, ((Byte) jval).byteValue());
			cond = Statement.parse("x=(new java.lang.Byte(\"2\"))--;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2, ((Byte) x).byteValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- postfix operator for char variable
	 */
	public void test_post_dec_char() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= 'b'; j=i--; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals('a', ((Character) ival).charValue());
			assertEquals('b', ((Character) jval).charValue());
			cond = Statement.parse("x=('b')--;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals('b', ((Character) x).charValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- postfix operator for short variable
	 */
	public void test_post_dec_short() {
		try {
			// long i=3; long j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Short(\"3\"); j=i--; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals(2, ((Short) ival).shortValue());
			assertEquals(3, ((Short) jval).shortValue());
			cond = Statement.parse("x=(new java.lang.Short(\"2\"))--;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2, ((Short) x).shortValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- postfix operator for integer variable
	 */
	public void test_post_dec_integer() {
		try {
			// int i=3; int j=i--; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3; j=i--; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=i--", 2, ((Integer) ival).intValue());
			assertEquals("j=i--", 3, ((Integer) jval).intValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- postfix operator for long variable
	 */
	public void test_post_dec_long() {
		try {
			// long i=3; long j=i--; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i= new java.lang.Long(\"3\"); j=i--; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=i--", 2, ((Long) ival).longValue());
			assertEquals("j=i--", 3, ((Long) jval).longValue());
			cond = Statement.parse("x=3L--;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(3L, ((Long) x).longValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- postfix operator for float variable
	 */
	public void test_post_dec_float() {
		try {
			// double i=3.14; double j=++i; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3.14F; j=i--; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals(2.14F, ((Float) ival).floatValue(), 0.0001);
			assertEquals(3.14F, ((Float) jval).floatValue(), 0.0001);
			cond = Statement.parse("x=2.5F--;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2.5F, ((Float) x).floatValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test -- postfix operator for double variable
	 */
	public void test_post_dec_double() {
		try {
			// double i=3.14; double j=i--; System.out.print("i=" + i + " " + "j=" + j);
			IExpression cond = Statement.parse("{ i=3.14; j=i--; ival=i; jval=j; }");
			Object objeval = cond.eval(m);
			Object ival = m.get("ival");
			Object jval = m.get("jval");
			assertEquals("j=i--", 2.14D, ((Double) ival).doubleValue(), 0.0001);
			assertEquals("j=i--", 3.14D, ((Double) jval).doubleValue(), 0.0001);
			cond = Statement.parse("x=2.5D--;");
			objeval = cond.eval(m);
			Object x = m.get("x");
			assertEquals(2.5D, ((Double) x).doubleValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test mixture of pre & post increment
	 */
	public void test_pre_and_post_inc() {
		try {
			// int i=5; int j=++i + ++i + i++; System.out.print("j=" + j);
			IExpression cond = Statement.parse("{ i = 5; jval = ++i + ++i + i++; }");
			Object objeval = cond.eval(m);
			Object jval = m.get("jval");
			assertEquals("j=i++", 20, ((Integer) jval).intValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test unary - operator
	 */
	public void test_unary_minus() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Byte
			cond = Statement.parse("bytevar = -(new Byte(\"-8\"));");
			objeval = cond.eval(m);
			assertEquals(-(new Byte("-8")), ((Integer) m.get("bytevar")).intValue());
			// Char
			cond = Statement.parse("charvar = -(new Character('a'));");
			objeval = cond.eval(m);
			assertEquals(-(new Character('a')), ((Integer) m.get("charvar")).intValue());
			// Short
			cond = Statement.parse("shortvar = -(new Short(\"-8\"));");
			objeval = cond.eval(m);
			assertEquals(-(new Short("-8")), ((Integer) m.get("shortvar")).intValue());
			// Integer
			cond = Statement.parse("intvar = -(12 - 34);");
			objeval = cond.eval(m);
			assertEquals(-(12 - 34), ((Integer) m.get("intvar")).intValue());
			cond = Statement.parse("intvar = -(-34);");
			objeval = cond.eval(m);
			assertEquals(34, ((Integer) m.get("intvar")).intValue());
			// Long
			cond = Statement.parse("longvar = -(12L - 34L);");
			objeval = cond.eval(m);
			assertEquals(-(12L - 34L), ((Long) m.get("longvar")).longValue());
			// Float
			cond = Statement.parse("floatvar = -(12F - 34F);");
			objeval = cond.eval(m);
			assertEquals(-(12F - 34F), ((Float) m.get("floatvar")).floatValue());
			// Double
			cond = Statement.parse("doublevar = -(12D - 34D);");
			objeval = cond.eval(m);
			assertEquals(-(12D - 34D), ((Double) m.get("doublevar")).doubleValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test unary + operator
	 */
	public void test_unary_plus() {
		IExpression cond = null;
		Object objeval = null;
		try {
			// Byte
			cond = Statement.parse("bytevar = +(new Byte(\"-8\"));");
			objeval = cond.eval(m);
			assertEquals(+(new Byte("-8")), ((Integer) m.get("bytevar")).intValue());
			// Char
			cond = Statement.parse("charvar = +(new Character('a'));");
			objeval = cond.eval(m);
			assertEquals(+(new Character('a')), ((Integer) m.get("charvar")).intValue());
			// Short
			cond = Statement.parse("shortvar = +(new Short(\"-8\"));");
			objeval = cond.eval(m);
			assertEquals(+(new Short("-8")), ((Integer) m.get("shortvar")).intValue());
			// Integer
			cond = Statement.parse("intvar = +(12 - 34);");
			objeval = cond.eval(m);
			assertEquals(+(12 - 34), ((Integer) m.get("intvar")).intValue());
			// Long
			cond = Statement.parse("longvar = +(12L - 34L);");
			objeval = cond.eval(m);
			assertEquals(+(12L - 34L), ((Long) m.get("longvar")).longValue());
			// Float
			cond = Statement.parse("floatvar = +(12F - 34F);");
			objeval = cond.eval(m);
			assertEquals(+(12F - 34F), ((Float) m.get("floatvar")).floatValue());
			// Double
			cond = Statement.parse("doublevar = +(12D - 34D);");
			objeval = cond.eval(m);
			assertEquals(+(12D - 34D), ((Double) m.get("doublevar")).doubleValue());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_unary_invalid_type() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("var = +(myobj);");
			objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/**
	 * Unescape Java characters in a Java expression
	 */
	public void test_unescape() {
		try {
			String expr = "message = \"This is line one\\nThis is line two\\n\\tThis line is tab indented.\";";
			IExpression cond = Statement.parse(expr);
			cond.eval(m);
			// System.out.println(m.get("message"));
			assertEquals("This is line one\nThis is line two\n\tThis line is tab indented.", m.get("message"));
			expr = "message = \"Return \\r \\b \\f \\\" \\\\ \\' \\011 \\411 .\";";
			cond = Statement.parse(expr);
			expr = "char = '\\u0021';";
			cond = Statement.parse(expr);
			expr = "val = 123e2D;";
			cond = Statement.parse(expr);
			expr = "val = 123e2d;";
			cond = Statement.parse(expr);
			expr = "val = 123E2d;";
			cond = Statement.parse(expr);
			expr = "val = 123E2D;";
			cond = Statement.parse(expr);
			expr = "val = 123e2F;";
			cond = Statement.parse(expr);
			expr = "val = 123e2f;";
			cond = Statement.parse(expr);
			expr = "val = 123E2f;";
			cond = Statement.parse(expr);
			expr = "val = 123E2F;";
			cond = Statement.parse(expr);
			expr = "val = 123E+2D;";
			cond = Statement.parse(expr);
			expr = "val = 123E-2D;";
			cond = Statement.parse(expr);
			expr = "val = 123l;";
			cond = Statement.parse(expr);
			expr = "val = 123f;";
			cond = Statement.parse(expr);
			expr = "val = 123d;";
			cond = Statement.parse(expr);
//			expr = "val = 0X11;";
//			cond = Statement.parse(expr);
			m.put("_var", "_");
			expr = "val = _var;";
			cond = Statement.parse(expr);
			m.put("$var", "$");
			expr = "val = $var;";
			cond = Statement.parse(expr);
			m.put("my$var", "$");
			expr = "val = my$var;";
			cond = Statement.parse(expr);

			// Construct StatementLexer with InputStream.
			InputStream is = new FileInputStream("pom.xml");
			new StatementLexer(is);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_invalid_ident() {
		try {
			IExpression cond = Statement.parse("val = @var;");
			m.put("@var", "@");
			cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public void test_instanceof() {
		try {
			// java.lang.String
			IExpression cond = Statement.parse("{ str = \"Hello\"; return str instanceof java.lang.String; }");
			Object objeval = cond.eval(m);
			assertTrue(((Boolean) objeval).booleanValue());
			// java.util.Date
			cond = Statement.parse("{ dt = new java.util.Date(); return dt instanceof java.util.Date; }");
			objeval = cond.eval(m);
			assertTrue(((Boolean) objeval).booleanValue());
			// Object
			cond = Statement.parse("{ dt = new java.util.Date(); return dt instanceof Object; }");
			objeval = cond.eval(m);
			assertTrue(((Boolean) objeval).booleanValue());
			// Not instance of
			cond = Statement.parse("{ dt = new java.util.Date(); return dt instanceof java.lang.String; }");
			objeval = cond.eval(m);
			assertFalse(((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			if (e instanceof ExpressionException) {
				System.out.println(((ExpressionException) e).getPrintStackTrace());
			}
			else {
				e.printStackTrace();
			}
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_instanceof_invalid_class() {
		try {
			IExpression cond = Statement.parse("{ dt = new java.util.Date(); return dt instanceof Bogus; }");
			cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_statement_list() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("a=12;");
			sb.append("b=34;");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			Integer a = (Integer) m.get("a");
			Integer b = (Integer) m.get("b");
			assertEquals(new Integer(12), a);
			assertEquals(null, b);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_statement_block() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("   a=12;");
			sb.append("   b=34;");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			Integer a = (Integer) m.get("a");
			Integer b = (Integer) m.get("b");
			assertEquals(new Integer(12), a);
			assertEquals(new Integer(34), b);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Run performance test
	 */
	public void test_performance() {
		try {
			StopWatch sw = new StopWatch();
			sw.start();
			IExpression cond = Statement.parse("if (myobj != null && myobj.rc == 42) return true;");
			sw.stop();
			System.out.println("Statement parse: " + sw.getTime() + " ms.");
			int iterations = ITER;
			sw.reset();
			sw.start();
			for (int i = 0; i < iterations; i++) {
				Object objeval = cond.eval(m);
				if (!((Boolean) objeval).booleanValue()) {
					throw new Exception("eval not true"); // NOPMD - Allow in test
				}
			}
			sw.stop();
			System.out.println("Iterations: " + iterations + " Time: " + sw.getTime() + " ms.");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public class MyClass {
		public int rc = 0;
		public String mystring = null;

		public MyClass() {
		}

		public MyClass(int i, String str) {
			rc = i;
			mystring = str;
		}

		public int doubleit(int i) {
			return (i * 2);
		}

		public String welcome() {
			return mystring;
		}
	}
}
