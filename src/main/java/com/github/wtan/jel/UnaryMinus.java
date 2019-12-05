package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Applies a unary minus '-' to an expression.
 *
 * @author Will Tan
 */
class UnaryMinus extends UnaryArithmeticExpression {
	/**
	 * @inheritDoc
	 */
	UnaryMinus(IExpression expr) {
		super(expr);
	}

	/**
	 * Unary minus on a byte
	 * @param a byte
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalbyte(Map m, byte a) throws ExpressionException {
		return this.toObject(-a);
	}
	/**
	 * Unary minus on a char
	 * @param a char
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalchar(Map m, char a) throws ExpressionException {
		return this.toObject(-a);
	}
	/**
	 * Unary minus on a short
	 * @param a short
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalshort(Map m, short a) throws ExpressionException {
		return this.toObject(-a);
	}
	/**
	 * Unary minus on an int
	 * @param a int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(Map m, int a) throws ExpressionException {
		return this.toObject(-a);
	}
	/**
	 * Unary minus on a long
	 * @param a long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(Map m, long a) throws ExpressionException {
		return this.toObject(-a);
	}
	/**
	 * Unary minus on a float
	 * @param a float
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalfloat(Map m, float a) throws ExpressionException {
		return this.toObject(-a);
	}
	/**
	 * Unary minus on a double
	 * @param a double
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evaldouble(Map m, double a) throws ExpressionException {
		return this.toObject(-a);
	}
}
