module types/pop-types/Instructions

imports
	signatures/-
	analysis/types/constraints

type rules
	  NOP()
	+ BREAKPOINT()
	has pop-type []