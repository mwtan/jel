package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates the logical OR (||) of two expressions.
 *
 * @author Will Tan
 */
class Or extends MultiExpression {
	/**
	 * @inheritDoc
	 */
	Or(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * Evaluates the logical OR of expression 1 and expression 2.
	 */
	public Object eval(Map m) throws ExpressionException {
		return toObject(getboolean(expr1.eval(m)) || getboolean(expr2.eval(m)));
	}
}
