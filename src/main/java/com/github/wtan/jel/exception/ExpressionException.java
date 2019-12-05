package com.github.wtan.jel.exception;

import com.github.wtan.exception.CommonException;

/**
 * This exception is thrown by an Expression when the evaluation of the expression
 * fails.
 *
 * @author Will Tan
 */
public class ExpressionException extends CommonException {
	private static final long serialVersionUID = 6631365023873434045L;

	/**
	 * @param inMessage - a descriptive message
	 * @param inThrowable - the underlying trapped exception
	 */
	public ExpressionException(String inMessage, Exception inThrowable) {
		super(inMessage, inThrowable);
	}

	/**
	 * @param inMessage - a descriptive message
	 */
	public ExpressionException(String inMessage) {
		super(inMessage);
	}
}
