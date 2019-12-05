package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java break statement.
 *
 * @author Will Tan
 */
class Break extends Expression {
	/**
	 * Constructs a "break" statement.
	 */
	Break() {
	}

	/**
	 * Nothing to evaluate for a break.
	 */
	public Object eval(Map m) throws ExpressionException {
		return this;
	}
}
