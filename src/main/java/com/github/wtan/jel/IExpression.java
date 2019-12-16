package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * The interface for all expression classes. Defines a Java expression that
 * can be evaluated.
 *
 * @author Will Tan
 */
public interface IExpression {
	/**
	 * Evaluates the expression. The expression being evaluated can contain a
	 * number of sub-expressions which are evaluated in order of the expression
	 * hierarchy.
	 *
	 * @param m Map Contains the attributes to be evaluated.
	 * @throws ExpressionException Problem encountered while evaluating the expression.
	 * @return Object Result of the expression. Can be null. (e.g. When an 'if'
	 *         condition is not met, the return statement is not executed and the
	 *         expression returns a Boolean that's neither true or false, but a null.)
	 */
	Object eval(Map<?, ?> m) throws ExpressionException;
}
