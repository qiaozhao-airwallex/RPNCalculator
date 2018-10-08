package domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.stream.Collectors;

import exception.InsufficientParameterException;
import operator.Operator;
import operator.OperatorFactory;
import repository.StackHistoryRepo;

public class MyStack {
    private Stack<BigDecimal> stackData;
    private StackHistoryRepo stackHistory;
    private OperatorFactory operatorFactory;

    public MyStack(Stack<BigDecimal> stackData,
                   StackHistoryRepo stackHistory,
                   OperatorFactory operatorFactory) {
        this.stackData = stackData;
        this.stackHistory = stackHistory;
        this.operatorFactory = operatorFactory;
    }

    public void push(BigDecimal value) {
        stackData.push(value);
    }

    public BigDecimal pop() {
        return stackData.pop();
    }

    public Stack<BigDecimal> popAll() {
        Stack<BigDecimal> all = new Stack<>();
        while (!stackData.isEmpty()) {
            all.push(stackData.pop());
        }
        return all;
    }

    public void push(String token) {
        try {
            BigDecimal number = new BigDecimal(token, new MathContext(15, RoundingMode.DOWN));
            this.push(number);
            stackHistory.push();
        } catch (NumberFormatException e) {
            Operator operator = operatorFactory.createOperator(token);
            try {
                operator.apply(this, stackHistory);
            } catch (EmptyStackException e1) {
                int position = stackHistory.size() * 2 + 1;
                throw new InsufficientParameterException(token, position);
            }
        }
    }

    public String display() {
        return stackData.stream()
               .map(s -> formatBigDecimal(s))
               .collect(Collectors.joining(" "));
    }

    private String formatBigDecimal(BigDecimal number) {
        DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.DOWN);
        df.setMaximumFractionDigits(10);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        return df.format(number);
    }

    public int size() {
        return stackData.size();
    }
}
