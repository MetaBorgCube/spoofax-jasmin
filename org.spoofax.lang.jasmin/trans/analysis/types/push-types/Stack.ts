module types/push-types/Stack

imports
  include/JasminXT
  analysis/names/extra-constructors

type rules
  POP()     has push-type []
  POP2()    has push-type []
  DUP()     has push-type ["TODO: get t from the stack, cannot be Double() or Long()"] 
  DUP2()    has push-type ["TODO: get t from the stack"]
  DUP_X1()  has push-type ["TODO: get t from the stack, cannot be Double() or Long()"]
  DUP2_X1() has push-type ["TODO: get t from the stack"]
  DUP_X2()  has push-type ["TODO: get t from the stack, cannot be Double() or Long()"]
  DUP2_X2() has push-type ["TODO: get t from the stack"]
  SWAP()    has push-type ["TODO: get t1 and t2 from the stack, cannot be Double() or Long()"]