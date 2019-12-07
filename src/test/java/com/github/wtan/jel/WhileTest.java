package com.github.wtan.jel;

import java.util.HashMap;
import java.util.Map;

import com.github.wtan.jel.IExpression;
import com.github.wtan.jel.Statement;

import junit.framework.TestCase;

public class WhileTest extends TestCase {
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

	public WhileTest(String name) {
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

	public void test_while_break() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    while (true) {");
			sb.append("        msg=msg+\"hello\";");
			sb.append("        if (msg.length()>=15) {");
			sb.append("            break;");
			sb.append("        }");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("hellohellohello", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_while_continue() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    while (true) {");
			sb.append("        msg=msg+\"hello\";");
			sb.append("        if (msg.length()>=15) {");
			sb.append("            break;");
			sb.append("            msg=msg+\"!\";");
			sb.append("        }");
			sb.append("        else {");
			sb.append("            if (msg.length()<10) {");
			sb.append("               msg=msg+\"#\";");
			sb.append("               continue;");
			sb.append("               msg=msg+\"$\";");
			sb.append("            }");
			sb.append("            else {");
			sb.append("               msg=msg+\"!\";");
			sb.append("               continue;");
			sb.append("               msg=msg+\"$\";");
			sb.append("            }");
			sb.append("        }");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("hello#hello!hello", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_while_return() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    while (true) {");
			sb.append("        msg=msg+\"hello\";");
			sb.append("        if (msg.length()>=15)");
			sb.append("            break;");
			sb.append("        else {");
			sb.append("            msg=msg+\"!\";");
			sb.append("            return msg;");
			sb.append("        }");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("hello!", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_while_decrement() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    i=4;");
			sb.append("    while (i>0) {");
			sb.append("        msg=msg+\"hello\";");
			sb.append("        i--;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("hellohellohellohello", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_while_decrement2() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    i=0;");
			sb.append("    while (i>0) {");
			sb.append("        msg=msg+\"hello\";");
			sb.append("        i--;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}
}
