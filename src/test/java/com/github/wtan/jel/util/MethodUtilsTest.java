package com.github.wtan.jel.util;

import java.lang.reflect.Method;

import org.apache.commons.lang.time.StopWatch;

import com.github.wtan.jel.util.MethodUtils;

import junit.framework.TestCase;

public class MethodUtilsTest extends TestCase {

	
	public MethodUtilsTest(String name) throws Exception {
		super(name);
	}

	public void test_invokeMethod() {
		String str = "Hello";
		try {
			MethodUtils.invokeMethod(str, "endsWith", "lo");
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_invokeMethod_null_arg() {
		StopWatch sw = new StopWatch();
		try {
			MethodUtils.invokeMethod(sw, "start", null);
			for (int i=0; i<1000000; i++) {int j=0;}
			MethodUtils.invokeMethod(sw, "stop", null);
			if (sw.getTime() == 0) {
				fail("startMillis was zero");
			}
			//System.out.println("invokeMethod_null_arg: " + sw.getTime());
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_invokeMethod_with_arg_type() {
		String str = "Hello";
		Object[] args = {"lo"};
		Class[] parameterTypes = {String.class};
		try {
			MethodUtils.invokeMethod(str, "endsWith", args, parameterTypes);
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_invokeMethod_null_method() {
		String str = "Hello";
		Object[] args = {"lo"};
		Class[] parameterTypes = {String.class};
		try {
			MethodUtils.invokeMethod(str, null, args, parameterTypes);
			fail("Failed");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void test_invokeMethod_null_arg2() {
		String str = "Hello";
		Object[] args = {"lo"};
		Class[] parameterTypes = {String.class};
		try {
			MethodUtils.invokeMethod(str, "endsWith", null, parameterTypes);
			fail("Failed");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void test_invokeMethod_null_paramtypes() {
		String str = "Hello";
		Object[] args = {"lo"};
		try {
			MethodUtils.invokeMethod(str, "endsWith", args, null);
			fail("Failed");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void test_invokeExactMethod() {
		String str = "Hello";
		try {
			MethodUtils.invokeExactMethod(str, "endsWith", "lo");
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_invokeExactMethod_null_arg() {
		StopWatch sw = new StopWatch();
		try {
			MethodUtils.invokeExactMethod(sw, "start", null);
			for (int i=0; i<1000000; i++) {int j=0;}
			MethodUtils.invokeMethod(sw, "stop", null);
			if (sw.getTime() == 0) {
				fail("startMillis was zero");
			}
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_invokeExactMethod_with_arg_type() {
		String str = "Hello";
		Object[] args = {"lo"};
		Class[] parameterTypes = {String.class};
		try {
			MethodUtils.invokeExactMethod(str, "endsWith", args, parameterTypes);
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_invokeExactMethod_null_method() {
		String str = "Hello";
		Object[] args = {"lo"};
		Class[] parameterTypes = {String.class};
		try {
			MethodUtils.invokeExactMethod(str, null, args, parameterTypes);
			fail("Failed");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void test_invokeExactMethod_null_arg2() {
		String str = "Hello";
		Object[] args = {"lo"};
		Class[] parameterTypes = {String.class};
		try {
			MethodUtils.invokeExactMethod(str, "endsWith", null, parameterTypes);
			fail("Failed");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void test_invokeExactMethod_null_paramtypes() {
		String str = "Hello";
		Object[] args = {"lo"};
		try {
			MethodUtils.invokeExactMethod(str, "endsWith", args, null);
			fail("Failed");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void test_getAccessibleMethod() {
		String str = "Hello";
		try {
			MethodUtils.getAccessibleMethod(String.class, "endsWith", String.class);
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_getAccessibleMethod_from_method() {
		String str = "Hello";
		try {
			Method m = MethodUtils.getAccessibleMethod(null);
			assertTrue(m == null);
			Class[] inttypes = {int.class, int.class};
			m = String.class.getDeclaredMethod("indexOfSupplementary", inttypes);
			m = MethodUtils.getAccessibleMethod(m);
			assertTrue(m == null);
			Class[] strtype = {String.class};
			m = String.class.getMethod("endsWith", strtype);
			m = MethodUtils.getAccessibleMethod(m);
			assertTrue(m != null);
		}
		catch (Exception e) {
			fail("Failed");
		}
	}



	public void test_GetAccessibleMethodFromInterfaceNest() {
		try {
			Class[] parameterTypes = {String.class};
			Method m = MethodUtils.testGetAccessibleMethodFromInterfaceNest(String.class, 
					"endsWith", parameterTypes);
		}
		catch (Exception e) {
			fail("Failed");
		}

	}

	public void test_canWiden() {
		try {
			boolean res = MethodUtils.testCanWiden(double.class, short.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(double.class, int.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(double.class, long.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(double.class, float.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(float.class, short.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(float.class, int.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(float.class, long.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(long.class, short.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(long.class, int.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(int.class, short.class);
			assertTrue(res);
			res = MethodUtils.testCanWiden(short.class, int.class);
			assertTrue(!res);
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_getPrimitiveWrapper() {
		try {
			Class c = MethodUtils.getPrimitiveWrapper(String.class);
			assertTrue(c == null);
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_getPrimitiveType() {
		try {
			Class c = MethodUtils.getPrimitiveType(Boolean.class);
			assertTrue(c != null);
			c = MethodUtils.getPrimitiveType(Byte.class);
			assertTrue(c != null);
			c = MethodUtils.getPrimitiveType(Character.class);
			assertTrue(c != null);
			c = MethodUtils.getPrimitiveType(Short.class);
			assertTrue(c != null);
			c = MethodUtils.getPrimitiveType(Integer.class);
			assertTrue(c != null);
			c = MethodUtils.getPrimitiveType(Long.class);
			assertTrue(c != null);
			c = MethodUtils.getPrimitiveType(Float.class);
			assertTrue(c != null);
			c = MethodUtils.getPrimitiveType(Double.class);
			assertTrue(c != null);
			c = MethodUtils.getPrimitiveType(String.class);
			assertTrue(c == null);
		}
		catch (Exception e) {
			fail("Failed");
		}
	}

	public void test_toNonPrimitiveClass() {
		try {
			Class c = MethodUtils.toNonPrimitiveClass(boolean.class);
			assertTrue(c != null);
			c = MethodUtils.toNonPrimitiveClass(byte.class);
			assertTrue(c != null);
			c = MethodUtils.toNonPrimitiveClass(char.class);
			assertTrue(c != null);
			c = MethodUtils.toNonPrimitiveClass(short.class);
			assertTrue(c != null);
			c = MethodUtils.toNonPrimitiveClass(int.class);
			assertTrue(c != null);
			c = MethodUtils.toNonPrimitiveClass(long.class);
			assertTrue(c != null);
			c = MethodUtils.toNonPrimitiveClass(float.class);
			assertTrue(c != null);
			c = MethodUtils.toNonPrimitiveClass(double.class);
			assertTrue(c != null);
			c = MethodUtils.toNonPrimitiveClass(String.class);
			assertTrue(c != null);
		}
		catch (Exception e) {
			fail("Failed");
		}
	}
}
