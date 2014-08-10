module types/push-types/LoadStore

imports
  include/JasminXT
  analysis/types/constraints

type rules
  BIPUSH(_) +
  SIPUSH(_) has push-type [Int()]
             
  ICONST_M1() +
  ICONST_0()  +
  ICONST_1()  +
  ICONST_2()  +
  ICONST_3()  +
  ICONST_4()  +
  ICONST_5()  has push-type [Int()]
             
  LCONST_0() +
  LCONST_1() has push-type [Long()]
             
  FCONST_0() +
  FCONST_1() +
  FCONST_2() has push-type [Float()]
             
  DCONST_0() +
  DCONST_1() has push-type [Double()]
             
  ACONST_NULL() has push-type [BOTTOM()] 
             
  LDC(c)    +
  LDC2_W(c) +
  LDC_W(c)  has push-type [t] where c : t
  // TODO: maybe add constraints about LDC(_W) only pushing String/int/float and LDC2 only pushing double/long?
  // perhaps that can be done here in TS?
             
  ILOAD(vr) +
  LLOAD(vr) +
  FLOAD(vr) +
  DLOAD(vr) +
  ALOAD(vr) has push-type ["TODO: get info from local variables"]
             
  ILOAD_W(vr) +                                                               
  LLOAD_W(vr) +                                                               
  FLOAD_W(vr) +                                                               
  DLOAD_W(vr) +                                                               
  ALOAD_W(vr) has push-type ["TODO: get info from local variables"]
             
  ILOAD_0() +
  ILOAD_1() +
  ILOAD_2() +
  ILOAD_3() has push-type [Int()]
             
  ALOAD_0() +
  ALOAD_1() +
  ALOAD_2() +
  ALOAD_3() has push-type [Reference(CRef("TODO: get info from local variables"))]
             
  LLOAD_0() +
  LLOAD_1() +
  LLOAD_2() +
  LLOAD_3() has push-type [Long()]
             
  DLOAD_0() +
  DLOAD_1() +
  DLOAD_2() +
  DLOAD_3() has push-type [Double()]
             
  FLOAD_0() +
  FLOAD_1() +
  FLOAD_2() +
  FLOAD_3() has push-type [Float()]
             
  ISTORE(_)   +
  LSTORE(_)   +
  FSTORE(_)   +
  DSTORE(_)   +
  ASTORE(_)   +
  ISTORE_W(_) +
  LSTORE_W(_) +
  FSTORE_W(_) +
  DSTORE_W(_) +
  ASTORE_W(_) +
  ISTORE_0()  +
  ISTORE_1()  +
  ISTORE_2()  +
  ISTORE_3()  +
  ASTORE_0()  +
  ASTORE_1()  +
  ASTORE_2()  +
  ASTORE_3()  +
  LSTORE_0()  +
  LSTORE_1()  +
  LSTORE_2()  +
  LSTORE_3()  +
  FSTORE_0()  +
  FSTORE_1()  +
  FSTORE_2()  +
  FSTORE_3()  +
  DSTORE_0()  +
  DSTORE_1()  +
  DSTORE_2()  +
  DSTORE_3()  has push-type []