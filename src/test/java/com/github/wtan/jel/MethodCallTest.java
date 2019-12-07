package com.github.wtan.jel;

import java.util.HashMap;
import java.util.Map;

import com.github.wtan.jel.IExpression;
import com.github.wtan.jel.IdentValue;
import com.github.wtan.jel.MethodCall;
import com.github.wtan.jel.Statement;
import com.github.wtan.jel.exception.ExpressionException;

import junit.framework.TestCase;

public class MethodCallTest extends TestCase {
	private static final byte BYTE_0X16 = 0x16;
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

	public MethodCallTest(String name) {
		super(name);
	}

	public void setUp() {
		m = new HashMap();
		m.put("rc", "21");
		m.put("id", "31");
		m.put("varbyte", new Byte(BYTE_0X16));
		m.put("varchar", new Character(CHAR_A));
		m.put("varshort", new Short(SHORT_256));
		m.put("varint", new Integer(INT_512));
		m.put("varlong", new Long(LONG_93000000));
		m.put("dist", new Long(LONG_93000000));
		m.put("pi", new Float(FLOAT_PI));
		m.put("pidouble", new Double(DOUBLE_PI));
		m.put("description", "Ask not what your country can do for you...");
		m.put("myobj", new MyTestClass(INT_42, "hello"));
	}

	/**
	 * Test method call
	 */
	public void test_method_call() {
		try {
			// length
			IExpression cond = Statement.parse("if (description.length() > 32) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(description.length() > 32)", true, ((Boolean) objeval).booleanValue());
			// indexOf
			cond = Statement.parse("if (description.indexOf(\"country\") > -1) return true;");
			objeval = cond.eval(m);
			assertEquals("(description.indexOf(\"country\") > -1)", true, ((Boolean) objeval).booleanValue());
			// equals
			cond = Statement.parse("if (rc.equals(\"21\")) return true;");
			objeval = cond.eval(m);
			assertEquals("(rc.equals(\"21\"))", true, ((Boolean) objeval).booleanValue());
			// substring/equals
			cond = Statement.parse("if (description.substring(18, 25).equals(\"country\")) return true;");
			objeval = cond.eval(m);
			assertEquals("(description.substring(18, 25).equals(\"country\"))", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			if (e instanceof ExpressionException) {
				System.out.println( ( (ExpressionException) e).getPrintStackTrace());
			}
			else {
				e.printStackTrace();
			}
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_method_call_invalid_method() {
		try {
			// null parameter
			IExpression cond = Statement.parse("return org.apache.commons.lang.StringEscapeUtils.bogus(null);");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			// Success
		}
	}

	public void test_method_call_no_method() {
		try {
			// null parameter
			IExpression cond = Statement.parse("return org.apache.commons.lang.StringEscapeUtils.(null);");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			// Success
		}
	}

	public void test_method_call_null_parameter() {
		try {
			// null parameter
			IExpression cond = Statement.parse("return org.apache.commons.lang.StringEscapeUtils.unescapeJava(null);");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			// Success
		}
	}

	public void test_method_call_null_object_parameter() {
		try {
			// null object parameter
			IExpression cond = Statement.parse("{ myobj = null; return org.apache.commons.lang.StringEscapeUtils.unescapeJava(myobj); }");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			// Success
		}
	}

	/**
	 * Test calling method of an object attribute of a class. 
	 */
	public void test_object_attribute_method_call() {
		try {
			IExpression cond = Statement.parse("System.out.println(\"Hello\\tWill.\");");
			Object objeval = cond.eval(m);
			// Success - no exception
		}
		catch (Exception e) {
			if (e instanceof ExpressionException) {
				System.out.println( ( (ExpressionException) e).getPrintStackTrace());
			}
			else {
				e.printStackTrace();
			}
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test calling static method in specified package/class. 
	 */
	public void test_package_method_call() {
		try {
			// String.valueOf
			IExpression cond = Statement.parse("{ num = 42; return java.lang.String.valueOf(num);}");
			Object objeval = cond.eval(m);
			assertTrue(((String) objeval).contains("42"));
			// StringEscapeUtils.unescapeJava
			cond = Statement.parse("return org.apache.commons.lang.StringEscapeUtils.unescapeJava(\"Hello\\nWorld.\");");
			objeval = cond.eval(m);
			assertTrue(((String) objeval).contains("Hello\nWorld."));
			// String in default java.lang package
			cond = Statement.parse("{ num = 42; return String.valueOf(num);}");
			objeval = cond.eval(m);
			assertTrue(((String) objeval).contains("42"));
			// Calendar in default java.util package
			cond = Statement.parse("{ return Calendar.getInstance();}");
			Object objcalendar = cond.eval(m);
			assertTrue(objcalendar instanceof java.util.Calendar);

		}
		catch (Exception e) {
			if (e instanceof ExpressionException) {
				System.out.println( ( (ExpressionException) e).getPrintStackTrace());
			}
			else {
				e.printStackTrace();
			}
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	/**
	 * Test object field
	 */
	public void test_object_field() {
		try {
			//
			IExpression cond = Statement.parse("if (myobj.rc == 42) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(myobj.rc == 42)", true, ((Boolean) objeval).booleanValue());
			//
			cond = Statement.parse("if (myobj.mystring.equals(\"hello\")) return true;");
			objeval = cond.eval(m);
			assertEquals("(myobj.mystring.equals(\"hello\"))", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			if (e instanceof ExpressionException) {
				System.out.println( ( (ExpressionException) e).getPrintStackTrace());
			}
			else {
				e.printStackTrace();
			}
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_invalid_object_field() {
		try {
			//
			IExpression cond = Statement.parse("if (myobj.abc == 42) return true;");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_invalid_object() {
		try {
			//
			IExpression cond = Statement.parse("if (myobjxxx == 42) return true;");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_no_object() {
		try {
			//
			IExpression cond = Statement.parse("if (.rc == 42) return true;");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/**
	 * Test object method
	 */
	public void test_object_method() {
		try {
			// welcome
			IExpression cond = Statement.parse("if (myobj.welcome().equals(\"hello\")) return true;");
			Object objeval = cond.eval(m);
			assertEquals("(myobj.welcome().equals(\"hello\"))", true, ((Boolean) objeval).booleanValue());
			// doubleit
			cond = Statement.parse("if (myobj.doubleit(42) == 84 ) return true;");
			objeval = cond.eval(m);
			assertEquals("(myobj.doubleit(42) == 84 )", true, ((Boolean) objeval).booleanValue());
		}
		catch (Exception e) {
			if (e instanceof ExpressionException) {
				System.out.println( ( (ExpressionException) e).getPrintStackTrace());
			}
			else {
				e.printStackTrace();
			}
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_invalid_dot_object() {
		try {
			IExpression cond = new MethodCall(new IdentValue(""), null);
			Map m = new HashMap();
			cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}
}
