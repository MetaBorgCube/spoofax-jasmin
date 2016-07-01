module types/push-types/Instructions

imports
  signatures/-
  analysis/types/constraints

type rules
  NOP() +
  BREAKPOINT() has push-type []