# Code Generation
As described in [issue #1 on YellowGrass](http://yellowgrass.org/issue/Jasmin/1), a strategy was required for generating JVM class files from Jasmin bytecode, without depending on the `jasmin.jar` executable. We chose to implement this functionality in two distinct parts.

## Translation
Our first step was to create a [translator](translator.str), which translates the Jasmin AST to a syntax that closely mimics the API of the ASM framework. Our motivation for this step was to minimize the amount of logic required in the Java code that interfaces with the framework. Instead, the logic of translating to the Java AST is placed in Stratego, for easy modification.

For each type of 'node' in the jasmin AST, we have a rewrite rule, that translates it to the appropriate node in the AST for the code generator. For instructions we created rules to translate the opcode to an opcode the code generator understands, and we use a generic rewrite rule to create instruction nodes.

## Code Generation
The second step is to take the translated AST and pass it into the ASM framework. Because the API of the code generation framework follows a tree structure, We decided that the intermediate AST should mimic the API of the code generation framework. In this way the interface with the framework is clean and generic, and it is most likely possible to reduce the amount of duplication much more.

The [java part of the code generator](../../editor/java/org/spoofax/asm/ClassGenerator.java) works by retrieving the constructor name of the term that is passed in. Based on this name it retrieves the appropriate constructor arguments and passes them to the correct method. This is repeated for child nodes where appropriate. This approach could be made more generic (e.g. using reflection and annotations) in order to reduce the amount of maintenance for new versions of the ASM framework, but we opted to keep things reasonably simple within the scope of this project.
