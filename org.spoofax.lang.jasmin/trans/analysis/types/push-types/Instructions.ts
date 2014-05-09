module types/push-types/Instructions

imports
  include/JasminXT
  analysis/names/extra-constructors

type rules
  NOP() +
  BREAKPOINT() has push-type []