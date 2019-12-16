package com.github.wtan.jel;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the bitwise &= operator.
 * If the lvalue is undefined, a zero value is assumed.
 *
 * @author Will Tan
 */
class BitwiseAndAssign extends BinaryBitwiseAssignExpression {
	/**
	 * @inheritDoc
	 */
	BitwiseAndAssign(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * int minus-assignment
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(int a, int b) throws ExpressionException {
		return this.toObject(a & b);
	}
	
	/**
	 * long minus-assignment
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(long a, long b) throws ExpressionException {
		return this.toObject(a & b);
	}
}
