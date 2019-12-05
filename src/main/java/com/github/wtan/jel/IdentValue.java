package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Constructs an object representing an identifier, i.e. object name, method
 * name, etc.
 *
 * @author Will Tan
 */
class IdentValue extends Expression {
	private String x;

	/**
	 * Constructs an identifier given its name.
	 *
	 * @param s String The identifier name.
	 */
	IdentValue(String s) {
		this.x = s;
	}

	/**
	 * Returns the identifier value, i.e. the map value.
	 *
	 * @param m Map The map being evaluated.
	 * @throws ExpressionException Evaluation error.
	 * @return Object A value object from the map or the identifier name.
	 */
	public Object eval(Map m) throws ExpressionException {
		return m.get(x);
	}

	/**
	 * Returns the identifier name.
	 *
	 * @return String The identifier name.
	 */
	public String eval() {
		return x;
	}
}
