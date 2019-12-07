package com.github.wtan.jel;

import java.util.HashMap;
import java.util.Map;

import com.github.wtan.jel.Break;
import com.github.wtan.jel.IExpression;
import com.github.wtan.jel.PlusAssign;

import junit.framework.TestCase;

public class BinaryArithmeticAssignmentExpressionTest extends TestCase {
	public BinaryArithmeticAssignmentExpressionTest(String name) {
		super(name);
	}

	public void test_invalid_dot_object() {
		try {
			IExpression cond = new PlusAssign(new Break(), null);
			Map m = new HashMap();
			Object o = cond.eval(m);
			assertTrue(o == null);
		}
		catch (Exception e) {
			fail("   FAILED.");
		}
	}

}
