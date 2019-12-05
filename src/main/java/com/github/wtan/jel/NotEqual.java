package com.github.wtan.jel;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the != operator.
 *
 * @author Will Tan
 */
class NotEqual
	extends LogicalExpression {
	/**
	 * @inheritDoc
	 */
	NotEqual(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * byte non-euqality
	 * @param a byte
	 * @param b byte
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalbyte(byte a, byte b)
		throws ExpressionException {
		return a != b;
	}

	/**
	 * char non-euqality
	 * @param a char
	 * @param b char
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalchar(char a, char b)
		throws ExpressionException {
		return a != b;
	}

	/**
	 * short non-euqality
	 * @param a short
	 * @param b short
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalshort(short a, short b)
		throws ExpressionException {
		return a != b;
	}

	/**
	 * int non-euqality
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalint(int a, int b)
		throws ExpressionException {
		return a != b;
	}

	/**
	 * long non-euqality
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evallong(long a, long b)
		throws ExpressionException {
		return a != b;
	}

	/**
	 * float non-euqality
	 * @param a float
	 * @param b float
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalfloat(float a, float b)
		throws ExpressionException {
		return a != b;
	}

	/**
	 * double non-euqality
	 * @param a double
	 * @param b double
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evaldouble(double a, double b)
		throws ExpressionException {
		return a != b;
	}

	/**
	 * object non-euqality
	 * @param a Object
	 * @param b Object
	 * @throws ExpressionException
	 * @return boolean
	 */
	public boolean evalobject(Object a, Object b)
		throws ExpressionException {
		if (a == null && b == null) {
			return false;
		}
		if (a == null) {
			return b != null;
		}
		if (b == null) {
			return a != null;
		}
		return a != b;
	}
}
