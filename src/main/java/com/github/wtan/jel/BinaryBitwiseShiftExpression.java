package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Base class for binary bitwise shift expressions.
 *
 * @author Will Tan
 */
abstract class BinaryBitwiseShiftExpression extends MultiExpression {
	/**
	 * Construct an expression consisting of an arithmetic operation on two
	 * separate expressions.
	 *
	 * @param expr1 IExpression Expression one.
	 * @param expr2 IExpression Expression two.
	 */
	BinaryBitwiseShiftExpression(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * @inheritDoc
	 */
	public Object eval(Map<?, ?> m) throws ExpressionException {
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
	 * @param obj2 Object Object 2. The shift multiple is an integer.
	 * @throws ExpressionException Neither object is of the primitive wrapper type.
	 * @return Object A Primitive wrapper.
	 */
	protected final Object evalBinary(Object obj1, Object obj2)
		throws ExpressionException {
		if (!(obj2 instanceof Integer)) {
			throw new ExpressionException("Invalid multiple for bitshift operation: " + 
					((obj2 != null) ? obj2.getClass() : null));
		}
		if (obj1 instanceof Long) {
			return evallong(getlong(obj1), getint(obj2));
		}
		if (obj1 instanceof Integer ||
			obj1 instanceof Short ||
			obj1 instanceof Character ||
			obj1 instanceof Byte) {
			return evalint(getint(obj1), getint(obj2));
		}
		throw new ExpressionException("Invalid type for bitwise operation: " + 
				((obj1 != null) ? obj1.getClass() : null));
	}

	/**
	 * The sub-classes' implementation to evaluate two longs.
	 * @param obj1 long
	 * @param obj2 long
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evallong(long obj1, int obj2)
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
