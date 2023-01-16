package list;

/**
 * 链表之和
 */
public class SumOfTwoLists {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res;
        ListNode p = new ListNode(0);
        res = p;
        int carry = 0; // 进位器
        while(l1 != null && l2 != null ){
            int real = l1.val + l2.val + carry;
            int current = real % 10;
            carry = real / 10;
            p.next = new ListNode(current);
            p = p.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int real = l1.val + carry;
            int current = real % 10;
            carry = real / 10;
            p.next = new ListNode(current);
            p = p.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            int real = l2.val + carry;
            int current = real % 10;
            carry = real / 10;
            p.next = new ListNode(current);
            p = p.next;
            l2 = l2.next;
        }
        if (carry > 0) {
            p.next = new ListNode(carry);
        }
        return res;
    }




}
