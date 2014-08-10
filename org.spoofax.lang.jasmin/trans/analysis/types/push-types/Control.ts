module types/push-types/Control

imports
  include/JasminXT
  analysis/types/constraints

type rules
  IFEQ(_)      +
  IFLT(_)      +
  IFLE(_)      +
  IFNE(_)      +
  IFGT(_)      +
  IFGE(_)      +
  IFNULL(_)    +
  IFNONNULL(_) +
  IF_ICMPEQ(_) +
  IF_ICMPLT(_) +
  IF_ICMPLE(_) +
  IF_ICMPNE(_) +
  IF_ICMPGT(_) +
  IF_ICMPGE(_) +
  IF_ACMPEQ(_) +
  IF_ACMPNE(_) has push-type []
  
  Table(_,_,_) +
  Lookup(_,_)  has push-type []
  
  GOTO(_)   +
  GOTO_W(_) has push-type []
  
  JSR(_)   +
  JSR_W(_) has push-type [ADDRESS()]