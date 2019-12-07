package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java "do statement (expression) ".
 *
 * @author Will Tan
 */
class Do extends Expression {
	private IExpression expr1;
	private IExpression expr2;

	/**
	 * Constructs a "do" statement with a statement block and the condition.
	 *
	 * @param expr1 IExpression The statement expression.
	 * @param expr2 IExpression The "condition" expression.
	 */
	Do(IExpression ex1, IExpression ex2) {
		this.expr1 = ex1;
		this.expr2 = ex2;
	}

	/**
	 * Evaluates the condition of the "while" loop and process the statement.
	 */
	public Object eval(Map m) throws ExpressionException {
		do {
			Object obj = expr1.eval(m);
			if (obj != null) {
				if (obj instanceof Break) {
					break;
				}
				else if (obj instanceof ReturnValue) {
					return obj;
				}
			}
		} while (getboolean(expr2.eval(m)));
		return null;
	}
}
