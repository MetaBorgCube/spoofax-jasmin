module types/Literals

imports include/JasminXT

type rules
	Int(i) : Int()
	Float(f) : Float()
	Double(d) : Double()
	String(s) : CRef("java/lang/String")