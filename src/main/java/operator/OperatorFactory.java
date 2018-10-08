package operator;

import exception.InvalidInputException;

public class OperatorFactory {
    public Operator createOperator(String token) {
        switch (token) {
            case "sqrt":
                return new SqrtOperator();
            case "-":
                return new SubtractOperator();
            case "+":
                return new AddOperator();
            case "*":
                return new MultiplyOperator();
            case "/":
                return new DivideOperator();
            case "clear":
                return new ClearOperator();
            case "undo":
                return new UndoOperator();

        }
        throw new InvalidInputException(token);
    }
}
