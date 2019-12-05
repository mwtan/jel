package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Applies a unary plus '+' to an expression.
 *
 * @author Will Tan
 */
class UnaryPlus extends UnaryArithmeticExpression {
	/**
	 * @inheritDoc
	 */
	UnaryPlus(IExpression expr) {
		super(expr);
	}

	/**
	 * Unary plus on a byte
	 * @param a byte
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalbyte(Map m, byte a) throws ExpressionException {
		return this.toObject(+a);
	}
	/**
	 * Unary plus on a char
	 * @param a char
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalchar(Map m, char a) throws ExpressionException {
		return this.toObject(+a);
	}
	/**
	 * Unary plus on a short
	 * @param a short
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalshort(Map m, short a) throws ExpressionException {
		return this.toObject(+a);
	}
	/**
	 * Unary plus on an int
	 * @param a int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(Map m, int a) throws ExpressionException {
		return this.toObject(+a);
	}
	/**
	 * Unary plus on a long
	 * @param a long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(Map m, long a) throws ExpressionException {
		return this.toObject(+a);
	}
	/**
	 * Unary plus on a float
	 * @param a float
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalfloat(Map m, float a) throws ExpressionException {
		return this.toObject(+a);
	}
	/**
	 * Unary plus on a double
	 * @param a double
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evaldouble(Map m, double a) throws ExpressionException {
		return this.toObject(+a);
	}
}
