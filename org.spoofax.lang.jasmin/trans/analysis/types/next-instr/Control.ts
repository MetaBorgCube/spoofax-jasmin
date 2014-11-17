module types/next-instr/Control

imports
  include/JasminXT
  analysis/types/constraints

type rules
  IFEQ(t)      +
  IFLT(t)      +
  IFLE(t)      +
  IFNE(t)      +
  IFGT(t)      +
  IFGE(t)      +
  IFNULL(t)    +
  IFNONNULL(t) +
  IF_ICMPEQ(t) +
  IF_ICMPLT(t) +
  IF_ICMPLE(t) +
  IF_ICMPNE(t) +
  IF_ICMPGT(t) +
  IF_ICMPGE(t) +
  IF_ACMPEQ(t) +
  IF_ACMPNE(t) has next-instr [NEXT(), t]
  
//  Table  +
//  Lookup has next-instr []
// TODO: all labels in the list
  
  GOTO(t)   +
  GOTO_W(t) +
  JSR(t)   +
  JSR_W(t) has next-instr [t]