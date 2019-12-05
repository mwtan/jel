package com.github.wtan.jel;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Holds a constant value as a primitive wrapper object.
 *
 * @author Will Tan
 */
class ConstantValue extends Expression {
	private Object x;

	/**
	 * Constructor
	 * @param o Object
	 */
	ConstantValue(Object o) {
		if (o instanceof String) {
			this.x = StringEscapeUtils.unescapeJava((String) o);
		}
		else {
			this.x = o;
		}
	}

	/**
	 * Simply returns the wrapper object.
	 * @inheritDoc
	 */
	public Object eval(Map m) throws ExpressionException {
		return x;
	}
	
	/**
	 * Drop the last character from numeric literals for long, float and double types. 
	 * @param str
	 * @return
	 */
	public static String dropSuffix(String str) {
		int strlen = str.length();
		if (strlen > 0) {
			Character last = str.toUpperCase().charAt(strlen - 1);
			if (last == 'L' || last == 'F' || last == 'D') {
				str = str.substring(0, strlen - 1);
			}
		}
		return str;
	}
}
