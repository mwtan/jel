header {
package com.github.wtan.jel;

import java.util.List;
import java.util.ArrayList;
}
/**
 * Java Expression Language Statement AST Tree Parser.
 * This generates a tree parser that walks the statement tree to
 * create Expression objects.
 *
 * NOTE: This Java source (StatementTreeParser.java) should not be modified
 *       directly. It is generate from statement.tree.g using the following
 *       command:
 *       java antlr.Tool statement.tree.g
 *
 * Author: Will Tan (Adapted from ANTLR's java.tree.g)
 */

class StatementTreeParser extends TreeParser;
options {
    importVocab = Statement;
}
{
    private static final String EXCEPTION_MSG = "Error parsing statement (null expression).";
}

compilationUnit returns [Expression r=null]
    :   r = statement
    ;
	
//type returns [Expression r = null]
//	:	r = identifier
//	;

//objectinitializer returns [Expression r = null]
//	:	#(INSTANCE_INIT r = slist)
//	;

//identifier returns [Expression r = null]
//	:	i:IDENT {
//            r = new IdentValue(i.getText());
//        }
//	|	#( DOT r = identifier IDENT )
//	;

slist returns [StatementList r = null]
    {
        Expression a=null;
        r = new StatementList();
    }
    :   #( SLIST
           ( a = statement { r.add(a); }
           )*
        )
    ;

statement returns [Expression r=null]
    { 
        Expression a=null, b=null, c=null; 
        List e1 = null, e2 = null;
	}
    :   a = expression {
            r = a;
        }
    |   #("if" a = expression b = statement (c = statement)?) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new If(a, b, c);
        }
	|	#("for"
			#(FOR_INIT (e1 = elist)?)
			#(FOR_CONDITION (a = expression)?)
			#(FOR_ITERATOR (e2 = elist)?)
			b = statement ) {
				if (a==null || b==null) {
					throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new For(e1, a, e2, b);
			}
	|	#("while" a = expression b = statement) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new While(a, b);
		}
	|	#("do" a = statement b = expression) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
			r = new Do(a, b);
		}
	|	#("break" (IDENT)? ) {
			r = new Break();
		}
	|	#("continue" (IDENT)? ) {
			r = new Continue();
		}
    |   #("return" (a = expression)? ) {
            r = new Return(a);
        }
	|	#("switch" a = expression {Switch sw = new Switch(a);} (c = caseGroup  {sw.addCase(c);} )*) {
            r = sw;
		}
    |   a = slist {
            r = a;
        }
    |   EMPTY_STAT {
            r = null;
        }
    ;

caseGroup returns [Expression r=null]
    { 
        Expression a=null, b=null;
	}
	:	#(CASE_GROUP (#("case" a=expression) | "default")+ b=slist) {
			if (a != null) {
				r = new Case(a, b);
			}
			else {
				r = new Case(b);
			}
		}
	;

elist returns [List r = null]
    {
        Expression a=null;
        r = new ArrayList();
    }
    :   #(  ELIST
            ( a = expression { r.add(a); }
            )*
        )
    ;

expression returns [Expression r=null]
    :   #(EXPR r = expr)
    ;

expr returns [Expression r = null]
    { Expression a = null, b = null, c = null; }
    :   #(QUESTION a = expr b = expr c = expr) {
            if (a==null || b==null || c==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Ternary(a, b, c);
        }
    |   #(ASSIGN a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Assign(a, b);
        }
    |   #(PLUS_ASSIGN a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new PlusAssign(a, b);
        }
    |   #(MINUS_ASSIGN a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new MinusAssign(a, b);
        }
    |   #(STAR_ASSIGN a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new MultiplyAssign(a, b);
        }
    |   #(DIV_ASSIGN a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new DivideAssign(a, b);
        }
    |   #(MOD_ASSIGN a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new ModulusAssign(a, b);
        }
    |   #(LOR a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Or(a, b);
        }
    |   #(LAND a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new And(a, b);
        }
    |   #(BOR a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new BitwiseOr(a, b);
        }
    |   #(BXOR a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new BitwiseXor(a, b);
        }
    |   #(BAND a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new BitwiseAnd(a, b);
        }
    |   #(NOT_EQUAL a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new NotEqual(a, b);
        }
    |   #(EQUAL a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new EqualEqual(a, b);
        }
    |   #(LT a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new LessThan(a, b);
        }
    |   #(GT a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new GreaterThan(a, b);
        }
    |   #(LE a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new LessThanEqual(a, b);
        }
    |   #(GE a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new GreaterThanEqual(a, b);
        }
    |   #(PLUS a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Plus(a, b);
        }
    |   #(MINUS a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Minus(a, b);
        }
    |   #(DIV a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Divide(a, b);
        }
    |   #(MOD a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Modulus(a, b);
        }
    |   #(STAR a = expr b = expr) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Multiply(a, b);
        }
    |   #(INC a = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Inc(a);
        }
    |   #(DEC a = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Dec(a);
        }
    |   #(POST_INC a = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new PostInc(a);
        }
    |   #(POST_DEC a = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new PostDec(a);
        }
    |   #(BNOT a = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new BitwiseNot(a);
        }
    |   #(LNOT a = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Not(a);
        }
	|	#("instanceof" a = expr b = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new InstanceOf(a, b);
		}
    |   #(UNARY_MINUS a = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new UnaryMinus(a);
        }
    |   #(UNARY_PLUS a = expr) {
            if (a==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new UnaryPlus(a);
        }
    |   r = primaryExpression
    ;

primaryExpression returns [Expression r = null]
    {
        Expression a=null, b=null;
        List el = null;
    }
    :   i:IDENT {
            r = new IdentValue(i.getText());
        }
    |   #(  DOT
            (   a = expr
                (   b = primaryExpression
                )
            )
        ) {
            if (a==null || b==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new Dot(a, b);
        }
    |   #(METHOD_CALL a = primaryExpression el = elist) {
            if (a==null || el==null) {
                throw new RecognitionException(EXCEPTION_MSG);
            }
            r = new MethodCall(a, el);
        }
    |   r = newExpression 
    |   r = constant
    |   "true" {
            r = new BooleanValue(true);
        }
    |   "false" {
            r = new BooleanValue(false);
        }
    |   "null" {
            r = new ConstantValue(null);
        }
    ;

newExpression returns [Expression r = null]
    {
        Expression a = null;
        List e1 = null;
    }
//	:	#( "new" a = type e1 = elist (objectinitializer)? 
	:	#( "new" a = primaryExpression e1 = elist 
		) {
            r = new New(a, e1);
		}
	;

/**
 * Returns a constant value as a primitive wrapper object.
 */
constant returns [Expression r=null]
    :   i:NUM_INT {
            r = new ConstantValue(new Integer(Integer.decode(i.getText())));
        }
    |   j:CHAR_LITERAL {
            String s = j.getText();
            // Remove beginning and ending single quote
            if (s.startsWith("\'")) {
                s = s.substring(1);
            }
            if (s.endsWith("\'")) {
                s = s.substring(0, s.length()-1);
            }
            // Just get first char for now. TODO: parse unicode, hex, etc.
            r = new ConstantValue(new Character(s.charAt(0)));
        }
    |   k:STRING_LITERAL  {
            String s = k.getText();
            // Remove beginning and ending double quote
            if (s.startsWith("\"")) {
                s = s.substring(1);
            }
            if (s.endsWith("\"")) {
                s = s.substring(0, s.length()-1);
            }
            r = new ConstantValue(s);
        }
    |   l:NUM_FLOAT {
            r = new ConstantValue(new Float(ConstantValue.dropSuffix(l.getText())));
        }
    |   m:NUM_DOUBLE {
            r = new ConstantValue(new Double(ConstantValue.dropSuffix(m.getText())));
        }
    |   n:NUM_LONG {
            r = new ConstantValue(new Long(ConstantValue.dropSuffix(n.getText())));
        }
    ;

