package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java "while (expression) statement".
 *
 * @author Will Tan
 */
class While extends Expression {
	private IExpression expr1;
	private IExpression expr2;

	/**
	 * Constructs a "while" statement with the condition and a statement block.
	 *
	 * @param expr1 IExpression The "condition" expression.
	 * @param expr2 IExpression The statement expression.
	 */
	While(IExpression ex1, IExpression ex2) {
		this.expr1 = ex1;
		this.expr2 = ex2;
	}

	/**
	 * Evaluates the condition of the "while" loop and process the statement.
	 */
	public Object eval(Map m) throws ExpressionException {
		while (getboolean(expr1.eval(m))) {
			Object obj = expr2.eval(m);
			if (obj != null) {
				if (obj instanceof Break) {
					break;
				}
				else if (obj instanceof ReturnValue) {
					return obj;
				}
			}
		}
		return null;
	}
}
