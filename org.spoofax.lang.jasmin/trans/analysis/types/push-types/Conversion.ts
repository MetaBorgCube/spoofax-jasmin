module types/push-types/Control

imports
  include/JasminXT
  analysis/types/constraints

type rules
  I2L() has push-type [Long()]
  I2F() has push-type [Float()]
  I2D() has push-type [Double()]
  L2F() has push-type [Float()]
  L2D() has push-type [Double()]
  F2D() has push-type [Double()]
  
  I2B() has push-type [Byte()]
  I2S() has push-type [Short()]
  I2C() has push-type [Char()]
  
  L2I() has push-type [Int()]
  F2I() has push-type [Int()]
  F2L() has push-type [Long()]
  D2I() has push-type [Int()]
  D2L() has push-type [Long()]
  D2F() has push-type [Float()]