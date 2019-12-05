package com.github.wtan.jel;

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import com.github.wtan.jel.exception.ExpressionException;

import antlr.ASTFactory;
import antlr.RecognitionException;
import antlr.TokenStreamException;

/**
 * JEL provides the ability to define Java statements in text form that is parsed at runtime
 * for execution. These statements can be stored in a property file, database or an XML
 * file to enable modification without requiring a code change. At run-time these statements 
 * are retrieved and <code>Statement</code> objects are created using the <code>parse</code> 
 * method. Once created, these objects are evaluated in sequence (by calling its 
 * <code>eval(Map)</code> method). The Map passed to the <code>Statement</code> object 
 * contains attribute or variables required in the evaluation. The evaluation can return 
 * an object including a <code>true</code> or <code>false</code> result as a <code>Boolean</code> 
 * wrapper.
 * <p>
 * <b>Statement Syntax</b><br>
 * JEL statements follow the basic rules of the Java language but with some limitations (see
 * Limitations below).
 * <p>
 * An expression can be given precendence using parentheses (i.e. ( and ) characters),
 * e.g. <code>4 * (3 + 5);</code>
 * <p>
 * <b>Compound Statements</b><br>
 * Statements can be compound statements with each statement terminated with a semi-colon.
 * Unlike single statements, compound statements must be enclosed by curly braces. e.g.<pre>
 *     {
 *         message = "Hello";
 *         volts = current * resistance;
 *     } </pre>
 * Of course, compound statements can include "if" and "return" statements: e.g.<pre>
 *     {
 *         if (myobj != null && myobj.rc == 42) {
 *             message = "hello";
 *         }
 *         if (varint == 512) {
 *             message += "world";
 *         }
 *         return true;
 *     } </pre>
 * <p>
 * <b>Return</b><br>
 * The <code>return</code> statement returns an object for the Statement being
 * evaluated. Normally, a Statement object would return a <code>true</code> or
 * <code>false</code> Boolean object. It could instead return a String or the result
 * of a method call. However, only objects are returned. Primitive values that result
 * from arithmetic expression or method calls are returned in their primitive wrappers.
 * <p>
 * A Statement object does not have to return a value, e.g. it could contain only
 * assignment statements. Without a <code>return</code> statement, a Statement
 * returns a null.
 * <p>
 * A <code>return</code> statement within a compound statement exits the Statement.
 * Any remaining statements are not executed. e.g.<pre>
 *     {
 *         distance = speed * time;
 *         return "Distance=" + distance;
 *         volts = current * resistance; // Doesn't get executed.
 *     } </pre>
 * <p>
 * A <code>return</code> statement without an expression simply exits the statement
 * and returns <code>null</code>.
 * <p>
 * <b>Comments</b><br>
 * Comments can be embedded in the statements. They can be single lined
 * (using a //) or multi-lined (using &frasl;* and *&frasl;). A // comment must be
 * terminated with a new-line character, otherwise subsequent lines may be ignored
 * causing errors.
 * <p>
 * <b>Logical Operators</b><br>
 * The usual logical operators <code>||</code> (OR), <code>&&</code> (AND) and
 * <code>!</code> (NOT) can be used. The  <code>||</code> and  <code>&&</code>
 * operators are also short-circuiting.
 * <p>
 * <b>Relational Operators</b><br>
 * All the Java relational operators can be used:
 * <ul>
 *   <li>equality (<code>==</code>)</li>
 *   <li>inequality (<code>!=</code>)</li>
 *   <li>greater than (<code>></code>)</li>
 *   <li>less than (<code><</code>)</li>
 *   <li>greater than or equal to (<code>>=</code>)</li>
 *   <li>less than or equal to (<code><=</code>)</li>
 * </ul>
 * <p>
 * <b>Arithmetic Operators</b><br>
 * Most the usual arithmetic operators are available. Postfix increment and
 * decrement operators are not available. The arithmetic operators also follow the
 * Java precedence rules, e.g. multiplication and division happen before addition
 * and subtraction.
 * <ul>
 *   <li>addition (<code>+</code>)</li>
 *   <li>subtraction (<code>-</code>)</li>
 *   <li>division (<code>/</code>)</li>
 *   <li>modulus (<code>%</code>)</li>
 *   <li>multiplication (<code>*</code>)</li>
 *   <li>Unary minus and plus operators (e.g. <code>-4</code>)</li>
 *   <li>prefix increment (<code>++i</code>)</li>
 *   <li>prefix decrement (<code>--i</code>)</li>
 *   <li>postfix increment (<code>i++</code>)</li>
 *   <li>postfix decrement (<code>i--</code>)</li>
 * </ul>
 * <p>
 * <b>Object Attributes and Method Calls</b><br>
 * Objects in the map being evaluated are referenced by name, i.e. the object
 * of that name is retrieved from the map. An invalid name or the absence of a
 * map entry produces a null. The test <code>(object == null)</code> or
 * <code>(object != null)</code> can be used.
 * <p>
 * Public object fields can be referenced using the DOT (.) operator, e.g.
 * <code>object.status</code>. Also, public object methods can be called, e.g.
 * with an object named <code>lastName</code> of type String, the String methods
 * can be called in the normal way, such as <code>lastName.length()</code> or
 * <code>lastName.equalsIgnoreCase("smith")</code>.
 * <p>
 * When an object is referenced that is not present in the workspace map, an 
 * attempt is made to reference the object in the java.lang and java.util package.  
 * E.g. the static methods in the Math class can be used directly, e.g. <pre>
 *     Math.max(123, 321); 
 * </pre>
 * The static methods of classes can be called using the explicit package and 
 * class name, e.g.<pre>
 *     { num = 42; return java.lang.String.valueOf(num); } 
 * </pre>
 * Since the String class is in the java.lang package, it can be used directly.<pre>
 *     { num = 42; return String.valueOf(num); }
 * </pre>
 * Here, a class in the Apache Commons library (present in the classpath) is used:<pre>
 *     return org.apache.commons.lang.StringEscapeUtils.unescapeJava("Hello\tWorld."); 
 * </pre>
 * The out print stream of the System class can be used in the usual way:<pre>
 *     System.out.println("Hello Will."); 
 * </pre>
 * Factory methods can be called to create objects:<pre>
 *     return Calendar.getInstance();
 * </pre>
 * <p>
 * <b>Object Instantiation</b><br>
 * The new operator is used to create objects.  The full package and class name can 
 * be specified to the new operator:<pre>
 *     obj = new java.util.ArrayList(7); // ArrayList with an initial size of 7.
 * </pre>
 * As before the java.lang or java.util package can be omitted:<pre>
 *     obj = new ArrayList(7); 
 * </pre>
 * Parameters to class constructors can be supplied:<pre>
 *     obj = new StringBuilder("Hello");
 * </pre>
 * <p>
 * <b>Sting Concatenation</b><br>
 * The arithmetic '+' operator is overloaded to concatenate Strings. A string or
 * string literal can be concatenated with other strings or a primitive types. The
 * '+=' operator can also be used. Examples:<pre>
 *     {
 *         result = 2 * pi * r;
 *         message = "Circumference=" + result;
 *         message += "km";
 *     } </pre>
 * <p>
 * <b>Escaping special characters</b><br>
 * When special characters need to be included within string, they need to be escaped 
 * in the normal way, e.g.<pre>
 *     message = "This is line one\nThis is line two\n\tThis line is tabbed.\";
 * </pre>
 * The standard Java special characters are:
 * <ul>
 *   <li><code>\'</code> - Single quotation mark</li>
 *   <li><code>\"</code> - Double quotation mark</li>
 *   <li><code>\\</code> - Backslash</li>
 *   <li><code>\t</code> - Tab</li>
 *   <li><code>\b</code> - Backspace</li>
 *   <li><code>\r</code> - Carriage return</li>
 *   <li><code>\f</code> - Formfeed</li>
 *   <li><code>\n</code> - Newline</li>
 * </ul>
 * <p>
 * <b>Assignment</b><br>
 * An assignment statement assigns an object value to a reference in the Map by
 * name. An identifier is therefore a Map entry that is referenced by the name (i.e.
 * the key) of the Map entry. The entry is added to the Map if it doesn't exist.
 * e.g.<pre>
 *     if (responsetime > threshold) {
 *         message = \"Response time exceeded \" + threshold + \" seconds.\";
 *         totaltime += responsetime;
 *         return false;
 *     } </pre>
 * <p>
 * Values assigned to 'identifiers' in the Map are available to the caller of the
 * Statement object and to subsequent Statements objects that are executed. 
 * <p>
 * The following assignment operators can also be used:<br>
 * <ul>
 *   <li><code>+=</code></li>
 *   <li><code>-=</code></li>
 *   <li><code>/=</code></li>
 *   <li><code>*=</code></li>
 *   <li><code>%=</code></li>
 * </ul>
 * <p>
 * When constants are assigned, the variable type assumes the type of the constant, 
 * e.g.<pre>
 *     counter = 12; // counter is an Integer 
 *     amount = 25.85; // amount is a Double
 * </pre>
 * The normal Java numeric suffixes can be used to explicitly type the constant, 
 * <p>
 * e.g.<pre>
 *     distancetosun = 93000000L; // A Long
 *     smallpi = 3.14F; // A Float
 *     bigpi = 3.141592653589D; // A Double
 * </pre>
 * <p>
 * <b>instanceof operator</b><br>
 * The instanceof operator can be used to test whether an object is an instance of a 
 * specified type, e.g.<pre>
 *     { dt = new Date(); return dt instanceof Date; } // returns true
 * </pre>
 * Since type checking does not exist during compile time, it may be necessary to 
 * validate an object type before accessing object attributes or making method calls.
 * <p>
 * <b>if statement</b><br>
 * An <code>if</code> statement begins with the <code>if</code> keyword followed by a
 * logical expression, a <code>then</code> expression and an optional <code>else</code>
 * expression. The <code>then</code> expression is evaluated if the condition is
 * true otherwise the <code>else</code> expression is evaluated. Both <code>then</code>
 * and <code>else</code> expressions can contain a <code>return</code> statement.
 * When the <code>else</code> expression is absent and the condition is not satisfied,
 * a <code>null</code> is returned.
 * <p>
 * An <code>if</code> statement can be nested and curly braces (i.e. { and }
 * characters) can be used to delineate code blocks or compound statements.
 * (Since declarations do not exist, there is no implication of scope.)
 * <p>
 * <b>while loop</b><br>
 * This is a standard while loop where a boolean condition is evaluate and the statement 
 * block is run zero of more times.  The statement block can contain break, continue or 
 * return statements. A break or continue to a label is not permitted.  E.g.<pre>
 *     {
 *         i=4;
 *         while (i>0) {
 *             msg=msg+\"hello\";
 *             i--;
 *         }
 *     }</pre>
 * <p>
 * <b>do-while loop</b><br>
 * A do-while statement executes the statement block at least once and a boolean condition 
 * is evaluated at the end of the statement block.  The same restriction on break, continue 
 * and return statements as with the while statement applies.  E.g.<pre>
 *     {
 *         msg = "";
 *         do {
 *             msg = msg + "hello";
 *             if (msg.length() >= 15) {
 *                 break;
 *             }
 *         } while (true);
 *     }</pre>
 * <p>
 * <b>for loop</b><br>
 * A for loop is made up of four parts:
 * <ul>
 *   <li>initializer - can be an expression or expression list</li>
 *   <li>termination - an expression</li>
 *   <li>iteration - can be an expression or expression list</li>
 *   <li>statement block</li>
 * </ul>
 * Variables are first initialized in the list of expressions in the initializer 
 * but variable declarations cannot be used. The statement block is executed followed 
 * by the iteration expression list.  This can increment or decrement variables.  
 * When the termination expression evaluates to false, the loop ends.  E.g.<pre>
 *     {
 *         msg = "";
 *             for (i=0, j=3; i<4; i++, j--) {
 *                 msg = msg + "hello " + i + j;
 *         }
 *     }</pre>
 * <p>
 * <b>switch statement</b><br>
 * A switch statement evaluates a switch expression and executes the statements under 
 * a matching case label.  The switch/case comparison works with the Byte, Character, Short, 
 * Integer, Long wrapper classes and the String class. The following example sets dayString 
 * to "Friday":<pre>
 *     {
 *         day = 6;
 *         switch (day) {
 *             case 1:  dayString = "Sunday";
 *                 break;
 *             case 2:  dayString = "Monday";
 *                 break;
 *             case 3:  dayString = "Tuesday";
 *                 break;
 *             case 4:  dayString = "Wednesday";
 *                 break;
 *             case 5:  dayString = "Thursday";
 *                 break;
 *             case 6:  dayString = "Friday";
 *                 break;
 *             case 7:  dayString = "Saturday";
 *                 break;
 *             default: dayString = "Invalid day";
 *                 break;
 *         }
 *     }</pre>
 * Without a break statement at each case, the flow falls through to the following case 
 * labels. In the following, dayString ends up with a value of "ThursdayFridaySaturday":<pre>
 *     {
 *         day = 5;
 *         dayString = "";
 *         switch (day) {
 *             case 1:  dayString = dayString + "Sunday";
 *             case 2:  dayString = dayString + "Monday";
 *             case 3:  dayString = dayString + "Tuesday";
 *             case 4:  dayString = dayString + "Wednesday";
 *             case 5:  dayString = dayString + "Thursday";
 *             case 6:  dayString = dayString + "Friday";
 *             case 7:  dayString = dayString + "Saturday";
 *                 break;
 *             default:
 *                 break;
 *         }
 *     }</pre>
 * A switch statement can also have multiple case labels assigned to a case statement.
 * The following switch statement determines the number of days in a month, e.g. April, 
 * June, September and November all have 30 days:<pre>
 *     {
 *         month = 6;
 *         year = 2000;
 *         dayString = "";
 *         switch (month) {
 *             case 1: case 3: case 5: case 7: case 8: case 10: case 12: 
 *                 numDays = 31;
 *                 break;
 *             case 4: case 6: case 9: case 11: 
 *                 numDays = 30;
 *                 break;
 *             case 2: 
 *                 if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0))
 *                     numDays = 29;
 *                 else
 *                     numDays = 28;
 *                 break;
 *             default:
 *                 numDays = 99;
 *                 break;
 *         }
 *     }</pre>
 * <p>
 * <b>Testing and experimentation</b><br>
 * The JELConsole application can be used to try out JEL statements. Run it as follows:<pre>
 *    java -cp .\jel-1.0.jar;.\*; com.mm.jel.JELConsole
 * </pre>
 * It can display results returned and the contents of the work map. The command line history 
 * allows previous commands to be recalled and modified. Type help at the prompt for help.
 * <p>
 * <b>Limitations</b><br>
 * <ul>
 *   <li>No casting.</li>
 *   <li>No variable declaration (not needed since new variables are created automatically)</li>
 *   <li>No bitwise or shift operators.</li>
 *   <li>No break or continue to a named label within while, do, and for loops. Normal break and
 *       continue statements in loops and conditional statements are allowed.</li>
 *   <li>No synchronize</li>
 *   <li>No throwing or catching exceptions</li>
 * </ul>
 * <p>
 * <b>More Statement Examples</b><br>
 * A simple statement:<pre>
 *     if (rc > 9) return false;</pre>
 * Referencing an object field:<pre>
 *     if (myobj.count > 99) return false;</pre>
 * Arithmetic operators:<pre>
 *     if ((myobj.linecount / 66) + 1 >= 12) return false;</pre>
 * A method call:<pre>
 *     if (eReasonCode.startsWith("MMFramework.manager.001")) return false;</pre>
 * An OR condition:<pre>
 *     if (environment.equalsIgnoreCase("UNIT") ||
 *         environment.equalsIgnoreCase("PROJ")) {
 *         return true;
 *     } </pre>
 * A nested <code>if</code> statement:<pre>
 *     if (myobj != null) {
 *         if (rc > myobj.errorlimit && errormsg.length() > 0) {
 *             return false;
 *         }
 *     }
 *     else {
 *         return true;
 *     }</pre>
 * <p>
 * 
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
	public static final Statement parse(String s)
		throws ExpressionException {
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
	public static final Statement parse(Reader r)
		throws ExpressionException {
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
