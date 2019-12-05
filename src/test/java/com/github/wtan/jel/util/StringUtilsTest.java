package com.github.wtan.jel.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wtan.jel.util.StringUtils;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

	public StringUtilsTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
	}

	public void testAbbreviate() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.abbreviate("Now is the time for all good men", 26);
			assertEquals("Now is the time for all...", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testCapitalize() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.capitalize("now is the time for all good men");
			assertEquals("Now is the time for all good men", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testCenter() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.center("now", 13);
			assertEquals("     now     ", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testChop() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.chop("now");
			assertEquals("no", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testContains() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.contains("now is the time for all good men", "time for");
			assertTrue(res);
			res = sutil.contains("now is the time for all good men", "xyzzy");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testContainsAny() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.containsAny("now is the time for all good men", "aeio");
			assertTrue(res);
			res = sutil.containsAny("now is the time for all good men", "u");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testContainsNone() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.containsNone("now is the time for all good men", "xyz");
			assertTrue(res);
			res = sutil.containsNone("now is the time for all good men", "aeio");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testContainsOnly() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.containsOnly("xyzzy", "xyz");
			assertTrue(res);
			res = sutil.containsOnly("xyzzy", "xyu");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testContainsIgnoreCase() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.containsIgnoreCase("xyzzy", "XYz");
			assertTrue(res);
			res = sutil.containsIgnoreCase("xyzzy", "Uu");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testCountMatches() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.countMatches("one two three two one", "one");
			assertTrue(count == 2);
			count = sutil.countMatches("one two three two one", "three");
			assertTrue(count == 1);
			count = sutil.countMatches("one two three two one", "four");
			assertTrue(count == 0);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testDefaultIfBlank() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.defaultIfBlank("one two", "three");
			assertTrue(str.equals("one two"));
			str = sutil.defaultIfBlank("    ", "three");
			assertTrue(str.equals("three"));
			str = sutil.defaultIfBlank("", "three");
			assertTrue(str.equals("three"));
			str = sutil.defaultIfBlank(null, "three");
			assertTrue(str.equals("three"));
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testDefaultIfEmpty() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.defaultIfEmpty("one two", "three");
			assertTrue(str.equals("one two"));
			str = sutil.defaultIfEmpty("    ", "three");
			assertTrue(str.equals("    "));
			str = sutil.defaultIfEmpty("", "three");
			assertTrue(str.equals("three"));
			str = sutil.defaultIfEmpty(null, "three");
			assertTrue(str.equals("three"));
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testDefaultString() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.defaultString("one two", "three");
			assertTrue(str.equals("one two"));
			str = sutil.defaultString("    ", "three");
			assertTrue(str.equals("    "));
			str = sutil.defaultString("", "three");
			assertTrue(str.equals(""));
			str = sutil.defaultString(null, "three");
			assertTrue(str.equals("three"));
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testEndsWith() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.endsWith("xyzzy", "zy");
			assertTrue(res);
			res = sutil.endsWith("xyzzy", "ZY");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testEndsWithIgnoreCase() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.endsWithIgnoreCase("xyzzy", "zy");
			assertTrue(res);
			res = sutil.endsWithIgnoreCase("xyzzy", "ZY");
			assertTrue(res);
			res = sutil.endsWithIgnoreCase("xyzzy", "xy");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testEquals() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.equals("xyzzy", "xyzzy");
			assertTrue(res);
			res = sutil.equals("xyzzy", "XYZZY");
			assertTrue(!res);
			res = sutil.equals("xyzzy", "yzzyx");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testEqualsIgnoreCase() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.equalsIgnoreCase("xyzzy", "xyzzy");
			assertTrue(res);
			res = sutil.equalsIgnoreCase("xyzzy", "XYZZY");
			assertTrue(res);
			res = sutil.equalsIgnoreCase("xyzzy", "yzzyx");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testIndexOf() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.indexOf("one two three two one", "three");
			assertTrue(count == 8);
			count = sutil.indexOf("one two three two one", "four");
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testIndexOf_start() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.indexOf("one two three two one", "three", 4);
			assertTrue(count == 8);
			count = sutil.indexOf("one two three two one", "three", 14);
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testIndexOfAny() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.indexOfAny("one two three two one", "thr");
			assertTrue(count == 4);
			count = sutil.indexOfAny("one two three two one", "xyz");
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testIndexOfIgnoreCase() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.indexOfIgnoreCase("one two three two one", "thr");
			assertTrue(count == 8);
			count = sutil.indexOfIgnoreCase("one two three two one", "THR");
			assertTrue(count == 8);
			count = sutil.indexOfIgnoreCase("one two three two one", "XYZ");
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testIndexOfIgnoreCase_start() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.indexOfIgnoreCase("one two three two one", "thr", 4);
			assertTrue(count == 8);
			count = sutil.indexOfIgnoreCase("one two three two one", "thr", 14);
			assertTrue(count == -1);
			count = sutil.indexOfIgnoreCase("one two three two one", "THR", 4);
			assertTrue(count == 8);
			count = sutil.indexOfIgnoreCase("one two three two one", "THR", 14);
			assertTrue(count == -1);
			count = sutil.indexOfIgnoreCase("one two three two one", "XYZ");
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testIsBlank() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.isBlank("xyzzy");
			assertTrue(!res);
			res = sutil.isBlank("     ");
			assertTrue(res);
			res = sutil.isBlank("");
			assertTrue(res);
			res = sutil.isBlank(null);
			assertTrue(res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testIsAlphanumeric() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.isAlphanumeric("xyzZY123");
			assertTrue(res);
			res = sutil.isAlphanumeric("     ");
			assertTrue(!res);
			res = sutil.isAlphanumeric("");
			assertTrue(res);
			res = sutil.isAlphanumeric(null);
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testIsNumeric() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.isNumeric("xyzZY123");
			assertTrue(!res);
			res = sutil.isNumeric("123");
			assertTrue(res);
			res = sutil.isNumeric("    ");
			assertTrue(!res);
			res = sutil.isNumeric("");
			assertTrue(res);
			res = sutil.isNumeric(null);
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testLastIndexOf() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.lastIndexOf("one two three two one", "two");
			assertTrue(count == 14);
			count = sutil.lastIndexOf("one two three two one", "four");
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testLastIndexOf_start() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.lastIndexOf("one two three two one", "two", 18);
			assertTrue(count == 14);
			count = sutil.lastIndexOf("one two three two one", "two", 12);
			assertTrue(count == 4);
			count = sutil.lastIndexOf("one two three two one", "two", 4);
			assertTrue(count == 4);
			count = sutil.lastIndexOf("one two three two one", "two", 3);
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testLastIndexOfIgnoreCase() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.lastIndexOfIgnoreCase("one two three two one", "Two");
			assertTrue(count == 14);
			count = sutil.lastIndexOfIgnoreCase("one two three two one", "fouR");
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testLastIndexOfIgnoreCase_start() {
		try {
			StringUtils sutil = new StringUtils();
			int count = sutil.lastIndexOfIgnoreCase("one two three two one", "Two", 18);
			assertTrue(count == 14);
			count = sutil.lastIndexOfIgnoreCase("one two three two one", "tWo", 12);
			assertTrue(count == 4);
			count = sutil.lastIndexOfIgnoreCase("one two three two one", "twO", 4);
			assertTrue(count == 4);
			count = sutil.lastIndexOfIgnoreCase("one two three two one", "two", 3);
			assertTrue(count == -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testLeftPad() {
		try {
			StringUtils sutil = new StringUtils();
			String padded = sutil.leftPad("123456", 16, "abc");
			assertEquals("abcabcabca123456", padded);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testLeftPadZeros() {
		try {
			StringUtils sutil = new StringUtils();
			String padded = sutil.leftPadZeros("123", 6);
			assertEquals("000123", padded);
			padded = sutil.leftPadZeros("123", 2);
			assertEquals("123", padded);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testLength() {
		try {
			StringUtils sutil = new StringUtils();
			int len = sutil.length("123456abc");
			assertEquals(len, 9);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testLowerCase() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.lowerCase("123");
			assertEquals("123", str);
			str = sutil.lowerCase("Xyzzy");
			assertEquals("xyzzy", str);
			str = sutil.lowerCase("XYZZY");
			assertEquals("xyzzy", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testMid() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.mid("123456abc", 3, 2);
			assertEquals(str, "45");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testOverlay() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.overlay("123456abc", "XYZ", 3, 6);
			assertEquals(str, "123XYZabc");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRemove() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.remove("123456abc", "456");
			assertEquals(str, "123abc");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRemoveEnd() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.removeEnd("123456abc", "abc");
			assertEquals(str, "123456");
			str = sutil.removeEnd("123456abc", "456");
			assertEquals(str, "123456abc");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRemoveEndIgnoreCase() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.removeEndIgnoreCase("123456Abc", "abc");
			assertEquals(str, "123456");
			str = sutil.removeEndIgnoreCase("123456abc", "456");
			assertEquals(str, "123456abc");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRemoveStart() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.removeStart("123456abc", "123");
			assertEquals(str, "456abc");
			str = sutil.removeStart("123456abc", "456");
			assertEquals(str, "123456abc");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRemoveStartIgnoreCase() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.removeStartIgnoreCase("XyzzyAbc", "XYZ");
			assertEquals(str, "zyAbc");
			str = sutil.removeStartIgnoreCase("XyzzyAbc", "zzy");
			assertEquals(str, "XyzzyAbc");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRepeat() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.repeat("xyz", 4);
			assertEquals(str, "xyzxyzxyzxyz");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRepeat_separator() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.repeat("xyz", ":", 4);
			assertEquals(str, "xyz:xyz:xyz:xyz");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testReplace() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.replace("Now is the time for all good men", "time", "moment");
			assertEquals("Now is the moment for all good men", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testReplaceChars() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.replaceChars("Now is the time for all good men", "mo", "na");
			assertEquals("Naw is the tine far all gaad nen", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testReplaceOnce() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.replaceOnce("Now is the time for the truth", "the", "our");
			assertEquals("Now is our time for the truth", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testReverse() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.reverse("xyzzy");
			assertEquals("yzzyx", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testRightPad() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.rightPad("xyzzy", 12, "abc");
			assertEquals("xyzzyabcabca", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testStartsWith() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.startsWith("xyzzy", "xy");
			assertTrue(res);
			res = sutil.startsWith("xyzzy", "XY");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testStartsWithIgnoreCase() {
		try {
			StringUtils sutil = new StringUtils();
			boolean res = sutil.startsWithIgnoreCase("xyzzy", "xy");
			assertTrue(res);
			res = sutil.startsWithIgnoreCase("xyzzy", "XY");
			assertTrue(res);
			res = sutil.startsWithIgnoreCase("xyzzy", "zy");
			assertTrue(!res);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testStrip() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.strip("   xyzzy   ");
			assertEquals("xyzzy", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testSubstring() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.substring("Now is the time for all good men", 11);
			assertEquals("time for all good men", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testSubstring_start_end() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.substring("Now is the time for all good men", 11, 23);
			assertEquals("time for all", str);
			String out = sutil.substring(null, 0, 1);
			assertNull(out);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testSubstringAfter() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.substringAfter("Now is the time for all good men", "for ");
			assertEquals("all good men", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testSubstringAfterLast() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.substringAfterLast("Now is the time for all the men", "the ");
			assertEquals("men", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testSubstringBefore() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.substringBefore("Now is the time for all the men", "the");
			assertEquals("Now is ", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testSubstringBeforeLast() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.substringBeforeLast("Now is the time for all the men", "the");
			assertEquals("Now is the time for all ", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testSubstringBetween() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.substringBetween("Now is the time for all the men", "the ", " the men");
			assertEquals("time for all", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testLeft() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.left("Now is the time for all the men", 3);
			assertEquals("Now", str);
			str = sutil.left("Now is the time for all the men", 99);
			assertEquals("Now is the time for all the men", str);
			str = sutil.left(null, 99);
			assertEquals(null, str);
			str = sutil.left("Now is the time for all the men", -5);
			assertEquals("", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testRight() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.right("Now is the time for all the men", 3);
			assertEquals("men", str);
			str = sutil.left("Now is the time for all the men", 99);
			assertEquals("Now is the time for all the men", str);
			str = sutil.left(null, 99);
			assertEquals(null, str);
			str = sutil.left("Now is the time for all the men", -5);
			assertEquals("", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testTrim() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.trim("   xyzzy   ");
			assertEquals("xyzzy", str);
			str = sutil.trim(null);
			assertEquals(null, str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testUncapitalize() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.uncapitalize("Now is the time for all the men");
			assertEquals("now is the time for all the men", str);
			str = sutil.uncapitalize("ABCDEF");
			assertEquals("aBCDEF", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testUpperCase() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.upperCase("Now is the time");
			assertEquals("NOW IS THE TIME", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testReadFile() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.readFile("pom.xml");
			assertTrue(str.contains("http://maven.apache.org"));
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testSeparatorsToSystem() {
		try {
			StringUtils sutil = new StringUtils();
			String str = sutil.separatorsToSystem(null);
			assertEquals(null, str);
			if (File.separatorChar == '\\') {
				str = sutil.separatorsToSystem("/dir");
				assertEquals("\\dir", str);
			}
			if (File.separatorChar == '/') {
				str = sutil.separatorsToSystem("\\dir");
				assertEquals("/dir", str);
			}
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testshiftLinesUp() {
		try {
			StringUtils sutil = new StringUtils();
			List<String> list = new ArrayList<String>(9);
			list.add("Line 1");
			list.add("Line 2");
			list.add("");
			list.add("");
			list.add("");
			list.add("Line 3");
			list.add("");
			list.add("Line 4");
			list.add("Line 5");
			sutil.shiftLinesUp(list);
			String out = list.toString();
			assertTrue(out.contains("[Line 1, Line 2, Line 3, Line 4, Line 5,"));
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testshiftLinesUp2() {
		try {
			StringUtils sutil = new StringUtils();
			List<String> list = new ArrayList<String>(9);
			list.add("Line 1");
			list.add("");
			list.add(null);
			list.add("Line 2");
			list.add("");
			list.add("Line 3");
			list.add("");
			list.add("Line 4");
			list.add("");
			sutil.shiftLinesUp(list);
			String out = list.toString();
			assertTrue(out.contains("[Line 1, Line 2, Line 3, Line 4,"));
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testshiftLinesUpOneLine() {
		try {
			StringUtils sutil = new StringUtils();
			List<String> list = new ArrayList<String>(9);
			list.add("Line 1");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			sutil.shiftLinesUp(list);
			String out = list.toString();
			assertTrue(out.contains("[Line 1,"));
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testshiftLinesUpNoLines() {
		try {
			StringUtils sutil = new StringUtils();
			List<String> list = new ArrayList<String>(9);
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
			sutil.shiftLinesUp(list);
			String out = list.toString();
			assertTrue(out.contains("[, , , , , , , , ]"));
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
	
	public void testSubstituteVars() {
		try {
			StringUtils sutil = new StringUtils();
			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("animal", "quick brown fox");
			valuesMap.put("target", "lazy dog");
			String templateString = "The ${animal} jumps over the ${target}.";
			String str = sutil.substituteVars(templateString, valuesMap);
			assertEquals("The quick brown fox jumps over the lazy dog.", str);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}	
	
	public void testfindPositiveOrNegDollar() {
		StringUtils sutil = new StringUtils();
		String out = sutil.findPositiveOrNegDollar("-300");
		assertTrue(out.equals("-"));
	}

	public void testfindPositiveOrNegDollarGivenNegDollarValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.findPositiveOrNegDollar("$300-");
		assertTrue(out.equals("-"));
	}

	public void testfindPositiveOrNegDollarGivenPosDollarValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.findPositiveOrNegDollar("$300");
		assertTrue(out.equals("+"));
	}
	
	public void testfindPositiveOrNegDollarGivenNullValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.findPositiveOrNegDollar("");
		assertTrue(out.equals(""));
	}
	
	public void testfindPositiveOrNegDollarGivenZeroValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.findPositiveOrNegDollar("0");
		assertTrue(out.equals("+"));
	}
	
	public void testfindPositiveOrNegDollarGivenEmptyValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.findPositiveOrNegDollar(null);
		assertTrue(out.equals(""));
	}

	public void testPositivesubtractAbsoluteDollarValues() {
		StringUtils sutil = new StringUtils();
		String out = sutil.subtractAbsoluteDollarValues("$500", "$345");
		assertTrue(out.equals("$155.00"));
	}

	public void testNegativeFirstsubtractAbsoluteDollarValues() {
		StringUtils sutil = new StringUtils();
		String out = sutil.subtractAbsoluteDollarValues("$500-", "$345");
		assertTrue(out.equals("-$845.00"));
	}
	
	public void testNegativeSecondsubtractAbsoluteDollarValues() {
		StringUtils sutil = new StringUtils();
		String out = sutil.subtractAbsoluteDollarValues("$500", "$300-");
		assertTrue(out.equals("$200.00"));
	}
	
	public void testSecondValueNullsubtractAbsoluteDollarValues() {
		StringUtils sutil = new StringUtils();
		String out = sutil.subtractAbsoluteDollarValues("$500", null);
		assertTrue(out.equals("$500.00"));
	}
	
	public void testFirstValueNullsubtractAbsoluteDollarValues() {
		StringUtils sutil = new StringUtils();
		String out = sutil.subtractAbsoluteDollarValues(null, "$345-");
		assertTrue(out.equals("-$345.00"));
	}

	public void testBothValueNullsubtractAbsoluteDollarValues() {
		StringUtils sutil = new StringUtils();
		String out = sutil.subtractAbsoluteDollarValues(null, null);
		assertTrue(out.equals("$0.00"));
	}
	
	public void testConvertNumToDollarGivenZeroValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertNumToDollar(0);
		assertTrue(out.equals("$0.00"));
	}
	
	public void testConvertNumToDollarGivenPositiveValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertNumToDollar(+10);
		assertTrue(out.equals("$10.00"));
	}
	
	public void testConvertNumToDollarGivenNegativeValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertNumToDollar(-10);
		assertTrue(out.equals("-$10.00"));
	}

	public void testConvertNumStringToDollarGivenPositiveValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertDollarToNumString("$345");
		out = sutil.convertNumToDollar(out);
		assertTrue(out.equals("$345.00"));
	}

	public void testConvertNumStringToDollarGivenStringFormat() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertDollarToNumString("$345.990909-");
		out = sutil.convertNumToDollar(out, "#,##0.00000000");
		assertTrue(out.equals("-$345.99090900"));
		out = sutil.convertNumToDollar("", "#,##0.00000000");
		assertTrue(out.equals("$0.00000000"));
	}
	
	public void testConvertNumStringToDollarGivenNegativeValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertDollarToNumString("$345-");
		out = sutil.convertNumToDollar(out);
		assertTrue(out.equals("-$345.00"));
	}
	
	public void testConvertNumStringToDollarGivenEmptyValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertNumToDollar(null);
		assertTrue(out.equals("$0.00"));
	}

	public void testConvertNumStringToPosDollarGivenPositiveValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertDollarToNumString("$345");
		out = sutil.convertNumToPositiveDollar(out);
		assertTrue(out.equals("$345.00"));
	}

	public void testConvertNumStringToPosDollarGivenNegativeValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertDollarToNumString("$345-");
		out = sutil.convertNumToPositiveDollar(out);
		assertTrue(out.equals("$345.00"));
	}
	
	public void testConvertNumStringToPosDollarGivenEmptyValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertNumToPositiveDollar(null);
		assertTrue(out.equals("$0.00"));
	}
	
	public void testConvertNumToPercentGivenNegativeValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertNumToPercent("-10.000");
		assertTrue(out.equals("-10.00%"));
	}

	public void testConvertNumToPercentGivenPositiveValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertNumToPercent("4.000");
		assertTrue(out.equals("4.00%"));
		out = sutil.convertNumToPercent("");
		assertTrue(out.equals("0.00%"));
	}

	public void testConvertNumToPercentGivenZeroValue() {
		StringUtils sutil = new StringUtils();
		String out = sutil.convertNumToPercent("0");
		assertTrue(out.equals("0.00%"));
	}
	
	public void testmoveSignUp() {
		StringUtils sutil = new StringUtils();
		String out = sutil.moveSignUpTest("3.141-");
		assertTrue(out.equals("-3.141"));
		out = sutil.moveSignUpTest("3.141+");
		assertTrue(out.equals("+3.141"));
	}
	
	public void testaddDollarValues() {
		StringUtils sutil = new StringUtils();
		String out = sutil.addDollarValues("3.14", "6.25");
		assertTrue(out.equals("$9.39"));
	}
}
