package com.github.wtan.jel;

import java.util.HashMap;
import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

import junit.framework.TestCase;

public class TernaryTest extends TestCase {
	private Map m;

	public TernaryTest(String name) {
		super(name);
	}

	public void setUp() {
		m = new HashMap();
		m.put("age1", "14");
		m.put("age2", "21");
	}

	public void test_condition_true() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("canvote = (age1 > 18) ? \"Yes, you can vote!\" : \"No, you can't vote!\";");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String canvote = (String) m.get("canvote");
			assertTrue(canvote.equals("No, you can't vote!"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public void test_condition_false() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("canvote = (age2 > 18) ? \"Yes, you can vote!\" : \"No, you can't vote!\";");
			IExpression cond = Statement.parse(sb.toString());
			Object objeval = cond.eval(m);
			String canvote = (String) m.get("canvote");
			assertTrue(canvote.equals("Yes, you can vote!"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
