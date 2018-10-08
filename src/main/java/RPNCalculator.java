import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import exception.InsufficientHistoryException;
import exception.InsufficientParameterException;
import exception.InvalidInputException;
import operator.OperatorFactory;
import repository.StackHistoryRepo;
import domain.MyStack;

public class RPNCalculator {

    private static MyStack myStack = new MyStack(
            new Stack<>(),
            new StackHistoryRepo(),
            new OperatorFactory());

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter whitespace separated list of numbers and operators: ");


        while (true) {
            String inputs = scanner.nextLine().trim();

            if ("".equals(inputs)) continue;
            if ("quit".equals(inputs)) break;

            try {
                Arrays.asList(inputs.split(" "))
                        .stream()
                        .forEach(s -> myStack.push(s));
            } catch (InsufficientParameterException
                    | InvalidInputException
                    | InsufficientHistoryException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println(myStack.display());
            }
        }
    }
}
