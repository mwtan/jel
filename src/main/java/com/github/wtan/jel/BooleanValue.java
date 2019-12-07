package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Contains a boolean value in the form of a Boolean object.
 *
 * @author Will Tan
 */
class BooleanValue extends Expression {
	private Boolean x;

	/**
	 * Constructs an instance given the boolean primitive.
	 * @param b boolean The boolean value.
	 */
	BooleanValue(boolean b) {
		this.x = Boolean.valueOf(b); // NOPMD - Valid boolean object instantiation
	}

	/**
	 * Simply returns the Boolean object.
	 * @inheritDoc
	 */
	public Object eval(Map m) throws ExpressionException {
		return x;
	}
}
