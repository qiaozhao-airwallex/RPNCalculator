package service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.InsufficientParameterException;
import exception.InvalidInputException;
import repository.StackHistoryRepo;
import repository.StackRepo;

public class StackServiceTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private StackRepo stackRepo = new StackRepo();
    private StackHistoryRepo stackHistoryRepo = new StackHistoryRepo();
    private StackService stackService = new StackService(stackRepo, stackHistoryRepo);

    @Test
    public void shouldPushToRepoOperandToStack() {
        assertThat(stackService.pushToRepo("5").display(), is("5"));
        assertThat(stackService.pushToRepo("2").display(), is("5 2"));
    }

    @Test
    public void shouldBeAbleToClearStack() {
        stackService.pushToRepo("5");
        assertThat(stackService.pushToRepo("clear").display(), is(""));
    }

    @Test
    public void shouldCalculateSqrtOperator() {
        stackService.pushToRepo("2");
        assertThat(stackService.pushToRepo("sqrt").display(), is("1.4142135623"));

        stackService.pushToRepo("4");
        assertThat(stackService.pushToRepo("sqrt").display(), is("1.4142135623 2"));
    }

    @Test
    public void shouldCalculateSubtractionOperator() {
        stackService.pushToRepo("5");
        stackService.pushToRepo("2");
        assertThat(stackService.pushToRepo("-").display(), is("3"));
        stackService.pushToRepo("3");
        assertThat(stackService.pushToRepo("-").display(), is("0"));
    }

    @Test
    public void shouldCalculateAdditionOperator() {
        stackService.pushToRepo("5");
        stackService.pushToRepo("2");
        assertThat(stackService.pushToRepo("+").display(), is("7"));
    }

    @Test
    public void shouldCalculateMultiplicationOperator() {
        stackService.pushToRepo("5");
        stackService.pushToRepo("2");
        assertThat(stackService.pushToRepo("*").display(), is("10"));
    }

    @Test
    public void shouldCalculateDivisionOperator() {
        stackService.pushToRepo("5");
        stackService.pushToRepo("2");
        assertThat(stackService.pushToRepo("/").display(), is("2.5"));
    }

    @Test
    public void shouldBeAbleToUndoLastOperation() {
        stackService.pushToRepo("5");
        stackService.pushToRepo("4");
        stackService.pushToRepo("3");
        stackService.pushToRepo("2");
        assertThat(stackService.pushToRepo("undo").display(), is("5 4 3"));
        assertThat(stackService.pushToRepo("undo").display(), is("5 4"));
    }

    @Test
    public void testExample4() {
        stackService.pushToRepo("5");
        stackService.pushToRepo("4");
        stackService.pushToRepo("3");
        stackService.pushToRepo("2");
        stackService.pushToRepo("undo");
        stackService.pushToRepo("undo");
        stackService.pushToRepo("*");
        assertThat(stackRepo.display(), is("20"));
        stackService.pushToRepo("5");
        stackService.pushToRepo("*");
        assertThat(stackRepo.display(), is("100"));
        stackService.pushToRepo("undo");
        assertThat(stackRepo.display(), is("20 5"));
    }

    @Test
    public void testExample5() {
        stackService.pushToRepo("7");
        stackService.pushToRepo("12");
        stackService.pushToRepo("2");
        stackService.pushToRepo("/");
        assertThat(stackRepo.display(), is("7 6"));
        stackService.pushToRepo("*");
        assertThat(stackRepo.display(), is("42"));
        stackService.pushToRepo("4");
        stackService.pushToRepo("/");
        assertThat(stackRepo.display(), is("10.5"));
    }

    @Test
    public void testExample6() {
        stackService.pushToRepo("1");
        stackService.pushToRepo("2");
        stackService.pushToRepo("3");
        stackService.pushToRepo("4");
        stackService.pushToRepo("5");
        assertThat(stackRepo.display(), is("1 2 3 4 5"));
        stackService.pushToRepo("*");
        assertThat(stackRepo.display(), is("1 2 3 20"));
        stackService.pushToRepo("clear");
        stackService.pushToRepo("3");
        stackService.pushToRepo("4");
        stackService.pushToRepo("-");
        assertThat(stackRepo.display(), is("-1"));
    }

    @Test
    public void testExample7() {
        stackService.pushToRepo("1");
        stackService.pushToRepo("2");
        stackService.pushToRepo("3");
        stackService.pushToRepo("4");
        stackService.pushToRepo("5");
        assertThat(stackRepo.display(), is("1 2 3 4 5"));
        stackService.pushToRepo("*");
        stackService.pushToRepo("*");
        stackService.pushToRepo("*");
        stackService.pushToRepo("*");
        assertThat(stackRepo.display(), is("120"));
    }

    @Test
    public void testExample8() {
        try {
            exceptionRule.expect(InsufficientParameterException.class);
            exceptionRule.expectMessage("operator * (position: 15): insufficient parameters");
            stackService.pushToRepo("1");
            stackService.pushToRepo("2");
            stackService.pushToRepo("3");
            stackService.pushToRepo("*");
            stackService.pushToRepo("5");
            stackService.pushToRepo("+");
            stackService.pushToRepo("*");
            stackService.pushToRepo("*");
            stackService.pushToRepo("6");
            stackService.pushToRepo("5");
        } finally {
            assertThat(stackRepo.display(), is("11"));
        }
    }

    @Test
    public void shouldDisplayWarningWhenMissingSufficientParameters() {
        exceptionRule.expect(InsufficientParameterException.class);
        exceptionRule.expectMessage("operator * (position: 1): insufficient parameters");
        stackService.pushToRepo("*").display();
    }

    @Test
    public void shouldThrowExceptionWhenInputIsNotANumberOrOperator() {
        exceptionRule.expect(InvalidInputException.class);
        exceptionRule.expectMessage("xyz is not a number or an operator");
        stackService.pushToRepo("xyz").display();
    }
}
