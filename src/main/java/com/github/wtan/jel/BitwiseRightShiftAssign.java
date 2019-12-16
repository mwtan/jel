package com.github.wtan.jel;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the bitwise right shift assign '>>=' operator.
 *
 * @author Will Tan
 */
class BitwiseRightShiftAssign extends BinaryBitwiseShiftAssignExpression {
	/**
	 * @inheritDoc
	 */
	BitwiseRightShiftAssign(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * int bitwise right shift assign
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(int a, int b) throws ExpressionException {
		return this.toObject(a >> b);
	}

	/**
	 * long bitwise right shift assign
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(long a, int b) throws ExpressionException {
		return this.toObject(a >> b);
	}
}
