package operator;

import java.math.BigDecimal;

import domain.MyStack;
import repository.StackHistoryRepo;

public class SubtractOperator implements Operator{
    @Override
    public void apply(MyStack myStack, StackHistoryRepo stackHistory) {
        assertStackSize(myStack,2);
        BigDecimal operand1 = myStack.pop();
        BigDecimal operand2 = myStack.pop();
        stackHistory.push(operand1, operand2);
        myStack.push(operand2.subtract(operand1));
    }
}
