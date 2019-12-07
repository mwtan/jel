package com.github.wtan.jel;

import java.util.HashMap;
import java.util.Map;

import com.github.wtan.jel.IExpression;
import com.github.wtan.jel.Statement;
import com.github.wtan.jel.exception.ExpressionException;

import junit.framework.TestCase;

public class IfTest extends TestCase {
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

	public IfTest(String name) {
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

	public void test_if_with_and() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  if (myobj != null && myobj.rc == 42) { ");
			sb.append("    return true; ");
			sb.append("  } ");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			assertEquals("Complex statement 1", true, ((Boolean) objeval).booleanValue());
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

	public void test_if_with_else() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  if (myobj != null && myobj.rc != 42) { ");
			sb.append("    return true; ");
			sb.append("  } ");
			sb.append("  else { ");
			sb.append("    return false; ");
			sb.append("  } ");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			assertEquals("Complex statement 2", false, ((Boolean) objeval).booleanValue());
			//
			sb = new StringBuffer();
			sb.append("  if (myobj != null && (myobj.rc == 43 || rc > 12)) { ");
			sb.append("    if (myobj.welcome().equals(\"hello\") ||");
			sb.append("        description.startsWith(\"Ask not\")) {");
			sb.append("       return true;");
			sb.append("    } ");
			sb.append("    else {");
			sb.append("      return false; ");
			sb.append("    } ");
			sb.append("  } ");
			sb.append("  else { ");
			sb.append("    return false; ");
			sb.append("  } ");
			cond = Statement.parse(sb.toString());
			objeval = cond.eval(m);
			assertEquals("Complex statement 3a", true, ((Boolean) objeval).booleanValue());
			//
			Map data = new HashMap();
			data.put("rc", "21");
			data.put("description", "Do ask what your country can do for you...");
			data.put("myobj", new MyTestClass(INT_42, "goodbye"));
			objeval = cond.eval(data);
			assertEquals("Complex statement 3b", false, ((Boolean) objeval).booleanValue());
			//
			Map data2 = new HashMap();
			data2.put("rc", "2");
			data2.put("description", "Ask not ");
			data2.put("myobj", new MyTestClass(43, "goodbye"));
			objeval = cond.eval(data2);
			assertEquals("Complex statement 3c", true, ((Boolean) objeval).booleanValue());
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

	public void test_if_with_nested_if() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  if (myobj != null && (myobj.rc == 43 || rc > 12)) { ");
			sb.append("    if (myobj.welcome().equals(\"hello\") ||");
			sb.append("        description.startsWith(\"Ask not\")) {");
			sb.append("       return true;");
			sb.append("    } ");
			sb.append("    else {");
			sb.append("      return false; ");
			sb.append("    } ");
			sb.append("  } ");
			sb.append("  else { ");
			sb.append("    return false; ");
			sb.append("  } ");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			assertEquals("Complex statement 3a", true, ((Boolean) objeval).booleanValue());
			//
			Map data = new HashMap();
			data.put("rc", "21");
			data.put("description", "Do ask what your country can do for you...");
			data.put("myobj", new MyTestClass(INT_42, "goodbye"));
			objeval = cond.eval(data);
			assertEquals("Complex statement 3b", false, ((Boolean) objeval).booleanValue());
			//
			Map data2 = new HashMap();
			data2.put("rc", "2");
			data2.put("description", "Ask not ");
			data2.put("myobj", new MyTestClass(43, "goodbye"));
			objeval = cond.eval(data2);
			assertEquals("Complex statement 3c", true, ((Boolean) objeval).booleanValue());
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
}
