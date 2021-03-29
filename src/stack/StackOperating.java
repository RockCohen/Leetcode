package stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

public class StackOperating {
/**
 * @param s
 * @return
 * @see https://leetcode-cn.com/problems/valid-parentheses/
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
 * @author Rock
 * @param tokens
 * @return
 * @see https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/
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
* * 概念：上述的表达就是一个中缀表达式
* 基本思路：将中缀表达式转换为逆序表达式，也就是著名的逆波兰表达式。
* 我们需要定义一个一个算符优先级函数。基本的运算符包括:(,),+,-,*,/
*
* 由于乘除优先于加减计算，因此不妨考虑先进行所有乘除运算，并将这些乘除运算后的整数值放回原表达式的相应位置，
则随后整个表达式的值，就等于一系列整数加减后的值。
*
* 基于此，我们可以用一个栈，保存这些（进行乘除运算后的）整数的值。对于加减号后的数字，将其直接压入栈中；
对于乘除号后的数字，可以直接与栈顶元素计算，并替换栈顶元素为计算后的结果。
*
* 具体来说，遍历字符串 ss，并用变量 \textit{preSign}preSign 记录每个数字之前的运算符，对于第一个数字，
其之前的运算符视为加号。每次遍历到数字末尾时，根据 \textit{preSign}preSign 来决定计算方式：
*
* 加号：将数字压入栈；
* 减号：将数字的相反数压入栈；
* 乘除号：计算数字与栈顶元素，并将栈顶元素替换为计算结果。
* 代码实现中，若读到一个运算符，或者遍历到字符串末尾，即认为是遍历到了数字末尾。
处理完该数字后，更新 \textit{preSign}preSign 为当前遍历的字符。
* 遍历完字符串 ss 后，将栈中元素累加，即为该字符串表达式的值。
* @param s
* @return
* @author Rock
* @param s
* @return
* @see 
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
/**
 * @author Rock
 * @param num
 * @param k
 * @return
 * @see https://leetcode-cn.com/problems/remove-k-digits/
 */
public String removeKdigits(String num, int k) {
    if(k>num.length())throw new RuntimeException("k不合法");
    if(num.length()==0||num.length()==k)return "0";
    StringBuilder stringBuilder=new StringBuilder();
    Stack<Character> stack=new Stack<>();
    stack.push(num.charAt(0));
    int i=1;
    int count=0;
    while(i<num.length()){
        if(num.charAt(i)<stack.peek()){
            stack.pop();
            stack.push(num.charAt(i));
            count++;
            if(count==k)
            {
                i++;
                break;
            }
        }
        else stack.push(num.charAt(i));
        i++;
    }
    while(i<num.length())stack.push(num.charAt(i++));
    while(!stack.isEmpty()){
        stringBuilder.append(String.valueOf(stack.pop()));
    }
    String res=String.valueOf(Integer.valueOf(stringBuilder.reverse().toString()));
    if(res.length()>num.length()-k)return res.substring(0, num.length()-k);
    return res;


}
/**
 * @author Rock
 * @param nums
 * @return
 * @see https://leetcode-cn.com/problems/132-pattern/
 */
public boolean find132pattern(int[] nums) {
    if (nums.length < 3)
        return false;
    Stack < Integer > stack = new Stack < > ();
    int[] min = new int[nums.length];
    min[0] = nums[0];
    for (int i = 1; i < nums.length; i++)
        min[i] = Math.min(min[i - 1], nums[i]);
    for (int j = nums.length - 1; j >= 0; j--) {
        if (nums[j] > min[j]) {
            while (!stack.isEmpty() && stack.peek() <= min[j])
                stack.pop();
            if (!stack.isEmpty() && stack.peek() < nums[j])
                return true;
                stack.push(nums[j]);
            }
        }
    return false;
}
/**
 * 基本思路：单调栈
 1. 遍历第二个链表，将元素入栈，如果当前元素比栈顶元素大，那么说明栈顶元素后面存在比其大的元素。此时将继续判断栈顶元素是否比当前元素小，如果是，继续记录当前栈顶元素的特性（即后面存在比起大的元素）直到上述条件不符合或者栈空。
 2. 如果不满足1的结束条件，此时将当前元素入栈，继续判断下一个元素，
 3. 按章上面的思路遍历整个链表2.
 * @author Rock
 * @param nums1
 * @param nums2
 * @return
 * @see https://leetcode-cn.com/problems/next-greater-element-i/
 */
public int[] nextGreaterElement(int[] nums1, int[] nums2) {
    int[] res=new int[nums1.length];
    Stack<Integer> stack=new Stack<>();
    Map<Integer,Integer> map=new HashMap<>();
    stack.push(nums2[0]);
    for(int i=1;i<nums2.length;i++){
        if(nums2[i]<stack.peek())stack.push(nums2[i]);
        else{
            while(!stack.isEmpty()){
                if(nums2[i]>stack.peek()){
                    map.put(stack.pop(),nums2[i]);
                }
                else break;
            }
            stack.push(nums2[i]);
        }
    }
    while(!stack.isEmpty()){
        map.put(stack.pop(),-1);
    }
    for(int i=0;i<nums1.length;i++){
        res[i]=map.get(nums1[i]);
    }
    return res;
}
/**
 * @author Rock
 * @param nums
 * @return
 * @see https://leetcode-cn.com/problems/next-greater-element-ii/
 */
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] ret = new int[n];
    Arrays.fill(ret, -1);
    Deque<Integer> stack = new LinkedList<Integer>();
    for (int i = 0; i < n * 2 - 1; i++) {
        while (!stack.isEmpty() && nums[stack.peek()] < nums[i % n]) {
            ret[stack.pop()] = nums[i % n];
        }
        stack.push(i % n);
    }
    return ret;
}
/**
 * 题解思路：
 * @author Rock
 * @param n
 * @param logs
 * @return 
 * @see https://leetcode-cn.com/problems/exclusive-time-of-functions/
 */
public int[] exclusiveTime(int n, List<String> logs) {
    Stack < Integer > stack = new Stack < > ();
        int[] res = new int[n];
        String[] s = logs.get(0).split(":");
        stack.push(Integer.parseInt(s[0]));
        int i = 1, prev = Integer.parseInt(s[2]);
        while (i < logs.size()) {
            s = logs.get(i).split(":");
            if (s[1].equals("start")) {
                if (!stack.isEmpty())
                    res[stack.peek()] += Integer.parseInt(s[2]) - prev;
                stack.push(Integer.parseInt(s[0]));
                prev = Integer.parseInt(s[2]);
            } else {
                res[stack.peek()] += Integer.parseInt(s[2]) - prev + 1;
                stack.pop();
                prev = Integer.parseInt(s[2]) + 1;
            }
            i++;
        }
        return res;
}
/**
 * @author rock
 * @param asteroids
 * @return
 * @see https://leetcode-cn.com/problems/asteroid-collision/
 */
public int[] asteroidCollision(int[] asteroids) {
    Stack<Integer> stack = new Stack<>();
    for (int ast: asteroids) {
        collision: {
            while (!stack.isEmpty() && ast < 0 && 0 < stack.peek()) {
                if (stack.peek() < -ast) {
                    stack.pop();
                    continue;
                } else if (stack.peek() == -ast) {
                    stack.pop();
                }
                break collision;
            }
            stack.push(ast);
        }
    }
    int[] ans = new int[stack.size()];
    for (int t = ans.length - 1; t >= 0; --t) {
        ans[t] = stack.pop();
    }
    return ans;
}
/**
 * 题解思路：该题与求解下一个更大的的思想相同。维护下标的单调栈。毕竟我们需要求解的天数。
 * 暴力解法
 * 直接从最后一个元素出发，将最后一个元素入栈，并且它对应的天数一定是0.
 * 然后遍历数组，并判断数组当前的元素与栈顶元素的大小，大的话，直接得到一天；
 * 如果小，退栈，直到遇到一个大的或者栈空，。
 * 退栈的次数便是天数，如果栈空了，说明没有把它大的，为0.
 * 
 * @author Rock
 * @param T
 * @return
 * @see https://leetcode-cn.com/problems/daily-temperatures/
 */
public int[] dailyTemperatures(int[] T) {
    int[] res=new int[T.length];
    for(int i=T.length-1;i>=0;i--){
        int j=0;
        for(j=i;j<T.length;j++){
            if(T[i]<T[j])
            {
                res[i]=j-i;
                break;
            }
        }
        if(j==T.length)res[i]=0;
    }
    return res;
}
public int[] dailyTemperaturesII(int[] T) {
    int length = T.length;
    int[] ans = new int[length];
    Deque<Integer> stack = new LinkedList<Integer>();
    for (int i = 0; i < length; i++) {
        int temperature = T[i];
        while (!stack.isEmpty() && temperature > T[stack.peek()]) {
            int prevIndex = stack.pop();
            ans[prevIndex] = i - prevIndex;
        }
        stack.push(i);
    }
    return ans;
}
/**
 * 题解思路：
 * @author rock
 * @param S
 * @return
 * @see https://leetcode-cn.com/problems/score-of-parentheses/
 */
public int scoreOfParentheses(String S) {
    return 0;
}
public static void main(String[] args){
}
}
