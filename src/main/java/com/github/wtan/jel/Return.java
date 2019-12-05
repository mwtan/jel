package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Returns the evaluation of the expression in a ReturnValue wrapper object.
 *
 * @author Will Tan
 */
class Return extends Expression {
	private Expression b;

	/**
	 * @inheritDoc
	 */
	Return(Expression e) {
		this.b = e;
	}

	/**
	 * Evaluates the "return" expression and returns it.
	 */
	public Object eval(Map m) throws ExpressionException {
		if (b != null) {
			return new ReturnValue(b.eval(m));
		}
		return new ReturnValue(null);
	}
}
