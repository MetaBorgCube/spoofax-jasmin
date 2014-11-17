module types/pop-types/InvokeReturn

imports
	include/JasminXT
	analysis/types/constraints

type rules
	// INVOKESPECIAL(JBCMethodRef(_, _, JBCMethodDesc(p, _)))     has pop-type <subtype of t> where p => t
	// INVOKEVIRTUAL(JBCMethodRef(_, _, JBCMethodDesc(p, _)))     has pop-type <subtype of t> where p => t
	// INVOKESTATIC(JBCMethodRef(_, _, JBCMethodDesc(p, _)))      has pop-type <subtype of t> where p => t
	// INVOKEINTERFACE(JBCMethodRef(_, _, JBCMethodDesc(p, _)),_) has pop-type <subtype of t> where p => t

	  // RETURN()
	  RET(_)
	+ RET_W(_)
	has pop-type []
	
	// IRETURN() has pop-type [Int()]
	// LRETURN() has pop-type [Long()]
	// FRETURN() has pop-type [Float()]
	// DRETURN() has pop-type [Double()]
	// ARETURN() has pop-type some Reference(Cref(...))
