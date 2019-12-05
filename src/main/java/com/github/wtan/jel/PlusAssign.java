package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates two expressions using the += operator.
 * If the lvalue is undefined, a zero value is assumed.
 *
 * @author Will Tan
 */
class PlusAssign extends BinaryArithmeticAssignExpression {
	/**
	 * @inheritDoc
	 */
	PlusAssign(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * @inheritDoc
	 */
	public Object eval(Map m) throws ExpressionException {
		if (expr1 instanceof IdentValue) {  // Ensure expr1 is an identifier
			String identName = ((IdentValue) expr1).eval();
			Object lvalue = expr1.eval(m);
			Object rvalue = expr2.eval(m);  // Evaluate the right expression
			if (lvalue == null) {           // Identifier not defined, use zero value
				if (rvalue instanceof java.lang.String) {
					m.put(identName, rvalue);
					return rvalue;
				}
				else {
					Object obj = super.evalBinary(0, rvalue);
					m.put(identName, obj); // Put in the Map with identifies name of expr1
					return rvalue;
				}
			}
			else {
				if (lvalue instanceof java.lang.String || rvalue instanceof java.lang.String) {
					String s = lvalue.toString() + rvalue.toString();
					m.put(identName, s);
					return s;
				}
				else {
					Object obj = super.evalBinary(lvalue, rvalue);
					m.put(identName, obj); // Put in the Map with identifier name of expr1
					return rvalue;
				}
			}
		}
		return null;
	}

	/**
	 * int plus-assignment
	 * @param a int
	 * @param b int
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalint(int a, int b) throws ExpressionException {
		return this.toObject(a + b);
	}
	/**
	 * long plus-assignment
	 * @param a long
	 * @param b long
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evallong(long a, long b) throws ExpressionException {
		return this.toObject(a + b);
	}
	/**
	 * float plus-assignment
	 * @param a float
	 * @param b float
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evalfloat(float a, float b) throws ExpressionException {
		return this.toObject(a + b);
	}
	/**
	 * double plus-assignment
	 * @param a double
	 * @param b double
	 * @throws ExpressionException
	 * @return Object
	 */
	public Object evaldouble(double a, double b) throws ExpressionException {
		return this.toObject(a + b);
	}
}
