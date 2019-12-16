package com.github.wtan.jel;

/**
 * Base class for binary and unary expressions.
 *
 * @author Will Tan
 */
abstract class MultiExpression extends Expression {
	/**
	 * Expression one. This is the expression for unary operators.
	 */
	protected IExpression expr1;
	/**
	 * Expression two.
	 */
	protected IExpression expr2;

	/**
	 * Construct a unary expression.
	 *
	 * @param expr The expression.
	 */
	MultiExpression(IExpression expr) {
		this.expr1 = expr;
	}

	/**
	 * Construct a binary expression.
	 *
	 * @param expr1 Expression one.
	 * @param expr2 Expression two.
	 */
	MultiExpression(IExpression ex1, IExpression ex2) {
		this.expr1 = ex1;
		this.expr2 = ex2;
	}
}
