package com.github.wtan.jel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.reflect.ConstructorUtils;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * Represents a Java 'new' keyword. Creates a new object instance.
 *
 * @author Will Tan
 */
class New extends MultiExpression {
	private List<?> elist;

	/**
	 * Constructs a New object containing an expression and a parameter list.
	 */
	New(IExpression expr, List<?> exprlist) {
		super(expr);
		this.elist = exprlist;
	}

	@Override
	public Object eval(Map<?, ?> m) throws ExpressionException {
		String classname = null;
		if (expr1 instanceof Dot) {
			Object obj = ((Dot)expr1).getPackageClassName(m);
			if (obj instanceof IdentValue) {
				classname = ((IdentValue) obj).eval();
			}
		}
		else {
			if (expr1 instanceof IdentValue) {
				classname = ((IdentValue) expr1).eval();
			}
			else {
				throw new ExpressionException("Cannot obtain package/classname.");
			}
		}
		// Create class
		Class<?> newclass = null;
		try {
			newclass = Dot.loadClass(classname);
			if (newclass == null) {
				throw new ClassNotFoundException();
			}
		}
		catch (ClassNotFoundException e) {
			throw new ExpressionException("Class not found: " + classname, e);
		}
		// Create parameter type and argument array
		Class<?>[] parmtypes = new Class[elist.size()];
		Object[] args = new Object[elist.size()];
		int index = 0;
		Iterator<?> it = elist.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			Object val = ((Expression) obj).eval(m);
			Class valclass = val.getClass();
			if (ClassUtils.wrapperToPrimitive(valclass) != null) {
				args[index] = val;
				if (val instanceof Boolean) {
					parmtypes[index] = Boolean.TYPE;
				}
				else if (val instanceof Byte) {
					parmtypes[index] = Byte.TYPE;
				}
				else if (val instanceof Character) {
					parmtypes[index] = Character.TYPE;
				}
				else if (val instanceof Short) {
					parmtypes[index] = Short.TYPE;
				}
				else if (val instanceof Integer) {
					parmtypes[index] = Integer.TYPE;
				}
				else if (val instanceof Long) {
					parmtypes[index] = Long.TYPE;
				}
				else if (val instanceof Float) {
					parmtypes[index] = Float.TYPE;
				}
				else if (val instanceof Double) {
					parmtypes[index] = Double.TYPE;
				}
			}
			else {
				parmtypes[index] = valclass;
				args[index] = val;
			}
			index++;
		}
		// Get constructor
		Constructor<?> cons = null;
		try {
			cons = ConstructorUtils.getMatchingAccessibleConstructor(newclass, parmtypes);
			if (cons == null) {
				String parmlist = null;
				for (Class c: parmtypes) {
					String parmclassname = c.getSimpleName();
					if (parmlist == null) {
						parmlist = parmclassname; 
					}
					else {
						StringBuilder plist = new StringBuilder(parmlist);
						plist.append(", ");
						plist.append(parmclassname);
						parmlist = plist.toString();
					}
			    }
				throw new NoSuchMethodException("Constructor " + classname + "(" + parmlist + ") not found.");
			}
		}
		catch (NoSuchMethodException | SecurityException e) {
			throw new ExpressionException("Constructor not found.", e);
		}
		// Create new instance
		try {
			return cons.newInstance(args);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ExpressionException("Could not instantiate object.", e);
		}
	}
}
