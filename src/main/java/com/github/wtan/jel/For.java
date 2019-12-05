package com.github.wtan.jel;

import java.util.List;
import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java "for" statement.
 *
 * @author Will Tan
 */
class For extends Expression {
	private List exinit;
	private IExpression excond;
	private List exiter;
	private IExpression slist;

	/**
	 * Constructs a "for" statement with the initializer, condition, incrementor and a statement block.
	 *
	 * @param expr1 IExpression The "condition" expression.
	 * @param expr2 IExpression The statement expression.
	 */
	For(List elist1, IExpression ex1, List elist2, IExpression ex2) {
		this.exinit = elist1;
		this.excond = ex1;
		this.exiter = elist2;
		this.slist = ex2;
	}

	/**
	 * Evaluates the condition of the "for" loop and process the statement.
	 */
	public Object eval(Map m) throws ExpressionException {
		for (this.eval(m, exinit); getboolean(excond.eval(m)); this.eval(m, exiter)) {
			Object obj = slist.eval(m);
			if (obj != null) {
				if (obj instanceof Break) {
					break;
				}
				else if (obj instanceof ReturnValue) {
					return obj;
				}
			}
		}
		return null;
	}
	
	/**
	 * Evaluate each expression in the list.
	 * @param m
	 * @param elist
	 * @return
	 * @throws ExpressionException
	 */
	public Object eval(Map m, List<?> elist) throws ExpressionException {
		int size = elist.size();
		for (int i = 0; i < size; i++) {
			((IExpression) elist.get(i)).eval(m);
		}
		return null;
	}		
}
