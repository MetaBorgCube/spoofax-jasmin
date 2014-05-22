module types/push-types/Objects

imports
  include/JasminXT
  analysis/names/extra-constructors

type rules
  NEW(cr) has push-type [cr]
              
  NEWARRAY(ct)         +
  ANEWARRAY(ct)        +
  ANEWARRAY(ct)        +
  MULTIANEWARRAY(ct,_) has push-type [Array(ct)]
                
  GETFIELD(fr)  has push-type [fr]
  PUTFIELD(_)   has push-type []
  GETSTATIC(fr) has push-type [fr]
  PUTSTATIC(_)  has push-type []
                
  IALOAD() has push-type [Int()]
  LALOAD() has push-type [Long()]
  BALOAD() has push-type ["TODO: find out what to do here because it may Byte() and Boolean()"]
  CALOAD() has push-type [Char()]
  SALOAD() has push-type [Short()]
  FALOAD() has push-type [Float()]
  DALOAD() has push-type [Double()]
  AALOAD() has push-type ["TODO: get class name from the stack"]
                
  IASTORE() +
  LASTORE() +
  BASTORE() +
  CASTORE() +
  SASTORE() +
  FASTORE() +
  DASTORE() +
  AASTORE() has push-type []
                
  ARRAYLENGTH() has push-type [Int()]
                
  CHECKCAST(cr)  has push-type ["TODO: get the pop-type, should be a class"]
  INSTANCEOF(cr) has push-type [Boolean()]