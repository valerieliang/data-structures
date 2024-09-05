# Discussion

**Document all error conditions you determined and why they are error
 conditions. Do this by including the inputs that you used to test your
  program and what error conditions they exposed:**

IllegalStateException:
In this program, an `IllegalStateException` is thrown when an operator is 
called, but there are insufficient elements in the stack for performing 
that operation. I thought this exception was an appropriate type because it 
indicates that a method has been invoked in an inappropriate manner.
Moreover, I thought it was important to distinguish between an `EmptyException` 
(where one accesses the non-existent values in an empty stack) versus and 
`IllegalStateException` because the stack could've been modified when the 
exception was thrown, with no way to fix it. 

For example, if the first value was already popped off the stack and when trying 
to access the second value, an exception is thrown, you cannot return the stack
to its original state, which would affect the program's desired implementation.
Let's say the user inputs `3 - ?`. There is only one value in the stack, so you
cannot perform subtraction. This displays the error message `ERROR: there are 
insufficient values to perform operation` and the `?` prints `[3]` indicating
that the failed operation did not affect the stack.

Similar operations were performed with all the other operations as shown:
user: `999 23 3 + + + + +`
computer: `ERROR: there are insufficient values to perform operation`
computer: `ERROR: there are insufficient values to perform operation`
computer: `ERROR: there are insufficient values to perform operation`
user: `?`
computer: `[1025]`

user: `*`
computer: `ERROR: there are insufficient values to perform operation`
user: `?`
computer: `[]`

user: `333 % / ?`
computer: `ERROR: there are insufficient values to perform operation`
computer: `ERROR: there are insufficient values to perform operation`
computer: `[333]`

ArithmeticException:
I used the `ArithmeticException` type for division by zero because this is an
illegal math operation. This type of exception only applies to the division and
modulus tokens, because it is perfectly acceptable to add, subtract, and multiply
by zero.

Testing:
user: `11 0 / ?`
computer: `ERROR: cannot divide by zero`
computer: `[11, 0]`

user: `0 1 / ?` (to check that dividing zero by another number is still valid)
computer: `[0]`

user: `0`
user: `-199`
user: `%`
computer: `ERROR: cannot divide by zero`
user: `?`
computer: `[0, -199]`

user: `0 3498 % ?` (to check that dividing zero by another number is still valid)
computer: `[0]`


InputMismatchException:
This is the exception thrown in the case that there is a bad token. I thought 
this type of exception was most appropriate for the situation because it 
indicates that the next item retrieved from the input stream does not match what 
is expected. This exception was identified already by the homework instructions.

Note that integers were be checked first in order to determine that they are valid 
values. This is because the `-` sign is both a token and an indicator of a negative
number. To ensure that the `-` was only parsed as a negative sign when appropriate, I
checked the length of the input. If it was only 1 character long, then that meant it 
was a valid `subtract()` token, otherwise it was likely a number. It would then go
through the process of being checked for integer validity.

tests:
user: `hellooo`
computer: `ERROR: bad token`
user: `-1` (ensures negative numbers are not bad tokens)
user: `-3apples` (checks that negative numbers are still validated properly)
computer: `ERROR: bad token`
user: `******` (checks that even though character is valid, when overall string is invalid, the program throws exception)
computer: `ERROR: bad token`
user: `1.000` (checks that the calculator only accepts integer values)
computer: `ERROR: bad token`
user: ? 3 ? * (displays state of stack to check that the bad tokens didn't affect it)
`[-1]`
`[-1, 3]`
`[-3]`
user: `. $ ?` (checks that operations can be called before and after bad tokens)
computer: `-3`
computer: `ERROR: bad token`
computer: `[-3]`
user: `!` (exits program)

EmptyException:
This is the exception already used by the `LinkedStack` class. I used it when
trying to print the top-most value of the stack if it was empty.
For example, let's say the user calls `.` without inputting any numbers beforehand.
That is, `.` is their only input. Because there are no values to print the program
displays the message: `ERROR: calculator history is empty`.

Note that trying to print an empty stack with `?` will not cause this error message
to be thrown because it still displays the brackets around an empty space (`[]`), making it
more clear than the dot token (which would only display the value) that the stack is
empty. This could avoid possible confusion for the user if they see an empty line
being displayed.