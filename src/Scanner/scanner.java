/* The following code was generated by JFlex 1.4.3 on 29/11/17 21:04 */

/*
 * JFlex specification for the lexical analyzer for a simple demo language
 * Change this into the scanner for your implementation of MiniJava.
 */


package Scanner;

import java_cup.runtime.*;
import Parser.sym;
import Throwables.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 29/11/17 21:04 from the specification file
 * <tt>src/Scanner/minijava.jflex</tt>
 */
public final class scanner implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\3\2\0\1\3\22\0\1\3\1\42\4\0\1\36"+
    "\1\0\1\43\1\44\1\41\1\34\1\52\1\40\1\32\1\0\12\2"+
    "\1\0\1\45\1\37\1\35\3\0\22\1\1\24\7\1\1\50\1\0"+
    "\1\51\1\0\1\53\1\0\1\13\1\16\1\11\1\22\1\5\1\26"+
    "\1\25\1\30\1\17\2\1\1\12\1\23\1\10\1\21\1\15\1\1"+
    "\1\4\1\14\1\6\1\7\1\20\1\27\1\33\1\31\1\1\1\46"+
    "\1\0\1\47\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\17\2\1\5\1\6"+
    "\1\7\1\1\1\10\1\11\1\12\1\13\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\1\23\14\2\1\24"+
    "\6\2\1\25\5\2\1\26\5\2\1\27\7\2\1\30"+
    "\1\2\1\31\1\32\5\2\1\33\1\34\6\2\1\35"+
    "\6\2\1\36\1\37\1\40\1\2\1\41\1\42\1\43"+
    "\1\2\1\44\1\2\1\45\1\46\13\0\1\47";

  private static int [] zzUnpackAction() {
    int [] result = new int[123];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\54\0\130\0\204\0\260\0\334\0\u0108\0\u0134"+
    "\0\u0160\0\u018c\0\u01b8\0\u01e4\0\u0210\0\u023c\0\u0268\0\u0294"+
    "\0\u02c0\0\u02ec\0\u0318\0\u0344\0\54\0\54\0\54\0\u0370"+
    "\0\54\0\54\0\54\0\54\0\54\0\54\0\54\0\54"+
    "\0\54\0\54\0\54\0\54\0\u039c\0\u03c8\0\u03f4\0\u0420"+
    "\0\u044c\0\u0478\0\u04a4\0\u04d0\0\u04fc\0\u0528\0\u0554\0\u0580"+
    "\0\130\0\u05ac\0\u05d8\0\u0604\0\u0630\0\u065c\0\u0688\0\54"+
    "\0\u06b4\0\u06e0\0\u070c\0\u0738\0\u0764\0\130\0\u0790\0\u07bc"+
    "\0\u07e8\0\u0814\0\u0840\0\130\0\u086c\0\u0898\0\u08c4\0\u08f0"+
    "\0\u091c\0\u0948\0\u0974\0\130\0\u09a0\0\130\0\130\0\u09cc"+
    "\0\u09f8\0\u0a24\0\u0a50\0\u0a7c\0\130\0\130\0\u0aa8\0\u0ad4"+
    "\0\u0b00\0\u0b2c\0\u0b58\0\u0b84\0\130\0\u0bb0\0\u0bdc\0\u0c08"+
    "\0\u0c34\0\u0c60\0\u0c8c\0\130\0\130\0\130\0\u0cb8\0\130"+
    "\0\130\0\130\0\u0ce4\0\130\0\u0d10\0\130\0\130\0\u0d3c"+
    "\0\u0d68\0\u0d94\0\u0dc0\0\u0dec\0\u0e18\0\u0e44\0\u0e70\0\u0e9c"+
    "\0\u0ec8\0\u0ef4\0\54";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[123];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\3"+
    "\1\11\1\12\1\13\1\3\1\14\1\15\1\16\1\17"+
    "\1\20\2\3\1\21\1\22\1\3\1\23\1\24\2\3"+
    "\1\25\1\3\1\26\1\27\1\30\1\31\1\32\1\33"+
    "\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43"+
    "\1\44\1\2\55\0\2\3\1\0\26\3\1\0\1\3"+
    "\17\0\1\3\2\0\1\4\54\0\1\5\51\0\2\3"+
    "\1\0\1\3\1\45\24\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\6\3\1\46\17\3\1\0\1\47"+
    "\17\0\1\3\1\0\2\3\1\0\1\50\23\3\1\51"+
    "\1\3\1\0\1\3\17\0\1\3\1\0\2\3\1\0"+
    "\1\3\1\52\24\3\1\0\1\3\17\0\1\3\1\0"+
    "\2\3\1\0\6\3\1\53\17\3\1\0\1\3\17\0"+
    "\1\3\1\0\2\3\1\0\1\3\1\54\24\3\1\0"+
    "\1\3\17\0\1\3\1\0\2\3\1\0\2\3\1\55"+
    "\23\3\1\0\1\3\17\0\1\3\1\0\2\3\1\0"+
    "\3\3\1\56\22\3\1\0\1\3\17\0\1\3\1\0"+
    "\2\3\1\0\15\3\1\57\10\3\1\0\1\3\17\0"+
    "\1\3\1\0\2\3\1\0\4\3\1\60\15\3\1\61"+
    "\3\3\1\0\1\3\17\0\1\3\1\0\2\3\1\0"+
    "\15\3\1\62\10\3\1\0\1\3\17\0\1\3\1\0"+
    "\2\3\1\0\7\3\1\63\16\3\1\0\1\3\17\0"+
    "\1\3\1\0\2\3\1\0\2\3\1\64\22\3\1\65"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\7\3"+
    "\1\66\16\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\24\3\1\67\1\3\1\0\1\3\17\0\1\3"+
    "\36\0\1\70\16\0\2\3\1\0\2\3\1\71\23\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\10\3"+
    "\1\72\15\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\2\3\1\73\23\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\3\3\1\74\22\3\1\0\1\3"+
    "\17\0\1\3\1\0\2\3\1\0\13\3\1\75\12\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\23\3"+
    "\1\76\2\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\7\3\1\77\16\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\4\3\1\100\21\3\1\0\1\3"+
    "\17\0\1\3\1\0\2\3\1\0\7\3\1\101\16\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\12\3"+
    "\1\102\13\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\15\3\1\103\10\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\2\3\1\104\23\3\1\0\1\3"+
    "\17\0\1\3\1\0\2\3\1\0\13\3\1\105\12\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\13\3"+
    "\1\106\12\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\1\107\25\3\1\0\1\3\17\0\1\3\1\0"+
    "\2\3\1\0\10\3\1\110\15\3\1\0\1\3\17\0"+
    "\1\3\1\0\2\3\1\0\6\3\1\111\17\3\1\0"+
    "\1\3\17\0\1\3\1\0\2\3\1\0\13\3\1\112"+
    "\12\3\1\0\1\3\17\0\1\3\1\0\2\3\1\0"+
    "\3\3\1\113\22\3\1\0\1\3\17\0\1\3\1\0"+
    "\2\3\1\0\1\3\1\114\24\3\1\0\1\3\17\0"+
    "\1\3\1\0\2\3\1\0\1\3\1\115\24\3\1\0"+
    "\1\3\17\0\1\3\1\0\2\3\1\0\1\3\1\116"+
    "\24\3\1\0\1\3\17\0\1\3\1\0\2\3\1\0"+
    "\10\3\1\117\15\3\1\0\1\3\17\0\1\3\1\0"+
    "\2\3\1\0\10\3\1\120\15\3\1\0\1\3\17\0"+
    "\1\3\1\0\2\3\1\0\21\3\1\121\4\3\1\0"+
    "\1\3\17\0\1\3\1\0\2\3\1\0\2\3\1\122"+
    "\23\3\1\0\1\3\17\0\1\3\1\0\2\3\1\0"+
    "\6\3\1\123\17\3\1\0\1\3\17\0\1\3\1\0"+
    "\2\3\1\0\6\3\1\124\17\3\1\0\1\3\17\0"+
    "\1\3\1\0\2\3\1\0\16\3\1\125\7\3\1\0"+
    "\1\3\17\0\1\3\1\0\2\3\1\0\4\3\1\126"+
    "\21\3\1\0\1\3\17\0\1\3\1\0\2\3\1\0"+
    "\13\3\1\127\12\3\1\0\1\3\17\0\1\3\1\0"+
    "\2\3\1\0\2\3\1\130\23\3\1\0\1\3\17\0"+
    "\1\3\1\0\2\3\1\0\10\3\1\131\15\3\1\0"+
    "\1\3\17\0\1\3\1\0\2\3\1\0\6\3\1\132"+
    "\17\3\1\0\1\3\17\0\1\3\1\0\2\3\1\0"+
    "\1\133\25\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\4\3\1\134\21\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\10\3\1\135\15\3\1\0\1\3"+
    "\17\0\1\3\1\0\2\3\1\0\2\3\1\136\23\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\13\3"+
    "\1\137\12\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\13\3\1\140\12\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\1\3\1\141\24\3\1\0\1\3"+
    "\17\0\1\3\1\0\2\3\1\0\4\3\1\142\21\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\1\3"+
    "\1\143\24\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\1\3\1\144\24\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\1\3\1\145\24\3\1\0\1\3"+
    "\17\0\1\3\1\0\2\3\1\0\4\3\1\146\21\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\16\3"+
    "\1\147\7\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\24\3\1\150\1\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\5\3\1\151\20\3\1\0\1\3"+
    "\17\0\1\3\1\0\2\3\1\0\5\3\1\152\20\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\7\3"+
    "\1\153\16\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\21\3\1\154\4\3\1\0\1\3\17\0\1\3"+
    "\1\0\2\3\1\0\17\3\1\155\6\3\1\0\1\3"+
    "\17\0\1\3\1\0\2\3\1\0\10\3\1\156\15\3"+
    "\1\0\1\3\17\0\1\3\1\0\2\3\1\0\4\3"+
    "\1\157\21\3\1\0\1\3\17\0\1\3\1\0\2\3"+
    "\1\0\26\3\1\160\1\3\17\0\1\3\21\0\1\161"+
    "\41\0\1\162\52\0\1\163\77\0\1\164\36\0\1\165"+
    "\42\0\1\166\66\0\1\167\44\0\1\170\51\0\1\171"+
    "\57\0\1\172\51\0\1\173\43\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3872];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\22\1\3\11\1\1\14\11\23\1\1\11"+
    "\67\1\13\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[123];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
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


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public scanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public scanner(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 126) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException, CompilerException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 4: 
          { /* ignore whitespace */
          }
        case 40: break;
        case 29: 
          { return symbol(sym.CLASS);
          }
        case 41: break;
        case 13: 
          { return symbol(sym.RPAREN);
          }
        case 42: break;
        case 33: 
          { return symbol(sym.LENGTH);
          }
        case 43: break;
        case 39: 
          { return symbol(sym.SYSOUT);
          }
        case 44: break;
        case 15: 
          { return symbol(sym.LBRACE);
          }
        case 45: break;
        case 3: 
          { return symbol(sym.INT, Integer.parseInt(yytext()) );
          }
        case 46: break;
        case 11: 
          { return symbol(sym.NOT);
          }
        case 47: break;
        case 21: 
          { return symbol(sym.AND);
          }
        case 48: break;
        case 5: 
          { return symbol(sym.POINT);
          }
        case 49: break;
        case 1: 
          { throw new LexicalCompilerException(
	"unexpected character in input: '" + yytext() + "'", 
	yyline+1, yycolumn+1);
          }
        case 50: break;
        case 32: 
          { return symbol(sym.RETURN);
          }
        case 51: break;
        case 6: 
          { return symbol(sym.PLUS);
          }
        case 52: break;
        case 37: 
          { return symbol(sym.EXTENDS);
          }
        case 53: break;
        case 8: 
          { return symbol(sym.LESS);
          }
        case 54: break;
        case 25: 
          { return symbol(sym.TRUE);
          }
        case 55: break;
        case 7: 
          { return symbol(sym.BECOMES);
          }
        case 56: break;
        case 27: 
          { return symbol(sym.VOID);
          }
        case 57: break;
        case 2: 
          { return symbol(sym.IDENTIFIER, yytext());
          }
        case 58: break;
        case 10: 
          { return symbol(sym.TIMES);
          }
        case 59: break;
        case 28: 
          { return symbol(sym.MAIN);
          }
        case 60: break;
        case 17: 
          { return symbol(sym.LBRACK);
          }
        case 61: break;
        case 24: 
          { return symbol(sym.ELSE);
          }
        case 62: break;
        case 34: 
          { return symbol(sym.STATIC);
          }
        case 63: break;
        case 31: 
          { return symbol(sym.WHILE);
          }
        case 64: break;
        case 20: 
          { return symbol(sym.IF);
          }
        case 65: break;
        case 12: 
          { return symbol(sym.LPAREN);
          }
        case 66: break;
        case 26: 
          { return symbol(sym.THIS);
          }
        case 67: break;
        case 16: 
          { return symbol(sym.RBRACE);
          }
        case 68: break;
        case 14: 
          { return symbol(sym.SEMICOLON);
          }
        case 69: break;
        case 23: 
          { return symbol(sym.INTEGER);
          }
        case 70: break;
        case 9: 
          { return symbol(sym.MINUS);
          }
        case 71: break;
        case 22: 
          { return symbol(sym.NEW);
          }
        case 72: break;
        case 38: 
          { return symbol(sym.BOOLEAN);
          }
        case 73: break;
        case 36: 
          { return symbol(sym.STRING);
          }
        case 74: break;
        case 19: 
          { return symbol(sym.COMMA);
          }
        case 75: break;
        case 30: 
          { return symbol(sym.FALSE);
          }
        case 76: break;
        case 18: 
          { return symbol(sym.RBRACK);
          }
        case 77: break;
        case 35: 
          { return symbol(sym.PUBLIC);
          }
        case 78: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
