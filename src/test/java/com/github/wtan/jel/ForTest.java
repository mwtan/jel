package com.github.wtan.jel;

import java.util.HashMap;
import java.util.Map;

import com.github.wtan.jel.IExpression;
import com.github.wtan.jel.Statement;

import junit.framework.TestCase;

public class ForTest extends TestCase {
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

	public ForTest(String name) {
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

	public void test_for_simple() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    for (i=0, j=3; i<4; i++, j--) {");
			sb.append("        msg=msg+\"hello\" + i + j;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("hello03hello12hello21hello30", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_for_break() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    for (i=0; i<6; i++) {");
			sb.append("        msg=msg+\"hello\";");
			sb.append("        if (msg.length()>=10) break;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("hellohello", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_for_continue() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    for (i=0; i<6; i++) {");
			sb.append("        msg=msg+\"hello\";");
			sb.append("        if (msg.length()<=12) {");
			sb.append("            msg=msg+\"#\";");
			sb.append("            continue;");
			sb.append("        }");
			sb.append("        msg=msg+\"$\";");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("hello#hello#hello$hello$hello$hello$", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_for_return() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    msg=\"\";");
			sb.append("    for (i=0; i<6; i++) {");
			sb.append("        msg=msg+\"hello\";");
			sb.append("        if (msg.length()>=10) return;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("msg");
			assertEquals("hellohello", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}
}
