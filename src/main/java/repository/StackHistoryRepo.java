package repository;

import java.math.BigDecimal;
import java.util.Stack;

public class StackHistoryRepo {
    private Stack<Stack<BigDecimal>> history = new Stack<>();

    public int size() {
        return history.size();
    }

    public void push(BigDecimal... operands) {
        Stack<BigDecimal> diffInLastOperation = new Stack<>();
        for (BigDecimal operand: operands) {
            diffInLastOperation.add(operand);
        }
        history.push(diffInLastOperation);
    }

    public void push(Stack<BigDecimal> operands) {
        Stack<BigDecimal> diffInLastOperation = new Stack<>();
        for (BigDecimal operand: operands) {
            diffInLastOperation.add(operand);
        }
        history.push(diffInLastOperation);
    }

    public Stack<BigDecimal> pop() {
        return history.pop();
    }
}
