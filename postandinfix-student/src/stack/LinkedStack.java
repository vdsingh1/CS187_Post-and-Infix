package stack;

/**
 * A {@link LinkedStack} is a generic stack that is implemented using
 * a Linked List structure to allow for unbounded size.
 */
public class LinkedStack<T> {

  // TODO: define class variables here
  public LLNode<T> top = null; //top is the head
  private int size = 0;
  /**
   * Remove and return the top element on this stack.
   * If stack is empty, return null (instead of throw exception)
   */
  public T pop() {
    // TODO
    if(isEmpty()) {
      return null;
    }
    LLNode<T> popped = top;
    top = top.link;
    size--;
    return popped.info;
  }

  /**
   * Return the top element of this stack (do not remove the top element).
   * If stack is empty, return null (instead of throw exception)
   */
  public T top() {
    // TODO
    if(isEmpty()) return null;
    return top.info;
  }

  /**
   * Return true if the stack is empty and false otherwise.
   */
  public boolean isEmpty() {
    // TODO
    return top == null;
  }

  /**
   * Return the number of elements in this stack.
   */
  public int size() {
    // TODO
    return size;
  }

  /**
   * Pushes a new element to the top of this stack.
   */
  public void push(T elem) {
    // TODO
    //make a new head
    LLNode<T> newTop = new LLNode<T>(elem);
    newTop.link = top;
    size++;
    top = newTop;
  }
  public void printStack() {
    System.out.print("Top ");
    LLNode<T> current = top;
    while(current != null) {
      System.out.print(current.info);
      current = current.link;
    }
  }
}
