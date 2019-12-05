package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Base class for binary expressions, i.e. contains two separate expressions.
 *
 * @author Will Tan
 */
public abstract class LogicalExpression extends MultiExpression {
	/**
	 * Construct a logical expression which consists of a logical relationship
	 * between two separate expressions.
	 *
	 * @param cond1 IExpression Expression one.
	 * @param cond2 IExpression Expression two.
	 */
	public LogicalExpression(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * @inheritDoc
	 */
	public Object eval(Map m) throws ExpressionException {
		Object obj1 = expr1.eval(m);
		Object obj2 = expr2.eval(m);
		return evalBinary(obj1, obj2);
	}

	/**
	 * Template method for evaluating two objects with a logical operator.
	 * @param obj1 Object
	 * @param obj2 Object
	 * @throws ExpressionException
	 * @return Object
	 */
	protected final Object evalBinary(Object obj1, Object obj2)
		throws ExpressionException {
		if (obj1 instanceof Double || obj2 instanceof Double) {
			boolean b = evaldouble(getdouble(obj1), getdouble(obj2));
			return this.toObject(b);
		}
		if (obj1 instanceof Float || obj2 instanceof Float) {
			boolean b = evalfloat(getfloat(obj1), getfloat(obj2));
			return this.toObject(b);
		}
		if (obj1 instanceof Long || obj2 instanceof Long) {
			boolean b = evallong(getlong(obj1), getlong(obj2));
			return this.toObject(b);
		}
		if (obj1 instanceof Integer || obj2 instanceof Integer) {
			boolean b = evalint(getint(obj1), getint(obj2));
			return this.toObject(b);
		}
		if (obj1 instanceof Short || obj2 instanceof Short) {
			boolean b = evalshort(getshort(obj1), getshort(obj2));
			return this.toObject(b);
		}
		if (obj1 instanceof Character || obj2 instanceof Character) {
			boolean b = evalchar(getchar(obj1), getchar(obj2));
			return this.toObject(b);
		}
		if (obj1 instanceof Byte || obj2 instanceof Byte) {
			boolean b = evalbyte(getbyte(obj1), getbyte(obj2));
			return this.toObject(b);
		}
		if (obj1 instanceof Boolean || obj2 instanceof Boolean) {
			throw new ExpressionException("Cannot evaluate boolean in logical expression.");
		}
		return this.toObject(evalobject(obj1, obj2));
	}

	/**
	 * Logical evaluation for two booleans.
	 * @param obj1 byte
	 * @param obj2 byte
	 * @throws ExpressionException
	 * @return boolean
	 */
	public abstract boolean evalbyte(byte obj1, byte obj2)
		throws ExpressionException;
	/**
	 * Logical evaluation for two chars.
	 * @param obj1 char
	 * @param obj2 char
	 * @throws ExpressionException
	 * @return boolean
	 */
	public abstract boolean evalchar(char obj1, char obj2)
		throws ExpressionException;
	/**
	 * Logical evaluation for two shorts.
	 * @param obj1 short
	 * @param obj2 short
	 * @throws ExpressionException
	 * @return boolean
	 */
	public abstract boolean evalshort(short obj1, short obj2)
		throws ExpressionException;
	/**
	 * Logical evaluation for two ints.
	 * @param obj1 int
	 * @param obj2 int
	 * @throws ExpressionException
	 * @return boolean
	 */
	public abstract boolean evalint(int obj1, int obj2)
		throws ExpressionException;
	/**
	 * Logical evaluation for two longs.
	 * @param obj1 long
	 * @param obj2 long
	 * @throws ExpressionException
	 * @return boolean
	 */
	public abstract boolean evallong(long obj1, long obj2)
		throws ExpressionException;
	/**
	 * Logical evaluation for two floats.
	 * @param obj1 float
	 * @param obj2 float
	 * @throws ExpressionException
	 * @return boolean
	 */
	public abstract boolean evalfloat(float obj1, float obj2)
		throws ExpressionException;
	/**
	 * Logical evaluation for two doubles.
	 * @param obj1 double
	 * @param obj2 double
	 * @throws ExpressionException
	 * @return boolean
	 */
	public abstract boolean evaldouble(double obj1, double obj2)
		throws ExpressionException;
	/**
	 * Logical evaluation for two objects.
	 * @param obj1 Object
	 * @param obj2 Object
	 * @throws ExpressionException
	 * @return boolean
	 */
	public abstract boolean evalobject(Object obj1, Object obj2)
		throws ExpressionException;
}
