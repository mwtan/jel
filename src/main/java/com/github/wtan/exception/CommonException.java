package com.github.wtan.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * General exception class that enables exception chaining.
 *
 * @author Will Tan
 */
public class CommonException extends Exception {
	private static final long serialVersionUID = -7329856070780733748L;
	private Exception chainedException = null;

	/**
	 * Default constructor.
	 * @param inMessage - a descriptive message
	 */
	public CommonException() {
		super();
	}

	/**
	 * @param inMessage - a descriptive message
	 * @param inException - the underlying trapped exception
	 */
	public CommonException(String inMessage, Exception inException) {
		super(inMessage, inException);
		chainedException = inException;
	}

	/**
	 * @param inMessage - a descriptive message
	 */
	public CommonException(String inMessage) {
		super(inMessage);
	}

	/**
	 * @param inException - the underlying trapped exception
	 */
	public CommonException(Exception inException) {
		super(inException);
		chainedException = inException;
	}

	/**
	 * Returns the embedded root cause if there is one, otherwise
	 * returns itself. This method navigates through all embedded
	 * causes until it finds the innermost Exception.
	 * <p>
	 * @return Exception representing the root embedded cause
	 */
	public Exception getRootCause() {
		if (chainedException == null) {
			return this;
		}
		else {
			if (chainedException instanceof CommonException) {
				return ((CommonException) chainedException).getRootCause();
			}
			else {
				return chainedException;
			}
		}
	}

	/**
	 * Returns the next Exception (cause) in the chain of Exceptions.
	 * <p>
	 * @return Exception representing the next Exception.
	 */
	public Exception getNextCause() {
		if (chainedException == null) {
			return null;
		}
		else {
			return chainedException;
		}
	}

	/**
	 * Returns the stack trace as a string for this exception.
	 * <p>
	 * @return String printStackTrace output
	 */
	public String getPrintStackTrace() {
		return CommonException.getPrintStackTrace(this);
	}

	/**
	 * Returns the stack trace as a string for the root exception.
	 * <p>
	 * @return String printStackTrace output
	 */
	public String getRootPrintStackTrace() {
		return CommonException.getPrintStackTrace(this.getRootCause());
	}

	/**
	 * Returns the stack trace as a string for the provided exception.
	 * <p>
	 * @return String printStackTrace output
	 */
	public static String getPrintStackTrace(Exception ex) {
		try {
			StringWriter sWriter = new StringWriter();
			PrintWriter pWriter = new PrintWriter(sWriter);
			ex.printStackTrace(pWriter);
			return sWriter.toString();
		}
		catch (Exception e) {
			return "Error obtaining exception stack trace.";
		}
	}
}
