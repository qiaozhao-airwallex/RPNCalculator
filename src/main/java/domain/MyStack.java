package domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.stream.Collectors;

import exception.InsufficientParameterException;
import exception.InvalidInputException;
import repository.StackHistoryRepo;

public class MyStack {
    private Stack<BigDecimal> stackData;
    private StackHistoryRepo stackHistory;

    public MyStack(Stack<BigDecimal> stackData, StackHistoryRepo stackHistory) {
        this.stackData = stackData;
        this.stackHistory = stackHistory;
    }

    public MyStack push(BigDecimal value) {
        stackData.push(value);
        return this;
    }

    public BigDecimal pop() {
        return stackData.pop();
    }

    public MyStack push(String token) {
        if (!"undo".equals(token)) {
            stackHistory.push(this);
        }
        try {
            switch (token) {
                case "sqrt":
                    return sqrt();
                case "-":
                    return subtract();
                case "+":
                    return add();
                case "*":
                    return multiply();
                case "/":
                    return divide();
                case "clear":
                    return clear();
                case "undo":
                    return undo();
                default:
                    this.push(new BigDecimal(token, new MathContext(15, RoundingMode.DOWN)));
            }
        } catch (EmptyStackException e) {
            int position = stackHistory.size() * 2 - 1;
            MyStack lastStack = stackHistory.pop();
            stackData = lastStack.getStackData();
            stackHistory = lastStack.getStackHistory();
            throw new InsufficientParameterException(token, position);
        } catch (NumberFormatException e) {
            MyStack lastStack = stackHistory.pop();
            stackData = lastStack.getStackData();
            stackHistory = lastStack.getStackHistory();
            throw new InvalidInputException(token);
        }
        return this;
    }

    public MyStack undo() {
        MyStack lastStack = stackHistory.pop();
        stackData = lastStack.getStackData();
        stackHistory = lastStack.getStackHistory();
        return this;
    }

    public MyStack divide() {
        BigDecimal operand1 = pop();
        BigDecimal operand2 = pop();
        this.push(operand2.divide(operand1));
        return this;
    }

    public MyStack multiply() {
        BigDecimal operand1 = pop();
        BigDecimal operand2 = pop();
        this.push(operand2.multiply(operand1));
        return this;
    }

    public MyStack add() {
        BigDecimal operand1 = pop();
        BigDecimal operand2 = pop();
        this.push(operand2.add(operand1));
        return this;
    }

    public MyStack subtract() {
        BigDecimal operand1 = pop();
        BigDecimal operand2 = pop();
        this.push(operand2.subtract(operand1));
        return this;
    }

    public MyStack sqrt() {
        BigDecimal lastNumber = pop();
        double sqrtRoot = Math.sqrt(lastNumber.doubleValue());
        this.push(BigDecimal.valueOf(sqrtRoot));
        return this;
    }

    public MyStack clear() {
        stackData.clear();
        return this;
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

    public Stack<BigDecimal> getStackData() {
        return stackData;
    }

    public void setStackData(Stack<BigDecimal> stackData) {
        this.stackData = stackData;
    }

    public MyStack clone() {
        Stack<BigDecimal> clone = (Stack<BigDecimal>)stackData.clone();
        MyStack myStack = new MyStack(clone, stackHistory);
        return myStack;
    }

    public StackHistoryRepo getStackHistory() {
        return stackHistory;
    }
}
