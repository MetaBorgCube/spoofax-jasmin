Syntax
======

The old SDF2 syntax was replaced by SDF3 templates. Using the
[JasminXT reference page](http://jasmin.sourceforge.net/xt.html) of the
Jasmin project, some of the syntax was extended. 

Tests
-----
The new SDF3 syntax was tested with SPT. Within the root directory is a
[tests](/tests) project. One of it's subdirectories is called syntax and
follows the file-structure of the syntax directory here. Only for some
large SDF3 files the tests were split up into multiple files in another
subdirectory. 

Caveats
-------
While porting the Jasmin syntax, the old SDF2 syntax sometimes had some
follow restrictions commented, so a grammar production commented. We
usually incorporated those in the SDF3 grammar, tested until we could
find why it was commented, then commented that part in the SDF3 grammar
but with the reason why it was commented. 

The extensions we made to the SDF2 grammar, based on the
[JasminXT reference page](http://jasmin.sourceforge.net/xt.html)
weren't all straightforward. The relative offset jump labels are
described inconsistently, and the whole `.annotation` part at the end is
a little unclear. The employed solutions are documented in the code. 
