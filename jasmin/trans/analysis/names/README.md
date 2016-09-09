Name analysis
=============
As described in
[issue #2 on YellowGrass](http://yellowgrass.org/issue/Jasmin/2): class
names, field names and local variable indices were not handled. This has
been resolved by using the `type` property in the `binding.nab` file.

Custom error messages were added in `check.str`, which is dependant on
the pretty printer that was generated from the SDF3 syntax. Notice that
the SDF3 syntax includes some core syntax from which no pretty printing
code is derived. This was written by hand in
`trans/JasminXT-Extra-PP-Rules.pp.str`.  
In these custom error messages, the use of HTML-like tags was found to
be interpreted by the Eclipse IDE. Since no escaping is done by Spoofax,
this is currently being done in `check.str`. 
