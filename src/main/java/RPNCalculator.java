import java.util.Arrays;
import java.util.Scanner;

import exception.InsufficientParameterException;
import exception.InvalidInputException;
import repository.StackHistoryRepo;
import repository.StackRepo;
import service.StackService;

public class RPNCalculator {

    private static StackService stackService = new StackService(new StackRepo(), new StackHistoryRepo());

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
                        .forEach(s -> stackService.pushToRepo(s));
            } catch (InsufficientParameterException e) {
                System.out.println(e.getMessage());
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println(stackService.display());
            }
        }
    }
}
