module types/push-types/Control

imports
  include/JasminXT
  analysis/names/extra-constructors

type rules
  IFEQ      +
  IFLT      +
  IFLE      +
  IFNE      +
  IFGT      +
  IFGE      +
  IFNULL    +
  IFNONNULL +
  IF_ICMPEQ +
  IF_ICMPLT +
  IF_ICMPLE +
  IF_ICMPNE +
  IF_ICMPGT +
  IF_ICMPGE +
  IF_ACMPEQ +
  IF_ACMPNE has push-type []
  
  Table  +
  Lookup has push-type []
  
  GOTO   +
  GOTO_W has push-type []
  
  JSR   +
  JSR_W has push-type []