package com.github.wtan.jel;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the -= operator.
 * If the lvalue is undefined, a zero value is assumed.
 *
 * @author Will Tan
 */
class MinusAssign extends BinaryArithmeticAssignExpression {
	/**
	 * @inheritDoc
	 */
	MinusAssign(IExpression expr1, IExpression expr2) {
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
		return this.toObject(a - b);
	}
	/**
	 * long minus-assignment
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(long a, long b) throws ExpressionException {
		return this.toObject(a - b);
	}
	/**
	 * float minus-assignment
	 * @param a float
	 * @param b float
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalfloat(float a, float b) throws ExpressionException {
		return this.toObject(a - b);
	}
	/**
	 * double minus-assignment
	 * @param a double
	 * @param b double
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evaldouble(double a, double b) throws ExpressionException {
		return this.toObject(a - b);
	}
}
