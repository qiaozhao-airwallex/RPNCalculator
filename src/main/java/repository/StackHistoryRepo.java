package repository;

import java.util.EmptyStackException;
import java.util.Stack;

import domain.MyStack;
import exception.InsufficientHistoryException;

public class StackHistoryRepo {
    private Stack<MyStack> history;

    public StackHistoryRepo(Stack<MyStack> history) {
        this.history = history;
    }

    public void push(MyStack stack) {
        MyStack clone = stack.clone();
        history.push(clone);
    }

    public MyStack pop() {
        try {
            return history.pop();
        } catch (EmptyStackException e) {
            throw new InsufficientHistoryException();
        }
    }

    public int size() {
        return history.size();
    }

    public StackHistoryRepo clone() {
        Stack<MyStack> clone = (Stack<MyStack>)history.clone();
        return new StackHistoryRepo(clone);
    }

}
