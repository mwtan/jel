package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates the bitwise NOT (~) of an expression.
 *
 * @author Will Tan
 */
class BitwiseNot extends MultiExpression {
	/**
	 * @inheritDoc
	 */
	BitwiseNot(IExpression expr) {
		super(expr);
	}

	/**
	 * Evaluates a bitwise NOT (~) only on valid types.
	 */
	public Object eval(Map<?, ?> m) throws ExpressionException {
		Object obj = expr1.eval(m);
		if (obj instanceof Byte) {
			return toObject(~getbyte(obj));
		}
		if (obj instanceof Character) {
			return toObject(~getchar(obj));
		}
		if (obj instanceof Short) {
			return toObject(~getshort(obj));
		}
		if (obj instanceof Integer) {
			return toObject(~getint(obj));
		}
		if (obj instanceof Long) {
			return toObject(~getlong(obj));
		}
		throw new ExpressionException("Invalid type for ~ operator: " + obj.getClass());
	}
}
