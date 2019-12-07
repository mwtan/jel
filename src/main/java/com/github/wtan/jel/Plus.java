package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the arithmetic '+' operator.
 *
 * @author Will Tan
 */
class Plus extends BinaryArithmeticExpression {
	/**
	 * @inheritDoc
	 */
	Plus(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * @inheritDoc
	 */
	public Object eval(Map m) throws ExpressionException {
		Object obj1 = expr1.eval(m);
		Object obj2 = expr2.eval(m);
		if (obj1 instanceof java.lang.String || obj2 instanceof java.lang.String) {
			if (obj1 == null) obj1 = "";
			if (obj2 == null) obj2 = "";
			return obj1.toString() + obj2.toString();
		}
		return evalBinary(obj1, obj2);
	}

	/**
	 * int plus
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(int a, int b) throws ExpressionException {
		return this.toObject(a + b);
	}
	/**
	 * long plus
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(long a, long b) throws ExpressionException {
		return this.toObject(a + b);
	}
	/**
	 * float plus
	 * @param a float
	 * @param b float
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalfloat(float a, float b) throws ExpressionException {
		return this.toObject(a + b);
	}
	/**
	 * double plus
	 * @param a double
	 * @param b double
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evaldouble(double a, double b) throws ExpressionException {
		return this.toObject(a + b);
	}
}
