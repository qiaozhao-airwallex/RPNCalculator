package operator;

import java.util.EmptyStackException;

import domain.MyStack;
import repository.StackHistoryRepo;

public interface Operator {
    void apply(MyStack myStack, StackHistoryRepo stackHistory);

    default void assertStackSize(MyStack myStack, int noOfRequiredOperands) {
        if (myStack.size() < noOfRequiredOperands) {
            throw new EmptyStackException();
        }
    }
}
