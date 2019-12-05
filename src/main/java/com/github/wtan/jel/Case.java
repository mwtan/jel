package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java "case" in a "switch" statement.
 *
 * @author Will Tan
 */
class Case extends Expression {
	private IExpression expr1;
	private StatementList expr2;

	/**
	 * Constructs a case group for a switch statement.
	 *
	 * @param expr1 IExpression The "condition" expression.
	 * @param expr2 IExpression The "then" expression.
	 */
	Case(IExpression ex1, IExpression ex2) {
		this.expr1 = ex1;
		this.expr2 = (StatementList) ex2;
	}

	/**
	 * Constructs a "default" group for a switch statement.
	 *
	 * @param ex1
	 */
	Case(IExpression ex2) {
		this.expr1 = null;
		this.expr2 = (StatementList) ex2;
	}

	/**
	 * Evaluates the case statement list if available. There is no statement list
	 * for empty case statements.
	 */
	public Object eval(Map m) throws ExpressionException {
		if (expr2 != null && expr2.size() > 0) {
			return expr2.eval(m);
		}
		return null;
	}

	/**
	 * Evaluates the case expression and compares it to the switch expression.
	 * The default case always returns true.
	 * @param o
	 * @param m
	 * @return
	 * @throws ExpressionException
	 */
	public boolean compare(Object o, Map m) throws ExpressionException {
		if (expr1 != null) {
			Object caseval = expr1.eval(m);
			return caseval.equals(o);
		}
		return true;
	}
}
