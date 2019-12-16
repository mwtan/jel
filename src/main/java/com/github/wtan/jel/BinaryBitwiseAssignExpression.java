package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Base class for binary bitwise assignment expressions.
 *
 * @author Will Tan
 */
abstract class BinaryBitwiseAssignExpression extends BinaryBitwiseExpression {
	/**
	 * Construct an expression consisting of an arithmetic assignment operation on
	 * two separate expressions.
	 *
	 * @param expr1 IExpression Expression one.
	 * @param expr2 IExpression Expression two.
	 */
	BinaryBitwiseAssignExpression(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Object eval(Map m) throws ExpressionException {
		if (expr1 instanceof IdentValue) {  // Ensure expr1 is an identifier
			String identName = ((IdentValue) expr1).eval();
			Object lvalue = expr1.eval(m);
			Object rvalue = expr2.eval(m);  // Evaluate the right expression
			if (lvalue == null) {           // Identifier not defined, use zero value
				Object obj = super.evalBinary(Integer.valueOf(0), rvalue);
				m.put(identName, obj);      // Put in the Map with identifies name of expr1
				return rvalue;
			}
			else {
				Object obj = super.evalBinary(lvalue, rvalue);
				m.put(identName, obj);      // Put in the Map with identifier name of expr1
				return rvalue;
			}
		}
		return null;
	}
}
