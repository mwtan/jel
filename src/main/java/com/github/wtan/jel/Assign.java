package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Assigns the result of expression 2 to the identifier in named in expression 1.
 *
 * @author Will Tan
 */
class Assign extends MultiExpression {
	/**
	 * @inheritDoc
	 */
	Assign(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * Assigns the result of expression 2 to the identifier expr1.
	 */
	public Object eval(Map m) throws ExpressionException {
		if (expr1 instanceof IdentValue) {  // Ensure expr1 is an identifier
			Object rvalue = expr2.eval(m);  // Evaluate the right expression
			String identName = ((IdentValue) expr1).eval();
			m.put(identName, rvalue);       // Put in the Map with identifier name of expr1
			return rvalue;
		}
		else {
			throw new ExpressionException("lvalue not an IdentValue object.");
		}
	}
}
