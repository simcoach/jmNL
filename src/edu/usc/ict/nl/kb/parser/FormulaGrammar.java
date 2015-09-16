/* Generated By:JavaCC: Do not edit this line. FormulaGrammar.java */
package edu.usc.ict.nl.kb.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.usc.ict.nl.dm.reward.model.DialogueOperatorEffect;
import edu.usc.ict.nl.kb.DialogueKBFormula;
import edu.usc.ict.nl.utils.Sanitizer;


/** Simple brace matcher. */
public class FormulaGrammar implements FormulaGrammarConstants {
	
    static final Logger logger = Logger.getLogger(FormulaGrammar.class);
    
    public DialogueKBFormula makeFormula(Token t,String p) throws ParseException {
        try {
                        return DialogueKBFormula.create(p,null);
                } catch (Exception e) {
                        logger.error(Sanitizer.log(e.getMessage()), e);
                        throw new ParseException("error creating formula starting at line: "+t.beginLine+" column: "+t.beginColumn);
                }
    }
    public DialogueKBFormula makeFormula(Token t,String p,List<DialogueKBFormula> args) throws ParseException {
        try {
        	return DialogueKBFormula.create(p,args);
        } catch (Exception e) {
            logger.error(Sanitizer.log(e.getMessage()), e);
        	throw new ParseException("error creating formula starting at line: "+t.beginLine+" column: "+t.beginColumn);
        }
    }
        public List<DialogueKBFormula> makeArglist(DialogueKBFormula f) {
                if (f==null) return new ArrayList<DialogueKBFormula>();
                else {
                        List<DialogueKBFormula> args=new ArrayList<DialogueKBFormula>();
                        args.add(f);
                        return args;
                }
        }
        public List<DialogueKBFormula> makeArglist() {
                return new ArrayList<DialogueKBFormula>();
        }
        public List<DialogueKBFormula> makeArglist(DialogueKBFormula f,List<DialogueKBFormula> args2) {
                if (f==null) return null;
                else {
                        List<DialogueKBFormula> args=new ArrayList<DialogueKBFormula>();
                        args.add(f);
                        if (args!=null) args.addAll(args2);
                        return args;
                }
        }
        public List<DialogueOperatorEffect> makeEfflist() {
                return new ArrayList<DialogueOperatorEffect>();
        }
        public List<DialogueOperatorEffect> makeEfflist(DialogueOperatorEffect f) {
                if (f==null) return new ArrayList<DialogueOperatorEffect>();
                else {
                        List<DialogueOperatorEffect> args=new ArrayList<DialogueOperatorEffect>();
                        args.add(f);
                        return args;
                }
        }
        public List<DialogueOperatorEffect> makeEfflist(DialogueOperatorEffect f,List<DialogueOperatorEffect> args2) {
                if (f==null) return null;
                else {
                        List<DialogueOperatorEffect> args=new ArrayList<DialogueOperatorEffect>();
                        args.add(f);
                        if (args!=null) args.addAll(args2);
                        return args;
                }
        }
        public DialogueOperatorEffect makeImplication(Token t,DialogueKBFormula cnd,DialogueOperatorEffect thenE,DialogueOperatorEffect elseE) throws ParseException {
                if (cnd!=null) {
                        try{
                                if (elseE!=null) {
                                        return DialogueOperatorEffect.createImplication(cnd,thenE,elseE);
                                } else {
                                        return DialogueOperatorEffect.createImplication(cnd,thenE);
                                }
                        } catch (Exception e) {
                                logger.error(Sanitizer.log(e.getMessage()), e);
                                throw new ParseException("error creating implication starting at line: "+t.beginLine+" column: "+t.beginColumn);
                        }
                }
                return null;
        }
        public DialogueOperatorEffect makeList(Token t,List<DialogueOperatorEffect> effs) throws ParseException {
                if (effs!=null) {
                        try{
                                return DialogueOperatorEffect.createList(effs);
                        } catch (Exception e) {
                                logger.error(Sanitizer.log(e.getMessage()), e);
                                throw new ParseException("error creating effect list starting at line: "+t.beginLine+" column: "+t.beginColumn);
                        }
                }
                return null;
        }
        public DialogueOperatorEffect makeAssignment(Token t,String var,DialogueKBFormula value) throws ParseException {
                try{
                        return DialogueOperatorEffect.createAssignment(var,value);
                } catch (Exception e) {
                        logger.error(Sanitizer.log(e.getMessage()), e);
                        throw new ParseException("error creating assignment starting at line: "+t.beginLine+" column: "+t.beginColumn);
                }
        }
        public DialogueOperatorEffect makeIncrement(Token t,String var,DialogueKBFormula inc) throws ParseException {
                try{
                        return DialogueOperatorEffect.createIncrementForVariable(var, inc);
                } catch (Exception e) {
                        logger.error(Sanitizer.log(e.getMessage()), e);
                        throw new ParseException("error creating increment starting at line: "+t.beginLine+" column: "+t.beginColumn);
                }
        }
        public DialogueOperatorEffect makeAssertion(Token t,DialogueKBFormula f) throws ParseException {
                try{
                        return DialogueOperatorEffect.createAssertion(f);
                } catch (Exception e) {
                        logger.error(Sanitizer.log(e.getMessage()), e);
                        throw new ParseException("error creating assertion starting at line: "+t.beginLine+" column: "+t.beginColumn);
                }
        }

/** Root production. */
  final public DialogueOperatorEffect effect() throws ParseException {
        DialogueKBFormula wff=null;
        DialogueOperatorEffect eff=null,eff2=null;
        List<DialogueOperatorEffect> effs=null;
        Token t=null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IMPLY:
      t = jj_consume_token(IMPLY);
      jj_consume_token(LB);
      wff = formula();
      jj_consume_token(CM);
      eff = assignment();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CM:
        jj_consume_token(CM);
        eff2 = assignment();
        break;
      default:
        jj_la1[0] = jj_gen;
        ;
      }
      jj_consume_token(RB);
                                                                                    {if (true) return makeImplication(t,wff,eff,eff2);}
      break;
    case PP:
    case LIST:
    case ASSIGN:
    case ASSERT:
    case ID:
      eff = assignment();
                           {if (true) return eff;}
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public List<DialogueOperatorEffect> assignmentList() throws ParseException {
        DialogueOperatorEffect wff;
        List<DialogueOperatorEffect> args=null;
    wff = assignment();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CM:
      jj_consume_token(CM);
      args = assignmentList();
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
                                                        {if (true) return (args!=null)?makeEfflist(wff,args):makeEfflist(wff);}
    throw new Error("Missing return statement in function");
  }

  final public DialogueOperatorEffect assignment() throws ParseException {
        DialogueKBFormula wff=null;
        Token t=null,p=null;
        List<DialogueOperatorEffect> effs=null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ASSIGN:
      p = jj_consume_token(ASSIGN);
      jj_consume_token(LB);
      t = jj_consume_token(ID);
      jj_consume_token(CM);
      wff = formula();
      jj_consume_token(RB);
                                                   {if (true) return makeAssignment(p,t.image,wff);}
      break;
    case PP:
      p = jj_consume_token(PP);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LB:
        jj_consume_token(LB);
        t = jj_consume_token(ID);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CM:
          jj_consume_token(CM);
          wff = formula();
          break;
        default:
          jj_la1[3] = jj_gen;
          ;
        }
        jj_consume_token(RB);
        break;
      case ID:
        t = jj_consume_token(ID);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                                                                {if (true) return makeIncrement(p,t.image,(wff==null)?makeFormula(t,"1"):wff);}
      break;
    case ID:
      t = jj_consume_token(ID);
      jj_consume_token(EQ);
      wff = formula();
                                  {if (true) return makeAssignment(t,t.image,wff);}
      break;
    case ASSERT:
      p = jj_consume_token(ASSERT);
      jj_consume_token(LB);
      wff = formula();
      jj_consume_token(RB);
                                          {if (true) return makeAssertion(p,wff);}
      break;
    case LIST:
      t = jj_consume_token(LIST);
      jj_consume_token(LB);
      effs = assignmentList();
      jj_consume_token(RB);
                                                {if (true) return makeList(t,effs);}
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public DialogueKBFormula formula() throws ParseException {
        DialogueKBFormula wff=null,wff2=null;
        List<DialogueKBFormula> args=null;
        Token t=null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ARIT:
    case STRING:
    case ID:
      wff = predicateOrFunction();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CMP:
        t = jj_consume_token(CMP);
        wff2 = predicateOrFunction();
        break;
      default:
        jj_la1[6] = jj_gen;
        ;
      }
                                                                       {if (true) return (t==null)?wff:makeFormula(t,t.image,makeArglist(wff,makeArglist(wff2)));}
      break;
    case LB:
      jj_consume_token(LB);
      wff = formula();
      jj_consume_token(RB);
                                {if (true) return wff;}
      break;
    case CMP:
      t = jj_consume_token(CMP);
      args = arguments2();
                                    {if (true) return makeFormula(t,t.image,args);}
      break;
    case AND:
      t = jj_consume_token(AND);
      args = arguments();
                                   {if (true) return makeFormula(t,"and",args);}
      break;
    case OR:
      t = jj_consume_token(OR);
      args = arguments();
                                  {if (true) return makeFormula(t,"or",args);}
      break;
    case NOT:
      t = jj_consume_token(NOT);
      wff = formula();
                                {if (true) return makeFormula(t,"not",makeArglist(wff));}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public DialogueKBFormula predicateOrFunction() throws ParseException {
        DialogueKBFormula wff,wff2;
        List<DialogueKBFormula> args=null;
        Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ARIT:
    case ID:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        t = jj_consume_token(ID);
        break;
      case ARIT:
        t = jj_consume_token(ARIT);
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LB:
        args = arguments();
        break;
      default:
        jj_la1[9] = jj_gen;
        ;
      }
                                              {if (true) return (args!=null)?makeFormula(t,t.image,args):makeFormula(t,t.image);}
      break;
    case STRING:
      jj_consume_token(STRING);
      t = jj_consume_token(ANY);
                            {if (true) return makeFormula(t,"'"+t.image);}
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public List<DialogueKBFormula> argumentsBody() throws ParseException {
        DialogueKBFormula wff;
        List<DialogueKBFormula> args=null;
    wff = formula();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CM:
      jj_consume_token(CM);
      args = argumentsBody();
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
                                                    {if (true) return (args!=null)?makeArglist(wff,args):makeArglist(wff);}
    throw new Error("Missing return statement in function");
  }

  final public List<DialogueKBFormula> arguments() throws ParseException {
        List<DialogueKBFormula> args=null;
    jj_consume_token(LB);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LB:
    case CMP:
    case OR:
    case AND:
    case NOT:
    case ARIT:
    case STRING:
    case ID:
      args = argumentsBody();
      break;
    default:
      jj_la1[12] = jj_gen;
      ;
    }
    jj_consume_token(RB);
                                         {if (true) return (args!=null)?args:makeArglist();}
    throw new Error("Missing return statement in function");
  }

  final public List<DialogueKBFormula> arguments1() throws ParseException {
        DialogueKBFormula wff;
    jj_consume_token(LB);
    wff = formula();
    jj_consume_token(RB);
                               {if (true) return makeArglist(wff);}
    throw new Error("Missing return statement in function");
  }

  final public List<DialogueKBFormula> arguments2() throws ParseException {
        DialogueKBFormula wff,wff2;
    jj_consume_token(LB);
    wff = formula();
    jj_consume_token(CM);
    wff2 = formula();
    jj_consume_token(RB);
                                                 {if (true) return makeArglist(wff,makeArglist(wff2));}
    throw new Error("Missing return statement in function");
  }

  final public List<DialogueKBFormula> arguments2P() throws ParseException {
        DialogueKBFormula wff;
        List<DialogueKBFormula> args;
    jj_consume_token(LB);
    wff = formula();
    jj_consume_token(CM);
    args = argumentsBody();
    jj_consume_token(RB);
                                                       {if (true) return makeArglist(wff,args);}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public FormulaGrammarTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[13];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x100,0x13a080,0x100,0x100,0x100020,0x11a080,0x400,0x1c5c20,0x140000,0x20,0x1c0000,0x100,0x1c5c20,};
   }

  /** Constructor with InputStream. */
  public FormulaGrammar(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public FormulaGrammar(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new FormulaGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public FormulaGrammar(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new FormulaGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public FormulaGrammar(FormulaGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(FormulaGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[22];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 13; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 22; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
