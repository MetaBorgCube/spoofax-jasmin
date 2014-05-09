module types/push-types/Exceptions

imports
  include/JasminXT
  analysis/names/extra-constructors

type rules
  MONITORENTER() +
  MONITOREXIT()  has push-type []