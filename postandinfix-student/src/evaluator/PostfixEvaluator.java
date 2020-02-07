package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class PostfixEvaluator extends Evaluator {

  private LinkedStack<Integer> stack = new LinkedStack<Integer>();

  /** return stack object (for testing purpose). */
  public LinkedStack<Integer> getStack() { 
    return stack; 
  }

  /** This method performs one step of evaluation of a postfix expression.
   *  The input is a token. Follow the postfix evaluation algorithm
   *  to implement this method. If the expression is invalid, throw an
   *  exception with the corresponding exception message.
   */
  public void evaluate_step(String token) throws Exception {
    if (isOperand(token)) {
      stack.push(Integer.parseInt(token));
    } else {
      int result;
      int a;
      try{
        a = stack.pop();  
      }catch(NullPointerException e) {
        throw new Exception("too few operands");
      }
      if(token.equals("!")) {
        result = a * -1;
        stack.push(result);
        return;
      }
      int b;
      try{
        b = stack.pop();  
      }catch(NullPointerException e) {
        throw new Exception("too few operands");
      }
      if(token.equals("*")) {
        result = b * a;
      }else if(token.equals("+")) {
        result = b + a;
      }else if(token.equals("-")) {
        result = b - a;
      }else if(token.equals("/")){
        if(a == 0) throw new Exception("division by zero");
        result = b / a;
      }else {
        throw new Exception("invalid operator");
      }
      stack.push(result);
    }
  }

  /** This method evaluates a postfix expression (defined by expr)
   *  and returns the evaluation result. It throws an Exception
   *  if the postfix expression is invalid.
   */
  public Integer evaluate(String expr) throws Exception {
    for (String token : ArithParser.parse(expr)) {
      evaluate_step(token);
    }
    // The stack should have exactly one operand after evaluation is done
    if (stack.size() > 1) {
      throw new Exception("too many operands");
    } else if (stack.size() < 1) {
      throw new Exception("too few operands");
    }
    return stack.pop(); 
  }

  public static void main(String[] args) throws Exception {
    //System.out.println(new PostfixEvaluator().evaluate("50 25 ! / 3 +"));
    System.out.println(new PostfixEvaluator().evaluate("1 + 2"));

  }
}