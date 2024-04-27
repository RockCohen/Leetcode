package stack;

import java.util.*;

public class EvalRPN {
    private EvalRPN(){}

    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (token.matches("^[0-9]*$")) {
                stack.push(Integer.parseInt(token));
            } else {
                int a = stack.pop();
                int b = stack.pop();
                switch (token) {
                    case "+":
                        stack.add(a+b);
                    case "-":
                        stack.add(a-b);
                    case "*":
                        stack.add(a*b);
                    case "/":
                        stack.add(a/b);
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String[] tokens = {"4","13","5","/","+"};
        int i = evalRPN(tokens);
        assert i == 6;
    }
}
