module types/push-types/Instructions

imports
  include/JasminXT
  analysis/types/constraints

type rules
  NOP() +
  BREAKPOINT() has push-type []