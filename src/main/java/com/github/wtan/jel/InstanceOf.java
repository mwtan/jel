package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Evaluates whether an object is an instanceof a class.
 *
 * @author Will Tan
 */
class InstanceOf extends MultiExpression {
	/**
	 * @inheritDoc
	 */
	InstanceOf(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * Evaluates whether expression 1 instanceof expression 2.
	 */
	public Object eval(Map m) throws ExpressionException {
		Object object = expr1.eval(m);
		// Get class name
		String classname = null;
		if (expr2 instanceof Dot) {
			Object obj = ((Dot)expr2).getPackageClassName(m);
			if (obj instanceof IdentValue) {
				classname = ((IdentValue) obj).eval();
			}
		}
		else {
			if (expr2 instanceof IdentValue) {
				classname = ((IdentValue) expr2).eval();
			}
			else {
				throw new ExpressionException("Cannot obtain package/classname.");
			}
		}
		// Create class
		Class<?> theclass = null;
		try {
			theclass = Dot.loadClass(classname);
			if (theclass == null) {
				throw new ClassNotFoundException();
			}
			// See if object if of the type theclass.
			return toObject(theclass.isInstance(object));
		}
		catch (ClassNotFoundException e) {
			throw new ExpressionException("Class not found: " + classname, e);
		}
	}
}
