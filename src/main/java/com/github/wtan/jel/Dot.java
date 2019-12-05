package com.github.wtan.jel;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;
import com.github.wtan.jel.util.MethodUtils;
import com.github.wtan.jel.util.StringUtils;

/**
 * Represents a Java 'dot' operator. This class is a container of an ObjectRef
 * and a field or method name.
 *
 * @author Will Tan
 */
class Dot extends MultiExpression {
	private static StringUtils sutils = new StringUtils();
	IExpression objectref = null;

	/**
	 * Constructs a Dot object.
	 *
	 * @param expr1 IExpression The objectref.
	 * @param expr2 IExpression The field or method name.
	 */
	Dot(IExpression expr1, IExpression expr2) {
		super(expr1, expr2);
	}

	/**
	 * Reflects on the ObjectRef (expression 1) and retrieves the field
	 * (named by expression 2).
	 */
	public Object eval(Map m) throws ExpressionException {
		try {
			// Get attribute name
			String name = ((IdentValue) expr2).eval();
			if (sutils.isBlank(name)) {
				throw new ExpressionException("Null or empty object field/attribute.");
			}
			// Get object (from Map) or get class name
			Object obj = expr1.eval(m);
			String classname = null;
			if (obj != null) {
				if (obj instanceof IdentValue) {
					classname = ((IdentValue) obj).eval();
				}
				else {
					return obj.getClass().getField(name).get(obj);
				}
			}
			else {
				classname = getIdentName(expr1);
				if (sutils.isBlank(classname)) {
					throw new ExpressionException("Null object/class name.");
				}
			}
			// Attempt to load class
			Class<?> clazz = loadClass(classname);
			if (clazz == null) {
				return new IdentValue(classname + "." + name);
			}
			else {
				return clazz.getField(name).get(obj);
			}
		}
		catch (Exception e) {
			throw new ExpressionException("Error retrieving object field. " + e.getMessage(), e);
		}
	}

	/**
	 * Reflects on the ObjectRef (expression 1) and invokes the method
	 * (named by expression 2).
	 */
	public Object eval(Map m, List<?> elist) throws ExpressionException {
		try {
			// Get method name
			String methodname = ((IdentValue) expr2).eval();
			if (sutils.isBlank(methodname)) {
				throw new ExpressionException("Method name not specified.");
			}
			// Build parameters
			int size = elist.size();
			Class[] parmtypes = new Class[size];
			Object[] args = new Object[size];
			for (int i=0; i<size; i++) {
				Object o = ((IExpression) elist.get(i)).eval(m);
				if (o != null) {
					parmtypes[i] = this.getObjectClass(o);
				}
				else {
					throw new ExpressionException("Null input object.");
				}
				args[i] = o;
			}
			// Retrieve object or class name
			Object obj = expr1.eval(m);
			if (obj != null) {
				if (obj instanceof IdentValue) {
					String classname = ((IdentValue) obj).eval();
					Class<?> clazz = loadDefaultClass(classname);
					Method method = MethodUtils.getMatchingAccessibleMethod(clazz, methodname, parmtypes);
					return method.invoke(null, args);
				}
				else {
					// Find and invoke the best matching method
					return MethodUtils.invokeMethod(obj, methodname, args);
				}
			}
			// Get class name
			String classname = getIdentName(expr1);
			if (classname == null) {
				throw new ExpressionException("Null object/class name.");
			}
			// Attempt to load class
			Class<?> clazz = loadClass(classname);
			if (clazz == null) {
				throw new ExpressionException("No such object/class: " + classname);
			}
			// Find method
			Method method = MethodUtils.getMatchingAccessibleMethod(clazz, methodname, parmtypes);
			if (method == null) {
				throw new ExpressionException("No such method: " + methodname);
			}
			// Invoke method
			return method.invoke(null, args);
		}
		catch (Exception e) {
			throw new ExpressionException("Error invoking object method. " + e.getMessage(), e);
		}
	}

	/**
	 * Retrieve the complete package and class name as an IdentValue object.
	 * @param m
	 * @return
	 * @throws ExpressionException
	 */
	public Object getPackageClassName(Map m) throws ExpressionException {
		StringBuilder sb = new StringBuilder(32);
		buildPackageClassName(m, sb);
		return new IdentValue(sb.toString());
	}
		
	private void buildPackageClassName(Map m, StringBuilder sb) {
		String name = ((IdentValue) expr2).eval();
		if (expr1 instanceof Dot) {
			((Dot) expr1).buildPackageClassName(m, sb);
		}
		else {
			if (expr1 instanceof IdentValue) {
				String classname = ((IdentValue) expr1).eval();
				sb.append(classname);
			}
		}
		sb.append(".");
		sb.append(name);
	}

	private String getIdentName(IExpression expr) {
		String ident = expr.toString();
		if (expr instanceof IdentValue) {
			IdentValue iv = (IdentValue) expr;
			ident = iv.eval();
		}
		return ident;
	}

	/**
	 * Load a class specified by the package and class name. The class is only loaded
	 * if the last name part (delimited by the '.' character) is capitalized, i.e. a
	 * standard class name.
	 * @param classname
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClass(String classname) throws ClassNotFoundException {
		Class<?> clazz = null;
		String lastpart = sutils.substringAfterLast(classname, ".");
		String firstchar = sutils.substring(lastpart, 0, 1);
		if ( (!firstchar.isEmpty() && Character.isUpperCase(firstchar.charAt(0))) || 
				Character.isUpperCase(classname.charAt(0))) {
			try {
				// Try loading class from default package
				clazz = loadDefaultClass(classname);
			}
			catch (ClassNotFoundException e) {
				try {
					// Try loading class from java.lang
					clazz = loadJavaLangCLass(classname);
				}
				catch (ClassNotFoundException e1) {
					clazz = loadJavaUtilCLass(classname);
				}
			}
		}
		return clazz;
	}
	
	private static Class<?> loadJavaLangCLass(String name) throws ClassNotFoundException {
		return Class.forName("java.lang." + name);
	}

	private static Class<?> loadJavaUtilCLass(String name) throws ClassNotFoundException {
		return Class.forName("java.util." + name);
	}

	private static Class<?> loadDefaultClass(String name) throws ClassNotFoundException {
		return Class.forName(name);
	}
}
