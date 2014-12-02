module types/pop-types/Arithmetic

imports
	include/JasminXT
	analysis/types/constraints

type rules
	  IADD()
	+ IAND()
	+ IDIV()
	+ IMUL()
	+ INEG()
	+ IOR()
	+ IREM()
	+ ISHL()
	+ ISHR()
	+ ISUB()
	+ IUSHR()
	+ IXOR()
	has pop-type [Int(), Int()]
	
	  LADD()
	+ LAND()
	+ LDIV()
	+ LMUL()
	+ LNEG()
	+ LOR()
	+ LREM()
	+ LSHL()
	+ LSHR()
	+ LSUB()
	+ LUSHR()
	+ LXOR()
	+ LCMP()
	has pop-type [Long(), Long()]
	
	  FADD()
	+ FDIV()
	+ FMUL()
	+ FNEG()
	+ FREM()
	+ FSUB()
	+ FCMPG()
	+ FCMPL()
	has pop-type [Float(), Float()]
	
	  DADD()
	+ DDIV()
	+ DMUL()
	+ DNEG()
	+ DREM()
	+ DSUB()
	+ DCMPG()
	+ DCMPL()
	has pop-type [Double(), Double()]
	
	  IINC(_, _)
	+ IINC_W(_,_)
	has pop-type []