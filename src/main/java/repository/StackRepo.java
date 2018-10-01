package repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.stream.Collectors;

public class StackRepo {
    private Stack<BigDecimal> stack = new Stack<>();

    public StackRepo push(BigDecimal value) {
        stack.push(value);
        return this;
    }

    public BigDecimal pop() {
        return stack.pop();
    }

    public String display() {
        return stack.stream()
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

    public Stack<BigDecimal> getStack() {
        return stack;
    }

    public void clear() {
        stack.clear();
    }

    public void setStack(Stack<BigDecimal> stack) {
        this.stack = stack;
    }
}
