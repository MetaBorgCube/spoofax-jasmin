module types/push-types/Objects

imports
  include/JasminXT
  analysis/types/constraints

type rules
  NEW(cr) has push-type [cr]
              
  NEWARRAY(ct)         +
  ANEWARRAY(ct)        +
  ANEWARRAY(ct)        +
  MULTIANEWARRAY(ct,_) has push-type [Array(ct)]
                
  GETFIELD(JBCFieldRef(_, _, JBCFieldDesc(ft)))
    has push-type [ft]
  GETSTATIC(JBCFieldRef(_, _, JBCFieldDesc(ft)))
    has push-type [ft]
  PUTFIELD(_)   +
  PUTSTATIC(_)  has push-type []
                
  // IALOAD() has push-type [Int()]
  // LALOAD() has push-type [Long()]
  // BALOAD() has push-type Byte() /\ Boolean()
  // CALOAD() has push-type [Char()]
  // SALOAD() has push-type [Short()]
  // FALOAD() has push-type [Float()]
  // DALOAD() has push-type [Double()]
  // AALOAD() has push-type Reference(CRef(<see array type on stack>)) /\ Array(<see array type on stack>)
                
  // IASTORE() +
  // LASTORE() +
  // BASTORE() +
  // CASTORE() +
  // SASTORE() +
  // FASTORE() +
  // DASTORE() +
  // AASTORE() has push-type []
                
  ARRAYLENGTH() has push-type [Int()]
                
  // CHECKCAST(cr)  has push-type Reference(CRef(<see the just popped type>))
  INSTANCEOF(cr) has push-type [Boolean()]