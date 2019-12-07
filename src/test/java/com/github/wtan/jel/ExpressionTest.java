package com.github.wtan.jel;

import com.github.wtan.jel.Expression;
import com.github.wtan.jel.IdentValue;

import junit.framework.TestCase;

public class ExpressionTest extends TestCase {
	Expression e = null;
	
	public ExpressionTest(String name) {
		super(name);
	}

	public void setUp() {
		e = new IdentValue("hi");
	}

	public void test_toObject() {
		try {
			Object inobj = new MyTestClass();
			Object obj = e.toObject(inobj);
			assertTrue(obj.equals(inobj));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	/*
	 * getboolean
	 */
	public void test_getboolean_null() {
		try {
			Object obj = e.getboolean(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getboolean_boolean() {
		try {
			Object obj = e.getboolean(true);
			assertTrue(obj instanceof Boolean && (Boolean)obj == true);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getboolean_string() {
		try {
			boolean obj = e.getboolean("true");
			assertTrue(obj);
			obj = e.getboolean("nottrue");
			assertFalse(obj);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getboolean_byte() {
		try {
			boolean obj = e.getboolean(new Byte("1"));
			assertTrue(obj);
			obj = e.getboolean(new Byte("0"));
			assertFalse(obj);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getboolean_invalid_byte() {
		try {
			boolean obj = e.getboolean(new Byte("64"));
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getboolean_char() {
		try {
			char c1 = 1;
			boolean obj = e.getboolean(new Character(c1));
			assertTrue(obj);
			char c0 = 0;
			obj = e.getboolean(new Character(c0));
			assertFalse(obj);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getboolean_invalid_char() {
		try {
			boolean obj = e.getboolean(new Character('0'));
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getboolean_short() {
		try {
			boolean obj = e.getboolean(new Short("1"));
			assertTrue(obj);
			obj = e.getboolean(new Short("0"));
			assertFalse(obj);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getboolean_invalid_short() {
		try {
			boolean obj = e.getboolean(new Short("256"));
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getboolean_integer() {
		try {
			boolean obj = e.getboolean(1);
			assertTrue(obj);
			obj = e.getboolean(0);
			assertFalse(obj);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getboolean_invalid_integer() {
		try {
			boolean obj = e.getboolean(123);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getboolean_long() {
		try {
			boolean obj = e.getboolean(1L);
			assertTrue(obj);
			obj = e.getboolean(0L);
			assertFalse(obj);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getboolean_invalid_long() {
		try {
			boolean obj = e.getboolean(123L);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getboolean_float() {
		try {
			boolean obj = e.getboolean(1.0F);
			assertTrue(obj);
			obj = e.getboolean(0.0F);
			assertFalse(obj);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getboolean_invalid_float() {
		try {
			boolean obj = e.getboolean(123.0F);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getboolean_double() {
		try {
			boolean obj = e.getboolean(1.0D);
			assertTrue(obj);
			obj = e.getboolean(0.0D);
			assertFalse(obj);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getboolean_invalid_double() {
		try {
			boolean obj = e.getboolean(123.0D);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getboolean_invalid_object() {
		try {
			Object obj = e.getboolean(new MyTestClass());
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/*
	 * getbyte
	 */
	public void test_getbyte_null() {
		try {
			Object obj = e.getbyte(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getbyte_byte() {
		try {
			Byte obj = e.getbyte(new Byte("123"));
			assertTrue(obj.byteValue() == new Byte("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getbyte_string() {
		try {
			Byte obj = e.getbyte("123");
			assertTrue(obj.byteValue() == new Byte("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	public void test_getbyte_boolean() {
		try {
			Byte obj = e.getbyte(true);
			assertTrue(obj.byteValue() == new Byte("1"));
			obj = e.getbyte(false);
			assertTrue(obj.byteValue() == new Byte("0"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getbyte_char() {
		try {
			Byte obj = e.getbyte(new Character('a'));
			assertTrue(obj.byteValue() == new Byte("97"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getbyte_short() {
		try {
			Byte obj = e.getbyte(new Short("123"));
			assertTrue(obj.byteValue() == new Byte("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getbyte_large_short() {
		try {
			Byte obj = e.getbyte(new Short("1234"));
			assertTrue(obj.byteValue() == new Byte("-46"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getbyte_integer() {
		try {
			Byte obj = e.getbyte(123);
			assertTrue(obj.byteValue() == new Byte("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getbyte_large_integer() {
		try {
			Byte obj = e.getbyte(1234);
			assertTrue(obj.byteValue() == new Byte("-46"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getbyte_long() {
		try {
			Byte obj = e.getbyte(123L);
			assertTrue(obj.byteValue() == new Byte("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getbyte_large_long() {
		try {
			Byte obj = e.getbyte(1234L);
			assertTrue(obj.byteValue() == new Byte("-46"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getbyte_float() {
		try {
			Byte obj = e.getbyte(123.0F);
			assertTrue(obj.byteValue() == new Byte("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getbyte_large_float() {
		try {
			Byte obj = e.getbyte(1234.0F);
			assertTrue(obj.byteValue() == new Byte("-46"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getbyte_double() {
		try {
			Byte obj = e.getbyte(123.0D);
			assertTrue(obj.byteValue() == new Byte("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	public void test_getbyte_large_double() {
		try {
			Byte obj = e.getbyte(1234.0D);
			assertTrue(obj.byteValue() == new Byte("-46"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getbyte_invalid_object() {
		try {
			Byte obj = e.getbyte(new MyTestClass());
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/*
	 * getchar
	 */
	public void test_getchar_null() {
		try {
			Object obj = e.getchar(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getchar_char() {
		try {
			Character obj = e.getchar('a');
			assertTrue(obj.charValue() == 'a');
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getchar_string() {
		try {
			Character obj = e.getchar("a");
			assertTrue(obj.charValue() == 'a');
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	public void test_getchar_boolean() {
		try {
			Character obj = e.getchar(true);
			assertTrue(obj.charValue()==1);
			obj = e.getchar(false);
			assertTrue(obj.charValue()==0);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getchar_byte() {
		try {
			Character obj = e.getchar(new Byte("97"));
			assertTrue(obj.charValue() == 'a');
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getchar_short() {
		try {
			Character obj = e.getchar(new Short("97"));
			assertTrue(obj.charValue() == 'a');
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getchar_integer() {
		try {
			Character obj = e.getchar(97);
			assertTrue(obj.charValue() == 'a');
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getchar_long() {
		try {
			Character obj = e.getchar(97L);
			assertTrue(obj.charValue() == 'a');
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getchar_float() {
		try {
			Character obj = e.getchar(97.0F);
			assertTrue(obj.charValue() == 'a');
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getchar_double() {
		try {
			Character obj = e.getchar(97.0D);
			assertTrue(obj.charValue() == 'a');
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getchar_invalid_object() {
		try {
			Character obj = e.getchar(new MyTestClass());
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/*
	 * getshort
	 */
	public void test_getshort_null() {
		try {
			Object obj = e.getshort(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getshort_short() {
		try {
			Short obj = e.getshort(new Short("123"));
			assertTrue(obj.shortValue() == new Short("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getshort_string() {
		try {
			Short obj = e.getshort("123");
			assertTrue(obj.shortValue() == new Short("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	public void test_getshort_boolean() {
		try {
			Short obj = e.getshort(true);
			assertTrue(obj.shortValue()==new Short("1"));
			obj = e.getshort(false);
			assertTrue(obj.shortValue()==new Short("0"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getshort_byte() {
		try {
			Short obj = e.getshort(new Byte("123"));
			assertTrue(obj.shortValue() == new Short("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getshort_char() {
		try {
			Short obj = e.getshort('a');
			assertTrue(obj.shortValue() == new Short("97"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getshort_integer() {
		try {
			Short obj = e.getshort(123);
			assertTrue(obj.shortValue() == new Short("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getshort_long() {
		try {
			Short obj = e.getshort(123L);
			assertTrue(obj.shortValue() == new Short("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getshort_float() {
		try {
			Short obj = e.getshort(123.0F);
			assertTrue(obj.shortValue() == new Short("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getshort_double() {
		try {
			Short obj = e.getshort(123.0D);
			assertTrue(obj.shortValue() == new Short("123"));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getshort_invalid_object() {
		try {
			Short obj = e.getshort(new MyTestClass());
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/*
	 * getint
	 */
	public void test_getint_null() {
		try {
			Object obj = e.getint(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getint_integer() {
		try {
			Integer obj = e.getint(123);
			assertTrue(obj.intValue() == 123);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getint_string() {
		try {
			Integer obj = e.getint("123");
			assertTrue(obj.intValue() == 123);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	public void test_getint_boolean() {
		try {
			Integer obj = e.getint(true);
			assertTrue(obj.intValue()==1);
			obj = e.getint(false);
			assertTrue(obj.intValue()==0);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getint_byte() {
		try {
			Integer obj = e.getint(new Byte("123"));
			assertTrue(obj.intValue() == 123);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getint_char() {
		try {
			Integer obj = e.getint('a');
			assertTrue(obj.intValue() == 97);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getint_short() {
		try {
			Integer obj = e.getint(new Short("123"));
			assertTrue(obj.intValue() == 123);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getint_long() {
		try {
			Integer obj = e.getint(123L);
			assertTrue(obj.intValue() == 123);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getint_float() {
		try {
			Integer obj = e.getint(123.0F);
			assertTrue(obj.intValue() == 123);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getint_double() {
		try {
			Integer obj = e.getint(123.0D);
			assertTrue(obj.intValue() == 123);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getint_invalid_object() {
		try {
			Integer obj = e.getint(new MyTestClass());
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/*
	 * getlong
	 */
	public void test_getlong_null() {
		try {
			Object obj = e.getlong(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getlong_long() {
		try {
			Long obj = e.getlong(123L);
			assertTrue(obj.longValue() == 123L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getlong_string() {
		try {
			Long obj = e.getlong("123");
			assertTrue(obj.longValue() == 123L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	public void test_getlong_boolean() {
		try {
			Long obj = e.getlong(true);
			assertTrue(obj.longValue()==1L);
			obj = e.getlong(false);
			assertTrue(obj.longValue()==0L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getlong_byte() {
		try {
			Long obj = e.getlong(new Byte("123"));
			assertTrue(obj.longValue()==123L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getlong_char() {
		try {
			Long obj = e.getlong('a');
			assertTrue(obj.longValue()==97L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getlong_short() {
		try {
			Long obj = e.getlong(new Short("123"));
			assertTrue(obj.longValue()==123L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getlong_integer() {
		try {
			Long obj = e.getlong(123);
			assertTrue(obj.longValue()==123L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getlong_float() {
		try {
			Long obj = e.getlong(123.0F);
			assertTrue(obj.longValue()==123L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getlong_double() {
		try {
			Long obj = e.getlong(123.0D);
			assertTrue(obj.longValue()==123L);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getlong_invalid_object() {
		try {
			Long obj = e.getlong(new MyTestClass());
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/*
	 * getfloat
	 */
	public void test_getfloat_null() {
		try {
			Object obj = e.getfloat(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getfloat_float() {
		try {
			Float obj = e.getfloat(123.0F);
			assertTrue(obj.floatValue()==123.0F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getfloat_string() {
		try {
			Float obj = e.getfloat("123");
			assertTrue(obj.floatValue()== 123.0F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	public void test_getfloat_boolean() {
		try {
			Float obj = e.getfloat(true);
			assertTrue(obj.floatValue()==1F);
			obj = e.getfloat(false);
			assertTrue(obj.floatValue()==0F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getfloat_byte() {
		try {
			Float obj = e.getfloat(new Byte("123"));
			assertTrue(obj.floatValue()==123F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getfloat_char() {
		try {
			Float obj = e.getfloat('a');
			assertTrue(obj.floatValue()==97F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getfloat_short() {
		try {
			Float obj = e.getfloat(new Short("123"));
			assertTrue(obj.floatValue()==123F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getfloat_integer() {
		try {
			Float obj = e.getfloat(123);
			assertTrue(obj.floatValue()==123.0F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getfloat_long() {
		try {
			Float obj = e.getfloat(123L);
			assertTrue(obj.floatValue()==123.0F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getfloat_double() {
		try {
			Float obj = e.getfloat(123.0D);
			assertTrue(obj.floatValue()==123.0F);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getfloat_invalid_object() {
		try {
			Float obj = e.getfloat(new MyTestClass());
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/*
	 * getdouble
	 */
	public void test_getdouble_null() {
		try {
			Object obj = e.getdouble(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getdouble_double() {
		try {
			Double obj = e.getdouble(123.0D);
			assertTrue(obj.doubleValue() == 123.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getdouble_string() {
		try {
			Double obj = e.getdouble("123");
			assertTrue(obj.doubleValue() == 123.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	public void test_getdouble_boolean() {
		try {
			Double obj = e.getdouble(true);
			assertTrue(obj.doubleValue()==1.0D);
			obj = e.getdouble(false);
			assertTrue(obj.doubleValue()==0.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getdouble_byte() {
		try {
			Double obj = e.getdouble(new Byte("123"));
			assertTrue(obj.doubleValue() == 123.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getdouble_char() {
		try {
			Double obj = e.getdouble('a');
			assertTrue(obj.doubleValue() == 97.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getdouble_short() {
		try {
			Double obj = e.getdouble(new Short("123"));
			assertTrue(obj.doubleValue() == 123.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getdouble_integer() {
		try {
			Double obj = e.getdouble(123);
			assertTrue(obj.doubleValue() == 123.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getdouble_long() {
		try {
			Double obj = e.getdouble(123L);
			assertTrue(obj.doubleValue() == 123.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getdouble_float() {
		try {
			Double obj = e.getdouble(123.0F);
			assertTrue(obj.doubleValue() == 123.0D);
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getdouble_invalid_object() {
		try {
			Double obj = e.getdouble(new MyTestClass());
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	/*
	 * getObjectClass
	 */
	public void test_getObjectClass_null_object() {
		try {
			Class obj = e.getObjectClass(null);
			fail("   FAILED. Exception expected.");
		}
		catch (Exception e) {
		}
	}

	public void test_getObjectClass_boolean() {
		try {
			Class obj = e.getObjectClass(new Boolean(true));
			assertTrue(obj.equals(Boolean.TYPE));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getObjectClass_byte() {
		try {
			Class obj = e.getObjectClass(new Byte("1"));
			assertTrue(obj.equals(Byte.TYPE));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
	
	public void test_getObjectClass_char() {
		try {
			Class obj = e.getObjectClass(new Character('a'));
			assertTrue(obj.equals(Character.TYPE));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getObjectClass_short() {
		try {
			Class obj = e.getObjectClass(new Short("123"));
			assertTrue(obj.equals(Short.TYPE));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getObjectClass_integer() {
		try {
			Class obj = e.getObjectClass(new Integer(123));
			assertTrue(obj.equals(Integer.TYPE));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getObjectClass_long() {
		try {
			Class obj = e.getObjectClass(new Long(123L));
			assertTrue(obj.equals(Long.TYPE));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getObjectClass_float() {
		try {
			Class obj = e.getObjectClass(new Float(123.0F));
			assertTrue(obj.equals(Float.TYPE));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getObjectClass_double() {
		try {
			Class obj = e.getObjectClass(new Double(123.0D));
			assertTrue(obj.equals(Double.TYPE));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}

	public void test_getObjectClass_object() {
		try {
			Class obj = e.getObjectClass(new MyTestClass(123));
			assertTrue(obj.equals(com.github.wtan.jel.MyTestClass.class));
		}
		catch (Exception e) {
			fail("   FAILED. Exception:" + e.toString());
		}
	}
}
