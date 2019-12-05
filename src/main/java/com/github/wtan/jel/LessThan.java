package com.github.wtan.jel;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the < operator.
 *
 * @author Will Tan
 */
class LessThan extends LogicalExpression {
	/**
	 * @inheritDoc
	 */
	LessThan(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * byte less-than
	 * @param a byte
	 * @param b byte
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalbyte(byte a, byte b) throws ExpressionException {
		return a < b;
	}
	/**
	 * char less-than
	 * @param a char
	 * @param b char
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalchar(char a, char b) throws ExpressionException {
		return a < b;
	}
	/**
	 * short less-than
	 * @param a short
	 * @param b short
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalshort(short a, short b) throws ExpressionException {
		return a < b;
	}
	/**
	 * int less-than
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalint(int a, int b) throws ExpressionException {
		return a < b;
	}
	/**
	 * long less-than
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evallong(long a, long b) throws ExpressionException {
		return a < b;
	}
	/**
	 * float less-than
	 * @param a float
	 * @param b float
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalfloat(float a, float b) throws ExpressionException {
		return a < b;
	}
	/**
	 * double less-than
	 * @param a double
	 * @param b double
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evaldouble(double a, double b) throws ExpressionException {
		return a < b;
	}
	/**
	 * object less-than
	 * @param a Object
	 * @param b Object
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalobject(Object a, Object b) throws ExpressionException {
		throw new ExpressionException("Can't apply < operator on java.lang.Object.");
	}
}
