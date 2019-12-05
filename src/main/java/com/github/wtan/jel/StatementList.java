package com.github.wtan.jel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * A StatementList object contains a list of statements. It is evaluated by
 * evaluating each statement in the list in sequence. The evaluation is
 * stopped when a ReturnValue is returned by an expression.
 *
 * @author Will Tan
 */
class StatementList extends Expression {
	private List slist;

	/**
	 * Constructs an empty StatementList object.
	 *
	 * @param sl List The list of statement.
	 */
	StatementList() {
		this.slist = new ArrayList();
	}

	/**
	 * Evaluates the statement list by evaluating each statement in the list. It
	 * stops evaluating then a ReturnValue object is returned. The evaluation is
	 * stopped when a ReturnValue is returned from an expression.
	 *
	 * @param m Map The map being evaluated.
	 * @throws ExpressionException Evaluation error.
	 * @return Object The result of the last statement evaluated.
	 */
	public Object eval(Map m)
		throws ExpressionException {
		Expression expr = null;
		Object ret = null;
		Iterator it = slist.iterator();
		while (it.hasNext()) {
			expr = (Expression) it.next();
			ret = expr.eval(m);
			if (ret instanceof ReturnValue || ret instanceof Continue || ret instanceof Break) {
				break; // Stop when a Return is encountered
			}
		}
		return ret;
	}

	/**
	 * Adds a statement to the StatementList.
	 *
	 * @param expr Expression
	 */
	public void add(Expression expr) {
		this.slist.add(expr);
	}
	
	/**
	 * Return the size of the statement list.
	 * @return
	 */
	public int size() {
		return slist.size();
	}
}
