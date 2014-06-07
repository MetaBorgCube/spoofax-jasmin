module types/push-types/Synchronisation

imports
	include/JasminXT
	analysis/names/extra-constructors

type rules
	MONITORENTER() has pop-type ["TODO: CRef to element on top of stack"]
	MONITOREXIT()  has pop-type ["TODO: CRef to element on top of stack"]