module types/push-types/Stack

imports
  include/JasminXT
  analysis/types/constraints

type rules
  POP()     has push-type []
  POP2()    has push-type []
  // DUP()     : duplicate a word on the stack (so cannot be Double or Long) 
  // DUP2()    : duplicate 2 words on the stack (e.g. 1 Long or 2 Ints)
  // DUP_X1()  : copy the top word to after the second word (so on the third place from the top)
  // DUP2_X1() : copy the top 2 words to after the second word
  // DUP_X2()  : copy the top word to after the third word
  // DUP2_X2() : copy the top 2 words to after the second word
  // SWAP()    : swap the top 2 words