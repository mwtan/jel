package com.github.wtan.jel;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the <= operator.
 *
 * @author Will Tan
 */
class LessThanEqual extends LogicalExpression {
	/**
	 * @inheritDoc
	 */
	LessThanEqual(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * byte less-than-equal
	 * @param a byte
	 * @param b byte
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalbyte(byte a, byte b) throws ExpressionException {
		return a <= b;
	}
	/**
	 * char less-than-equal
	 * @param a char
	 * @param b char
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalchar(char a, char b) throws ExpressionException {
		return a <= b;
	}
	/**
	 * short less-than-equal
	 * @param a short
	 * @param b short
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalshort(short a, short b) throws ExpressionException {
		return a <= b;
	}
	/**
	 * int less-than-equal
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalint(int a, int b) throws ExpressionException {
		return a <= b;
	}
	/**
	 * long less-than-equal
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evallong(long a, long b) throws ExpressionException {
		return a <= b;
	}
	/**
	 * float less-than-equal
	 * @param a float
	 * @param b float
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalfloat(float a, float b) throws ExpressionException {
		return a <= b;
	}
	/**
	 * double less-than-equal
	 * @param a double
	 * @param b double
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evaldouble(double a, double b) throws ExpressionException {
		return a <= b;
	}
	/**
	 * object less-than-equal
	 * @param a Object
	 * @param b Object
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalobject(Object a, Object b) throws ExpressionException {
		throw new ExpressionException("Can't apply <= operator on java.lang.Object.");
	}
}
