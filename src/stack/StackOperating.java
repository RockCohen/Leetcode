package stack;

import java.util.Stack;

public class StackOperating {
    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     *
     * 有效字符串需满足：
     *
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     *  
     *
     * 示例 1：
     *
     * 输入：s = "()"
     * 输出：true
     * 示例 2：
     *
     * 输入：s = "()[]{}"
     * 输出：true
     * 示例 3：
     *
     * 输入：s = "(]"
     * 输出：false
     * 示例 4：
     *
     * 输入：s = "([)]"
     * 输出：false
     * 示例 5：
     *
     * 输入：s = "{[]}"
     * 输出：true
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if(s.length()==0)return true;
        Stack<Character> stack=new Stack<>();
        stack.push(s.charAt(0));
        int i=1;
        while(i<s.length()){
            if(stack.isEmpty())stack.push(s.charAt(i));
            else if(isMatch(stack.peek(),s.charAt(i)))stack.pop();
            else stack.push(s.charAt(i));
            i++;
        }
        return stack.isEmpty();

    }
    public boolean isMatch(char s,char p){
        return s == '(' && p == ')' || s == '[' && p == ']' || s == '{' && p == '}';
    }
}
