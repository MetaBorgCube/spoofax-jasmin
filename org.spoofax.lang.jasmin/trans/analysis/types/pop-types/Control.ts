module types/pop-types/Control

imports
	include/JasminXT
	analysis/types/constraints

type rules
	  IFEQ(_)
	+ IFLT(_)
	+ IFLE(_)
	+ IFNE(_)
	+ IFGT(_)
	+ IFGE(_)
	+ IFNULL(_)
	+ IFNONNULL(_)
	has pop-type [Int()]
	
	  IF_ICMPEQ(_)
	+ IF_ICMPLT(_)
	+ IF_ICMPLE(_)
	+ IF_ICMPNE(_)
	+ IF_ICMPGT(_)
	+ IF_ICMPGE(_)
	+ IF_ACMPEQ(_)
	+ IF_ACMPNE(_)
	has pop-type [Int(), Int()]
	
	  GOTO(_)
	+ GOTO_W(_)
	+ JSR(_)
	+ JSR_W(_)
	has pop-type []