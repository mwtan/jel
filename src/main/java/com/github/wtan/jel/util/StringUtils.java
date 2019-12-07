package com.github.wtan.jel.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;

/**
 * Provides string utilities from org.apache.commons.lang.StringUtils as non-static
 * methods that can be used by Expressions from com.mm.enterprise.util.Condition.
 * <p>
 * @author Will Tan
 */
public class StringUtils {

	public StringUtils() {
	}
			
	/**
	 * Abbreviates a String using ellipses. This will turn "Now is the time for all good men" 
	 * into "Now is the time for..."
	 * @param str
	 * @param maxWidth
	 * @return
	 */
	public String abbreviate(String str, int maxWidth) {
		return org.apache.commons.lang.StringUtils.abbreviate(str, maxWidth);
	}
	
	/**
	 * Capitalizes a String changing the first letter to title case.
	 * @param str
	 * @return
	 */
	public String capitalize(String str) {
		return org.apache.commons.lang.StringUtils.capitalize(str);
	}
	
	/**
	 * Centers a String in a larger String of size size using the space character (' '). 
	 * If the size is less than the String length, the String is returned. A null String 
	 * returns null. A negative size is treated as zero.
	 * @param str
	 * @param size
	 * @return
	 */
	public String center(String str, int size) {
		return org.apache.commons.lang.StringUtils.center(str, size);
	}
	
	/**
	 * Remove the last character from a String.
	 * @param str
	 * @return
	 */
	public String chop(String str) {
		return org.apache.commons.lang.StringUtils.chop(str);
	}
	
	/**
	 * Checks if String contains a search String, handling null.
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public boolean contains(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.contains(str, searchStr);
	}
	
	/**
	 * Checks if the String contains any character in the given set of characters.
	 * A null String will return false. A null search string will return false.
	 * @param str
	 * @param searchChars
	 * @return
	 */
	public boolean containsAny(String str, String searchChars) {
		return org.apache.commons.lang.StringUtils.containsAny(str, searchChars);
	}
	
	/**
	 * Checks that the String does not contain certain characters. A null String will 
	 * return true. A null invalid character array will return true. An empty String ("") 
	 * always returns true.
	 * @param str
	 * @param invalidChars
	 * @return
	 */
	public boolean containsNone(String str, String invalidChars) {
		return org.apache.commons.lang.StringUtils.containsNone(str, invalidChars);
	}

	/**
	 * Checks if the String contains only certain characters. A null String will 
	 * return false. A null valid character String will return false. An empty 
	 * String (length()=0) always returns true.
	 * @param str
	 * @param validChars
	 * @return
	 */
	public boolean containsOnly(String str, String validChars) {
		return org.apache.commons.lang.StringUtils.containsOnly(str, validChars);
	}

	/**
	 * Checks if String contains a search String irrespective of case, handling null.
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public boolean containsIgnoreCase(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.containsIgnoreCase(str, searchStr);
	}

	/**
	 * Counts how many times the substring appears in the larger String.
	 * A null or empty ("") String input returns 0.
	 * @param str
	 * @param sub
	 * @return
	 */
	public int countMatches(String str, String sub) {
		return org.apache.commons.lang.StringUtils.countMatches(str, sub);
	}
	
	/**
	 * Returns either the passed in String, or if the String is whitespace, 
	 * empty ("") or null, the value of defaultStr.
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public String defaultIfBlank(String str, String defaultStr) {
		return org.apache.commons.lang.StringUtils.defaultIfBlank(str, defaultStr);
	}
	
	/**
	 * Returns either the passed in String, or if the String is empty or null, the 
	 * value of defaultStr.
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public String defaultIfEmpty(String str, String defaultStr) {
		return org.apache.commons.lang.StringUtils.defaultIfEmpty(str, defaultStr);
	}

	/**
	 * Returns either the passed in String, or if the String is null, the value of defaultStr.
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public String defaultString(String str, String defaultStr) {
		return org.apache.commons.lang.StringUtils.defaultString(str, defaultStr);
	}

	/**
	 * Check if a String ends with a specified suffix. nulls are handled without exceptions. 
	 * Two null references are considered to be equal. The comparison is case sensitive.
	 * @param str
	 * @param suffix
	 * @return
	 */
	public boolean endsWith(String str, String suffix) {
		return org.apache.commons.lang.StringUtils.endsWith(str, suffix);
	}
	
	/**
	 * Case insensitive check if a String ends with a specified suffix. nulls are handled without 
	 * exceptions. Two null references are considered to be equal. The comparison is case insensitive.
	 * @param str
	 * @param suffix
	 * @return
	 */
	public boolean endsWithIgnoreCase(String str, String suffix) {
		return org.apache.commons.lang.StringUtils.endsWithIgnoreCase(str, suffix);
	}
	
	/**
	 * Compares two Strings, returning true if they are equal. nulls are handled without exceptions.
	 * Two null references are considered to be equal. The comparison is case sensitive.
	 * @param str1
	 * @param str2
	 * @return
	 */
	public boolean equals(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.equals(str1, str2);
	}

	/**
	 * Compares two Strings, returning true if they are equal ignoring the case. nulls are handled 
	 * without exceptions. Two null references are considered equal. Comparison is case insensitive.
	 * @param str1
	 * @param str2
	 * @return
	 */
	public boolean equalsIgnoreCase(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.equalsIgnoreCase(str1, str2);
	}

	/**
	 * Returns the index within this string of the first occurrence of the specified search string.
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public int indexOf(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.indexOf(str, searchStr);
	}

	/**
	 * Returns the index within this string of the first occurrence of the specified search string, 
	 * starting at the specified position.
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public int indexOf(String str, String searchStr, int startPos) {
		return org.apache.commons.lang.StringUtils.indexOf(str, searchStr, startPos);
	}
	
	/**
	 * Search a String to find the first index of any character in the given set of characters.
	 * @param str
	 * @param searchChars
	 * @return
	 */
	public int indexOfAny(String str, String searchChars) {
		return org.apache.commons.lang.StringUtils.indexOfAny(str, searchChars);
	}

	/**
	 * Case in-sensitive find of the first index within a String.
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public int indexOfIgnoreCase(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.indexOfIgnoreCase(str, searchStr);
	}
	
	/**
	 * Case in-sensitive find of the first index within a String of the specified search string, 
	 * starting at the specified position.
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public int indexOfIgnoreCase(String str, String searchStr, int startPos) {
		return org.apache.commons.lang.StringUtils.indexOfIgnoreCase(str, searchStr, startPos);
	}

	/**
	 * Checks if a String is whitespace, empty ("") or null.
	 * @param str
	 * @return
	 */
	public boolean isBlank(String str) {
		return org.apache.commons.lang.StringUtils.isBlank(str);
	}
	
	/**
	 * Checks if the String contains only unicode letters or digits. null will return 
	 * false. An empty String (length()=0) will return true.
	 * @param str
	 * @return
	 */
	public boolean isAlphanumeric(String str) {
		return org.apache.commons.lang.StringUtils.isAlphanumeric(str);
	}

	/**
	 * Checks if the String contains only unicode digits. A decimal point is not a unicode
	 * digit and returns false.
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		return org.apache.commons.lang.StringUtils.isNumeric(str);
	}
	
	/**
	 * Returns the index within this string of the last occurrence of the specified substring.
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public int lastIndexOf(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.lastIndexOf(str, searchStr);
	}

	/**
	 * Returns the index within this string of the last occurrence of the specified substring, 
	 * searching backward starting at the specified position.
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public int lastIndexOf(String str, String searchStr, int startPos) {
		return org.apache.commons.lang.StringUtils.lastIndexOf(str, searchStr, startPos);
	}

	/**
	 * Case in-sensitive find of the last index within a String.
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public int lastIndexOfIgnoreCase(String str, String searchStr) {
		return org.apache.commons.lang.StringUtils.lastIndexOfIgnoreCase(str, searchStr);
	}
	
	/**
	 * Case in-sensitive find of the last index within a String of the specified substring, 
	 * searching backward starting at the specified position.
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public int lastIndexOfIgnoreCase(String str, String searchStr, int startPos) {
		return org.apache.commons.lang.StringUtils.lastIndexOfIgnoreCase(str, searchStr, startPos);
	}

	/**
	 * Left pad a String with a specified String.
	 * @param str
	 * @param size
	 * @param padStr
	 * @return
	 */
	public String leftPad(String str, int size, String padStr) {
		return org.apache.commons.lang.StringUtils.leftPad(str, size, padStr);
	}
	
	/**
	 * Left pad a string with zeros.
	 * @param val
	 * @param width
	 * @return
	 */
	public String leftPadZeros(String val, int width) {
		if (width > val.length()) {
			return org.apache.commons.lang.StringUtils.leftPad(val, width, '0');
		}
		else {
			return val;
		}
	}

	/**
	 * Gets a String's length or 0 if the String is null.
	 * @param str
	 * @return
	 */
	public int length(String str) {
		return org.apache.commons.lang.StringUtils.length(str);
	}
	
	/**
	 * Converts a String to lower case as per String.toLowerCase().
	 * @param str
	 * @return
	 */
	public String lowerCase(String str) {
		return org.apache.commons.lang.StringUtils.lowerCase(str);
	}
	
	/**
	 * Gets len characters from the middle of a String.
	 * If len characters are not available, the remainder of the String will be returned 
	 * without an exception. If the String is null, null will be returned. An empty String 
	 * is returned if len is negative or exceeds the length of str.
	 * @param str
	 * @param pos
	 * @param len
	 * @return
	 */
	public String mid(String str, int pos, int len) {
		return org.apache.commons.lang.StringUtils.mid(str, pos, len);
	}
	
	/**
	 * Overlays part of a String with another String.
	 * @param str
	 * @param overlay
	 * @param start
	 * @param end
	 * @return
	 */
	public String overlay(String str, String overlay, int start, int end) {
		return org.apache.commons.lang.StringUtils.overlay(str, overlay, start, end);
	}
	
	/**
	 * Removes all occurrences of a substring from within the source string.
	 * @param str
	 * @param remove
	 * @return
	 */
	public String remove(String str, String remove) {
		return org.apache.commons.lang.StringUtils.remove(str, remove);
	}
	
	/**
	 * Removes a substring only if it is at the end of a source string, otherwise returns 
	 * the source string.
	 * @param str
	 * @param remove
	 * @return
	 */
	public String removeEnd(String str, String remove) {
		return org.apache.commons.lang.StringUtils.removeEnd(str, remove);
	}
	
	/**
	 * Case insensitive removal of a substring if it is at the end of a source string, 
	 * otherwise returns the source string.
	 * @param str
	 * @param remove
	 * @return
	 */
	public String removeEndIgnoreCase(String str, String remove) {
		return org.apache.commons.lang.StringUtils.removeEndIgnoreCase(str, remove);
	}
	
	/**
	 * Removes a substring only if it is at the begining of a source string, otherwise 
	 * returns the source string.
	 * @param str
	 * @param remove
	 * @return
	 */
	public String removeStart(String str, String remove) {
		return org.apache.commons.lang.StringUtils.removeStart(str, remove);
	}
	
	/**
	 * Case insensitive removal of a substring if it is at the begining of a source string, 
	 * otherwise returns the source string.
	 * @param str
	 * @param remove
	 * @return
	 */
	public String removeStartIgnoreCase(String str, String remove) {
		return org.apache.commons.lang.StringUtils.removeStartIgnoreCase(str, remove);
	}

	/**
	 * Repeat a String repeat times to form a new String.
	 * @param str
	 * @param repeat
	 * @return
	 */
	public String repeat(String str, int repeat) {
		return org.apache.commons.lang.StringUtils.repeat(str, repeat);
	}
	
	/**
	 * Repeat a String repeat times to form a new String, with a String separator injected each time.
	 * @param str
	 * @param separator
	 * @param repeat
	 * @return
	 */
	public String repeat(String str, String separator, int repeat) {
		return org.apache.commons.lang.StringUtils.repeat(str, separator, repeat);
	}

	/**
	 * Replaces all occurrences of a String within another String.
	 * @param text
	 * @param searchString
	 * @param replacement
	 * @return
	 */
	public String replace(String text, String searchString, String replacement) {
		return org.apache.commons.lang.StringUtils.replace(text, searchString, replacement);
	}
	
	/**
	 * Replaces multiple characters in a String in one go. This method can also be used 
	 * to delete characters.
	 * @param str
	 * @param searchChars
	 * @param replaceChars
	 * @return
	 */
	public String replaceChars(String str, String searchChars, String replaceChars) {
		return org.apache.commons.lang.StringUtils.replaceChars(str, searchChars, replaceChars);
	}
	
	/**
	 * Replaces a String with another String inside a larger String, once.
	 * @param text
	 * @param searchString
	 * @param replacement
	 * @return
	 */
	public String replaceOnce(String text, String searchString, String replacement) {
		return org.apache.commons.lang.StringUtils.replaceOnce(text, searchString, replacement);
	}
	
	/**
	 * Reverses a String as per StrBuilder.reverse().
	 * @param str
	 * @return
	 */
	public String reverse(String str) {
		return org.apache.commons.lang.StringUtils.reverse(str);
	}
	
	/**
	 * Right pad a String with a specified String.
	 * @param str
	 * @param size
	 * @param padStr
	 * @return
	 */
	public String rightPad(String str, int size, String padStr) {
		return org.apache.commons.lang.StringUtils.rightPad(str, size, padStr);
	}
	
	/**
	 * Check if a String starts with a specified prefix.
	 * @param str
	 * @param prefix
	 * @return
	 */
	public boolean startsWith(String str, String prefix) {
		return org.apache.commons.lang.StringUtils.startsWith(str, prefix);
	}
	
	/**
	 * Case insensitive check if a String starts with a specified prefix.
	 * @param str
	 * @param prefix
	 * @return
	 */
	public boolean startsWithIgnoreCase(String str, String prefix) {
		return org.apache.commons.lang.StringUtils.startsWithIgnoreCase(str, prefix);
	}
	
	
	/**
	 * Strips whitespace from the start and end of a String.
	 * @param str
	 * @return
	 */
	public String strip(String str) {
		return org.apache.commons.lang.StringUtils.strip(str);
	}
	
	/**
	 * Gets a substring from the specified String avoiding exceptions.
	 * The substring begins with the character at the specified index and extends 
	 * to the end of this string.
	 * @param str
	 * @param start
	 * @return
	 */
	public String substring(String str, int start) {
		return org.apache.commons.lang.StringUtils.substring(str, start);
	}
	
	/**
	 * Gets a substring from the specified String avoiding exceptions.
	 * The substring begins at the specified beginIndex and extends to the character 
	 * at index endIndex - 1. Thus the length of the substring is endIndex-beginIndex.
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public String substring(String str, int start, int end) {
		return org.apache.commons.lang.StringUtils.substring(str, start, end);
	}
	
	/**
	 * Gets the substring after the first occurrence of a separator. The 
	 * separator is not returned.
	 * @param str
	 * @param separator
	 * @return
	 */
	public String substringAfter(String str, String separator) {
		return org.apache.commons.lang.StringUtils.substringAfter(str, separator);
	}
	
	/**
	 * Gets the substring after the last occurrence of a separator. The 
	 * separator is not returned.
	 * @param str
	 * @param separator
	 * @return
	 */
	public String substringAfterLast(String str, String separator) {
		return org.apache.commons.lang.StringUtils.substringAfterLast(str, separator);
	}
	
	/**
	 * Gets the substring before the first occurrence of a separator. The 
	 * separator is not returned.
	 * @param str
	 * @param separator
	 * @return
	 */
	public String substringBefore(String str, String separator) {
		return org.apache.commons.lang.StringUtils.substringBefore(str, separator);
	}
	
	/**
	 * Gets the substring before the last occurrence of a separator. The 
	 * separator is not returned.
	 * @param str
	 * @param separator
	 * @return
	 */
	public String substringBeforeLast(String str, String separator) {
		return org.apache.commons.lang.StringUtils.substringBeforeLast(str, separator);
	}
	
	/**
	 * Gets the String that is nested in between two Strings. Only the first 
	 * match is returned.
	 * @param str
	 * @param open
	 * @param close
	 * @return
	 */
	public String substringBetween(String str, String open, String close) {
		return org.apache.commons.lang.StringUtils.substringBetween(str, open, close);
	}
	
	/**
	 * Gets the leftmost len characters of a String. If len characters are not available, 
	 * or the String is null, the String will be returned without an exception. An empty 
	 * String is returned if len is negative.
	 * @param str
	 * @param len
	 * @return
	 */
	public String left(String str, int len) {
		return org.apache.commons.lang.StringUtils.left(str, len);
	}

	/**
	 * Gets the rightmost len characters of a String. If len characters are not available,
	 * or the String is null, the String will be returned without an an exception. An empty 
	 * String is returned if len is negative.
	 * @param str
	 * @param len
	 * @return
	 */
	public String right(String str, int len) {
		return org.apache.commons.lang.StringUtils.right(str, len);
	}

	/**
	 * Removes control characters (char <= 32) from both ends of this String, 
	 * handling null by returning null.
	 * @param str
	 * @return
	 */
	public String trim(String str) {
		return org.apache.commons.lang.StringUtils.trim(str);
	}
	
	/**
	 * Uncapitalizes a String changing the first letter to title case as per 
	 * Character.toLowerCase(char). No other letters are changed.
	 * @param str
	 * @return
	 */
	public String uncapitalize(String str) {
		return org.apache.commons.lang.StringUtils.uncapitalize(str);
	}
	
	/**
	 * Converts a String to upper case as per String.toUpperCase().
	 * @param str
	 * @return
	 */
	public String upperCase(String str) {
		return org.apache.commons.lang.StringUtils.upperCase(str);
	}
	
	/**
	 * Reads the contents of the specified file and returns it as a String.
	 * @param path
	 * @return the contents of the file as a String. 
	 * @throws IOException When file is not found.
	 */
	public String readFile(String path) throws IOException	{
		String outstr = "";
		byte[] encoded;
		encoded = Files.readAllBytes(Paths.get(path));
		outstr = new String(encoded);
		return outstr;
	}
	
	/**
	 * Converts separators in a file path to the system appropriate separator. 
	 * @param path
	 * @return
	 */
	public String separatorsToSystem(String path) {
	    if (path == null) {
	    	return null;
	    }
	    if (File.separatorChar == '\\') {
	        // From Windows to Linux/Mac
	        return path.replace('/', File.separatorChar);
	    } else {
	        // From Linux/Mac to Windows
	        return path.replace('\\', File.separatorChar);
	    }
	}
	
	/**
	 * Shift lines up to the top of the list, removing blank lines.
	 * @param lines
	 */
	public void shiftLinesUp(List<String> lines) {
		int pos = findBlankPos(lines, findNonBlankPos(lines, lines.size()-1));
		while (pos >= 0) {
			Collections.rotate(lines.subList(pos, lines.size()), -1);
			pos--;
			if (pos >= 0) {
				pos = findBlankPos(lines, pos);
			}
		}
	}
	
	/**
	 * Performs substitution of variable in a template string with values from a
	 * supplied Map. The default definition of a variable is ${variableName}.
	 * @param templatestr
	 * @param valuesmap
	 * @return The substituted string.
	 */
	public String substituteVars(String templatestr, Map valuesmap) {
		StrSubstitutor sub = new StrSubstitutor(valuesmap);
		String resolvedString = sub.replace(templatestr);
		return resolvedString;
	}
	
	/**
	 * checks if a number is positive or negative
	 * @param numStringLiteral
	 * @return String
	 */
	public String findPositiveOrNegDollar(String numStringLiteral) {
		if(!isBlank(numStringLiteral)) {
			numStringLiteral = convertDollarToNumString(numStringLiteral);
			double num = Double.parseDouble(numStringLiteral);
			if (num >= 0) {
				return "+";
			} else {
				return "-";
			}
		} else {
			return "";
		}
	}
	
	/**
	 * checks if a number is positive or negative and adds $ sign accordingly
	 * @param numStringLiteral
	 * @return String
	 */
	public String convertNumToDollar(String numStringLiteral) {
		if(!isBlank(numStringLiteral)) {
			double num = Double.parseDouble(numStringLiteral);
			DecimalFormat fmt = new DecimalFormat("$#,##0.00;-$#,##0.00");
			return fmt.format(num); 
		} else {
			return "$0.00";
		}
	}
	/**
	 * checks if a number is positive or negative and adds $ sign accordingly
	 * @param numStringLiteral
	 * @return String
	 */
	public String convertNumToDollar(String numStringLiteral, String format) {
		double num = 0.0;
		String dollarFormat = "$"+format+";-$"+format;
		DecimalFormat fmt = new DecimalFormat(dollarFormat);
		if(!isBlank(numStringLiteral)) {
			num = Double.parseDouble(numStringLiteral);
			return fmt.format(num); 
		} else {
			return fmt.format(num); 
		}
	}

	/**
	 * checks if a number is positive or negative and adds $ sign accordingly
	 * @param numStringLiteral
	 * @return String
	 */
	public String convertNumToPositiveDollar(String numStringLiteral) {
		if(!isBlank(numStringLiteral)) {
			numStringLiteral = numStringLiteral.replace(",", "");
			numStringLiteral = numStringLiteral.replace("$", "");
			numStringLiteral = numStringLiteral.replace("-", "");
			double num = Double.parseDouble(numStringLiteral);
			DecimalFormat fmt = new DecimalFormat("$#,##0.00");
			return fmt.format(num); 
		} else {
			return "$0.00";
		}
	}

	/**
	 * checks if a number is positive or negative and adds % sign accordingly
	 * @param numStringLiteral
	 * @return String
	 */
	public String convertNumToPercent(String numStringLiteral) {
		if(!isBlank(numStringLiteral)) {
			double num = Double.parseDouble(numStringLiteral);
			DecimalFormat fmt = new DecimalFormat("#,##0.00");
			return fmt.format(num)+"%"; 
		} else {
			return "0.00%";
		}
	}
	
	/**
	 * checks if a number is positive or negative and adds $ sign accordingly
	 * @param numStringLiteral
	 * @return String
	 */
	public String convertNumToDollar(float num) {
		DecimalFormat fmt = new DecimalFormat("$#,##0.00;-$#,##0.00");
		return fmt.format(num); 
	}
	
	/**
	 * Converts a dollar to a number
	 * @param numStringLiteral
	 * @return String
	 */
	public String convertDollarToNumString(String numStringLiteral) {
		if(!isBlank(numStringLiteral)) {
			numStringLiteral = numStringLiteral.replace(",", "");
			numStringLiteral = numStringLiteral.replace("$", "");
			numStringLiteral = moveSignUp(numStringLiteral);
			return numStringLiteral;
		} else {
			return "0";
		}
	}

	/**
	 * Helper method to move the + or - sign from the end to the start of the string
	 * @param numStringLiteral
	 * @return
	 */
	private String moveSignUp(String numStringLiteral) {
		if(numStringLiteral.endsWith("-")) {
			numStringLiteral = numStringLiteral.replace("-", "");
			numStringLiteral = "-" + numStringLiteral;
		} else if(numStringLiteral.endsWith("+")) {
			numStringLiteral = numStringLiteral.replace("+", "");
			numStringLiteral = "+" + numStringLiteral;
		}
		return numStringLiteral;
	}
	String moveSignUpTest(String numStringLiteral) {
		return moveSignUp(numStringLiteral);
	}
	
	/**
	 * Convert the dollar strings to number, convert the subtrahend to absolute value, subtract and return dollar string
	 * @param str1, str2
	 * @return String
	 */
	public String subtractAbsoluteDollarValues(String str1, String str2) {
		if(str2 != null) {
			str2 = str2.replace("-", "");
		}
		str1 = convertDollarToNumString(str1);
		str2 = convertDollarToNumString(str2);
		return subtractDollarValues(str1, str2);
	}

	public String subtractDollarValues(String str1, String str2) {
		BigDecimal bd1 = new BigDecimal(str1);
		BigDecimal bd2 = new BigDecimal(str2);
		String diff = String.valueOf(bd1.subtract(bd2));
		return convertNumToDollar(diff);
	}

	/**
	 * Convert the strings to number and add
	 * @param str1, str2
	 * @return String
	 */
	public String addDollarValues(String str1, String str2) {
		str1 = convertDollarToNumString(str1);
		str2 = convertDollarToNumString(str2);
		String add = String.valueOf(Double.parseDouble(str1) + Double.parseDouble(str2));
		return convertNumToDollar(add);
	}
	
	private int findNonBlankPos(List<String> lines, int pos) {
		for (; pos >= 0; pos--) {
			if (!this.isBlank(lines.get(pos))) {
				pos--;
				break;
			}
		}
		return pos;
	}
	
	private int findBlankPos(List<String> lines, int pos) {
		for (; pos >= 0; pos--) {
			if (this.isBlank(lines.get(pos))) {
				break;
			}
		}
		return pos;
	}
}
