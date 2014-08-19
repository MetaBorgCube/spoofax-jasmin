module types/pop-types/Instructions

imports
	include/JasminXT
	analysis/types/constraints

type rules
	  NOP()
	+ BREAKPOINT()
	has pop-type []