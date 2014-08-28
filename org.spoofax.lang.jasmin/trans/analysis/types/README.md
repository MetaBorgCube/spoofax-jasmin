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
Solving the collected constraints is fully implemented in Stratego and
uses a fixed-point iteration approach, meaning that the
`solve-constraints-step` strategy will be executed until an iteration
did not change anything in the intermediate result.  
Once the fixed-point iterations stop, the solver returns its final output.
This consists of a tuple of two lists, being respectively the solved and
unsolved constraints. In the case the second list is empty, the type 
analysis succeeded and no errors are found. In the case the second list is
not empty, the result can be used to give errors in the analysed code.
However, this is not part of this project.

Each iteration in the constraint solving contains a number of steps to be
performed and multiple types of constraints have to be handled, which as well
might be dependent upon each other.  
The most common constraint is the `equals` constraint, which specifies that
a certain variable has a certain type. Also the type equivalence of two
variables is specified using this constraint.  
Besides that, the equivalence constraint is also used to express that a
certain variable is equal to a stack of types with in the first position a
type, followed by another variable referring to the rest of the stack.  
Another relationship of variables is described by the `subtype` constraint,
indicating that a variable is a subtype of some type or other variable. In
many cases solving these constraints requires finding a least upper bound
of two arbitrary types. This however is not part this project and therefore
not implemented.  
Third, we have the `not equals` constraint, which indicates that a variable
does not have a certain type or does not have the same type as another
variable. In the first case, the solver removes the constraint from the list,
if the type of the variable (when known) is not equal to the specified type.
In the second case the constraint is rewritten into another `not equals`
constraint holding that the type of the first variable is not equal to the
type of the second variable, which is looked up. This reduced the constraint
to the first case.

TODO:

* more specific description of what equals and subtype do?
* highlevel description of the COr