// $ANTLR 2.7.3: "statement.tree.g" -> "StatementTreeParser.java"$

package com.github.wtan.jel;

import java.util.List;
import java.util.ArrayList;

public interface StatementTreeParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int BLOCK = 4;
	int OBJBLOCK = 5;
	int SLIST = 6;
	int CTOR_DEF = 7;
	int METHOD_DEF = 8;
	int VARIABLE_DEF = 9;
	int INSTANCE_INIT = 10;
	int PARAMETERS = 11;
	int PARAMETER_DEF = 12;
	int LABELED_STAT = 13;
	int INDEX_OP = 14;
	int POST_INC = 15;
	int POST_DEC = 16;
	int METHOD_CALL = 17;
	int EXPR = 18;
	int UNARY_MINUS = 19;
	int UNARY_PLUS = 20;
	int CASE_GROUP = 21;
	int ELIST = 22;
	int FOR_INIT = 23;
	int FOR_CONDITION = 24;
	int FOR_ITERATOR = 25;
	int SUPER_CTOR_CALL = 26;
	int CTOR_CALL = 27;
	int IDENT = 28;
	int DOT = 29;
	int LCURLY = 30;
	int RCURLY = 31;
	int SEMI = 32;
	int LITERAL_if = 33;
	int LPAREN = 34;
	int RPAREN = 35;
	int LITERAL_else = 36;
	int LITERAL_for = 37;
	int LITERAL_while = 38;
	int LITERAL_do = 39;
	int LITERAL_break = 40;
	int LITERAL_continue = 41;
	int LITERAL_return = 42;
	int LITERAL_switch = 43;
	int LITERAL_case = 44;
	int LITERAL_default = 45;
	int COLON = 46;
	int COMMA = 47;
	int ASSIGN = 48;
	int PLUS_ASSIGN = 49;
	int MINUS_ASSIGN = 50;
	int STAR_ASSIGN = 51;
	int DIV_ASSIGN = 52;
	int MOD_ASSIGN = 53;
	int SR_ASSIGN = 54;
	int BSR_ASSIGN = 55;
	int SL_ASSIGN = 56;
	int BAND_ASSIGN = 57;
	int BXOR_ASSIGN = 58;
	int BOR_ASSIGN = 59;
	int QUESTION = 60;
	int LOR = 61;
	int LAND = 62;
	int BOR = 63;
	int BXOR = 64;
	int BAND = 65;
	int NOT_EQUAL = 66;
	int EQUAL = 67;
	int LT = 68;
	int GT = 69;
	int LE = 70;
	int GE = 71;
	int LITERAL_instanceof = 72;
	int SL = 73;
	int SR = 74;
	int BSR = 75;
	int PLUS = 76;
	int MINUS = 77;
	int STAR = 78;
	int DIV = 79;
	int MOD = 80;
	int INC = 81;
	int DEC = 82;
	int BNOT = 83;
	int LNOT = 84;
	int LITERAL_class = 85;
	int LITERAL_true = 86;
	int LITERAL_false = 87;
	int LITERAL_null = 88;
	int LITERAL_new = 89;
	int NUM_INT = 90;
	int CHAR_LITERAL = 91;
	int STRING_LITERAL = 92;
	int NUM_FLOAT = 93;
	int NUM_LONG = 94;
	int NUM_DOUBLE = 95;
	int WS = 96;
	int SL_COMMENT = 97;
	int ML_COMMENT = 98;
	int ESC = 99;
	int HEX_DIGIT = 100;
	int VOCAB = 101;
	int EXPONENT = 102;
	int FLOAT_SUFFIX = 103;
	int EMPTY_STAT = 104;
}
