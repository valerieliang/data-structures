package rpncalculator;

import static java.lang.Character.isDigit;

import exceptions.EmptyException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A program for an RPN calculator that uses a stack.
 */
public final class Calc {
  private LinkedStack<Integer> calculator;
  private int numElements;

  /**
   * The default constructor.
   * Creates empty LinkedStack with zero elements.
   */
  public Calc() {
    calculator = new LinkedStack<Integer>(); // calculator only stores integer numbers (not tokens)
    numElements = 0; // new empty stack has zero elements
  }

  /**
   * Inserts a number into the calculator.
   *
   * @param num is the number to be included
   */
  public void insert(int num) {
    calculator.push(num);
    numElements++;
  }

  /**
   * Determines if the System input is a number.
   *
   * @param input is the String being checked.
   * @return {@code true} if every value is an integer, {@code false} if not
   */
  public boolean isNum(String input) {
    for (int i = 0; i < input.length(); i++) {
      char curr = input.charAt(i);
      if (input.length() > 1 && i == 0 && (curr == '-' || curr == '+')) {
        continue; // edge case for signed numbers
      }
      if (!isDigit(curr)) {
        return false;
      }
    }
    return true;
  }

  // returns true if there are enough numbers in the stack to perform operations
  // i.e: at least two
  private boolean hasEnough() {
    return numElements >= 2;
  }

  /**
   * When called, takes the top two items off the stack and adds them together.
   * This results in the two items being removed from the stack, and the new sum being added.
   *
   * @throws IllegalStateException if there are insufficient elements to perform the operation.
   */
  private void add() throws IllegalStateException {
    if (!hasEnough()) {
      throw new IllegalStateException();
    }
    int first = calculator.top();
    calculator.pop();
    int second = calculator.top();
    calculator.pop();
    int sum = first + second; // commutative
    calculator.push(sum);
    numElements--; // removes two numbers, adds 1
  }

  /**
   * When called, takes the top two items off the stack and subtracts one from the other.
   * This results in the two items being removed from the stack, and the new difference being added.
   *
   * @throws IllegalStateException if there are insufficient elements to perform the operation.
   */
  private void subtract() throws IllegalStateException {
    if (!hasEnough()) {
      throw new IllegalStateException();
    }
    int first = calculator.top();
    calculator.pop();
    int second = calculator.top();
    calculator.pop();
    int difference = second - first;
    calculator.push(difference);
    numElements--; // removes two numbers, adds 1
  }

  /**
   * When called, takes the top two items off the stack and multiplies them together.
   * This results in the two items being removed from the stack, and the new product being added.
   *
   * @throws IllegalStateException if there are insufficient elements to perform the operation.
   */
  private void multiply() throws IllegalStateException {
    if (!hasEnough()) {
      throw new IllegalStateException("ERROR: there are insufficient values to perform operation");
    }
    int first = calculator.top();
    calculator.pop();
    int second = calculator.top();
    calculator.pop();
    int product = first * second; // commutative
    calculator.push(product);
    numElements--; // removes two numbers, adds 1
  }

  /**
   * When called, takes the top two items off the stack and divides one from the other.
   * This results in the two items being removed from the stack, and the new quotient being added.
   * Note: This is integer division, so it will round down.
   *
   * @throws IllegalStateException if there are insufficient elements to perform the operation.
   * @throws ArithmeticException for dividing by zero.
   */
  private void divide() throws IllegalStateException, ArithmeticException {
    if (!hasEnough()) {
      throw new IllegalStateException();
    }
    int first = calculator.top();
    calculator.pop();
    if (first == 0) {
      calculator.push(first);
      throw new ArithmeticException();
    }
    int second = calculator.top();
    calculator.pop();
    int quotient = second / first;
    calculator.push(quotient);
    numElements--; // removes two numbers, adds 1
  }

  /**
   * When called, takes the top two items off the stack and divides one from the other.
   * This results in the two items being removed from the stack, and the remainder being added.
   *
   * @throws IllegalStateException if there are insufficient elements to perform the operation.
   * @throws ArithmeticException for dividing by zero.
   */
  private void modulus() throws IllegalStateException, ArithmeticException {
    if (!hasEnough()) {
      throw new IllegalStateException();
    }
    int first = calculator.top();
    calculator.pop();
    if (first == 0) {
      calculator.push(first);
      throw new ArithmeticException();
    }
    int second = calculator.top();
    calculator.pop();
    int remainder =  second % first;
    calculator.push(remainder);
    numElements--; // removes two numbers, adds 1
  }

  /**
   * Determines if the System input is an operation.
   * Calls the necessary function if the operation is used.
   *
   * @param input is the String being checked.
   * @return {@code true} if the value is an operation, {@code false} if not
   */
  private boolean isOp(String input) {
    boolean multi = "*".equals(input);
    boolean div = "/".equals(input);
    boolean add = "+".equals(input);
    boolean sub = "-".equals(input);
    boolean mod = "%".equals(input);

    return (multi || div || add || sub || mod);
  }

  /**
   * Calls the necessary function for the operation used.
   * Note: assumes that the token is a valid operation. (checked by isOp())
   *
   * @param input is the token being used.
   */
  private void callOp(String input) {
    switch (input) {
      case "*":
        multiply();
        break;
      case "/":
        divide();
        break;
      case "+":
        add();
        break;
      case "-":
        subtract();
        break;
      default:
        modulus(); // only leftover operation
        break;
    }
  }

  /**
   * When called, prints the entire LinkedStack in its current state.
   */
  private void question() {
    System.out.println(calculator.toString());
  }

  /**
   * When called, prints the current top value of theLinkedStack.
   */
  private void dot() {
    try {
      int top = calculator.top();
      System.out.println(top);
    } catch (EmptyException ex) {
      System.out.println("ERROR: calculator history is empty");
    }
  }

  /**
   * Determines if the System input is a valid token.
   * Calls the necessary function if the token is used.
   *
   * @param input is the String being checked.
   * @throws InputMismatchException if there is a bad token
   */
  private void isToken(String input) throws InputMismatchException {
    switch (input) {
      case ".":
        dot();
        break;
      case "?":
        question();
        break;
      case "!":
        return;
      default:
        throw new InputMismatchException();
    }
  }

  /**
   * Takes the input, and checks if it is a valid token.
   * If it is valid, the respective function will be used.
   *
   * @param input the token to be checked
   */
  public void calculate(String input) {
    if (isOp(input)) {
      try {
        callOp(input); // calls specified operation within the callOp() method
      } catch (IllegalStateException ex) {
        System.out.println("ERROR: there are insufficient values to perform operation");
      } catch (ArithmeticException ex) {
        System.out.println("ERROR: cannot divide by zero");
      }
    } else {
      try {
        isToken(input);
      } catch (InputMismatchException ex) {
        System.out.println("ERROR: bad token");
      }
    }
  }

  /**
   * The main function.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {
    String input = "";
    Scanner in = new Scanner(System.in);
    Calc calc = new Calc();

    while (!("!".equals(input))) {
      input = in.next(); // takes the next string that's not whitespace as input
      if (calc.isNum(input)) { // cast to integer and add to stack
        int num = Integer.parseInt(input);
        calc.insert(num);
      } else {
        calc.calculate(input); // performs the call as indicated by the token
        // Displays error message if invalid token, or there is a problem with the operation.
      }
    }
    in.close();
  }
}
