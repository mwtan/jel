package com.github.wtan.jel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java "switch" statement.
 *
 * @author Will Tan
 */
class Switch extends Expression {
	private IExpression expr;
	private List<Case> clist = new ArrayList<>(12);

	/**
	 * Constructs a Switch object with a switch expression which value is compared to
	 * each case group. 
	 *
	 * @param expr1 IExpression The "condition" expression.
	 */
	Switch(IExpression ex1) {
		this.expr = ex1;
	}

	/**
	 * Add a CaseGroup to the list.
	 * @param c
	 */
	void addCase(Expression c) {
		clist.add((Case) c);
	}
	
	/**
	 * Evaluates each case group in the list.
	 */
	public Object eval(Map m) throws ExpressionException {
		Object swval = expr.eval(m);
		Iterator<Case> caseit = clist.iterator();
		boolean matchcase = false;
		while (caseit.hasNext()) {
			Case c = caseit.next();
			if (!matchcase) {
				matchcase = c.compare(swval, m);
			}
			Object obj = null;
			if (matchcase) {
				obj = c.eval(m);
				if (obj != null) {
					if (obj instanceof Break) {
						break;
					}
					else if (obj instanceof ReturnValue) {
						return obj;
					}
				}
			}
		}
		return null;
	}
}
