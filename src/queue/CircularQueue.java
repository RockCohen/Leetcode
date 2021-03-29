/**
  @see https://leetcode-cn.com/problems/design-circular-queue/
  @author Rock
 */
public class CircularQueue{
    private int[] queue;
    private int font;
    private int rear;
    /**
    初始化队列长度为k */
    public CircularQueue(int k) {
        queue=new int[k];
        font=0;
        rear=0;
    }
    
    public boolean enQueue(int value) {
        if(isFull())return false;
        else{
            queue[(rear)%initSize()]=value;
            rear=(rear+1)%initSize();
            return true;
        }

    }
    
    public boolean deQueue() {
        if(isFull())return false;
        else{
            font=(font+1)%initSize();
            return true;
        }
    }
    
    public int Front() {
        if(isEmpty())return -1;
        return queue[font];
    }
    
    public int Rear() {
        if(isEmpty())return -1;
        return queue[rear];
    }
    
    public boolean isEmpty() {
        return font==rear;

    }
    
    public boolean isFull() {
        return (rear-font+1)%initSize()==0?true:false;
    }
    private int initSize(){
        return queue.length;
    }
    public static void main(String[] args) {
        CircularQueue c=new CircularQueue(4);
        System.out.println("空？："+c.isEmpty());
        System.out.println("满？"+c.isFull());
        System.out.println("入队？"+c.enQueue(3));
        System.out.println("入队？"+c.enQueue(4));
        System.out.println("入队？"+c.enQueue(5));
        System.out.println("入队？"+c.enQueue(6));
        System.out.println("入队？"+c.enQueue(8));
        System.out.println(c.deQueue());
        System.out.println(c.deQueue());
        System.out.println(c.enQueue(3));
        System.out.println(c.enQueue(3));
        System.out.println(c.isFull());
    }
}