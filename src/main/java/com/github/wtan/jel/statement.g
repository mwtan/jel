header {
package com.github.wtan.jel;
}
/**
 * Java Expression Language Statement Recognizer.
 * Parses the tokens generated by the Lexer to produce an AST which is
 * then processed by the tree parser.
 *
 * NOTE: This Java source (StatementRecognizer.java) should not be modified
 *       directly. It is generate from statement.g using the following
 *       command:
 *       java antlr.Tool statement.g
 *
 * Author: Will Tan (Adapted from ANTLR's java.g)
 */

class StatementRecognizer extends Parser;
options {
    k = 2;                           // two token lookahead
    exportVocab=Statement;           // Call its vocabulary "Statement"
    codeGenMakeSwitchThreshold = 2;  // Some optimizations
    codeGenBitsetTestThreshold = 3;
    defaultErrorHandler = false;     // Don't generate parser error handlers
    buildAST = true;
}

tokens {
    BLOCK; OBJBLOCK; SLIST; CTOR_DEF; METHOD_DEF; VARIABLE_DEF;
    INSTANCE_INIT; PARAMETERS; PARAMETER_DEF; LABELED_STAT; INDEX_OP;
    POST_INC; POST_DEC; METHOD_CALL; EXPR;
    UNARY_MINUS; UNARY_PLUS; CASE_GROUP; ELIST; FOR_INIT; FOR_CONDITION;
    FOR_ITERATOR; SUPER_CTOR_CALL; CTOR_CALL;
}

// Compilation Unit: In Java, this is a single file.  This is the start
//   rule for this parser
compilationUnit
    :   // My compilation unit starts with a statement
        ( statement )*
        EOF!
    ;

// A class type specification is a class type with possible brackets afterwards
//   (which would make it an array type).
classTypeSpec
	:	identifier
	;

// A type name. which is either a (possibly qualified) class name or
//   a primitive (builtin) type
type
	:	identifier
	;

// A (possibly-qualified) java identifier.  We start with the first IDENT
//   and expand its name by adding dots and following IDENTS
identifier
	:	IDENT  ( DOT^ IDENT )*
	;
	
compoundStatement
    :   lc:LCURLY^ {#lc.setType(SLIST);}
            // include the (possibly-empty) list of statements
            (statement)*
        RCURLY!
    ;

statement
    // A list of statements in curly braces -- start a new scope!
    :   compoundStatement

    // An expression statement.  This could be a method call,
    // assignment statement, or any other expression evaluated for
    // side-effects.
    |   expression SEMI!

    // If-else statement
    |   "if"^ LPAREN! expression RPAREN! statement
        (
            // CONFLICT: the old "dangling-else" problem...
            //           ANTLR generates proper code matching
            //           as soon as possible.  Hush warning.
            options {
                warnWhenFollowAmbig = false;
            }
        :
            "else"! statement
        )?

	// For statement
	|	"for"^
			LPAREN!
				forInit SEMI!   // initializer
				forCond	SEMI!   // condition test
				forIter         // updater
			RPAREN!
			statement                     // statement to loop over

	// While statement
	|	"while"^ LPAREN! expression RPAREN! statement

	// do-while statement
	|	"do"^ statement "while"! LPAREN! expression RPAREN! SEMI!

	// get out of a loop (or switch)
	|	"break"^ (IDENT)? SEMI!

	// do next iteration of a loop
	|	"continue"^ (IDENT)? SEMI!
		
    // Return an expression
    |   "return"^ (expression)? SEMI!

	// switch/case statement
	|	"switch"^ LPAREN! expression RPAREN! LCURLY!
			( casesGroup )*
		RCURLY!
    ;

casesGroup
	:	aCase caseSList
		{#casesGroup = #([CASE_GROUP, "CASE_GROUP"], #casesGroup);}
	;

aCase
	:	("case"^ expression | "default") COLON!
	;

caseSList
	:	(statement)*
		{#caseSList = #(#[SLIST,"SLIST"],#caseSList);}
	;

// The initializer for a for loop
forInit
	:	(expressionList)?
		{#forInit = #(#[FOR_INIT,"FOR_INIT"],#forInit);}
	;

forCond
	:	(expression)?
		{#forCond = #(#[FOR_CONDITION,"FOR_CONDITION"],#forCond);}
	;

forIter
	:	(expressionList)?
		{#forIter = #(#[FOR_ITERATOR,"FOR_ITERATOR"],#forIter);}
	;

// expressions
// Note that most of these expressions follow the pattern
//   thisLevelExpression :
//       nextHigherPrecedenceExpression
//           (OPERATOR nextHigherPrecedenceExpression)*
// which is a standard recursive definition for a parsing an expression.
// The operators in java have the following precedences:
//    lowest  (13)  = *= /= %= += -= <<= >>= >>>= &= ^= |=
//            (12)  ?:
//            (11)  ||
//            (10)  &&
//            ( 9)  |
//            ( 8)  ^
//            ( 7)  &
//            ( 6)  == !=
//            ( 5)  < <= > >=
//            ( 4)  << >>
//            ( 3)  +(binary) -(binary)
//            ( 2)  * / %
//            ( 1)  ++ -- +(unary) -(unary)  ~  !  (type)
//                  []   () (method call)  . (dot -- identifier qualification)
//                  new   ()  (explicit parenthesis)
//
// the last two are not usually on a precedence chart; I put them in
// to point out that new has a higher precedence than '.', so you
// can validy use
//     new Frame().show()
//
// Note that the above precedence levels map to the rules below...
// Once you have a precedence chart, writing the appropriate rules as below
//   is usually very straightfoward

// the mother of all expressions
expression
    :   assignmentExpression
        {#expression = #(#[EXPR,"EXPR"],#expression);}
    ;


// This is a list of expressions.
expressionList
    :   expression (COMMA! expression)*
        {#expressionList = #(#[ELIST,"ELIST"], expressionList);}
    ;

// assignment expression (level 13)
assignmentExpression
    :   conditionalExpression
        (   (   ASSIGN^
            |   PLUS_ASSIGN^
            |   MINUS_ASSIGN^
            |   STAR_ASSIGN^
            |   DIV_ASSIGN^
            |   MOD_ASSIGN^
//            |   SR_ASSIGN^
//            |   BSR_ASSIGN^
//            |   SL_ASSIGN^
//            |   BAND_ASSIGN^
//            |   BXOR_ASSIGN^
//            |   BOR_ASSIGN^
            )
            assignmentExpression
        )?
    ;


// conditional test (level 12)
conditionalExpression
    :   logicalOrExpression
    ;


// logical or (||)  (level 11)
logicalOrExpression
    :   logicalAndExpression (LOR^ logicalAndExpression)*
    ;


// logical and (&&)  (level 10)
logicalAndExpression
    :   equalityExpression (LAND^ equalityExpression)*
    ;


// equality/inequality (==/!=) (level 6)
equalityExpression
    :   relationalExpression ((NOT_EQUAL^ | EQUAL^) relationalExpression)*
    ;


// boolean relational expressions (level 5)
relationalExpression
    :   additiveExpression
        (   (   (   LT^
                |   GT^
                |   LE^
                |   GE^
                )
                additiveExpression
            )*
		|	"instanceof"^ classTypeSpec
        )
    ;


// binary addition/subtraction (level 3)
additiveExpression
    :   multiplicativeExpression ((PLUS^ | MINUS^) multiplicativeExpression)*
    ;


// multiplication/division/modulo (level 2)
multiplicativeExpression
    :   unaryExpression ((STAR^ | DIV^ | MOD^ ) unaryExpression)*
    ;

unaryExpression
    :   INC^ unaryExpression
    |   DEC^ unaryExpression
    |   MINUS^ {#MINUS.setType(UNARY_MINUS);} unaryExpression
    |   PLUS^  {#PLUS.setType(UNARY_PLUS);} unaryExpression
    |   unaryExpressionNotPlusMinus
    ;

unaryExpressionNotPlusMinus
//    :   BNOT^ unaryExpression
    :   LNOT^ unaryExpression
    |   postfixExpression
    ;

// qualified names, array expressions, method invocation, post inc/dec
postfixExpression
    :
        primaryExpression
        (
            DOT^ IDENT
            (   lp:LPAREN^ {#lp.setType(METHOD_CALL);}
                argList
                RPAREN!
            )?
        )*

		(   // possibly add on a post-increment or post-decrement.
            // allows INC/DEC on too much, but semantics can check
			in:INC^ {#in.setType(POST_INC);}
	 	|	de:DEC^ {#de.setType(POST_DEC);}
		)?
    ;

// the basic element of an expression
primaryExpression
    :   identPrimary ( options {greedy=true;} : DOT^ "class" )?
    |   constant
    |   "true"
    |   "false"
    |   "null"
    |   newExpression
    |   LPAREN! assignmentExpression RPAREN!
    ;

/** Match a, a.b.c refs, a.b.c(...) refs, a.b.c[], a.b.c[].class,
 *  and a.b.c.class refs.  Also this(...) and super(...).  Match
 *  this or super.
 */
identPrimary
    :   IDENT
        (
            options {
                // .ident could match here or in postfixExpression.
                // We do want to match here.  Turn off warning.
                greedy=true;
            }
        :   DOT^ IDENT
        )*
        (
            options {
                // ARRAY_DECLARATOR here conflicts with INDEX_OP in
                // postfixExpression on LBRACK RBRACK.
                // We want to match [] here, so greedy.  This overcomes
                // limitation of linear approximate lookahead.
                greedy=true;
            }
        :   ( lp:LPAREN^ {#lp.setType(METHOD_CALL);} argList RPAREN! )
        )?
    ;

newExpression
	:	"new"^ type
		( LPAREN! argList RPAREN! )
	;

argList
    :   (   expressionList
        |   /*nothing*/
            {#argList = #[ELIST,"ELIST"];}
        )
    ;

constant
    :   NUM_INT
    |   CHAR_LITERAL
    |   STRING_LITERAL
    |   NUM_FLOAT
    |   NUM_LONG
    |   NUM_DOUBLE
    ;


/**
 * Java Statement Scanner.
 * Parses the statement into tokens for processing by the Recognizer.
 *
 * NOTE: This Java source (StatementLexer.java) should not be modified
 *       directly. It is generate from statement.g using the following
 *       command:
 *       java antlr.Tool statement.g
 *
 * Author: Will Tan (Adapted from ANTLR's java.g)
 */
class StatementLexer extends Lexer;

options {
    exportVocab=Statement; // call the vocabulary "Statement"
    testLiterals=false;    // don't automatically test for literals
    k=4;                   // four characters of lookahead
    charVocabulary='\u0003'..'\u7FFF';
    // without inlining some bitset tests, couldn't do unicode;
    // I need to make ANTLR generate smaller bitsets; see
    // bottom of JavaLexer.java
    codeGenBitsetTestThreshold=20;
}



// OPERATORS
LPAREN          :   '('     ;
RPAREN          :   ')'     ;
LCURLY          :   '{'     ;
RCURLY          :   '}'     ;
COLON			:	':'		;
COMMA           :   ','     ;
ASSIGN          :   '='     ;
EQUAL           :   "=="    ;
LNOT            :   '!'     ;
//BNOT            :   '~'     ;
NOT_EQUAL       :   "!="    ;
DIV             :   '/'     ;
DIV_ASSIGN      :   "/="    ;
PLUS            :   '+'     ;
PLUS_ASSIGN     :   "+="    ;
INC             :   "++"    ;
MINUS           :   '-'     ;
MINUS_ASSIGN    :   "-="    ;
DEC             :   "--"    ;
STAR            :   '*'     ;
STAR_ASSIGN     :   "*="    ;
MOD             :   '%'     ;
MOD_ASSIGN      :   "%="    ;
//SR_ASSIGN       :   ">>="   ;
//BSR_ASSIGN      :   ">>>="  ;
GE              :   ">="    ;
GT              :   ">"     ;
//SL              :   "<<"    ;
//SL_ASSIGN       :   "<<="   ;
LE              :   "<="    ;
LT              :   '<'     ;
//BXOR_ASSIGN     :   "^="    ;
//BOR_ASSIGN      :   "|="    ;
LOR             :   "||"    ;
//BAND_ASSIGN     :   "&="    ;
LAND            :   "&&"    ;
SEMI            :   ';'     ;


// Whitespace -- ignored
WS  :   (   ' '
        |   '\t'
        |   '\f'
            // handle newlines
        |   (   options {generateAmbigWarnings=false;}
            :   "\r\n"  // Evil DOS
            |   '\r'    // Macintosh
            |   '\n'    // Unix (the right way)
            )
            { newline(); }
        )+
        { _ttype = Token.SKIP; }
    ;

// Single-line comments
SL_COMMENT
    :   "//"
        (~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
        {$setType(Token.SKIP); newline();}
    ;

// multiple-line comments
ML_COMMENT
    :   "/*"
        (   /*  '\r' '\n' can be matched in one alternative or by matching
                '\r' in one iteration and '\n' in another.  I am trying to
                handle any flavor of newline that comes in, but the language
                that allows both "\r\n" and "\r" and "\n" to all be valid
                newline is ambiguous.  Consequently, the resulting grammar
                must be ambiguous.  I'm shutting this warning off.
             */
            options {
                generateAmbigWarnings=false;
            }
        :
            { LA(2)!='/' }? '*'
        |   '\r' '\n'       {newline();}
        |   '\r'            {newline();}
        |   '\n'            {newline();}
        |   ~('*'|'\n'|'\r')
        )*
        "*/"
        {$setType(Token.SKIP);}
    ;


// character literals
CHAR_LITERAL
    :   '\'' ( ESC | ~('\''|'\n'|'\r'|'\\') ) '\''
    ;

// string literals
STRING_LITERAL
    :   '"' (ESC|~('"'|'\\'|'\n'|'\r'))* '"'
    ;


// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'9' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
protected
ESC
    :   '\\'
        (   'n'
        |   'r'
        |   't'
        |   'b'
        |   'f'
        |   '"'
        |   '\''
        |   '\\'
        |   ('u')+ HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
        |   '0'..'3'
            (
                options {
                    warnWhenFollowAmbig = false;
                }
            :   '0'..'7'
                (
                    options {
                        warnWhenFollowAmbig = false;
                    }
                :   '0'..'7'
                )?
            )?
        |   '4'..'7'
            (
                options {
                    warnWhenFollowAmbig = false;
                }
            :   '0'..'7'
            )?
        )
    ;


// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
    :   ('0'..'9'|'A'..'F'|'a'..'f')
    ;


// a dummy rule to force vocabulary to be all characters (except special
//   ones that ANTLR uses internally (0 to 2)
protected
VOCAB
    :   '\3'..'\377'
    ;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
    options {testLiterals=true;}
    :   ('a'..'z'|'A'..'Z'|'_'|'$') ('a'..'z'|'A'..'Z'|'_'|'0'..'9'|'$')*
    ;


// a numeric literal
NUM_INT
    {boolean isDecimal=false; Token t=null;}
    :   '.' {_ttype = DOT;}
            (   ('0'..'9')+ (EXPONENT)? (f1:FLOAT_SUFFIX {t=f1;})?
                {
                if (t != null && t.getText().toUpperCase().indexOf('F')>=0) {
                    _ttype = NUM_FLOAT;
                }
                else {
                    _ttype = NUM_DOUBLE; // assume double
                }
                }
            )?

    |   (   '0' {isDecimal = true;} // special case for just '0'
            (   ('x'|'X')
                (                                           // hex
                    // the 'e'|'E' and float suffix stuff look
                    // like hex digits, hence the (...)+ doesn't
                    // know when to stop: ambig.  ANTLR resolves
                    // it correctly by matching immediately.  It
                    // is therefor ok to hush warning.
                    options {
                        warnWhenFollowAmbig=false;
                    }
                :   HEX_DIGIT
                )+

            |   //float or double with leading zero
                (('0'..'9')+ ('.'|EXPONENT|FLOAT_SUFFIX)) => ('0'..'9')+

            |   ('0'..'7')+                                 // octal
            )?
        |   ('1'..'9') ('0'..'9')*  {isDecimal=true;}       // non-zero decimal
        )
        (   ('l'|'L') { _ttype = NUM_LONG; }

        // only check to see if it's a float if looks like decimal so far
        |   {isDecimal}?
            (   '.' ('0'..'9')* (EXPONENT)? (f2:FLOAT_SUFFIX {t=f2;})?
            |   EXPONENT (f3:FLOAT_SUFFIX {t=f3;})?
            |   f4:FLOAT_SUFFIX {t=f4;}
            )
            {
            if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0) {
                _ttype = NUM_FLOAT;
            }
            else {
                _ttype = NUM_DOUBLE; // assume double
            }
            }
        )?
    ;


// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
    :   ('e'|'E') ('+'|'-')? ('0'..'9')+
    ;


protected
FLOAT_SUFFIX
    :   'f'|'F'|'d'|'D'
    ;
