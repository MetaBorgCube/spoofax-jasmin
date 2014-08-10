module types/pop-types/Conversion

imports
	include/JasminXT
	analysis/types/constraints

type rules
	  I2L()
	+ I2F()
	+ I2D()
	+ I2B()
	+ I2S()
	+ I2C()
	has pop-type [Int()]
	
	  L2F()
	+ L2D()
	+ L2I()
	has pop-type [Long()]
	
	  F2D()
	+ F2I()
	+ F2L()
	has pop-type [Float()]
	
	  D2I()
	+ D2L()
	+ D2F()
	has pop-type [Double()]