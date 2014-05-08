module types/pop-types/Instructions

imports
	include/JasminXT
	analysis/names/extra-constructors

type rules
	  NOP()
	+ BREAKPOINT()
	has pop-type []