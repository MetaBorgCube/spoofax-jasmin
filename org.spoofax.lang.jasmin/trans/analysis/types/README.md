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

The constraints being collected take the forms:
 * Equivalence (`CEq`, e.g. introduced by `next-instr`)
 * Subtype (`CSub`, e.g. when a class is loaded/stored)
 * Disjunction (`COr`, e.g. `ALOAD` can load a class OR a `JSR` address)
 * Non-Equivalence (`CNEq`, e.g. `SWAP` is not allowed on a `double`)

The constraints are collected in a list which implicitly means that a
conjunction of these constraints must be true for the program to
type-check. The constraints can be between types and variables, but also
between whole stacks or local variable lists. The latter means the
constraint should be considered pointwise for each of the entries. 

Solving constraints
-------------------
Solving the collected constraints is fully implemented in Stratego and uses a fixed-point iteration approach, meaning that the `solve-constraints-step` strategy will be executed until an iteration did not change anything in the intermediate result.  
Once the fixed-point iterations stop, the solver returns its final output, being a tuple of two lists. The first list is a dictionary with solved constraints in the form of a tuple like `(TypeVar("a"), Int())`, meaning that the type variable `"a"` has type `Int()`. The second list holds the constraints that could not be solved. In the case this list is empty, the type analysis succeeded and no type errors were found. Otherwise, type errors are found on the locations the constraints originate from. These unresolved constraints can potentially be used to give errors in the analysed code, although that is not part of this project.

Type constraints are specified in 4 different forms. The most common of all is the `equals` constraint, which specifies that a certain variable has a certain type (for example: `CEq(TypeVar("a"), Int())`). These once can directly be put in the resulting dictionary in the form of a tuple as shown before.  
Besides that, the `equals` constraint can be used to indicate that 2 type variables share a certain type (for example: `CEq(TypeVar("a"), TypeVar("b"))`). With the previous constraint already in its dictionary, the algorithm can derive that type variable `"b"` also has type `Int()`.  
The `equals` constraint also has a third use: to express that a certain variable is equal to a stack of types with in the first position a type, followed by another type variable referring to the rest of the stack. So suppose the dictionary contains `(TypeVar("a"), [Int(), Boolean(), Byte()])`, then it can infer from `CEq(TypeVar("a"), ([Int()], TypeVar("b")))` that `(TypeVar("b"), [Boolean(), Byte()])`.

Another type constraint is the `subtype` constraint, indicating that a type variable's type is a subtype of some other type or that a type variable's type is a subtype of another type variable's type. This can for instance be used to state that type variable `"b"` is a subtype of `java/lang/String`, type variable `"a"` is a subtype of `java/lang/Object` and that `"a"` and `"b"` have the same type:

* `CSub(TypeVar("b"), Reference(CRef("java/lang/String")))`
* `CSub(TypeVar("a"), Reference(CRef("java/lang/Object")))`
* `CEq(TypeVar("a"), TypeVar("b"))`

In order to try to find the types of both type variables, the constraint solver must find an upper-bound of the presented types. However, finding this upper-bound is not part of the project, so it is hard-coded for the instance at hand. In this case, the upper-bound of the 2 types is `java/lang/String`, from which the constraint solver concludes that both `"a"` and `"b"` have at least the type `java/lang/String`. 

A third type of constraint is the `not equals` constraint, which indicates that a type variable is not of a certain type or does not have the same type as another type variable. In the first case, the solver simply removes the constraint from the list, if and only if the tuple (`<variable>`, `<type>`) does not occur in the dictionary yet. In the second case the constraint is rewritten into another `not equals` constraint holding that the type of the first variable is not equal to the type of the second variable, which is looked up. This reduces the constraint to one of the first case.  
The following example states that type variables `"a"` and `"b"` have the same type, that type variable `"a"` is not of type `Int()`, and that type variable `"b"` is of type `Int()`:

* CEq(TypeVar("a"), TypeVar("b"))
* CNEq(TypeVar("a"), Int())
* CEq(TypeVar("b"), Int())

It is clear that this is a contradiction, therefore the constraint solver ends up in failure:

* Resolved: `(TypeVar("b"), Int())` and `(TypeVar("fail"), Int())`
* Unresolved: `CNEq(TypeVar("fail"), Int())` and `CNEq(TypeVar("b"), Int())`

Notice that this yields an extra `not equals` constraint, since type variables `"a"` and `"b"` share the same type and type variable `"a"` is not of type `Int()`. Therefore type variable `"b"` cannot be of type `Int()`, although it claims it is.

The final type constraint is the `or` constraint. **TODO - highlevel description of the COr**
