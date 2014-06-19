module types/push-types/InvokeReturn

imports
  include/JasminXT
  analysis/names/extra-constructors

type rules
  // INVOKESPECIAL(  JBCMethodRef(_, _, JBCMethodDesc(_, r))   ) +
  // INVOKEVIRTUAL(  JBCMethodRef(_, _, JBCMethodDesc(_, r))   ) +
  // INVOKESTATIC(   JBCMethodRef(_, _, JBCMethodDesc(_, r))   ) +
  // INVOKEINTERFACE(JBCMethodRef(_, _, JBCMethodDesc(_, r)), _)
  //   has push-type <subtype of t> where (r => Void() and [] => t) or [r] => t

  // RETURN()  +
  // IRETURN() +
  // LRETURN() +
  // FRETURN() +
  // DRETURN() +
  // ARETURN() has push-type <Empty stack>
  
  RET(_)   +
  RET_W(_) has push-type []