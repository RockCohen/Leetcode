package list;

import java.util.*;

/**
 * 单链表操作对象
 */
public class ListOperating {
    /**
     * 获取首元素
     *
     * @param node 头节点
     * @return 返回首元素
     */
    public ListNode getHead(ListNode node) {
        return node.next;
    }

    /**
     * 链表长度
     *
     * @param head 头节点
     * @return 链表长度
     */
    public int listLength(ListNode head) {
        int count = 0;
        while (head.next != null) {
            head = head.next;
            count++;
        }
        return count;
    }

    /**
     * 采用递归的方式来求解链表的长度。
     *
     * @param head 头节点
     * @return 链表长度
     */
    public int listLengthII(ListNode head) {
        if (head == null) return 0;
        return listLengthII(head.next) + 1;
    }

    /**
     * 节点插入
     *
     * @param head    单链表表头
     * @param element 插入元素,
     * @param index   插入位置，这里默认插入位置是合法的。
     * @return 返回插入节点后的链表
     */
    public ListNode insertNode(ListNode head, int element, int index) {//插入的数据类型暂定为int类型
        ListNode node = new ListNode(element);
        ListNode p = head;
        int i = 1;
        while (i++ < index) p = p.next;
        node.next = p.next;
        p.next = node;
        return head;
    }

    /**
     * 141. 环形链表
     * 给定一个链表，判断链表中是否有环。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
     * 如果 pos 是 -1，则在该链表中没有环。
     * 注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * 如果链表中存在环，则返回 true 。 否则，返回 false 。
     * 进阶:
     * 你能用 O(1)（即，常量）内存解决此问题吗？
     * 示例 1：
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     * 示例 2：
     * 输入：head = [1,2], pos = 0
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第一个节点。
     * 示例 3：
     * 输入：head = [1], pos = -1
     * 输出：false
     * 解释：链表中没有环。
     * <p>
     * 题解思路：
     * 1. 最明确的思路：利用哈希表，遍历链表将链表地址放入哈希表，并且查看他的下一个节点的地址是否在哈希表中，如果在那么一定存在环。
     * 2. 本方法需要读者对「Floyd 判圈算法」（又称龟兔赛跑算法）有所了解。
     * 假想「乌龟」和「兔子」在链表上移动，「兔子」跑得快，「乌龟」跑得慢。
     * 当「乌龟」和「兔子」从链表上的同一个节点开始移动时，如果该链表中没有环，那么「兔子」将一直处于「乌龟」的前方；
     * 如果该链表中有环，那么「兔子」会先于「乌龟」进入环，并且一直在环内移动。
     * 等到「乌龟」进入环时，由于「兔子」的速度快，它一定会在某个时刻与乌龟相遇，即套了「乌龟」若干圈。
     * 我们可以根据上述思路来解决本题。具体地，我们定义两个指针，一快一满。
     * 慢指针每次只移动一步，而快指针每次移动两步。初始时，慢指针在位置 head，而快指针在位置 head.next。
     * 这样一来，如果在移动的过程中，快指针反过来追上慢指针，就说明该链表为环形链表。
     * 否则快指针将到达链表尾部，该链表不为环形链表。//简直牛逼
     *
     * @param head 表头
     * @return bool值
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 142. 环形链表 II
     * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * <p>
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意，pos 仅仅是用于标识环的情况，并不会作为参数传递到函数中。
     * <p>
     * 说明：不允许修改给定的链表。
     * <p>
     * 进阶：
     * <p>
     * 你是否可以使用 O(1) 空间解决此题？
     * 示例 1：
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：返回索引为 1 的链表节点
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     * 示例 2：
     * 输入：head = [1,2], pos = 0
     * 输出：返回索引为 0 的链表节点
     * 解释：链表中有一个环，其尾部连接到第一个节点。
     * 示例 3：
     * 输入：head = [1], pos = -1
     * 输出：返回 null
     * 解释：链表中没有环。
     * <p>
     * 题解思路：
     * 1. 利用哈希表还是最快的。但是会消耗O(n)的空间复杂度。
     * 2. 要在O(1)的空间复杂度上解决问题：
     * 我们使用两个指针，fast 与 slow。它们起始都位于链表的头部。
     * 随后，slow 指针每次向后移动一个位置，而 fast 指针向后移动两个位置。
     * 如果链表中存在环，则 fast 指针最终将再次与 slow 指针在环中相遇。
     * <p>
     * 如下图所示，设链表中环外部分的长度为 a。slow 指针进入环后，又走了 b 的距离与fast 相遇。
     * 此时fast 指针已经走完了环的 n 圈，因此它走过的总距离为 a+n(b+c)+b=a+(n+1)b+nc。
     * 根据题意，任意时刻，fast 指针走过的距离都为 slow 指针的 2 倍。因此，我们有
     * <p>
     * a+(n+1)b+nc=2(a+b) ---> a=c+(n-1)(b+c)
     * a+(n+1)b+nc=2(a+b)⟹a=c+(n−1)(b+c)
     * <p>
     * 有了 a=c+(n-1)(b+c)a=c+(n−1)(b+c) 的等量关系，我们会发现：从相遇点到入环点的距离加上 n-1圈的环长，恰好等于从链表头部到入环点的距离。
     * <p>
     * 因此，当发现 slow 与 fast 相遇时，我们再额外使用一个指针 ptr。起始，它指向链表头部；
     * 随后，它和 slow 每次向后移动一个位置。最终，它们会在入环点相遇。
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            if (fast == slow) {//第一次相遇，然后根据数量关系寻找入圈节点。
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }

    /**
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * <p>
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/add-two-numbers
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 示例 1：
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     * 解题思路：该题的实质就是一个加法器，不过对于数据的存储采用单链表来实现罢了。
     * 所以就是对单链表进行一个合并，需要注意的问题就是：加法器中进位的处理，其次最终需要判断进位是否为1.比如99+99.最终进位为1，所以需要输出进位位。
     * 该题相似的题目包括两个有序数组合并成为一个有序数组，也是通过采用同样的方式进行遍历，这样的事件复杂度仅为：O(max(m,n)).
     * 题目二：
     * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
     * 进阶：
     * <p>
     * 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
     * <p>
     *  
     * <p>
     * 示例：
     * <p>
     * 输入：(7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 8 -> 0 -> 7
     * <p>
     * <p>
     * 该题的思考：
     * 1. 如何在原位进行操作，并且不想引入空间复杂度来解决这个问题。显然这个问题从链表的头部开始的话，一层一层下去，需要达到最后一个节点才能把问题，这不就是递归的味道嘛。
     * 于是考虑递归的方法来做这个题目。所以方法一：递归法
     * 2. 如果引入空间复杂度，那问题就好办了，直接将链表的元素存储到数组或者栈中，当然这个题目用栈来存储更加合理，刚好体现栈的基本思想。这本身也是这个题目的目的吧。
     * 此时的事件复杂度O(max(m,n)),空间复杂度为O(m+n).链表的头插法值得学习。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/add-two-numbers-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p = l1;
        ListNode q = l2;
        ListNode res = new ListNode();
        ListNode result = res;
        int carry = 0;
        while (p != null && q != null) {
            res.next = new ListNode((p.val + q.val + carry) % 10);
            res = res.next;
            carry = (p.val + q.val + carry) / 10;
            p = p.next;
            q = q.next;
        }
        while (p != null) {
            res.next = new ListNode((p.val + carry) % 10);
            res = res.next;
            carry = (p.val + carry) / 10;
            p = p.next;
        }
        while (q != null) {
            res.next = new ListNode((q.val + carry) % 10);
            res = res.next;
            carry = (q.val + carry) / 10;
            q = q.next;
        }
        if (carry > 0) res.next = new ListNode(carry);
        return result.next;
    }

    public String addBinary(String a, String b) {
        StringBuilder res = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;
        int carry = 0;
        while (i >= 0 && j >= 0) {
            int i1 = a.charAt(i) - '0' + b.charAt(j) - '0' + carry;
            res.append(i1 % 2);
            carry = i1 / 2;
            j--;
            i--;
        }
        while (i >= 0) {
            int i1 = a.charAt(i) - '0' + carry;
            res.append(i1 % 2);
            carry = i1 / 2;
            i--;
        }
        while (j >= 0) {
            int i1 = b.charAt(j) - '0' + carry;
            res.append(i1 % 2);
            carry = i1 / 2;
            j--;
        }
        if (carry > 0) res.append(carry);
        return res.reverse().toString();
    }

    public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {//堆栈法
        Stack<Integer> stackL1 = new Stack<>();
        Stack<Integer> stackL2 = new Stack<>();
        ListNode p = l1;
        ListNode q = l2;
        ListNode head = null;
        int carry = 0;
        while (p != null) {
            stackL1.push(p.val);
            p = p.next;
        }
        while (q != null) {
            stackL2.push(q.val);
            q = q.next;
        }
        while ((!stackL1.isEmpty()) || (!stackL2.isEmpty()) || carry == 1) {//通过这种判断方法来精简代码量，非常具有借鉴意义。
            int sum = carry;
            sum += stackL1.isEmpty() ? 0 : stackL1.pop();
            sum += stackL2.isEmpty() ? 0 : stackL2.pop();
            ListNode node = new ListNode(sum % 10);
            node.next = head;//这里采用头插法，这样就不用一个变量来记录首节点了。非常有意思的一种方法。
            head = node;
            carry = sum / 10;
        }
        return head;
    }

    public ListNode addTwoNumbersIIDG(ListNode l1, ListNode l2) {
        //
        return null;
    }

    /**
     * 82. 删除排序链表中的重复元素 II
     * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
     * <p>
     * 示例 1:
     * 输入: 1->2->3->3->4->4->5
     * 输出: 1->2->5
     * 示例 2:
     * 输入: 1->1->1->2->3
     * 输出: 2->3
     * 题解思路：
     * 1. 双指针，通过快慢指针来确定相同元素的范围，如果存在重复元素则全部不入队列，如果不存在重复，那么将慢指针的元素放入队列中。
     * 得到最终的队列之后，重新根据队列的元素排列来整理链表的链接顺序，切记：打破原来的链接顺序，特别是最后的表尾。
     * 2. 三指针，通过双指针来确定重复的元素，通过第三个指针来更新新的链表的元素。
     * 总结：显然这个题目没有改变元素的相对顺序，那么就可以考虑用队列来完成其求解。
     */
    public ListNode deleteDuplicates(ListNode head) {
        Queue<ListNode> queue = new LinkedList<>();
        ListNode quick = head;//快指针
        ListNode slow = head;//慢指针
        while (slow != null) {
            int count = 0;
            while (quick != null && slow.val == quick.val) {
                count++;
                quick = quick.next;
            }
            if (count < 2) {
                queue.add(slow);
            }
            slow = quick;
        }
        ListNode node = new ListNode();
        ListNode node1 = node;
        while (!queue.isEmpty()) {
            node1.next = queue.poll();
            node1 = node1.next;
        }
        node1.next = null;//破坏原来的链接状态。不然出大问题。当然没有重新申请和占用内存。
        return node.next;
    }
    /**
     * 19. 删除链表的倒数第 N 个结点
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * 进阶：你能尝试使用一趟扫描实现吗？
     * 滑动窗口的用法。不过这里的滑动窗口是需要预先处理的，首先得通过滑动来控制窗口的边界，然后再进行窗口的滑动。
     *
     *  
     *
     * 示例 1：
     *
     *
     * 输入：head = [1,2,3,4,5], n = 2
     * 输出：[1,2,3,5]
     * 示例 2：
     *
     * 输入：head = [1], n = 1
     * 输出：[]
     * 示例 3：
     *
     * 输入：head = [1,2], n = 1
     * 输出：[1]
     *
     *
     * 思路：
     * 1. 最暴力的方法。先遍历，再删除。
     * 2. 快慢指针，滑动窗口。
     * 3. 倒数第几个，放在栈里就是正数第几个退栈的，所以可以使用栈来解决。
     * 4. 根据倒数第几个这个提示，讲道理也是可以使用递归来解决的。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    /**
     * 通过快慢指针来删除倒数第k个节点。双指针的各种变化显然是值得总结的。
     *
     * @param head 链表头节点
     * @param n    倒数第n
     * @return 删除节点后的链表
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int i = 1;//快指针索引
        ListNode first = new ListNode();//头节点增加操作的遍历。
        first.next = head;//引入头节点便于插入删除操作
        ListNode quick = first;//快指针
        ListNode slow = first;//慢指针
        while (i++ <= n) {//对窗口的上界进行预处理
            quick = quick.next;
            if (quick == null) return null;//如果n不合法，直接返回null或者抛出异常
        }
        while (quick.next != null) {//滑动窗口，使得窗口的下界指向需要删除节点的上一个节点
            slow = slow.next;
            quick = quick.next;
        }
        slow.next = slow.next.next;//删除节点
        return first.next;//返回表头
    }

    /**
     * 采用递归的方式删除倒数第k个节点
     *
     * @param head 头节点
     * @param n    参数n
     * @return 返回删除后的链表头。
     */
    public ListNode removeNthFromEndII(ListNode head, int n) {
        int pos = length(head, n);
        // 说明删除的是头节点，这个地方也可以直接使用增加头节点的方法来实现删除插入的方法的统一
        if (pos == n)
            return head.next;
        return head;
    }

    /**
     * 用于删除节点中索引节点的查找。
     *
     * @param node 头节点，
     * @param n    参数n
     * @return 返回索引
     */
    private int length(ListNode node, int n) {
        if (node == null)
            return 0;
        int pos = length(node.next, n) + 1;
        //获取要删除链表的前一个结点，就可以完成链表的删除
        if (pos == n + 1)
            node.next = node.next.next;//直接再这里执行删除节点的操作
        return pos;
    }

    /**
     * 61. 旋转链表
     * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
     * <p>
     * 示例 1:
     * <p>
     * 输入: 1->2->3->4->5->NULL, k = 2
     * 输出: 4->5->1->2->3->NULL
     * 解释:
     * 向右旋转 1 步: 5->1->2->3->4->NULL
     * 向右旋转 2 步: 4->5->1->2->3->NULL
     * 示例 2:
     * <p>
     * 输入: 0->1->2->NULL, k = 4
     * 输出: 2->0->1->NULL
     * 解释:
     * 向右旋转 1 步: 2->0->1->NULL
     * 向右旋转 2 步: 1->2->0->NULL
     * 向右旋转 3 步: 0->1->2->NULL
     * 向右旋转 4 步: 2->0->1->NULL
     * 题解思路：两遍扫描，第一遍确定链表长度并且将表尾部与头部连在一起，构成环形链表，然后在选择合适的位置截断，前一个节点做表尾，后一个节点做表头。
     */
    public ListNode rotateRight(ListNode head, int k) {
        ListNode node = head;
        ListNode p = head;
        ListNode q = head;
        int count = 0;
        while (node != null) {//计算链表长度
            count++;
            q = node;
            node = node.next;
        }
        if (count == 0) return head;//表空
        if (k % count == 0) return head;//如果是链表长的倍数，不做处理。否则才构造环形链表
        q.next = head;//构造单向环形链表
        count = count - (k % count);
        int i = 1;
        while (i++ < count) {//选择截断点
            p = p.next;
        }
        ListNode res = p.next;//获取新的表头
        p.next = null;//将前一个节点的尾部置为空。
        return res;//返回表头。
    }

    /**
     * 24. 两两交换链表中的节点
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     * <p>
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 示例 1：
     * <p>
     * <p>
     * 输入：head = [1,2,3,4]
     * 输出：[2,1,4,3]
     * 示例 2：
     * <p>
     * 输入：head = []
     * 输出：[]
     * 示例 3：
     * <p>
     * 输入：head = [1]
     * 输出：[1]
     *  
     * <p>
     * 提示：
     * <p>
     * 链表中节点的数目在范围 [0, 100] 内
     * 0 <= Node.val <= 100
     * <p>
     * 题解：这个题的关键在于滑动窗口的临界条件的判断。还有就是通过增加头节点简化处理过程的复杂性。
     * 进阶：你能在不修改链表节点值的情况下解决这个问题吗?（也就是说，仅修改节点本身。）
     */
    public ListNode swapPairs(ListNode head) {
        ListNode first = new ListNode();
        first.next = head;//增加头节点，简化处理的简单性。
        ListNode slow = first;
        ListNode quick = first.next;
        while (quick != null && quick.next != null) {
            ListNode p = quick.next;
            slow.next = p;
            quick.next = p.next;
            p.next = quick;
            slow = slow.next.next;//一下滑动两个单位
            quick = slow.next;
        }
        return first.next;
    }

    /**
     * 143. 重排链表
     * 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
     * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 示例 1:
     * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
     * 示例 2:
     * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
     * 题解思路：
     * 1. 这就是一个双端队列就搞定的事情。双端队列两边都可以进行数据的释放。
     */
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        LinkedList<ListNode> deque = new LinkedList<>();
        while (head != null) {
            deque.add(head);
            head = head.next;
        }
        ListNode vh = new ListNode(0);
        while (!deque.isEmpty()) {
            ListNode prev = deque.pollFirst();
            vh.next = prev;
            ListNode next = deque.pollLast();
            prev.next = next;
            if (next == null) {
                vh = prev;
                return;
            }
            vh = next;
        }
        vh.next = null;
    }

    /**
     * 148. 排序链表
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     * <p>
     * 进阶：
     * <p>
     * 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
     * 示例 1：
     * 输入：head = [4,2,1,3]
     * 输出：[1,2,3,4]
     * 示例 2：
     * <p>
     * <p>
     * 输入：head = [-1,5,3,4,0]
     * 输出：[-1,0,3,4,5]
     * 示例 3：
     * <p>
     * 输入：head = []
     * 输出：[]
     * <p>
     * 题解：分治法，不过是排序在链表上的运用罢了。有待解决。
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        return null;

    }

    /**
     * 160. 相交链表
     * 编写一个程序，找到两个单链表相交的起始节点。
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Reference of the node with value = 8
     * 输入解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
     * 从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。
     * 在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     * <p>
     * 题解思路：
     * 1. 逆向思维，显然从链表后面来看，后面的节点都是相同的，那么从后面开始遍历节点，遇到不相同的时候返回上一个节点即可。
     * 那么从后面开始遍历，则需要借助栈的思想啦。
     * 2. 第二种思路：显然两个链表的长度不一定相等，但是一定存在这样的数量关系。
     * 那就是：A+B=B+A。这是数学中的交换路，这是显然成立的，并且这里面在于A与B里面都包含一个共同的C，也就是：
     * A1+C=A;B1+C=B不难得出，A1+B1=B1+A1.我想说什么呢，也就是将A与B首尾相接连城环，即可得到这个题的思路。
     * 通过两个指针遍历，然后两个指针一定相遇。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Stack<ListNode> A = new Stack<>();
        Stack<ListNode> B = new Stack<>();
        do {
            if (headA != null) {
                A.push(headA);
                headA = headA.next;
            }
            if (headB != null) {
                B.push(headB);
                headB = headB.next;
            }
        } while (headA != null || headB != null);
        ListNode nodeA = null;
        ListNode nodeB = null;
        ListNode res = null;
        while (!A.isEmpty() && !B.isEmpty()) {
            nodeA = A.pop();
            nodeB = B.pop();
            if (nodeA != nodeB) return res;
            else res = nodeA;
        }
        return res;
    }

    /**
     * 206. 反转链表
     * 题解思路：
     * 1. 递归法（头插的递归实现）
     * 2. 迭代法（头插法）
     * 3. 暴力法(通过栈来实现）
     * @param head
     * @return
     */
    public ListNode reverseListRecursive(ListNode head) {//反转链表的递归实现
        if(head==null||head.next==null)return head;
        //这里其实获取的时候头节点，于是最终需要将其返回。所以我们需要处理的是尾部节点，也就是：hea.next.
        ListNode node=reverseListRecursive(head.next);
        head.next.next=head;//交换
        head.next=null;//处理尾部
        return node;
    }
    public ListNode reverseListIteration(ListNode head){//迭代实现
        if(head==null||head.next==null)return head;
        ListNode iteration=head.next;
        ListNode node=head;
        ListNode p=iteration;
        while(p!=null){//头插法
            iteration=p;
            p=p.next;
            iteration.next=node;
            node=iteration;
        }
        head.next=null;//这一步很关键，是决定链表是否出现循环的关键，最后需要将头节点的尾指针置为空，这样才是单链表的实质。
        return node;
    }

    /**
     * 234. 回文链表
     * 234. 回文链表
     * 请判断一个链表是否为回文链表。
     * 示例 1:
     * 输入: 1->2
     * 输出: false
     * 示例 2:
     * 输入: 1->2->2->1
     * 输出: true
     * 进阶：
     * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
     *
     *
     * 题解思路：
     * 1. 这就是一个双端队列的操作。
     * 2. 回文串关键是找到前一半链表的元素与后一半链表的元素相同与否，那么如何得到后一半的链表呢。
     *    显然就是查找中间节点的方法，快慢指针即可找到中间节点。
     *    但是找到中间节点还没完，还需要进行后一半链表的逆序查看元素进行前一半链表的正序对比。
     *    如果在多线程下面，则可以这样做，在寻找后一半链表的时候，对前半链表进行翻转。然后两个链表再进行对比。
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
       ListNode middle=middleNode(head);
       ListNode p=head;
       ListNode reverseMiddle=reverseListIteration(middle);
       while(p!=middle){
           if(p.val!=reverseMiddle.val)return false;
           reverseMiddle=reverseMiddle.next;
           p=p.next;
       }
       return true;
    }

    /**
     * 876. 链表的中间结点
     * 给定一个头结点为 head 的非空单链表，返回链表的中间结点。
     *
     * 如果有两个中间结点，则返回第二个中间结点。
     * 示例 1：
     *
     * 输入：[1,2,3,4,5]
     * 输出：此列表中的结点 3 (序列化形式：[3,4,5])
     * 返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
     * 注意，我们返回了一个 ListNode 类型的对象 ans，这样：
     * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
     * 示例 2：
     *
     * 输入：[1,2,3,4,5,6]
     * 输出：此列表中的结点 4 (序列化形式：[4,5,6])
     * 由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
     *
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        ListNode quick=head;
        ListNode slow=head;
        while(quick!=null&&quick.next!=null){
            slow=slow.next;
            quick=quick.next.next;
        }
       return slow;
    }

    /**
     * 328. 奇偶链表
     * 给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
     *
     * 请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。
     *
     * 示例 1:
     *
     * 输入: 1->2->3->4->5->NULL
     * 输出: 1->3->5->2->4->NULL
     * 示例 2:
     *
     * 输入: 2->1->3->5->6->4->7->NULL
     * 输出: 2->3->6->7->1->5->4->NULL
     * 说明:
     *
     * 应当保持奇数节点和偶数节点的相对顺序。
     * 链表的第一个节点视为奇数节点，第二个节点视为偶数节点，以此类推。
     *
     *
     * 题解思路：
     * 1. 暴力法，将链表遍历之后的结果存入数组，然后利用数组的下标来重新构建链表。空间复杂度为O(n)。时间复杂度为O(n).
     * 2. 直接一遍遍历，记录交换后的最后一个奇数的最后一个位置，记录下一个待交换的奇数的前后位置。
     *    这样做的目的是为了便于更加方便地进行交换。可以画一个图解，然后记录算法地流程。
     *    然后你会发现难以记录下一个待交换的奇数的位置，但是，它是和交换的次数是有着直接相关的联系。
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if(head==null||head.next==null||head.next.next==null)return head;//当节点数量少于3个时，不需要交换。
        ListNode lastOdd=head;//已经交换的最后一个奇数的位置。
        ListNode nextOdd=head;//还未交换的第一个奇数的位置。
        int swapCount=1;//用于找到下一个需要交换的奇数的循环变量，与交换次数直接相关。
        while(nextOdd.next!=null){
            int i=0;
            while(nextOdd!=null&&i<swapCount){
                nextOdd=nextOdd.next;
                i++;
            }
            //节点数量为奇数与偶数个的时候的差别可能存在一定的差别。
            // 这个结束的标志需要特别的判断。我觉得这是该条目的核心问题。
            if(i<swapCount||nextOdd==null||nextOdd.next==null)return head;
            //节点交换的过程。节点交换的前提时节点存在，至于节点的存在性已经在上面的判断条件里面包含了。
            ListNode q=lastOdd.next;
            ListNode p=nextOdd.next;
            lastOdd.next=p;
            nextOdd.next=p.next;
            p.next=q;
            swapCount++;
            lastOdd=lastOdd.next;
            nextOdd=lastOdd;
        }
        return head;
    }

    /**
     * 817. 链表组件
     * 给定链表头结点 head，该链表上的每个结点都有一个 唯一的整型值 。
     *
     * 同时给定列表 G，该列表是上述链表中整型值的一个子集。
     *
     * 返回列表 G 中组件的个数，这里对组件的定义为：链表中一段最长连续结点的值（该值必须在列表 G 中）构成的集合。
     * 示例 1：
     *
     * 输入:
     * head: 0->1->2->3
     * G = [0, 1, 3]
     * 输出: 2
     * 解释:
     * 链表中,0 和 1 是相连接的，且 G 中不包含 2，所以 [0, 1] 是 G 的一个组件，同理 [3] 也是一个组件，故返回 2。
     * 示例 2：
     *
     * 输入:
     * head: 0->1->2->3->4
     * G = [0, 3, 1, 4]
     * 输出: 2
     * 解释:
     * 链表中，0 和 1 是相连接的，3 和 4 是相连接的，所以 [0, 1] 和 [3, 4] 是两个组件，故返回 2。
     * 题解思路：
     * 1. 为了在遍历链表的时候，不反复的查询数组G里面的元素，那么我们需要将G哈希化。这样来快速的查询。
     *    有了hash表之后，只需要一遍遍历链表即可搞定问题。需要注意的是：一个组件结束判断标志：
     *    应该是：链表的当前节点在G中，并且链表的下一个节点不在G中或者下一个节点为空。
     *
     * @param head
     * @param G
     * @return
     */
    public int numComponents(ListNode head, int[] G) {
        if(head==null||G.length==0)return 0;
        HashSet<Integer> hashSet=new HashSet<>();
        for (int value : G) hashSet.add(value);
        ListNode node=head;
        int count=0;
        while(node!=null){
            if(hashSet.contains(node.val)&&(node.next==null||!hashSet.contains(node.next.val))) count++;//这个判断非常有意思。
            node=node.next;
        }
        return count;
    }

    /**、
     *
     * 1290. 二进制链表转整数
     * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
     *
     * 请你返回该链表所表示数字的 十进制值 。
     * 示例 1：
     * 输入：head = [1,0,1]
     * 输出：5
     * 解释：二进制数 (101) 转化为十进制数 (5)
     * 示例 2：
     *
     * 输入：head = [0]
     * 输出：0
     * 示例 3：
     *
     * 输入：head = [1]
     * 输出：1
     * 示例 4：
     *
     * 输入：head = [1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
     * 输出：18880
     * 示例 5：
     *
     * 输入：head = [0,0]
     * 输出：0
     * 题解思路：
     * 1. 通过反转链表，然后直接按照二进制转十进制计算即可。
     * 2. 不通过反转链表，模拟十进制转二进制的过程，然后采用逆序即可得到对应的结果。
     * @param head
     * @return
     */
    public int getDecimalValue(ListNode head) {
        ListNode node=reverseListIteration(head);
        int res=0;
        int power=0;
        while(node!=null){
            res+=node.val*Math.pow(2,power++);
            node=node.next;
        }
        return res;
    }

    /**
     * 面试题 02.04. 分割链表
     * 编写程序以 x 为基准分割链表，使得所有小于 x 的节点排在大于或等于 x 的节点之前。
     * 如果链表中包含 x，x 只需出现在小于 x 的元素之后(如下所示)。
     * 分割元素 x 只需处于“右半部分”即可，其不需要被置于左右两部分之间。
     *
     * 示例:
     *
     * 输入: head = 3->5->8->5->10->2->1, x = 5
     * 输出: 3->1->2->10->5->5->8
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        return null;//待实现

    }
}
