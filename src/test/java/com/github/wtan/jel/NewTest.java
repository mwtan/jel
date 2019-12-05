package com.github.wtan.jel;

import java.util.HashMap;
import java.util.Map;

import com.github.wtan.jel.IExpression;
import com.github.wtan.jel.Statement;
import com.github.wtan.jel.exception.ExpressionException;

import junit.framework.TestCase;

public class NewTest extends TestCase {
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

	public NewTest(String name) {
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
	}

	public void test_new_const_constructor() {
		try {
			IExpression cond = Statement.parse("obj = new java.util.ArrayList(7);");
			Object objeval = cond.eval(m);
			Object newobj = m.get("obj");
			assertTrue(newobj instanceof java.util.ArrayList);
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

	public void test_new_object_constructor() {
		try {
			IExpression cond = Statement.parse("{ str = \"Hello\"; obj = new java.lang.StringBuilder(str);}");
			Object objeval = cond.eval(m);
			Object newobj = m.get("obj");
			assertTrue(newobj instanceof java.lang.StringBuilder);
			assertEquals("Hello", m.get("obj").toString());
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

	public void test_new_no_matching_constructor() {
		try {
			IExpression cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(true, 2);");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void test_new_instantiation_exception() {
		try {
			IExpression cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(true, 2, \"hi\");");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public void test_new_parm_types() {
		IExpression cond = null;
		Object objeval = null;
		try {
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(true);");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(new Byte(\"2\"));");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass('a');");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(new Short(\"2\"));");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(2);");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(2L);");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(2.0F);");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(2.0D);");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
			cond = Statement.parse("return new com.github.wtan.jel.MyTestClass(2, \"hi\");");
			objeval = cond.eval(m);
			assertTrue(objeval instanceof com.github.wtan.jel.MyTestClass);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_new_object_constructor_new() {
		try {
			IExpression cond = Statement.parse("obj = new java.lang.StringBuilder(new java.lang.String(\"World\"));");
			Object objeval = cond.eval(m);
			Object newobj = m.get("obj");
			assertTrue(newobj instanceof java.lang.StringBuilder);
			assertEquals("World", m.get("obj").toString());
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

	public void test_new_constructor_with_object_sub_type() {
		try {
			IExpression cond = Statement.parse("buffreader = new java.io.BufferedReader(new java.io.FileReader(\"pom.xml\"));");
			Object objeval = cond.eval(m);
			Object buffreader = m.get("buffreader");
			assertTrue(buffreader != null);
			assertTrue(buffreader instanceof java.io.BufferedReader);
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

	public void test_invalid_class() {
		try {
			IExpression cond = Statement.parse("obj = new xyz.Bogus(7);");
			Object objeval = cond.eval(m);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}
}
