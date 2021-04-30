package swordPointingOffer;

import list.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 剑指offer相关题目
 */
public class Offer {
    /**
     * 题目：数组中重复的数字
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof/
     *
     * 题解：
     * 1. 排序之后寻找第一个或者最后一个或者所有的重复元素。
     * 2. 哈希表，直接利用哈希存储对应元素的个数
     */
    public int findRepeatNumber(int[] nums) {
        int[] hash=new int[nums.length+1];
        for(int num:nums)hash[num]++;
        for(int i=0;i<hash.length;i++){
            if(hash[i]>1)return i;
        }
        return 0;
    }

    /**
     * 题目：二维矩阵的搜索
     * @param matrix
     * @param target
     * @return
     * @see https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int col = 0, row = matrix.length - 1;
        while(row >= 0 && col < matrix[0].length){
            if(target > matrix[row][col]){
                col++;
            }else if(target < matrix[row][col]){
                row--;
            }else{
                return true;
            }
        }
        return false;
    }

    /**
     * 题目：空格替换
     * @param s
     * @return
     * @see https://leetcode-cn.com/problems/ti-huan-kong-ge-lcof/
     */
    public String replaceSpace(String s) {
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                stringBuilder.append("%20");
            }
            else stringBuilder.append(s.charAt(i));
        }
        return stringBuilder.toString();
    }

    /**
     * 题目：反转链表
     * @param head
     * @return
     * @see https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/
     * 题解思路：直接使用栈的思想，涉及到从后面访问元素的思想都是使用栈。
     */

    public int[] reversePrint(ListNode head) {
        ListNode p=head;
        List<Integer> l=new ArrayList<>();
       while(p!=null){
           l.add(0,p.val);
           p=p.next;
       }
       int[] res=new int[l.size()];
        for (int i = 0; i < res.length; i++) {
            res[i]=l.get(i);
        }
        return res;
    }

    /**
     * 题目：斐波拉契数列
     * @param n
     * @return
     * @see https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/
     */
    public int fib(int n) {
        if(0==n)return 0;
        if(1==n)return 1;
        int cur=1;
        int former=0;
        for(int i=2;i<=n;i++)
        {
            int cCur=(former+cur)%1000000007;
            former=cur;
            cur=cCur;
        }
        return cur;
    }

    /**
     * 题目：旋转数组的最小值
     * @param numbers
     * @return
     * 题解：
     * 1. 最小值一定存在在极值处。那么根据极值的特点遍历一遍数组即可。
     * 2.  二分法， [9,10,1,2,3,4,5,6,7,8]
     *     旋转数组的特点就是：前一部分的值一定比后一部分的值大。
     *     于是看可以利用这特点进行检索的压缩。
     */
    public int minArray(int[] numbers) {
        for(int i=0;i<numbers.length-1;i++){
            if(numbers[i]>numbers[i+1])return numbers[i+1];
        }
        return numbers[0];
    }
    public int minArrayBinarySearch(int[] numbers) {
        return binarySearch(numbers,0,numbers.length-1);
    }
    public int binarySearch(int[] n,int l,int r){
        if (r - l <= 1) return Math.min(n[l], n[r]);
        if (n[l] < n[r]) return n[l];
        int mid=(l+r)/2;
        if(n[mid]==n[r])return binarySearch(n,l,r-1);
        if(n[mid]==n[l])return binarySearch(n,l+1,r);
        if(n[mid]>n[r])return binarySearch(n,mid,r);
        else return  binarySearch(n, l,mid);
    }

    /**
     * 题目：二进制数中的1的个数。
     * @param n
     * @return
     * @see https://leetcode-cn.com/problems/er-jin-zhi-zhong-1de-ge-shu-lcof/submissions/
     *
     * 题解思路：
     * 最大的问题是对于负数的处理，在Java中没有无符号数的概念，所以我们可以利用类型扩充的方式来实现。
     * 对于一个int类型的负数对应的无符号数我们可以利用公式计算：(long)2^32+n。这样就变成了一个正数了。
     *
     * 其实Java中提供了无符号左移与右移操作:>>>  <<<.直接利用这个即可。
     *
     */
    public int hammingWeight(int n) {
        long t= n<0? (long) (Math.pow(2, 32) + n) :n;
        int count=1;
        while(t!=1){
            if(t%2==1)count++;
            t/=2;
        }
        return count;
    }
    public int hammingWeightII(int n) {
        int res = 0;
        while(n != 0) {
            res += n & 1;
            n >>>= 1;
        }
        return res;
    }
    public ListNode deleteNode(ListNode head, int val) {
        ListNode root=new ListNode(0);//增加头结点，提供删除的便利性
        root.next=head;
        ListNode p=root;
        while(p.next!=null&&p.next.val!=val)p=p.next;
        if(p.next==null)return root.next;
        p.next=p.next.next;
        return root.next;
    }

    public static void main(String[] args) {
       ListNode head=new ListNode(3);//ListNode(0,new ListNode(1,new ListNode(2)));
       ListNode res=new Offer().deleteNode(null,3);
       if(res==null)System.out.println("res is null");
       if(res!=null) res.print();
       System.out.println(res);
    }
}
