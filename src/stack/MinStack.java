package stack;

import java.util.ArrayList;
import java.util.List;

/**
 * 155. 最小栈
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) —— 将元素 x 推入栈中。
 * pop() —— 删除栈顶的元素。
 * top() —— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 *
 *
 * 题解思路：设定两个栈，
 */
public class MinStack {
    List<Integer> list;
    List<Integer> minStack;
    /** initialize your data structure here. */
    public MinStack() {
        list = new ArrayList<>();
        minStack=new ArrayList<>();
    }
    public void push(int x) {
        if(minStack.size()==0)minStack.add(x);
        else{
            //何止是妙，简直就是妙！！！
            //通过这种方法能保证对应元素在没有被弹出栈之前保证整个栈的最小值依然是当前最小序列的第一个值。
            if(x<minStack.get(0))minStack.add(0,x);
            else minStack.add(0,getMin());
        }
        list.add(0,x);
    }
    public void pop() {
        list.remove(0);
        minStack.remove(0);
    }
    public int top() {
        return list.get(0);
    }
    public int getMin() {
        return minStack.get(0);
    }
}
