package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class InfixEvaluator extends Evaluator {

  private LinkedStack<String> operators = new LinkedStack<String>();
  private LinkedStack<Integer> operands  = new LinkedStack<Integer>();

  /** return stack object (for testing purpose). */
  public LinkedStack<String> getOperatorStack() { 
    return operators; 
  }

  public LinkedStack<Integer> getOperandStack() { 
    return operands;
  }


  /** This method performs one step of evaluation of a infix expression.
   *  The input is a token. Follow the infix evaluation algorithm
   *  to implement this method. If the expression is invalid, throw an
   *  exception with the corresponding exception message.
   */
  public void evaluate_step(String token) throws Exception {
    if (isOperand(token)) {
      // TODO: What do we do if the token is an operand?
      operands.push(Integer.parseInt(token));
    } else {
      /* TODO: What do we do if the token is an operator?
               If the expression is invalid, make sure you throw
               an exception with the correct message.

               You can call precedence(token) to get the precedence
               value of an operator. It's already defined in 
               the Evaluator class.
       */ 
      if(token.equals("(")) {
        operators.push(token);
      }else if(operators.isEmpty() || precedence(token) > precedence(operators.top())) {
        operators.push(token);
      }else if(token.equals(")")){
        //do process_operator until ( is found
        while(!operators.top().equals("(")) {
          process_operator();
          if(operators.isEmpty()) {
            throw new Exception("missing (");
          }
        }
        operators.pop();
      }else { //Otherwise (i.e. when none of the above applies), do process_operator repeatedly 
        //until the current token’s precedence is greater than the operators stack’s top element 
        //(or if there is nothing left in the operators stack). Then push the current token to the 
        //operators stack.
        while(true) {
          if(operators.isEmpty() || precedence(token) > precedence(operators.top())) {
            operators.push(token);
            break;
          }
          process_operator();
        }
      }
    }
  }
  public void process_operator() throws Exception {
    String oper = operators.pop();
    int result = -1;
    if(oper.equals("!")) {
      int operand;
      try {
        operand = operands.pop();
      }catch(Exception e) {
        throw new Exception("too few operands");
      }
      result = operand * -1;
    }else {
      int operand1;
      int operand2;
      try {
        operand1 = operands.pop();
        operand2 = operands.pop();

      }catch(Exception e) {
        throw new Exception("too few operands");
      }
      if(oper.equals("+")) {
        result = operand2 + operand1;
      }else if(oper.equals("-")) {
        result = operand2 - operand1;
      }else if(oper.equals("*")) {
        result = operand2 * operand1;
      }else if(oper.equals("/")) {
        if(operand1 == 0) {
          throw new Exception("division by zero");
        }
        result = operand2 / operand1;
      }else {
        //operator isnt valid
        System.out.println("Operator: " + oper);
        throw new Exception("invalid operator");
      }
    }
    operands.push(result);
  }

  /** This method evaluates an infix expression (defined by expr)
   *  and returns the evaluation result. It throws an Exception object
   *  if the infix expression is invalid.
   */
  public Integer evaluate(String expr) throws Exception {

    for (String token : ArithParser.parse(expr)) {
      evaluate_step(token);
    }

    /* TODO: what do we do after all tokens have been processed? */
    while(!operators.isEmpty()) {
      process_operator();
    }
    // The operand stack should have exactly one operand after the evaluation is done
    if (operands.size() > 1) {
      throw new Exception("too many operands");
    } else if (operands.size() < 1) {
      throw new Exception("too few operands");
    }

    return operands.pop();
  }

  public static void main(String[] args) throws Exception {
    //System.out.println(new InfixEvaluator().evaluate("5+(5+2*(5+9))/!8"));
    //System.out.println(new InfixEvaluator().evaluate("5*(5+2)"));

  }
}
