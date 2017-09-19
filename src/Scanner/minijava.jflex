/*
 * JFlex specification for the lexical analyzer for a simple demo language
 * Change this into the scanner for your implementation of MiniJava.
 */


package Scanner;

import java_cup.runtime.*;
import Parser.sym;
import Throwables.*;

%%

%public
%final
%class scanner
%yylexthrow CompilerException
%unicode
%cup
%line
%column

%{
  // note that these Symbol constructors are abusing the Symbol
  // interface to use Symbol's left and right fields as line and column
  // fields instead
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

  // print out a symbol (aka token) nicely
  public String symbolToString(Symbol s) {
    switch (s.sym) {
      case sym.BECOMES: return "BECOMES";
      case sym.SEMICOLON: return "SEMICOLON";
      case sym.PLUS: return "PLUS";
      case sym.LPAREN: return "LPAREN";
      case sym.RPAREN: return "RPAREN";
      case sym.RETURN: return "RETURN";
      case sym.IDENTIFIER: return "ID(" + (String)s.value + ")";
      case sym.EOF: return "<EOF>";
      case sym.error: return "<ERROR>";
      case sym.LBRACE: return "LBRACE";
      case sym.RBRACE: return "RBRACE";
      case sym.LBRACK: return "LBRACK";
      case sym.RBRACK: return "RBRACK";
      case sym.CLASS: return "CLASS";
      case sym.PUBLIC: return "PUBLIC";
      case sym.STATIC: return "STATIC";
      case sym.VOID: return "VOID";
      case sym.MAIN: return "MAIN";
      case sym.STRING: return "STRING";
      case sym.INT: return "INT(" + s.value + ")";
      case sym.BOOLEAN: return "BOOLEAN";
      case sym.INTEGER: return "INTEGER";
      case sym.IF: return "IF";
      case sym.ELSE: return "ELSE";
      case sym.SYSOUT: return "SYSOUT";
      case sym.AND: return "AND";
      case sym.LESS: return "LESS";
      case sym.MINUS: return "MINUS";
      case sym.TIMES: return "TIMES";
      case sym.POINT: return "POINT";
      case sym.LENGTH: return "LENGTH";
      case sym.COMMA: return "COMMA";
      case sym.TRUE: return "TRUE";
      case sym.FALSE: return "FALSE";
      case sym.THIS: return "THIS";
      case sym.NEW: return "NEW";
      case sym.NOT: return "NOT";
      case sym.EXTENDS: return "EXTENDS";
      default: return "<UNEXPECTED TOKEN " + s.toString() + ">";
    }
  }
%}

/* Helper definitions */
letter = [a-zA-Z]
digit = [0-9]
eol = [\r\n]
white = {eol}|[ \t]

%%

/* Token definitions */
{digit}({digit})* { return symbol(sym.INT, Integer.parseInt(yytext()) ); }

/* reserved words */
/* (put here so that reserved words take precedence over identifiers) */
"return" { return symbol(sym.RETURN); }
"class" { return symbol(sym.CLASS); }
"public" { return symbol(sym.PUBLIC); }
"static" { return symbol(sym.STATIC); }
"void" { return symbol(sym.VOID); }
"main" { return symbol(sym.MAIN); }
"String" { return symbol(sym.STRING); }
"int" { return symbol(sym.INTEGER); }
"boolean" { return symbol(sym.BOOLEAN); }
"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }
"while" { return symbol(sym.WHILE); }
"System.out.println" { return symbol(sym.SYSOUT); }
"length" { return symbol(sym.LENGTH); }
"true" { return symbol(sym.TRUE); }
"false" { return symbol(sym.FALSE); }
"this" { return symbol(sym.THIS); }
"new" { return symbol(sym.NEW); }
"extends" { return symbol(sym.EXTENDS); }


/* operators */
"+" { return symbol(sym.PLUS); }
"=" { return symbol(sym.BECOMES); }
"&&" { return symbol(sym.AND); }
"<" { return symbol(sym.LESS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"." { return symbol(sym.POINT); }
"!" { return symbol(sym.NOT); }

/* delimiters */
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
";" { return symbol(sym.SEMICOLON); }
"{" { return symbol(sym.LBRACE); }
"}" { return symbol(sym.RBRACE); }
"[" { return symbol(sym.LBRACK); }
"]" { return symbol(sym.RBRACK); }
"," { return symbol(sym.COMMA); }

/* identifiers */
{letter} ({letter}|{digit}|_)* { return symbol(sym.IDENTIFIER, yytext()); }


/* whitespace */
{white}+ { /* ignore whitespace */ }

/* lexical errors (put last so other matches take precedence) */
. { throw new LexicalCompilerException(
	"unexpected character in input: '" + yytext() + "'", 
	yyline+1, yycolumn+1);
  }
