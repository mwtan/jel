package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Base class for unary arithmetic expressions.
 *
 * @author Will Tan
 */
abstract class UnaryArithmeticExpression extends Expression {
	/**
	 * The expression for the unary operator.
	 */
	protected IExpression expr;

	/**
	 * Construct a unary arithmetic operation on an expression.
	 *
	 * @param expr IExpression Expression one.
	 */
	UnaryArithmeticExpression(IExpression ex) {
		this.expr = ex;
	}

	/**
	 * @inheritDoc
	 */
	public Object eval(Map<?, ?> m) throws ExpressionException {
		Object obj = expr.eval(m);
		return evalUnary(m, obj);
	}

	/**
	 * Template method for evaluating an object.
	 *
	 * @param obj Object
	 * @throws ExpressionException
	 * @return Object
	 */
	protected final Object evalUnary(Map<?, ?> m, Object obj)
		throws ExpressionException {
		if (obj instanceof Double) {
			return evaldouble(m, getdouble(obj));
		}
		if (obj instanceof Float) {
			return evalfloat(m, getfloat(obj));
		}
		if (obj instanceof Long) {
			return evallong(m, getlong(obj));
		}
		if (obj instanceof Integer) {
			return evalint(m, getint(obj));
		}
		if (obj instanceof Short) {
			return evalshort(m, getshort(obj));
		}
		if (obj instanceof Character) {
			return evalchar(m, getchar(obj));
		}
		if (obj instanceof Byte) {
			return evalbyte(m, getbyte(obj));
		}
		throw new ExpressionException("Unknown type: " + ((obj != null) ? obj.getClass() : null));
	}

	/**
	 * Unary operation on a byte.
	 * @param obj byte
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evalbyte(Map<?, ?> m, byte obj)
		throws ExpressionException;
	/**
	 * Unary operation on a char.
	 * @param obj char
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evalchar(Map<?, ?> m, char obj)
		throws ExpressionException;
	/**
	 * Unary operation on a short.
	 * @param obj short
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evalshort(Map<?, ?> m, short obj)
		throws ExpressionException;
	/**
	 * Unary operation on an int.
	 * @param obj int
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evalint(Map<?, ?> m, int obj)
		throws ExpressionException;
	/**
	 * Unary operation on a long.
	 * @param obj long
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evallong(Map<?, ?> m, long obj)
		throws ExpressionException;
	/**
	 * Unary operation on a float.
	 * @param obj float
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evalfloat(Map<?, ?> m, float obj)
		throws ExpressionException;
	/**
	 * Unary operation on a double.
	 * @param obj double
	 * @throws ExpressionException
	 * @return Object
	 */
	public abstract Object evaldouble(Map<?, ?> m, double obj)
		throws ExpressionException;
}
