package com.github.wtan.jel;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the bitwise unsigned right shift assign '>>>=' operator.
 *
 * @author Will Tan
 */
class BitwiseUnsignedRightShiftAssign extends BinaryBitwiseShiftAssignExpression {
	/**
	 * @inheritDoc
	 */
	BitwiseUnsignedRightShiftAssign(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * int bitwise unsigned right shift assign
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(int a, int b) throws ExpressionException {
		return this.toObject(a >>> b);
	}

	/**
	 * long bitwise unsigned right shift assign
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(long a, int b) throws ExpressionException {
		return this.toObject(a >>> b);
	}
}
