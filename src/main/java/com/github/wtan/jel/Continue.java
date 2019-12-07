
package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java continue statement.
 *
 * @author Will Tan
 */
class Continue extends Expression {
	/**
	 * Constructs a "break" statement.
	 */
	Continue() {
	}

	/**
	 * Nothing to evaluate for a break.
	 */
	public Object eval(Map m) throws ExpressionException {
		return this;
	}
}
