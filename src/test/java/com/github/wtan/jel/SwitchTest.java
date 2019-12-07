package com.github.wtan.jel;

import java.util.HashMap;
import java.util.Map;

import com.github.wtan.jel.IExpression;
import com.github.wtan.jel.Statement;

import junit.framework.TestCase;

public class SwitchTest extends TestCase {
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

	public SwitchTest(String name) {
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

	public void test_switch_int() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    day = 6;");
			sb.append("    switch (day) {");
			sb.append("        case 1:  dayString = \"Sunday\";");
			sb.append("            break;");
			sb.append("        case 2:  dayString = \"Monday\";");
			sb.append("            break;");
	        sb.append("        case 3:  dayString = \"Tuesday\";");
			sb.append("            break;");
			sb.append("        case 4:  dayString = \"Wednesday\";");
			sb.append("            break;");
			sb.append("        case 5:  dayString = \"Thursday\";");
			sb.append("            break;");
			sb.append("        case 6:  dayString = \"Friday\";");
			sb.append("            break;");
			sb.append("        case 7:  dayString = \"Saturday\";");
			sb.append("            break;");
			sb.append("        default: dayString = \"Invalid day\";");
			sb.append("            break;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("dayString");
			assertEquals("Friday", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_switch_long() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    day = 6L;");
			sb.append("    switch (day) {");
			sb.append("        case 1L:  dayString = \"Sunday\";");
			sb.append("            break;");
			sb.append("        case 2L:  dayString = \"Monday\";");
			sb.append("            break;");
	        sb.append("        case 3L:  dayString = \"Tuesday\";");
			sb.append("            break;");
			sb.append("        case 4L:  dayString = \"Wednesday\";");
			sb.append("            break;");
			sb.append("        case 5L:  dayString = \"Thursday\";");
			sb.append("            break;");
			sb.append("        case 6L:  dayString = \"Friday\";");
			sb.append("            break;");
			sb.append("        case 7L:  dayString = \"Saturday\";");
			sb.append("            break;");
			sb.append("        default: dayString = \"Invalid day\";");
			sb.append("            break;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("dayString");
			assertEquals("Friday", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_switch_string() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    day = \"wed\";");
			sb.append("    switch (day) {");
			sb.append("        case \"sun\":  dayString = \"Sunday\";");
			sb.append("            break;");
			sb.append("        case \"mon\":  dayString = \"Monday\";");
			sb.append("            break;");
	        sb.append("        case \"tue\":  dayString = \"Tuesday\";");
			sb.append("            break;");
			sb.append("        case \"wed\":  dayString = \"Wednesday\";");
			sb.append("            break;");
			sb.append("        case \"thu\":  dayString = \"Thursday\";");
			sb.append("            break;");
			sb.append("        case \"fri\":  dayString = \"Friday\";");
			sb.append("            break;");
			sb.append("        case \"sat\":  dayString = \"Saturday\";");
			sb.append("            break;");
			sb.append("        default: dayString = \"Invalid day\";");
			sb.append("            break;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("dayString");
			assertEquals("Wednesday", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_switch_continue() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    day = \"wed\";");
			sb.append("    switch (day) {");
			sb.append("        case \"sun\":  dayString = \"Sunday\";");
			sb.append("            continue;");
			sb.append("        case \"mon\":  dayString = \"Monday\";");
			sb.append("            continue;");
	        sb.append("        case \"tue\":  dayString = \"Tuesday\";");
			sb.append("            continue;");
			sb.append("        case \"wed\":  dayString = \"Wednesday\";");
			sb.append("            continue;");
			sb.append("        case \"thu\":  dayString = \"Thursday\";");
			sb.append("            continue;");
			sb.append("        case \"fri\":  dayString = \"Friday\";");
			sb.append("            continue;");
			sb.append("        case \"sat\":  dayString = \"Saturday\";");
			sb.append("            continue;");
			sb.append("        default: dayString = \"Invalid day\";");
			sb.append("            break;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("dayString");
			assertEquals("Invalid day", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_switch_return() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    day = \"wed\";");
			sb.append("    switch (day) {");
			sb.append("        case \"sun\":  return \"Sunday\";");
			sb.append("        case \"mon\":  return \"Monday\";");
	        sb.append("        case \"tue\":  return \"Tuesday\";");
			sb.append("        case \"wed\":  return \"Wednesday\";");
			sb.append("        case \"thu\":  return \"Thursday\";");
			sb.append("        case \"fri\":  return \"Friday\";");
			sb.append("        case \"sat\":  return \"Saturday\";");
			sb.append("        default: return \"Invalid day\";");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			assertEquals("Wednesday", objeval);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_switch_default() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    day = \"xyz\";");
			sb.append("    switch (day) {");
			sb.append("        case \"sun\":  return \"Sunday\";");
			sb.append("        case \"mon\":  return \"Monday\";");
	        sb.append("        case \"tue\":  return \"Tuesday\";");
			sb.append("        case \"wed\":  return \"Wednesday\";");
			sb.append("        case \"thu\":  return \"Thursday\";");
			sb.append("        case \"fri\":  return \"Friday\";");
			sb.append("        case \"sat\":  return \"Saturday\";");
			sb.append("        default: return \"Invalid day\";");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			assertEquals("Invalid day", objeval);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_switch_fall_through() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    day = 5;");
			sb.append("    dayString = \"\";");
			sb.append("    switch (day) {");
			sb.append("        case 1:  dayString = dayString + \"Sunday\";");
			sb.append("        case 2:  dayString = dayString + \"Monday\";");
	        sb.append("        case 3:  dayString = dayString + \"Tuesday\";");
			sb.append("        case 4:  dayString = dayString + \"Wednesday\";");
			sb.append("        case 5:  dayString = dayString + \"Thursday\";");
			sb.append("        case 6:  dayString = dayString + \"Friday\";");
			sb.append("        case 7:  dayString = dayString + \"Saturday\";");
			sb.append("            break;");
			sb.append("        default:");
			sb.append("            break;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String str = (String) m.get("dayString");
			assertEquals("ThursdayFridaySaturday", str);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_switch_multi_case_label() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    month = 2;");
			sb.append("    year = 2000;");
			sb.append("    dayString = \"\";");
			sb.append("    switch (month) {");
			sb.append("        case 1: case 3: case 5: case 7: case 8: case 10: case 12: ");
			sb.append("            numDays = 31;");
			sb.append("            break;");
	        sb.append("        case 4: case 6: case 9: case 11: ");
			sb.append("            numDays = 30;");
			sb.append("            break;");
			sb.append("        case 2: ");
			sb.append("            if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0))");
			sb.append("                numDays = 29;");
			sb.append("            else");
			sb.append("                numDays = 28;");
			sb.append("            break;");
			sb.append("        default:");
			sb.append("            numDays = 99;");
			sb.append("            break;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			int numDays = (Integer) m.get("numDays");
			assertEquals(29, numDays);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_switch_multi_case_label2() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("    month = 4;");
			sb.append("    year = 2000;");
			sb.append("    dayString = \"\";");
			sb.append("    switch (month) {");
			sb.append("        case 1: case 3: case 5: case 7: case 8: case 10: case 12: ");
			sb.append("            numDays = 31;");
			sb.append("            break;");
	        sb.append("        case 4: case 6: case 9: case 11: ");
			sb.append("            numDays = 30;");
			sb.append("            break;");
			sb.append("        case 2: ");
			sb.append("            if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0))");
			sb.append("                numDays = 29;");
			sb.append("            else");
			sb.append("                numDays = 28;");
			sb.append("            break;");
			sb.append("        default:");
			sb.append("            numDays = 99;");
			sb.append("            break;");
			sb.append("    }");
			sb.append("}");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			int numDays = (Integer) m.get("numDays");
			assertEquals(30, numDays);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("   FAILED. Exception:" + e.toString());
		}
	}
}
