package swordPointingOffer;

import java.util.*;

public class MinStack {
    List<Integer> stack;
    Queue<Integer> minStack;
    /** initialize your data structure here. */
    public MinStack() {
        stack=new ArrayList<>();
        minStack= new PriorityQueue<>();
    }
    public void push(int x) {
        stack.add(x);
        minStack.add(x);
    }

    public void pop() {
        int e=stack.remove(stack.size()-1);
        minStack.remove(e);
    }

    public int top() {
        return stack.get(stack.size()-1);
    }

    public int min() {
        return minStack.peek();
    }
}
