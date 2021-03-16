package stack;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 150. 逆波兰表达式求值
     * 根据 逆波兰表示法，求表达式的值。
     *
     * 有效的运算符包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
     *
     *
     *
     * 说明：
     *
     * 整数除法只保留整数部分。
     * 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
     *
     *
     * 示例 1：
     *
     * 输入: ["2", "1", "+", "3", "*"]
     * 输出: 9
     * 解释: 该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
     * 示例 2：
     *
     * 输入: ["4", "13", "5", "/", "+"]
     * 输出: 6
     * 解释: 该算式转化为常见的中缀算术表达式为：(4 + (13 / 5)) = 6
     * 示例 3：
     *
     * 输入: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
     * 输出: 22
     * 解释:
     * 该算式转化为常见的中缀算术表达式为：
     *   ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
     * = ((10 * (6 / (12 * -11))) + 17) + 5
     * = ((10 * (6 / -132)) + 17) + 5
     * = ((10 * 0) + 17) + 5
     * = (0 + 17) + 5
     * = 17 + 5
     * = 22
     *
     *
     * 逆波兰表达式：
     *
     * 逆波兰表达式是一种后缀表达式，所谓后缀就是指算符写在后面。
     *
     * 平常使用的算式则是一种中缀表达式，如 ( 1 + 2 ) * ( 3 + 4 ) 。
     * 该算式的逆波兰表达式写法为 ( ( 1 2 + ) ( 3 4 + ) * ) 。
     * 逆波兰表达式主要有以下两个优点：
     * 去掉括号后表达式无歧义，上式即便写成 1 2 + 3 4 + * 也可以依据次序计算出正确结果。
     * 适合用栈操作运算：遇到数字则入栈；遇到算符则取出栈顶两个数字进行计算，并将结果压入栈中。
     *
     * 题解思路：根据逆波兰表达式的定义，我们只需要检测当前的字符串是数字还是运算符号。
     *         如果式运算符，那么将栈顶两个元素取出来计算然后再将结果存入栈中。
     *         为了更加清晰明了我们的代码，我们采用try-catch结构来简化我们的代码，将当前的字符串转换为数字，如果抛出异常，说明是计算符号。
     *         那么这个时候直接退栈进行计算存栈。否则则将转换的数字存入栈中。
     * @param tokens
     * @return
     */
    public int evalRPN(List<String> tokens) {
        Stack<Integer> stack=new Stack<>();
        int i=0;
        while(i<tokens.size()){
            try{
                stack.push(Integer.parseInt(tokens.get(i)));
            }
            catch (NumberFormatException e){
                int a=stack.pop();
                int b=stack.pop();
                int res=calculate(tokens.get(i),b,a);
                stack.push(res);
            }
            i++;
        }
        return stack.peek();
    }
    private int calculate(String cal,int a,int b){
        switch (cal){
            case "+":return a+b;
            case "-":return a-b;
            case "/":return a/b;
            case "*":return a*b;
            default: return 0;
        }
    }

    /**
     * 227. 基本计算器 II
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     *
     * 整数除法仅保留整数部分。
     *
     *
     *
     * 示例 1：
     *
     * 输入：s = "3+2*2"
     * 输出：7
     * 示例 2：
     *
     * 输入：s = " 3/2 "
     * 输出：1
     * 示例 3：
     *
     * 输入：s = " 3+5 / 2 "
     * 输出：5
     *
     *
     *
     * 概念：上述的表达就是一个中缀表达式
     * 基本思路：将中缀表达式转换为逆序表达式，也就是著名的逆波兰表达式。
     * 我们需要定义一个一个算符优先级函数。基本的运算符包括:(,),+,-,*,/
     *
     * 由于乘除优先于加减计算，因此不妨考虑先进行所有乘除运算，并将这些乘除运算后的整数值放回原表达式的相应位置，则随后整个表达式的值，就等于一系列整数加减后的值。
     *
     * 基于此，我们可以用一个栈，保存这些（进行乘除运算后的）整数的值。对于加减号后的数字，将其直接压入栈中；对于乘除号后的数字，可以直接与栈顶元素计算，并替换栈顶元素为计算后的结果。
     *
     * 具体来说，遍历字符串 ss，并用变量 \textit{preSign}preSign 记录每个数字之前的运算符，对于第一个数字，其之前的运算符视为加号。每次遍历到数字末尾时，根据 \textit{preSign}preSign 来决定计算方式：
     *
     * 加号：将数字压入栈；
     * 减号：将数字的相反数压入栈；
     * 乘除号：计算数字与栈顶元素，并将栈顶元素替换为计算结果。
     * 代码实现中，若读到一个运算符，或者遍历到字符串末尾，即认为是遍历到了数字末尾。处理完该数字后，更新 \textit{preSign}preSign 为当前遍历的字符。
     *
     * 遍历完字符串 ss 后，将栈中元素累加，即为该字符串表达式的值。
     * @param s
     * @return
     */
    public List<String> calculator(String s) {
        List<String> stringStack=new ArrayList<>();
        Stack<String> operators=new Stack<>();//运算符
        int j=0;
        int i=0;
        while(j<s.length()&&Character.isDigit(s.charAt(j)))j++;
        stringStack.add(s.substring(i,j));
        operators.push(String.valueOf(s.charAt(j)));
        j++;
        i=j;
        while(j<s.length()&&Character.isDigit(s.charAt(j)))j++;
        stringStack.add(s.substring(i,j));
        int k=0;
        while(j<s.length()){
            int priority=operatorPriority(String.valueOf(s.charAt(j)),operators.peek());
            switch (priority){
                    case 0:
                        stringStack.add(operators.pop());
                        operators.push(String.valueOf(s.charAt(j)));
                        j++;
                        k=j;
                        while(k<s.length()&&Character.isDigit(s.charAt(k)))k++;
                        stringStack.add(s.substring(j,k));
                        break;
                    case 1:
                        String op= String.valueOf(s.charAt(j));
                        j++;
                        k=j;
                        while(k<s.length()&&Character.isDigit(s.charAt(k)))k++;
                        stringStack.add(s.substring(j,k));
                        stringStack.add(op);
                        break;
                default:break;
                }
                j=k;
            }
        if(!operators.isEmpty()) stringStack.add(operators.pop());
        return stringStack;
        }


    /**
     * a>b?1:a==b?0:-1;
     * @param a
     * @param b
     * @return
     */
    public int operatorPriority(String a,String b){
       if(a.equals("-") && b.equals("+") || a.equals("+") && b.equals("-") || a.equals("*") && b.equals("/") || a.equals("/") && b.equals("*"))return 0;
       else if((a.equals("+") || a.equals("-"))&&(b.equals("*") || b.equals("/")))return -1;
       else return 1;
    }

    public static void main(String[] args){
       // String[] tokens={"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
        //System.out.println(new StackOperating().evalRPN(tokens));
        //System.out.println(Integer.parseInt("-"));
        //Integer.MAX_VALUE
        String s="3/2";
        StackOperating stackOperating=new StackOperating();
        List<String> list=stackOperating.calculator(s);
        System.out.println(stackOperating.evalRPN(list));
    }
}
