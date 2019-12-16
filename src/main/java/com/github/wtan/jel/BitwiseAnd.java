package com.github.wtan.jel;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the bitwise AND '&' operator.
 *
 * @author Will Tan
 */
class BitwiseAnd extends BinaryBitwiseExpression {
	/**
	 * @inheritDoc
	 */
	BitwiseAnd(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * int bitwise AND
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(int a, int b) throws ExpressionException {
		return this.toObject(a & b);
	}

	/**
	 * long bitwise AND
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(long a, long b) throws ExpressionException {
		return this.toObject(a & b);
	}
}
