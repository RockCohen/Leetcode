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
    List<Integer> list = new ArrayList<>();
    List<Integer> minStack=new ArrayList<>();

    /** initialize your data structure here. */
    public MinStack() {
    }
    public void push(int x) {
        if(minStack.size()==0)minStack.add(x);
        else{
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
    public void print(){
        for(int x:list) System.out.println(x);
    }
    public static void main(String[] args){
        MinStack minStack=new MinStack();
        minStack.push(2);
        minStack.push(0);
        minStack.push(3);
        minStack.push(0);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());


    }
}
