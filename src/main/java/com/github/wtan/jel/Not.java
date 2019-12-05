package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates the logical NOT (!) of an expression.
 *
 * @author Will Tan
 */
class Not extends MultiExpression {
	/**
	 * @inheritDoc
	 */
	Not(IExpression expr) {
		super(expr);
	}

	/**
	 * Evaluates !expression
	 */
	public Object eval(Map m) throws ExpressionException {
		return toObject(!getboolean(expr1.eval(m)));
	}
}
