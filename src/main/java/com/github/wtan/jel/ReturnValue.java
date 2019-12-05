package com.github.wtan.jel;

/**
 * A wrapper object for returned values. This provides the ability to identify
 * a value returned via a Return statement.
 *
 * @author Will Tan
 */
class ReturnValue {
	private Object object;

	/**
	 * Standard constructor.
	 * @param obj Object The object to be returned.
	 */
	public ReturnValue(Object obj) {
		this.object = obj;
	}

	/**
	 * Simply returns the object.
	 */
	public Object getObject() {
		return object;
	}
}
