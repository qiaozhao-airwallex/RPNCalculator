package operator;

import java.math.BigDecimal;

import domain.MyStack;
import repository.StackHistoryRepo;

public class SqrtOperator implements Operator{
    @Override
    public void apply(MyStack myStack, StackHistoryRepo stackHistory) {
        BigDecimal lastNumber = myStack.pop();
        double sqrtRoot = Math.sqrt(lastNumber.doubleValue());
        myStack.push(BigDecimal.valueOf(sqrtRoot));
        stackHistory.push(lastNumber);
    }
}
