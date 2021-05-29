package list;

/**
 * 单链表数据结构__这是一个带头节点的单链表
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    public void print(){//打印链表
      System.out.println(this.val);
      while(next!=null){
          System.out.println(next.val);
          next=next.next;
      }
    }
    public ListNode makeList(int[] val){
      ListNode node =new ListNode();
      ListNode node1=node;
      int i=0;
      while(i<val.length){
          node1.next=new ListNode(val[i]);
          node1=node1.next;
          i++;
      }
      return node.next;
    }
}
