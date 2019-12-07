package com.github.wtan.jel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wtan.jel.Dot;
import com.github.wtan.jel.Expression;
import com.github.wtan.jel.IdentValue;

import junit.framework.TestCase;

public class DotTest extends TestCase {
	Expression identblank = null;
	Expression identempty = null;
	Expression ident1 = null;
	Expression ident2 = null;
	Map m = null;

	public DotTest(String name) {
		super(name);
	}

	public void setUp() {
		identblank = new IdentValue("");
		identempty = new IdentValue("empty");
		ident1 = new IdentValue("hi");
		ident2 = new IdentValue("there");
		m = new HashMap();
		m.put("empty", "");
		m.put("bogus", "bogus");
		m.put("rc", "21");
		m.put("id", "31");
		m.put("description", "Ask not what your country can do for you...");
	}

	public void test_null_expression() {
		try {
			Dot dot = new Dot(null, null);
			dot.eval(m);
			fail("FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_empty_attribute_name() {
		try {
			Dot dot = new Dot(identblank, identblank);
			dot.eval(m);
			fail("FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_empty_class_name() {
		try {
			Dot dot = new Dot(identblank, ident2);
			dot.eval(m);
			fail("FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_eval_empty_method_name() {
		try {
			Dot dot = new Dot(identblank, identblank);
			dot.eval(m, null);
			fail("FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_could_not_load_class() {
		try {
			List elist = new ArrayList(4);
			Dot dot = new Dot(new IdentValue("badclass"), ident2);
			m.put("badclass", ident2);
			dot.eval(m, elist);
			fail("FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_null_object_class_name() {
		try {
			List elist = new ArrayList(4);
			Dot dot = new Dot(new IdentValue(null), ident2);
			dot.eval(m, elist);
			fail("FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_no_such_object_class() {
		try {
			List elist = new ArrayList(4);
			Dot dot = new Dot(new IdentValue("java.lang.Bogus"), new IdentValue("bogus"));
			dot.eval(m, elist);
			fail("FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_no_such_method() {
		try {
			List elist = new ArrayList(4);
			Dot dot = new Dot(new IdentValue("java.lang.String"), new IdentValue("bogus"));
			dot.eval(m, elist);
			fail("FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}
}
