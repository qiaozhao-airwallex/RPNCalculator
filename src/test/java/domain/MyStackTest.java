package domain;

import static org.hamcrest.MatcherAssert.*;

import java.util.Stack;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.InsufficientParameterException;
import exception.InvalidInputException;
import operator.OperatorFactory;
import repository.StackHistoryRepo;

public class MyStackTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private MyStack myStack = new MyStack(new Stack<>(), new StackHistoryRepo(), new OperatorFactory());

    @Test
    public void shouldPushOperandToStack() {
        assertThat(myStack.push("5").display(), Is.is("5"));
        assertThat(myStack.push("2").display(), Is.is("5 2"));
    }

    @Test
    public void shouldBeAbleToClearStack() {
        myStack.push("5");
        assertThat(myStack.push("clear").display(), Is.is(""));
    }

    @Test
    public void shouldCalculateSqrtOperator() {
        myStack.push("2");
        assertThat(myStack.push("sqrt").display(), Is.is("1.4142135623"));

        myStack.push("4");
        assertThat(myStack.push("sqrt").display(), Is.is("1.4142135623 2"));
    }

    @Test
    public void shouldCalculateSubtractionOperator() {
        myStack.push("5");
        myStack.push("2");
        assertThat(myStack.push("-").display(), Is.is("3"));
        myStack.push("3");
        assertThat(myStack.push("-").display(), Is.is("0"));
    }

    @Test
    public void shouldCalculateAdditionOperator() {
        myStack.push("5");
        myStack.push("2");
        assertThat(myStack.push("+").display(), Is.is("7"));
    }

    @Test
    public void shouldCalculateMultiplicationOperator() {
        myStack.push("5");
        myStack.push("2");
        assertThat(myStack.push("*").display(), Is.is("10"));
    }

    @Test
    public void shouldCalculateDivisionOperator() {
        myStack.push("5");
        myStack.push("2");
        assertThat(myStack.push("/").display(), Is.is("2.5"));
    }

    @Test
    public void shouldBeAbleToUndoLastOperation() {
        myStack.push("5");
        myStack.push("4");
        myStack.push("3");
        myStack.push("2");
        assertThat(myStack.push("undo").display(), Is.is("5 4 3"));
        assertThat(myStack.push("undo").display(), Is.is("5 4"));
    }

    @Test
    public void testExample4() {
        myStack.push("5");
        myStack.push("4");
        myStack.push("3");
        myStack.push("2");
        myStack.push("undo");
        myStack.push("undo");
        myStack.push("*");
        MatcherAssert.assertThat(myStack.display(), Is.is("20"));
        myStack.push("5");
        myStack.push("*");
        MatcherAssert.assertThat(myStack.display(), Is.is("100"));
        myStack.push("undo");
        MatcherAssert.assertThat(myStack.display(), Is.is("20 5"));
    }

    @Test
    public void testExample5() {
        myStack.push("7");
        myStack.push("12");
        myStack.push("2");
        myStack.push("/");
        MatcherAssert.assertThat(myStack.display(), Is.is("7 6"));
        myStack.push("*");
        MatcherAssert.assertThat(myStack.display(), Is.is("42"));
        myStack.push("4");
        myStack.push("/");
        MatcherAssert.assertThat(myStack.display(), Is.is("10.5"));
    }

    @Test
    public void testExample6() {
        myStack.push("1");
        myStack.push("2");
        myStack.push("3");
        myStack.push("4");
        myStack.push("5");
        MatcherAssert.assertThat(myStack.display(), Is.is("1 2 3 4 5"));
        myStack.push("*");
        MatcherAssert.assertThat(myStack.display(), Is.is("1 2 3 20"));
        myStack.push("clear");
        myStack.push("3");
        myStack.push("4");
        myStack.push("-");
        MatcherAssert.assertThat(myStack.display(), Is.is("-1"));
    }

    @Test
    public void testExample7() {
        myStack.push("1");
        myStack.push("2");
        myStack.push("3");
        myStack.push("4");
        myStack.push("5");
        MatcherAssert.assertThat(myStack.display(), Is.is("1 2 3 4 5"));
        myStack.push("*");
        myStack.push("*");
        myStack.push("*");
        myStack.push("*");
        MatcherAssert.assertThat(myStack.display(), Is.is("120"));
    }

    @Test
    public void testExample8() {
        try {
            exceptionRule.expect(InsufficientParameterException.class);
            exceptionRule.expectMessage("operator * (position: 15): insufficient parameters");
            myStack.push("1");
            myStack.push("2");
            myStack.push("3");
            myStack.push("*");
            myStack.push("5");
            myStack.push("+");
            myStack.push("*");
            myStack.push("*");
            myStack.push("6");
            myStack.push("5");
        } finally {
            MatcherAssert.assertThat(myStack.display(), Is.is("11"));
        }
    }

    @Test
    public void shouldDisplayWarningWhenMissingSufficientParameters() {
        exceptionRule.expect(InsufficientParameterException.class);
        exceptionRule.expectMessage("operator * (position: 1): insufficient parameters");
        myStack.push("*").display();
    }

    @Test
    public void shouldThrowExceptionWhenInputIsNotANumberOrOperator() {
        exceptionRule.expect(InvalidInputException.class);
        exceptionRule.expectMessage("xyz is not a number or an operator");
        myStack.push("xyz").display();
    }
}
