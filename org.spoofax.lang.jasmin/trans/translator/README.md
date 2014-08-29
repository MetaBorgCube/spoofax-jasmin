# Code Generation
As described in [issue #1 on YellowGrass](http://yellowgrass.org/issue/Jasmin/1), a strategy was required for generating JVM class files from Jasmin bytecode, without depending on the `jasmin.jar` executable. We chose to implement this functionality in two distinct parts.

## Translation
Our first step was to create a [translator](translator.str), which translates the Jasmin AST to a syntax that closely mimics the API of the ASM framework. Our motivation for this step was to minimize the amount of logic required in the Java code that interfaces with the framework. Instead, the logic of translating to the Java AST is placed in Stratego, for easy modification.

TODO: How does this translator work exactly?

## Code Generation
The second step is to take the translated AST and pass it into the ASM framework. We decided that the intermediate AST should mimic the API of the code generation framework. In this way the interface with the framework is clean and generic, and it is most likely possible to reduce the amount of duplication much more.

TODO: example of the API and AST.