package com.github.wtan.jel;

import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

/**
 * The base class for all expressions. It provides useful methods for converting
 * primitives to and from and object.
 *
 * @author Will Tan
 */
public abstract class Expression // NOPMD - Cyclomatic complexity unavoidable. Needed to process each primitive type.
	implements IExpression {
	private static final String NULL_INPUT_OBJECT = "Null input object.";
	private static final long LONG_1 = 1L;
	private static final long LONG_0 = 0L;
	private static final double DOUBLE_1 = 1.0d;

	/**
	 * Default constructor.
	 */
	public Expression() {
		// Nothing to do.
	}

	/**
	 * Converts the primitive boolean to the wrapper object.
	 */
	public final Object toObject(boolean x) {
		return Boolean.valueOf(x); // NOPMD - Valid boolean object instantiation

	}

	/**
	 * Converts the primitive byte to the wrapper object.
	 */
	public final Object toObject(byte x) {
		return Byte.valueOf(x);
	}

	/**
	 * Converts the primitive char to the wrapper object.
	 */
	public final Object toObject(char x) {
		return Character.valueOf(x);
	}

	/**
	 * Converts the primitive short to the wrapper object.
	 */
	public final Object toObject(short x) {
		return Short.valueOf(x);
	}

	/**
	 * Converts the primitive int to the wrapper object.
	 */
	public final Object toObject(int x) {
		return Integer.valueOf(x);
	}

	/**
	 * Converts the primitive long to the wrapper object.
	 */
	public final Object toObject(long x) {
		return Long.valueOf(x);
	}

	/**
	 * Converts the primitive float to the wrapper object.
	 */
	public final Object toObject(float x) {
		return Float.valueOf(x);
	}

	/**
	 * Converts the primitive double to the wrapper object.
	 */
	public final Object toObject(double x) {
		return Double.valueOf(x);
	}

	/**
	 * Converts an object to an object. i.e. does nothing.
	 */
	public final Object toObject(Object x) {
		return x;
	}

	/**
	 * Get the boolean value from an object.
	 */
	public final boolean getboolean(Object x) // NOPMD - Cyclomatic complexity unavoidable. Needed to process each primitive type.
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Boolean) {
			return ( (Boolean) x).booleanValue();
		}
		else if (x instanceof String) {
			return Boolean.parseBoolean((String) x);
		}
		else if (x instanceof Byte) {
			byte b = ( (Byte) x).byteValue();
			if (b == 0) {
				return false;
			}
			if (b == 1) {
				return true;
			}
			throw new ExpressionException("Error converting Byte to boolean.");
		}
		else if (x instanceof Character) {
			char b = ( (Character) x).charValue();
			if (b == 0) {
				return false;
			}
			if (b == 1) {
				return true;
			}
			throw new ExpressionException("Error converting Character to boolean.");
		}
		else if (x instanceof Short) {
			short b = ( (Short) x).shortValue();
			if (b == 0) {
				return false;
			}
			if (b == 1) {
				return true;
			}
			throw new ExpressionException("Error converting Short to boolean.");
		}
		else if (x instanceof Integer) {
			int b = ( (Integer) x).intValue();
			if (b == 0) {
				return false;
			}
			if (b == 1) {
				return true;
			}
			throw new ExpressionException("Error converting Integer to boolean.");
		}
		else if (x instanceof Long) {
			long b = ( (Long) x).longValue();
			if (b == 0) {
				return false;
			}
			if (b == 1) {
				return true;
			}
			throw new ExpressionException("Error converting Long to boolean.");
		}
		else if (x instanceof Float) {
			float b = ( (Float) x).floatValue();
			if (b == 0) {
				return false;
			}
			if (b == 1) {
				return true;
			}
			throw new ExpressionException("Error converting Float to boolean.");
		}
		else if (x instanceof Double) {
			double b = ( (Double) x).doubleValue();
			if (b == 0) {
				return false;
			}
			if (b == 1) {
				return true;
			}
			throw new ExpressionException("Error converting Double to boolean.");
		}
		else {
			throw new ExpressionException("Error converting Object to boolean.");
		}
	}

	/**
	 * Get the byte value from an object.
	 */
	public final byte getbyte(Object x)
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Byte) {
			return ( (Byte) x).byteValue();
		}
		else if (x instanceof String) {
			return Byte.parseByte( (String) x);
		}
		if (x instanceof Boolean) {
			boolean b = ( (Boolean) x).booleanValue();
			if (b) {
				return 1;
			}
			return 0;
		}
		else if (x instanceof Character) {
			char b = ( (Character) x).charValue();
			return (byte) b;
		}
		else if (x instanceof Short) {
			short b = ( (Short) x).shortValue();
			return (byte) b;
		}
		else if (x instanceof Integer) {
			int b = ( (Integer) x).intValue();
			return (byte) b;
		}
		else if (x instanceof Long) {
			long b = ( (Long) x).longValue();
			return (byte) b;
		}
		else if (x instanceof Float) {
			float b = ( (Float) x).floatValue();
			return (byte) b;
		}
		else if (x instanceof Double) {
			double b = ( (Double) x).doubleValue();
			return (byte) b;
		}
		else {
			throw new ExpressionException("Error converting Object to byte.");
		}
	}

	/**
	 * Get the char value from an object.
	 */
	public final char getchar(Object x)
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Character) {
			return ( (Character) x).charValue();
		}
		else if (x instanceof String) {
			return ( (String) x).charAt(0);
		}
		if (x instanceof Boolean) {
			boolean b = ( (Boolean) x).booleanValue();
			if (b) {
				return 1;
			}
			return 0;
		}
		else if (x instanceof Byte) {
			byte b = ( (Byte) x).byteValue();
			return (char) b;
		}
		else if (x instanceof Short) {
			short b = ( (Short) x).shortValue();
			return (char) b;
		}
		else if (x instanceof Integer) {
			int b = ( (Integer) x).intValue();
			return (char) b;
		}
		else if (x instanceof Long) {
			long b = ( (Long) x).longValue();
			return (char) b;
		}
		else if (x instanceof Float) {
			float b = ( (Float) x).floatValue();
			return (char) b;
		}
		else if (x instanceof Double) {
			double b = ( (Double) x).doubleValue();
			return (char) b;
		}
		else {
			throw new ExpressionException("Error converting Object to char.");
		}
	}

	/**
	 * Get the short value from an object.
	 */
	public final short getshort(Object x)
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Short) {
			return ( (Short) x).shortValue();
		}
		else if (x instanceof String) {
			return Short.parseShort( (String) x);
		}
		if (x instanceof Boolean) {
			boolean b = ( (Boolean) x).booleanValue();
			if (b) {
				return 1;
			}
			return 0;
		}
		else if (x instanceof Byte) {
			return ( (Byte) x).byteValue();
		}
		else if (x instanceof Character) {
			char b = ( (Character) x).charValue();
			return (short) b;
		}
		else if (x instanceof Integer) {
			int b = ( (Integer) x).intValue();
			return (short) b;
		}
		else if (x instanceof Long) {
			long b = ( (Long) x).longValue();
			return (short) b;
		}
		else if (x instanceof Float) {
			float b = ( (Float) x).floatValue();
			return (short) b;
		}
		else if (x instanceof Double) {
			double b = ( (Double) x).doubleValue();
			return (short) b;
		}
		else {
			throw new ExpressionException("Error converting Object to short.");
		}
	}

	/**
	 * Get the int value from an object.
	 */
	public final int getint(Object x)
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Integer) {
			return ( (Integer) x).intValue();
		}
		else if (x instanceof String) {
			return Integer.parseInt( (String) x);
		}
		if (x instanceof Boolean) {
			boolean b = ( (Boolean) x).booleanValue();
			if (b) {
				return 1;
			}
			return 0;
		}
		else if (x instanceof Byte) {
			return ( (Byte) x).intValue();
		}
		else if (x instanceof Character) {
			return ( (Character) x).charValue();
		}
		else if (x instanceof Short) {
			return ( (Short) x).intValue();
		}
		else if (x instanceof Long) {
			return ( (Long) x).intValue();
		}
		else if (x instanceof Float) {
			float b = ( (Float) x).floatValue();
			return (int) b;
		}
		else if (x instanceof Double) {
			double b = ( (Double) x).doubleValue();
			return (int) b;
		}
		else {
			throw new ExpressionException("Error converting Object to int.");
		}
	}

	/**
	 * Get the long value from an object.
	 */
	public final long getlong(Object x)
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Long) {
			return ( (Long) x).longValue();
		}
		else if (x instanceof String) {
			return Long.parseLong( (String) x);
		}
		if (x instanceof Boolean) {
			boolean b = ( (Boolean) x).booleanValue();
			if (b) {
				return LONG_1;
			}
			return LONG_0;
		}
		else if (x instanceof Byte) {
			return ( (Byte) x).intValue();
		}
		else if (x instanceof Character) {
			return ( (Character) x).charValue();
		}
		else if (x instanceof Short) {
			return ( (Short) x).longValue();
		}
		else if (x instanceof Integer) {
			return ( (Integer) x).longValue();
		}
		else if (x instanceof Float) {
			return ( (Float) x).longValue();
		}
		else if (x instanceof Double) {
			return ( (Double) x).longValue();
		}
		else {
			throw new ExpressionException("Error converting Object to long.");
		}
	}

	/**
	 * Get the float value from an object.
	 */
	public final float getfloat(Object x)
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Float) {
			return ( (Float) x).floatValue();
		}
		else if (x instanceof String) {
			return Float.parseFloat( (String) x);
		}
		if (x instanceof Boolean) {
			boolean b = ( (Boolean) x).booleanValue();
			if (b) {
				return 1F;
			}
			return 0F;
		}
		else if (x instanceof Byte) {
			return ( (Byte) x).floatValue();
		}
		else if (x instanceof Character) {
			return ( (Character) x).charValue();
		}
		else if (x instanceof Short) {
			return ( (Short) x).floatValue();
		}
		else if (x instanceof Integer) {
			return ( (Integer) x).floatValue();
		}
		else if (x instanceof Long) {
			return ( (Long) x).floatValue();
		}
		else if (x instanceof Double) {
			return ( (Double) x).floatValue();
		}
		else {
			throw new ExpressionException("Error converting Object to float.");
		}
	}

	/**
	 * Get the double value from an object.
	 */
	public final double getdouble(Object x)
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Double) {
			return ( (Double) x).doubleValue();
		}
		else if (x instanceof String) {
			return Double.parseDouble( (String) x);
		}
		if (x instanceof Boolean) {
			boolean b = ( (Boolean) x).booleanValue();
			if (b) {
				return DOUBLE_1;
			}
			return 0.0D;
		}
		else if (x instanceof Byte) {
			return ( (Byte) x).doubleValue();
		}
		else if (x instanceof Character) {
			return ( (Character) x).charValue();
		}
		else if (x instanceof Short) {
			return ( (Short) x).doubleValue();
		}
		else if (x instanceof Integer) {
			return ( (Integer) x).doubleValue();
		}
		else if (x instanceof Long) {
			return ( (Long) x).doubleValue();
		}
		else if (x instanceof Float) {
			return ( (Float) x).doubleValue();
		}
		else {
			throw new ExpressionException("Error converting Object to double.");
		}
	}

	/**
	 * Returns the class of a primitive wrapper object.
	 * @param x Object
	 * @throws ExpressionException
	 * @return Class
	 */
	public final Class getObjectClass(Object x)
		throws ExpressionException {
		if (x == null) {
			throw new ExpressionException(NULL_INPUT_OBJECT);
		}
		if (x instanceof Boolean) {
			return Boolean.TYPE;
		}
		else if (x instanceof Byte) {
			return Byte.TYPE;
		}
		else if (x instanceof Character) {
			return Character.TYPE;
		}
		else if (x instanceof Short) {
			return Short.TYPE;
		}
		else if (x instanceof Integer) {
			return Integer.TYPE;
		}
		else if (x instanceof Long) {
			return Long.TYPE;
		}
		else if (x instanceof Float) {
			return Float.TYPE;
		}
		else if (x instanceof Double) {
			return Double.TYPE;
		}
		else {
			return x.getClass();
		}
	}
}
