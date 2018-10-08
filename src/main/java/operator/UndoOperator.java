package operator;

import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Stack;

import domain.MyStack;
import exception.InsufficientHistoryException;
import repository.StackHistoryRepo;

public class UndoOperator implements Operator{
    @Override
    public void apply(MyStack myStack, StackHistoryRepo stackHistory) {
        try {
            Stack<BigDecimal> lastStep = stackHistory.pop();
            myStack.pop();
            while (!lastStep.isEmpty()) {
                myStack.push(lastStep.pop());
            }
        } catch (EmptyStackException e) {
            throw new InsufficientHistoryException();
        }
    }
}
