package service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.EmptyStackException;

import exception.InsufficientParameterException;
import exception.InvalidInputException;
import repository.StackHistoryRepo;
import repository.StackRepo;

public class StackService {

    private StackRepo stackRepo;
    private StackHistoryRepo stackHistory;

    public StackService(final StackRepo stackRepo, final StackHistoryRepo stackHistory) {
        this.stackRepo = stackRepo;
        this.stackHistory = stackHistory;
    }

    public StackRepo pushToRepo(String token) {
        if (!"undo".equals(token)) {
            stackHistory.push(stackRepo.getStack());
        }
        try {
            switch (token) {
                case "sqrt":
                    BigDecimal lastNumber = stackRepo.pop();
                    double sqrtRoot = Math.sqrt(lastNumber.doubleValue());
                    stackRepo.push(BigDecimal.valueOf(sqrtRoot));
                    break;
                case "-":
                    BigDecimal operand1 = stackRepo.pop();
                    BigDecimal operand2 = stackRepo.pop();
                    stackRepo.push(operand2.subtract(operand1));
                    break;
                case "+":
                    operand1 = stackRepo.pop();
                    operand2 = stackRepo.pop();
                    stackRepo.push(operand2.add(operand1));
                    break;
                case "*":
                    operand1 = stackRepo.pop();
                    operand2 = stackRepo.pop();
                    stackRepo.push(operand2.multiply(operand1));
                    break;
                case "/":
                    operand1 = stackRepo.pop();
                    operand2 = stackRepo.pop();
                    stackRepo.push(operand2.divide(operand1));
                    break;
                case "clear":
                    stackRepo.clear();
                    break;
                case "undo":
                    stackRepo.setStack(stackHistory.pop());
                    break;
                default:
                    stackRepo.push(new BigDecimal(token, new MathContext(15, RoundingMode.DOWN)));
            }
        } catch (EmptyStackException e) {
            int position = stackHistory.size() * 2 - 1;
            stackRepo.setStack(stackHistory.pop());
            throw new InsufficientParameterException(token, position);
        } catch (NumberFormatException e) {
            stackRepo.setStack(stackHistory.pop());
            throw new InvalidInputException(token);
        }
        return stackRepo;
    }

    public String display() {
        return stackRepo.display();
    }
}
