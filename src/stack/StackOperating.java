package stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    /**
* 394. 字符串解码
给定一个经过编码的字符串，返回它解码后的字符串。

编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。

你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。

此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
示例 1：

输入：s = "3[a]2[bc]"
输出："aaabcbc"
示例 2：

输入：s = "3[a2[c]]"
输出："accaccacc"
示例 3：

输入：s = "2[abc]3[cd]ef"
输出："abcabccdcdcdef"
示例 4：

输入：s = "abc3[cd]xyz"
输出："abccdcdcdxyz"
基本思路：
     */
    public String decodeString(String s) {
        return null;

    }
/**
* 402. 移掉K位数字
给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。

注意:

num 的长度小于 10002 且 ≥ k。
num 不会包含任何前导零。
示例 1 :

输入: num = "1432219", k = 3
输出: "1219"
解释: 移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219。
示例 2 :

输入: num = "10200", k = 1
输出: "200"
解释: 移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
示例 3 :

输入: num = "10", k = 2
输出: "0"
解释: 从原数字移除所有的数字，剩余为空就是0。
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
 * 456. 132模式
给定一个整数序列：a1, a2, ..., an，一个132模式的子序列 ai, aj, ak 被定义为：当 i < j < k 时，ai < ak < aj。设计一个算法，当给定有 n 个数字的序列时，验证这个序列中是否含有132模式的子序列。

注意：n 的值小于15000。

示例1:

输入: [1, 2, 3, 4]

输出: False

解释: 序列中不存在132模式的子序列。
示例 2:

输入: [3, 1, 4, 2]

输出: True

解释: 序列中有 1 个132模式的子序列： [1, 4, 2].
示例 3:

输入: [-1, 3, 2, 0]

输出: True

解释: 序列中有 3 个132模式的的子序列: [-1, 3, 2], [-1, 3, 0] 和 [-1, 2, 0].
题解思路：首先找到前缀最小值。然后再利用栈从数组尾部开始遍历来维护32模型。
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
 * 496. 下一个更大元素 I
给你两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。

请你找出 nums1 中每个元素在 nums2 中的下一个比其大的值。

nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。

 

示例 1:

输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
输出: [-1,3,-1]
解释:
    对于 num1 中的数字 4 ，你无法在第二个数组中找到下一个更大的数字，因此输出 -1 。
    对于 num1 中的数字 1 ，第二个数组中数字1右边的下一个较大数字是 3 。
    对于 num1 中的数字 2 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
示例 2:

输入: nums1 = [2,4], nums2 = [1,2,3,4].
输出: [3,-1]
解释:
    对于 num1 中的数字 2 ，第二个数组中的下一个较大数字是 3 。
    对于 num1 中的数字 4 ，第二个数组中没有下一个更大的数字，因此输出 -1 。


基本思路：单调栈
1. 遍历第二个链表，将元素入栈，如果当前元素比栈顶元素大，那么说明栈顶元素后面存在比其大的元素。此时将继续判断栈顶元素是否比当前元素小，如果是，继续记录当前栈顶元素的特性（即后面存在比起大的元素）直到上述条件不符合或者栈空。
2. 如果不满足1的结束条件，此时将当前元素入栈，继续判断下一个元素，
3. 按章上面的思路遍历整个链表2.
 * 
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
 * 503. 下一个更大元素 II
给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。

示例 1:

输入: [1,2,1]
输出: [2,-1,2]
解释: 第一个 1 的下一个更大的数是 2；
数字 2 找不到下一个更大的数； 
第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
注意: 输入数组的长度不会超过 10000。

解题思路：
根上面的题目一样，直接两遍扫描，填满所有数据的结果即可。
 */
public int[] nextGreaterElements(int[] nums) {
    class Node{
        int index;
        int num;
        int res;
        Node(int index,int num,int res){
            this.index=index;
            this.num=num;
            this.res=res;
        }
    }
    Map<Integer,Node> res=new HashMap<>();//用存储结果
    Stack<Node> stack=new Stack<>(); //单调栈
    int i=1;//循环变量
    stack.push(new Node(0,nums[0],Integer.MAX_VALUE));//将第一一个元素放入栈中。
    while(res.size()<nums.length){
        int index=i%nums.length;//当前元素
        if(nums[index]<=stack.peek().num)stack.push(new Node(index,nums[index],Integer.MAX_VALUE));//判断与栈顶元素的大小，如果小于栈顶元素，直接进栈
        else{
            while(!stack.isEmpty()){
                if(nums[index]>stack.peek().num){
                    Node n=stack.pop();
                    Node no=new Node(n.index,n.num,nums[index]);//记录栈顶元素的结果
                    if(!res.containsKey(index))res.put(n.index,no);
                }
                else break;
            }
            stack.push(new Node(index,nums[index],Integer.MAX_VALUE));
        }
        i++;
    }
    int[] result=new int[nums.length];
    for(int k=0;k<nums.length;k++){
        result[k]=res.get(k).res;
    }
    return result;  
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
    List<List<String>> log=processLogs(logs);
    Stack<List<String>> stack=new Stack<>();
    stack.push(log.get(0));
    int i=1;
    int[] res=new int[n];
    while(!stack.isEmpty()&&i<log.size()){
        boolean flag=(stack.peek().get(0).equals(log.get(i).get(0)))&&(stack.peek().get(1).equals("start"))&&log.get(i).get(1).equals("end");
        int time=Integer.valueOf(log.get(i).get(2))-Integer.valueOf(stack.peek().get(2));
        if(flag){
            List<String> s=stack.pop();
           res[Integer.valueOf(s.get(0))]+=(time+1);
        }
        else 
        {
            res[Integer.valueOf(stack.peek().get(0))]+=Integer.valueOf(log.get(i).get(2))-Integer.valueOf(stack.peek().get(2));
            stack.push(log.get(i));
        }
        i++;
    }
    return res;
}
private List<List<String>> processLogs(List<String> s){
    List<List<String>> result=new ArrayList<>();
    for(int j=0;j<s.size();j++){
        String string=s.get(j)+":";
        List<String> res= new ArrayList<>();
        int begin=0;
        for(int i=0;i<string.length();i++){
           if(String.valueOf(string.charAt(i)).equals(":")){
              res.add(string.substring(begin, i));
              begin=i+1;
            } 
        }
        result.add(res);
    }
    return result;
}
/**
 * 题解思路：栈：
 * 当前栈顶的元素如果存在与现在遍历位置的卫星存在碰撞的问题，那么将处理栈顶元素与当前数组位置的行星的关系。
 * 处理规则：留下幸免遇难的行星。
 * 继续判断栈顶的元素与当前数组位置元素的碰撞关系，直到栈为空或者不满足碰撞条件。
 * 当栈为空时，并且还没有遍历完数组，那么需要将当前碰撞结果后的元素留下插入栈中。
 * 或者不满足碰撞条件，直接将当前数组位置的元素入栈，继续判断。
 * 
 * @author rock
 * @param asteroids
 * @return
 * @see https://leetcode-cn.com/problems/asteroid-collision/
 */
public int[] asteroidCollision(int[] asteroids) {
    Stack<Integer> stack=new Stack<>();
    stack.push(asteroids[0]);
    int i=1;
    while(i<asteroids.length){
        int t=0;
        while(!stack.isEmpty()){
            int x=isCollision(stack.peek(),asteroids[i]);
            if(x==0){
                stack.pop();
                break;
            }
            else if(x==-1){
                stack.push(asteroids[i]);
                break;
            }
            else {
                if(x==asteroids[i])stack.pop();
                else break;
            }
            t=x;
        }
        i++;
        if(stack.isEmpty())stack.push(t);
    }
    if(stack.isEmpty())
    { System.out.println("为空");
        return new int[0];
    }
    int len=stack.size()-1;
    int[] res=new int[len+1];
    while(!stack.isEmpty())res[len--]=stack.pop();
    return res;
}
public int isCollision(int a,int b){
    if(a>0&&b<0)
    {
        if(Math.abs(a)==Math.abs(b))return 0;
        return Math.abs(a)>Math.abs(b)?a:b;
    }
    else return -1;
}

public static void main(String[] args){
       // String[] tokens={"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
        //System.out.println(new StackOperating().evalRPN(tokens));
        //System.out.println(Integer.parseInt("-"));
        //Integer.MAX_VALUE
        // String s="3/2";
        // StackOperating stackOperating=new StackOperating();
        // List<String> list=stackOperating.calculator(s);
        // System.out.println(stackOperating.evalRPN(list));
    //    ArrayList<String> list=new ArrayList<>(Arrays.asList("0:start:0","0:start:1", "0:end:2",
    //    "0:end:3","1:start:4","1:end:5"));
       int[] s={8,-8};
       int[] res=new StackOperating().asteroidCollision(s);
       for (int i : res) {
        System.out.println(i);
       }
    
       




        
    }
}
