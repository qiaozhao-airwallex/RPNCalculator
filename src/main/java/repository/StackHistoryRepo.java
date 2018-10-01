package repository;

import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Stack;

public class StackHistoryRepo {
    private Stack<Stack<BigDecimal>> history = new Stack<>();

    @SuppressWarnings("unchecked")
    public void push(Stack<BigDecimal> stack) {
        Stack<BigDecimal> clone = (Stack<BigDecimal>)stack.clone();
        history.push(clone);
    }

    public Stack<BigDecimal> pop() {
        try {
            return history.pop();
        } catch (EmptyStackException e) {
            return new Stack<>();
        }
    }

    public int size() {
        return history.size();
    }
}
