module types/pop-types/Stack

imports
	include/JasminXT
	analysis/names/extra-constructors

type rules
	POP()     has pop-type ["TODO: if an element is present on the stack, pop the top most one"]
	POP2()    has pop-type ["TODO: if two element is present on the stack (or one if it is a float or double), pop the top most two"]
	DUP()     has pop-type ["TODO: get the top element off the stack, cannot be Double() or Long()"]
	DUP2()    has pop-type ["TODO: get the top two elements off the stack"]
	DUP_X1()  has pop-type ["TODO: get the top two elements off the stack"]
	DUP2_X1() has pop-type ["TODO: get the top three elements off the stack"]
	DUP_X2()  has pop-type ["TODO: get the top three elements off the stack"]
	DUP2_X2() has pop-type ["TODO: get the top four elements off the stack"]
	SWAP()    has pop-type ["TODO: get the top two elements off the stack"]