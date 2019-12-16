// $ANTLR 2.7.3: "statement.tree.g" -> "StatementTreeParser.java"$

package com.github.wtan.jel;

import java.util.List;
import java.util.ArrayList;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


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
public class StatementTreeParser extends antlr.TreeParser       implements StatementTreeParserTokenTypes
 {

    private static final String EXCEPTION_MSG = "Error parsing statement (null expression).";
public StatementTreeParser() {
	tokenNames = _tokenNames;
}

	public final Expression  compilationUnit(AST _t) throws RecognitionException {
		Expression r=null;
		
		AST compilationUnit_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			r=statement(_t);
			_t = _retTree;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	public final Expression  statement(AST _t) throws RecognitionException {
		Expression r=null;
		
		AST statement_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		Expression a=null, b=null, c=null; 
		List e1 = null, e2 = null;
			
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EXPR:
			{
				a=expression(_t);
				_t = _retTree;
				
				r = a;
				
				break;
			}
			case LITERAL_if:
			{
				AST __t7 = _t;
				AST tmp1_AST_in = (AST)_t;
				match(_t,LITERAL_if);
				_t = _t.getFirstChild();
				a=expression(_t);
				_t = _retTree;
				b=statement(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SLIST:
				case EXPR:
				case LITERAL_if:
				case LITERAL_for:
				case LITERAL_while:
				case LITERAL_do:
				case LITERAL_break:
				case LITERAL_continue:
				case LITERAL_return:
				case LITERAL_switch:
				case EMPTY_STAT:
				{
					c=statement(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t7;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new If(a, b, c);
				
				break;
			}
			case LITERAL_for:
			{
				AST __t9 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,LITERAL_for);
				_t = _t.getFirstChild();
				AST __t10 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,FOR_INIT);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ELIST:
				{
					e1=elist(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t10;
				_t = _t.getNextSibling();
				AST __t12 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,FOR_CONDITION);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case EXPR:
				{
					a=expression(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t12;
				_t = _t.getNextSibling();
				AST __t14 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,FOR_ITERATOR);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ELIST:
				{
					e2=elist(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t14;
				_t = _t.getNextSibling();
				b=statement(_t);
				_t = _retTree;
				_t = __t9;
				_t = _t.getNextSibling();
				
								if (a==null || b==null) {
									throw new RecognitionException(EXCEPTION_MSG);
								}
								r = new For(e1, a, e2, b);
							
				break;
			}
			case LITERAL_while:
			{
				AST __t16 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,LITERAL_while);
				_t = _t.getFirstChild();
				a=expression(_t);
				_t = _retTree;
				b=statement(_t);
				_t = _retTree;
				_t = __t16;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new While(a, b);
						
				break;
			}
			case LITERAL_do:
			{
				AST __t17 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,LITERAL_do);
				_t = _t.getFirstChild();
				a=statement(_t);
				_t = _retTree;
				b=expression(_t);
				_t = _retTree;
				_t = __t17;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
							r = new Do(a, b);
						
				break;
			}
			case LITERAL_break:
			{
				AST __t18 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,LITERAL_break);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case IDENT:
				{
					AST tmp9_AST_in = (AST)_t;
					match(_t,IDENT);
					_t = _t.getNextSibling();
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t18;
				_t = _t.getNextSibling();
				
							r = new Break();
						
				break;
			}
			case LITERAL_continue:
			{
				AST __t20 = _t;
				AST tmp10_AST_in = (AST)_t;
				match(_t,LITERAL_continue);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case IDENT:
				{
					AST tmp11_AST_in = (AST)_t;
					match(_t,IDENT);
					_t = _t.getNextSibling();
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t20;
				_t = _t.getNextSibling();
				
							r = new Continue();
						
				break;
			}
			case LITERAL_return:
			{
				AST __t22 = _t;
				AST tmp12_AST_in = (AST)_t;
				match(_t,LITERAL_return);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case EXPR:
				{
					a=expression(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t22;
				_t = _t.getNextSibling();
				
				r = new Return(a);
				
				break;
			}
			case LITERAL_switch:
			{
				AST __t24 = _t;
				AST tmp13_AST_in = (AST)_t;
				match(_t,LITERAL_switch);
				_t = _t.getFirstChild();
				a=expression(_t);
				_t = _retTree;
				Switch sw = new Switch(a);
				{
				_loop26:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==CASE_GROUP)) {
						c=caseGroup(_t);
						_t = _retTree;
						sw.addCase(c);
					}
					else {
						break _loop26;
					}
					
				} while (true);
				}
				_t = __t24;
				_t = _t.getNextSibling();
				
				r = sw;
						
				break;
			}
			case SLIST:
			{
				a=slist(_t);
				_t = _retTree;
				
				r = a;
				
				break;
			}
			case EMPTY_STAT:
			{
				AST tmp14_AST_in = (AST)_t;
				match(_t,EMPTY_STAT);
				_t = _t.getNextSibling();
				
				r = null;
				
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	public final StatementList  slist(AST _t) throws RecognitionException {
		StatementList r = null;
		
		AST slist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		Expression a=null;
		r = new StatementList();
		
		
		try {      // for error handling
			AST __t3 = _t;
			AST tmp15_AST_in = (AST)_t;
			match(_t,SLIST);
			_t = _t.getFirstChild();
			{
			_loop5:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					a=statement(_t);
					_t = _retTree;
					r.add(a);
				}
				else {
					break _loop5;
				}
				
			} while (true);
			}
			_t = __t3;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	public final Expression  expression(AST _t) throws RecognitionException {
		Expression r=null;
		
		AST expression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t37 = _t;
			AST tmp16_AST_in = (AST)_t;
			match(_t,EXPR);
			_t = _t.getFirstChild();
			r=expr(_t);
			_t = _retTree;
			_t = __t37;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	public final List  elist(AST _t) throws RecognitionException {
		List r = null;
		
		AST elist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		Expression a=null;
		r = new ArrayList();
		
		
		try {      // for error handling
			AST __t33 = _t;
			AST tmp17_AST_in = (AST)_t;
			match(_t,ELIST);
			_t = _t.getFirstChild();
			{
			_loop35:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==EXPR)) {
					a=expression(_t);
					_t = _retTree;
					r.add(a);
				}
				else {
					break _loop35;
				}
				
			} while (true);
			}
			_t = __t33;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	public final Expression  caseGroup(AST _t) throws RecognitionException {
		Expression r=null;
		
		AST caseGroup_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		Expression a=null, b=null;
			
		
		try {      // for error handling
			AST __t28 = _t;
			AST tmp18_AST_in = (AST)_t;
			match(_t,CASE_GROUP);
			_t = _t.getFirstChild();
			{
			int _cnt31=0;
			_loop31:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_case:
				{
					AST __t30 = _t;
					AST tmp19_AST_in = (AST)_t;
					match(_t,LITERAL_case);
					_t = _t.getFirstChild();
					a=expression(_t);
					_t = _retTree;
					_t = __t30;
					_t = _t.getNextSibling();
					break;
				}
				case LITERAL_default:
				{
					AST tmp20_AST_in = (AST)_t;
					match(_t,LITERAL_default);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					if ( _cnt31>=1 ) { break _loop31; } else {throw new NoViableAltException(_t);}
				}
				}
				_cnt31++;
			} while (true);
			}
			b=slist(_t);
			_t = _retTree;
			_t = __t28;
			_t = _t.getNextSibling();
			
						if (a != null) {
							r = new Case(a, b);
						}
						else {
							r = new Case(b);
						}
					
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	public final Expression  expr(AST _t) throws RecognitionException {
		Expression r = null;
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		Expression a = null, b = null, c = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case QUESTION:
			{
				AST __t39 = _t;
				AST tmp21_AST_in = (AST)_t;
				match(_t,QUESTION);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				c=expr(_t);
				_t = _retTree;
				_t = __t39;
				_t = _t.getNextSibling();
				
				if (a==null || b==null || c==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Ternary(a, b, c);
				
				break;
			}
			case ASSIGN:
			{
				AST __t40 = _t;
				AST tmp22_AST_in = (AST)_t;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t40;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Assign(a, b);
				
				break;
			}
			case PLUS_ASSIGN:
			{
				AST __t41 = _t;
				AST tmp23_AST_in = (AST)_t;
				match(_t,PLUS_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t41;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new PlusAssign(a, b);
				
				break;
			}
			case MINUS_ASSIGN:
			{
				AST __t42 = _t;
				AST tmp24_AST_in = (AST)_t;
				match(_t,MINUS_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t42;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new MinusAssign(a, b);
				
				break;
			}
			case STAR_ASSIGN:
			{
				AST __t43 = _t;
				AST tmp25_AST_in = (AST)_t;
				match(_t,STAR_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t43;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new MultiplyAssign(a, b);
				
				break;
			}
			case DIV_ASSIGN:
			{
				AST __t44 = _t;
				AST tmp26_AST_in = (AST)_t;
				match(_t,DIV_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t44;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new DivideAssign(a, b);
				
				break;
			}
			case MOD_ASSIGN:
			{
				AST __t45 = _t;
				AST tmp27_AST_in = (AST)_t;
				match(_t,MOD_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t45;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new ModulusAssign(a, b);
				
				break;
			}
			case SR_ASSIGN:
			{
				AST __t46 = _t;
				AST tmp28_AST_in = (AST)_t;
				match(_t,SR_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t46;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseRightShiftAssign(a, b);
				
				break;
			}
			case BSR_ASSIGN:
			{
				AST __t47 = _t;
				AST tmp29_AST_in = (AST)_t;
				match(_t,BSR_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t47;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseUnsignedRightShiftAssign(a, b);
				
				break;
			}
			case SL_ASSIGN:
			{
				AST __t48 = _t;
				AST tmp30_AST_in = (AST)_t;
				match(_t,SL_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t48;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseLeftShiftAssign(a, b);
				
				break;
			}
			case BAND_ASSIGN:
			{
				AST __t49 = _t;
				AST tmp31_AST_in = (AST)_t;
				match(_t,BAND_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t49;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseAndAssign(a, b);
				
				break;
			}
			case BXOR_ASSIGN:
			{
				AST __t50 = _t;
				AST tmp32_AST_in = (AST)_t;
				match(_t,BXOR_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t50;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseXorAssign(a, b);
				
				break;
			}
			case BOR_ASSIGN:
			{
				AST __t51 = _t;
				AST tmp33_AST_in = (AST)_t;
				match(_t,BOR_ASSIGN);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t51;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseOrAssign(a, b);
				
				break;
			}
			case LOR:
			{
				AST __t52 = _t;
				AST tmp34_AST_in = (AST)_t;
				match(_t,LOR);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t52;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Or(a, b);
				
				break;
			}
			case LAND:
			{
				AST __t53 = _t;
				AST tmp35_AST_in = (AST)_t;
				match(_t,LAND);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t53;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new And(a, b);
				
				break;
			}
			case BOR:
			{
				AST __t54 = _t;
				AST tmp36_AST_in = (AST)_t;
				match(_t,BOR);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t54;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseOr(a, b);
				
				break;
			}
			case BXOR:
			{
				AST __t55 = _t;
				AST tmp37_AST_in = (AST)_t;
				match(_t,BXOR);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t55;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseXor(a, b);
				
				break;
			}
			case BAND:
			{
				AST __t56 = _t;
				AST tmp38_AST_in = (AST)_t;
				match(_t,BAND);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t56;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseAnd(a, b);
				
				break;
			}
			case NOT_EQUAL:
			{
				AST __t57 = _t;
				AST tmp39_AST_in = (AST)_t;
				match(_t,NOT_EQUAL);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t57;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new NotEqual(a, b);
				
				break;
			}
			case EQUAL:
			{
				AST __t58 = _t;
				AST tmp40_AST_in = (AST)_t;
				match(_t,EQUAL);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t58;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new EqualEqual(a, b);
				
				break;
			}
			case LT:
			{
				AST __t59 = _t;
				AST tmp41_AST_in = (AST)_t;
				match(_t,LT);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t59;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new LessThan(a, b);
				
				break;
			}
			case GT:
			{
				AST __t60 = _t;
				AST tmp42_AST_in = (AST)_t;
				match(_t,GT);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t60;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new GreaterThan(a, b);
				
				break;
			}
			case LE:
			{
				AST __t61 = _t;
				AST tmp43_AST_in = (AST)_t;
				match(_t,LE);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t61;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new LessThanEqual(a, b);
				
				break;
			}
			case GE:
			{
				AST __t62 = _t;
				AST tmp44_AST_in = (AST)_t;
				match(_t,GE);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t62;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new GreaterThanEqual(a, b);
				
				break;
			}
			case SL:
			{
				AST __t63 = _t;
				AST tmp45_AST_in = (AST)_t;
				match(_t,SL);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t63;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseLeftShift(a, b);
				
				break;
			}
			case SR:
			{
				AST __t64 = _t;
				AST tmp46_AST_in = (AST)_t;
				match(_t,SR);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t64;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseRightShift(a, b);
				
				break;
			}
			case BSR:
			{
				AST __t65 = _t;
				AST tmp47_AST_in = (AST)_t;
				match(_t,BSR);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t65;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseUnsignedRightShift(a, b);
				
				break;
			}
			case PLUS:
			{
				AST __t66 = _t;
				AST tmp48_AST_in = (AST)_t;
				match(_t,PLUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t66;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Plus(a, b);
				
				break;
			}
			case MINUS:
			{
				AST __t67 = _t;
				AST tmp49_AST_in = (AST)_t;
				match(_t,MINUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t67;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Minus(a, b);
				
				break;
			}
			case DIV:
			{
				AST __t68 = _t;
				AST tmp50_AST_in = (AST)_t;
				match(_t,DIV);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t68;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Divide(a, b);
				
				break;
			}
			case MOD:
			{
				AST __t69 = _t;
				AST tmp51_AST_in = (AST)_t;
				match(_t,MOD);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t69;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Modulus(a, b);
				
				break;
			}
			case STAR:
			{
				AST __t70 = _t;
				AST tmp52_AST_in = (AST)_t;
				match(_t,STAR);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t70;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Multiply(a, b);
				
				break;
			}
			case INC:
			{
				AST __t71 = _t;
				AST tmp53_AST_in = (AST)_t;
				match(_t,INC);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t71;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Inc(a);
				
				break;
			}
			case DEC:
			{
				AST __t72 = _t;
				AST tmp54_AST_in = (AST)_t;
				match(_t,DEC);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t72;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Dec(a);
				
				break;
			}
			case POST_INC:
			{
				AST __t73 = _t;
				AST tmp55_AST_in = (AST)_t;
				match(_t,POST_INC);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t73;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new PostInc(a);
				
				break;
			}
			case POST_DEC:
			{
				AST __t74 = _t;
				AST tmp56_AST_in = (AST)_t;
				match(_t,POST_DEC);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t74;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new PostDec(a);
				
				break;
			}
			case BNOT:
			{
				AST __t75 = _t;
				AST tmp57_AST_in = (AST)_t;
				match(_t,BNOT);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t75;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new BitwiseNot(a);
				
				break;
			}
			case LNOT:
			{
				AST __t76 = _t;
				AST tmp58_AST_in = (AST)_t;
				match(_t,LNOT);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t76;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Not(a);
				
				break;
			}
			case LITERAL_instanceof:
			{
				AST __t77 = _t;
				AST tmp59_AST_in = (AST)_t;
				match(_t,LITERAL_instanceof);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t77;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new InstanceOf(a, b);
						
				break;
			}
			case UNARY_MINUS:
			{
				AST __t78 = _t;
				AST tmp60_AST_in = (AST)_t;
				match(_t,UNARY_MINUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t78;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new UnaryMinus(a);
				
				break;
			}
			case UNARY_PLUS:
			{
				AST __t79 = _t;
				AST tmp61_AST_in = (AST)_t;
				match(_t,UNARY_PLUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t79;
				_t = _t.getNextSibling();
				
				if (a==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new UnaryPlus(a);
				
				break;
			}
			case METHOD_CALL:
			case IDENT:
			case DOT:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case LITERAL_new:
			case NUM_INT:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case NUM_FLOAT:
			case NUM_LONG:
			case NUM_DOUBLE:
			{
				r=primaryExpression(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	public final Expression  primaryExpression(AST _t) throws RecognitionException {
		Expression r = null;
		
		AST primaryExpression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		
		Expression a=null, b=null;
		List el = null;
		
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				i = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				
				r = new IdentValue(i.getText());
				
				break;
			}
			case DOT:
			{
				AST __t81 = _t;
				AST tmp62_AST_in = (AST)_t;
				match(_t,DOT);
				_t = _t.getFirstChild();
				{
				a=expr(_t);
				_t = _retTree;
				{
				b=primaryExpression(_t);
				_t = _retTree;
				}
				}
				_t = __t81;
				_t = _t.getNextSibling();
				
				if (a==null || b==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new Dot(a, b);
				
				break;
			}
			case METHOD_CALL:
			{
				AST __t84 = _t;
				AST tmp63_AST_in = (AST)_t;
				match(_t,METHOD_CALL);
				_t = _t.getFirstChild();
				a=primaryExpression(_t);
				_t = _retTree;
				el=elist(_t);
				_t = _retTree;
				_t = __t84;
				_t = _t.getNextSibling();
				
				if (a==null || el==null) {
				throw new RecognitionException(EXCEPTION_MSG);
				}
				r = new MethodCall(a, el);
				
				break;
			}
			case LITERAL_new:
			{
				r=newExpression(_t);
				_t = _retTree;
				break;
			}
			case NUM_INT:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case NUM_FLOAT:
			case NUM_LONG:
			case NUM_DOUBLE:
			{
				r=constant(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_true:
			{
				AST tmp64_AST_in = (AST)_t;
				match(_t,LITERAL_true);
				_t = _t.getNextSibling();
				
				r = new BooleanValue(true);
				
				break;
			}
			case LITERAL_false:
			{
				AST tmp65_AST_in = (AST)_t;
				match(_t,LITERAL_false);
				_t = _t.getNextSibling();
				
				r = new BooleanValue(false);
				
				break;
			}
			case LITERAL_null:
			{
				AST tmp66_AST_in = (AST)_t;
				match(_t,LITERAL_null);
				_t = _t.getNextSibling();
				
				r = new ConstantValue(null);
				
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	public final Expression  newExpression(AST _t) throws RecognitionException {
		Expression r = null;
		
		AST newExpression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		Expression a = null;
		List e1 = null;
		
		
		try {      // for error handling
			AST __t86 = _t;
			AST tmp67_AST_in = (AST)_t;
			match(_t,LITERAL_new);
			_t = _t.getFirstChild();
			a=primaryExpression(_t);
			_t = _retTree;
			e1=elist(_t);
			_t = _retTree;
			_t = __t86;
			_t = _t.getNextSibling();
			
			r = new New(a, e1);
					
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
/**
 * Returns a constant value as a primitive wrapper object.
 */
	public final Expression  constant(AST _t) throws RecognitionException {
		Expression r=null;
		
		AST constant_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST j = null;
		AST k = null;
		AST l = null;
		AST m = null;
		AST n = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NUM_INT:
			{
				i = (AST)_t;
				match(_t,NUM_INT);
				_t = _t.getNextSibling();
				
				r = new ConstantValue(new Integer(Integer.decode(i.getText())));
				
				break;
			}
			case CHAR_LITERAL:
			{
				j = (AST)_t;
				match(_t,CHAR_LITERAL);
				_t = _t.getNextSibling();
				
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
				
				break;
			}
			case STRING_LITERAL:
			{
				k = (AST)_t;
				match(_t,STRING_LITERAL);
				_t = _t.getNextSibling();
				
				String s = k.getText();
				// Remove beginning and ending double quote
				if (s.startsWith("\"")) {
				s = s.substring(1);
				}
				if (s.endsWith("\"")) {
				s = s.substring(0, s.length()-1);
				}
				r = new ConstantValue(s);
				
				break;
			}
			case NUM_FLOAT:
			{
				l = (AST)_t;
				match(_t,NUM_FLOAT);
				_t = _t.getNextSibling();
				
				r = new ConstantValue(new Float(ConstantValue.dropSuffix(l.getText())));
				
				break;
			}
			case NUM_DOUBLE:
			{
				m = (AST)_t;
				match(_t,NUM_DOUBLE);
				_t = _t.getNextSibling();
				
				r = new ConstantValue(new Double(ConstantValue.dropSuffix(m.getText())));
				
				break;
			}
			case NUM_LONG:
			{
				n = (AST)_t;
				match(_t,NUM_LONG);
				_t = _t.getNextSibling();
				
				r = new ConstantValue(new Long(ConstantValue.dropSuffix(n.getText())));
				
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"BLOCK",
		"OBJBLOCK",
		"SLIST",
		"CTOR_DEF",
		"METHOD_DEF",
		"VARIABLE_DEF",
		"INSTANCE_INIT",
		"PARAMETERS",
		"PARAMETER_DEF",
		"LABELED_STAT",
		"INDEX_OP",
		"POST_INC",
		"POST_DEC",
		"METHOD_CALL",
		"EXPR",
		"UNARY_MINUS",
		"UNARY_PLUS",
		"CASE_GROUP",
		"ELIST",
		"FOR_INIT",
		"FOR_CONDITION",
		"FOR_ITERATOR",
		"SUPER_CTOR_CALL",
		"CTOR_CALL",
		"IDENT",
		"DOT",
		"LCURLY",
		"RCURLY",
		"SEMI",
		"\"if\"",
		"LPAREN",
		"RPAREN",
		"\"else\"",
		"\"for\"",
		"\"while\"",
		"\"do\"",
		"\"break\"",
		"\"continue\"",
		"\"return\"",
		"\"switch\"",
		"\"case\"",
		"\"default\"",
		"COLON",
		"COMMA",
		"ASSIGN",
		"PLUS_ASSIGN",
		"MINUS_ASSIGN",
		"STAR_ASSIGN",
		"DIV_ASSIGN",
		"MOD_ASSIGN",
		"SR_ASSIGN",
		"BSR_ASSIGN",
		"SL_ASSIGN",
		"BAND_ASSIGN",
		"BXOR_ASSIGN",
		"BOR_ASSIGN",
		"QUESTION",
		"LOR",
		"LAND",
		"BOR",
		"BXOR",
		"BAND",
		"NOT_EQUAL",
		"EQUAL",
		"LT",
		"GT",
		"LE",
		"GE",
		"\"instanceof\"",
		"SL",
		"SR",
		"BSR",
		"PLUS",
		"MINUS",
		"STAR",
		"DIV",
		"MOD",
		"INC",
		"DEC",
		"BNOT",
		"LNOT",
		"\"class\"",
		"\"true\"",
		"\"false\"",
		"\"null\"",
		"\"new\"",
		"NUM_INT",
		"CHAR_LITERAL",
		"STRING_LITERAL",
		"NUM_FLOAT",
		"NUM_LONG",
		"NUM_DOUBLE",
		"WS",
		"SL_COMMENT",
		"ML_COMMENT",
		"ESC",
		"HEX_DIGIT",
		"VOCAB",
		"EXPONENT",
		"FLOAT_SUFFIX",
		"EMPTY_STAT"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 17463337287744L, 1099511627776L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	}
	
