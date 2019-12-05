package com.github.wtan.exception;

import com.github.wtan.exception.CommonException;

import junit.framework.TestCase;

public class CommonExceptionTest extends TestCase {

	public CommonExceptionTest(String name) {
		super(name);
	}

	public void setUp() {
	}

	public void test_empty_constructor() {
		CommonException e = new CommonException();
		String s = e.getPrintStackTrace();
		assertTrue(s.contains("(CommonExceptionTest.java:17)"));
	}

	public void test_exception_chain1() {
		CommonException ex1 = new CommonException();
		CommonException ex2 = new CommonException(ex1);
		CommonException ex3 = new CommonException(ex2);
		// stack trace contains all 3 exceptions
		String s = ex3.getPrintStackTrace();
		assertTrue(s.contains("(CommonExceptionTest.java:25)") && 
				   s.contains("(CommonExceptionTest.java:24)") && 
				   s.contains("(CommonExceptionTest.java:23)"));
		// stack trace from prior exception
		Exception enext = ex3.getNextCause();
		String snext = CommonException.getPrintStackTrace(enext);
		assertTrue(!snext.contains("(CommonExceptionTest.java:25)") && 
					snext.contains("(CommonExceptionTest.java:24)") && 
					snext.contains("(CommonExceptionTest.java:23)"));
		// stack trace from root cause
		Exception eroot = ex1.getRootCause();
		s = CommonException.getPrintStackTrace(eroot);
		assertTrue(s.contains("(CommonExceptionTest.java:23)"));
		// getNextCause of root exception
		Exception elast = ((CommonException) eroot).getNextCause();
		assertTrue(elast ==  null);
	}

	public void test_exception_chain2() {
		try {
			method_a();
			fail("Failed");
		}
		catch (CommonException e) {
			// stack trace contains all 3 exceptions
			String s = e.getPrintStackTrace();
			assertTrue(s.contains("foo") && s.contains("bar") && s.contains("baz"));
			// stack trace from prior exception
			Exception enext = e.getNextCause();
			String snext = CommonException.getPrintStackTrace(enext);
			assertTrue(!snext.contains("foo") && snext.contains("bar") && snext.contains("baz"));
			// stack trace contains root exception
			Exception e1 = e.getRootCause();
			String s1 = CommonException.getPrintStackTrace(e1);
			assertTrue(!s1.contains("foo") && !s1.contains("bar") && s1.contains("baz"));
			// stack trace from getRootPrintStackTrace is the same as above
			String s2 = e.getRootPrintStackTrace();
			assertTrue(s1.equals(s2));
		}
	}
	public void method_a() throws CommonException {
		try {
			method_b();
			fail("Failed");
		}
		catch (Exception e) {
			throw new CommonException("foo", e);
		}
	}
	public static void method_b() throws CommonException {
		try {
			method_c();
		}
		catch (Exception e) {
			throw new CommonException("bar", e);
		}
	}
	public static void method_c() throws Exception {
		throw new Exception("baz");
	}
	
	
	
	public void test_getPrintStackTrace_null_exception() {
		try {
			String s1 = CommonException.getPrintStackTrace(null);
			assertTrue(s1.contains("Error obtaining exception stack trace."));
		}
		catch (Exception e) {
			fail("Failed");
		}
	}
}
