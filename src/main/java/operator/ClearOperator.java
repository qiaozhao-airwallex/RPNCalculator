package operator;

import domain.MyStack;
import repository.StackHistoryRepo;

public class ClearOperator implements Operator {
    @Override
    public void apply(MyStack myStack, StackHistoryRepo stackHistory) {
        stackHistory.push(myStack.popAll());
    }
}
