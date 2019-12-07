package com.github.wtan.jel;

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

import antlr.ASTFactory;
import antlr.RecognitionException;
import antlr.TokenStreamException;

/**
 * The main class for interacting with JEL. It parses an expression string and the  
 * resulting Statement object can then be used to evaluate the expression.
 * <p>
 * @author Will Tan
 */
public class Statement implements IExpression {
	private IExpression expression;

	/**
	 * Prevent instantiation.
	 */
	private Statement() {
		// Empty constructor
	}

	/**
	 * Constructs a Statement object containing an expression.
	 * @param e IExpression The expression created by the parse factory methods.
	 */
	private Statement(IExpression e) {
		this.expression = e;
	}

	/**
	 * Evaluates the Statement object and returns the return value (i.e. the
	 * expression of the Return statement). If the statement did not include a
	 * Return statement, null is returned.
	 */
	public Object eval(Map m) throws ExpressionException {
		Object obj = expression.eval(m);
		if (obj instanceof ReturnValue) {
			return ((ReturnValue) obj).getObject();
		}
		return null;
	}

	/**
	 * A factory method that parses the expression string supplied and returns a
	 * Statement object.
	 *
	 * @param s String The input statement string.
	 * @throws ExpressionException Error parsing statement.
	 * @return Statement A Statement object that can be evaluated.
	 */
	public static final Statement parse(String s) throws ExpressionException {
		StringReader sr = new StringReader(s);
		return parse(sr);
	}

	/**
	 * A factory method that parses the statement string supplied by the reader
	 * and returns a Statement object.
	 *
	 * @param r Reader Reads the statement string.
	 * @throws ExpressionException Error parsing statement.
	 * @return Statement A Statement object that can be evaluated.
	 */
	public static final Statement parse(Reader r) throws ExpressionException {
		IExpression expr = null;
		// Create a scanner that reads from the input stream passed to us
		StatementLexer lexer = new StatementLexer(r);
		// Create a parser that reads from the scanner
		StatementRecognizer parser = new StatementRecognizer(lexer);
		// Set the ASTFactory
		parser.setASTFactory(new ASTFactory());
		try {
			// Start parsing at the statement rule
			parser.statement();
			// Process the tree to create the statement
			StatementTreeParser tparse = new StatementTreeParser();
			expr = tparse.compilationUnit(parser.getAST());
			if (expr == null) {
				throw new ExpressionException("Parsed statement is null.");
			}
		}
		catch (RecognitionException e) {
			StringBuilder sb = new StringBuilder("Parse error. ");
			sb.append(e.getMessage());
			sb.append(" (Line ");
			sb.append(e.getLine());
			sb.append(" Column ");
			sb.append(e.getColumn());
			sb.append(")");
			throw new ExpressionException(sb.toString(), e);
		}
		catch (TokenStreamException e) {
			throw new ExpressionException("Token error: " + e.getMessage(), e);
		}
		// Wrap expression in a Statement object
		return new Statement(expr);
	}
}
