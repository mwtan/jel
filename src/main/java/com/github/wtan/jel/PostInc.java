package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Increments the result of an expression using the '++' operator.
 *
 * @author Will Tan
 */
class PostInc extends UnaryArithmeticExpression {
	/**
	 * @inheritDoc
	 */
	PostInc(IExpression expr) {
		super(expr);
	}

	/**
	 * Increment a byte
	 * @param a byte
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalbyte(Map m, byte a) throws ExpressionException {
		if (expr instanceof IdentValue) {
			byte b = a;
			Byte newa = ++b;
			m.put(((IdentValue) expr).eval(), newa);
		}
		return this.toObject(a);
	}
	/**
	 * Increment a char
	 * @param a char
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalchar(Map m, char a) throws ExpressionException {
		if (expr instanceof IdentValue) {
			char b = a;
			Character newa = ++b;
			m.put(((IdentValue) expr).eval(), newa);
		}
		return this.toObject(a);
	}
	/**
	 * Increment a short
	 * @param a short
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalshort(Map m, short a) throws ExpressionException {
		if (expr instanceof IdentValue) {
			short b = a;
			Short newa = ++b;
			m.put(((IdentValue) expr).eval(), newa);
		}
		return this.toObject(a);
	}
	/**
	 * Increment an int
	 * @param a int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(Map m, int a) throws ExpressionException {
		if (expr instanceof IdentValue) {
			int b = a;
			Integer newa = ++b;
			m.put(((IdentValue) expr).eval(), newa);
		}
		return this.toObject(a);
	}
	/**
	 * Increment a long
	 * @param a long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(Map m, long a) throws ExpressionException {
		if (expr instanceof IdentValue) {
			long b = a;
			Long newa = ++b;
			m.put(((IdentValue) expr).eval(), newa);
		}
		return this.toObject(a);
	}
	/**
	 * Increment a float
	 * @param a float
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalfloat(Map m, float a) throws ExpressionException {
		if (expr instanceof IdentValue) {
			float b = a;
			Float newa = ++b;
			m.put(((IdentValue) expr).eval(), newa);
		}
		return this.toObject(a);
	}
	/**
	 * Increment a double
	 * @param a double
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evaldouble(Map m, double a) throws ExpressionException {
		if (expr instanceof IdentValue) {
			double b = a;
			Double newa = ++b;
			m.put(((IdentValue) expr).eval(), newa);
		}
		return this.toObject(a);
	}
}
