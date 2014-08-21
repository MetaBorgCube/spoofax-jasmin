Type Analysis
=============
As described in
[issue #3 on YellowGrass](http://yellowgrass.org/issue/Jasmin/3) type
analysis on Jasmin can be done in multiple ways as described in
"Java bytecode verification: algorithms and formalisations" by Xavier
Leroy. 

We chose to use a 2-phase (collect/solve) solution to the problem,
first collecting constraints, then solving those constraints. 

Collecting constraints
----------------------
The constraints to collect describe the stack and the local variables
before and after every instruction in a Jasmin program. 
Because most information about this is statically known, we tried to
encode much of it in properties that are collected by NaBL/TS. These
properties are: `push-type`, `pop-type`, `next-instr`, `in-stack` and
`out-stack`.  
The push and pop-type of most instructions are defined in
TS files in their respective subdirectory as long as they don't depend
on local variables or what was already on the stack.  
The next-instr is the instruction that comes next and only defined for
control flow instructions. Otherwise it is taken to be `NEXT()`,
meaning to the next instruction in the program.  
The in and out-stack are just unique names given to the stack at that
point, which are used as variables in the constraints to be collected. 

In `collect-constraints.str` the push, pop and next information is put
into constraints. When one or more things are to be popped from the
stack, then at that point the stack should have those things to pop
off. So the `in-stack` of the instruction is said to be equal to a
tuple of the `pop-type` and the rest of the stack, which is represented
as a fresh type variable.  
Something similar is done for the `push-type` and the `out-stack`.  
The `next-instr` is translated into constraints by saying the
`out-stack` of one instruction is equal to the `in-stack` of another. 

To deal with local variables and instructions that do things relative
to the stack, the `complex-constraints` are defined in Stratego.  
For every instruction that deals with classes, local variables or stack
instructions like pop and swap, the Stratego rules define the behaviour
these instruction represent. 

Solving constraints
-------------------
TODO
