package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Base class for binary arithmetic expressions.
 *
 * @author Will Tan
 */
abstract class BinaryArithmeticExpression extends MultiExpression {
	/**
	 * Construct an expression consisting of an arithmetic operation on two
	 * separate expressions.
	 *
	 * @param expr1 IExpression Expression one.
	 * @param expr2 IExpression Expression two.
	 */
	BinaryArithmeticExpression(IExpression expr1, IExpression expr2) {
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
	 * Template method for evaluating two objects. It is called by sub-classes
	 * to convert both objects to the largest type. The implementation that
	 * does the evaluation is in the sub-class.
	 *
	 * @param obj1 Object Object 1.
	 * @param obj2 Object Object 2
	 * @throws ExpressionException Neither object is of the primitive wrapper type.
	 * @return Object A Primitive wrapper.
	 */
	protected final Object evalBinary(Object obj1, Object obj2)
		throws ExpressionException {
		if (obj1 instanceof Double || obj2 instanceof Double) {
			if (obj1 == null) { double var = 0; obj1 = Double.valueOf(var);}
			if (obj2 == null) { double var = 0; obj2 = Double.valueOf(var);}
			return evaldouble(getdouble(obj1), getdouble(obj2));
		}
		if (obj1 instanceof Float || obj2 instanceof Float) {
			if (obj1 == null) { float var = 0; obj1 = Float.valueOf(var);}
			if (obj2 == null) { float var = 0; obj2 = Float.valueOf(var);}
			return evalfloat(getfloat(obj1), getfloat(obj2));
		}
		if (obj1 instanceof Long || obj2 instanceof Long) {
			if (obj1 == null) { long var = 0; obj1 = Long.valueOf(var);}
			if (obj2 == null) { long var = 0; obj2 = Long.valueOf(var);}
			return evallong(getlong(obj1), getlong(obj2));
		}
		if (obj1 instanceof Integer || obj2 instanceof Integer) {
			if (obj1 == null) { int var = 0; obj1 = Integer.valueOf(var);}
			if (obj2 == null) { int var = 0; obj2 = Integer.valueOf(var);}
			return evalint(getint(obj1), getint(obj2));
		}
		if (obj1 instanceof Short || obj2 instanceof Short) {
			if (obj1 == null) { short var = 0; obj1 = Short.valueOf(var);}
			if (obj2 == null) { short var = 0; obj2 = Short.valueOf(var);}
			return evalint(getint(obj1), getint(obj2));
		}
		if (obj1 instanceof Character || obj2 instanceof Character) {
			if (obj1 == null) { char var = 0; obj1 = Character.valueOf(var);}
			if (obj2 == null) { char var = 0; obj2 = Character.valueOf(var);}
			return evalint(getint(obj1), getint(obj2));
		}
		if (obj1 instanceof Byte || obj2 instanceof Byte) {
			if (obj1 == null) { byte var = 0; obj1 = Byte.valueOf(var);}
			if (obj2 == null) { byte var = 0; obj2 = Byte.valueOf(var);}
			return evalint(getint(obj1), getint(obj2));
		}
		if (obj1 instanceof Boolean || obj2 instanceof Boolean) {
			throw new ExpressionException("Cannot evaluate boolean in arithmetic expression.");
		}
		throw new ExpressionException("Unknown or invalid type.");
	}

	/**
	 * The sub-classes' implementation to evaluate two doubles.
	 * @param obj1 double
	 * @param obj2 double
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evaldouble(double obj1, double obj2)
		throws ExpressionException;

	/**
	 * The sub-classes' implementation to evaluate two floats.
	 * @param obj1 float
	 * @param obj2 float
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evalfloat(float obj1, float obj2)
		throws ExpressionException;

	/**
	 * The sub-classes' implementation to evaluate two longs.
	 * @param obj1 long
	 * @param obj2 long
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evallong(long obj1, long obj2)
		throws ExpressionException;

	/**
	 * The sub-classes' implementation to evaluate two ints.
	 * @param obj1 int
	 * @param obj2 int
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evalint(int obj1, int obj2)
		throws ExpressionException;
}
