package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Increments the result of an expression using the '++' operator.
 *
 * @author Will Tan
 */
class Inc extends UnaryArithmeticExpression {
	/**
	 * @inheritDoc
	 */
	Inc(IExpression expr) {
		super(expr);
	}

	/**
	 * Increment a byte
	 * @param a byte
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalbyte(Map m, byte a) throws ExpressionException {
		++a;
		if (expr instanceof IdentValue) {
			Byte newa = Byte.valueOf(a);
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
		++a;
		if (expr instanceof IdentValue) {
			Character newa = Character.valueOf(a);
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
		++a;
		if (expr instanceof IdentValue) {
			Short newa = Short.valueOf(a);
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
		++a;
		if (expr instanceof IdentValue) {
			Integer newa = Integer.valueOf(a);
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
		++a;
		if (expr instanceof IdentValue) {
			Long newa = Long.valueOf(a);
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
		++a;
		if (expr instanceof IdentValue) {
			Float newa = Float.valueOf(a);
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
		++a;
		if (expr instanceof IdentValue) {
			Double newa = Double.valueOf(a);
			m.put(((IdentValue) expr).eval(), newa);
		}
		return this.toObject(a);
	}
}
