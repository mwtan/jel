package com.github.wtan.jel;

import java.util.List;
import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Performs a Java method call.
 *
 * @author Will Tan
 */
class MethodCall extends MultiExpression {
	private List elist;

	/**
	 * Constructs a MethodCall object containing an expression (a Dot object)
	 * and a parameter list.
	 */
	MethodCall(IExpression expr, List exprlist) {
		super(expr);
		this.elist = exprlist;
	}

	/**
	 * Invokes the method call.
	 */
	public Object eval(Map m) throws ExpressionException {
		if (expr1 instanceof Dot) {
			return ((Dot)expr1).eval(m, elist);
		}
		throw new ExpressionException("Cannot evaluate method call. (Not a Dot object)");
	}
}
