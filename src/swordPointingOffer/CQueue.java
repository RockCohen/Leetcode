package swordPointingOffer;

import java.util.Stack;

/**
 * 用两个栈来实现队列的功能
 *  * Your CQueue object will be instantiated and called as such:
 *  * CQueue obj = new CQueue();
 *  * obj.appendTail(value);
 *  * int param_2 = obj.deleteHead();
 *  题目链接：https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/
 *
 *  使用Stack的方式来做这道题，会造成速度较慢；
 *  原因的话是Stack继承了Vector接口，
 *  而Vector底层是一个Object[]数组，
 *  那么就要考虑空间扩容和移位的问题了。
 *  可以使用LinkedList来做Stack的容器，
 *  因为LinkedList实现了Deque接口，
 *  所以Stack能做的事LinkedList都能做，
 *  其本身结构是个双向链表，扩容消耗少
 */
public class CQueue {
    private Stack<Integer> stackIn;
    private Stack<Integer> stackOut;
    //构造函数初始化两个栈
    public CQueue() {
        stackIn=new Stack<>();
        stackOut=new Stack<>();
    }
    //入队
    public void appendTail(int value) {
        stackIn.add(value);
    }
    //出队
    public int deleteHead() {
        if(stackIn.empty())return -1;
        while(stackIn.size()>1){
            stackOut.add(stackIn.pop());
        }
        int res=stackIn.pop();
        while (!stackOut.empty()){
            stackIn.add(stackOut.pop());
        }
        return res;
    }

    public static void main(String[] args) {
        CQueue obj = new CQueue();
        obj.appendTail(1);
        obj.appendTail(2);
        System.out.println(obj.deleteHead());
        obj.appendTail(3);
        obj.appendTail(4);
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        obj.appendTail(5);
        obj.appendTail(6);
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
    }
}
