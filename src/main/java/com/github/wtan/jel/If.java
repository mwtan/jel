package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java "if (expression) statement" where the statement is evaluated
 * if the boolean expression is true, otherwise the "else" expression is
 * evaluated (if one exists).
 *
 * @author Will Tan
 */
class If extends Expression {
	private IExpression expr1;
	private IExpression expr2;
	private IExpression expr3;

	/**
	 * Constructs an "if" statement with the condition, then and else
	 * components.
	 *
	 * @param expr1 IExpression The "condition" expression.
	 * @param expr2 IExpression The "then" expression.
	 * @param expr3 IExpression The "else" expression.
	 */
	If(IExpression ex1, IExpression ex2, IExpression ex3) {
		this.expr1 = ex1;
		this.expr2 = ex2;
		this.expr3 = ex3;
	}

	/**
	 * Evaluates the if-condition and returns the result of the if-statement.
	 */
	public Object eval(Map m) throws ExpressionException {
		Object ret = null;  // Returns null if condition not met
		if (getboolean(expr1.eval(m))) {
			ret = expr2.eval(m);
		}
		else {
			if (expr3 != null) {
				ret = expr3.eval(m);
			}
		}
		return ret;
	}
}
