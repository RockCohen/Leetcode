package top100;

import list.ListNode;

import javax.swing.plaf.SliderUI;
import java.util.*;

public class Solution {
    /**
     * 1. 两数之和
     * @param nums 原数组
     * @param target 目标值
     * @return
     * @see https://leetcode-cn.com/problems/two-sum/
     * 题解思路：哈希
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map= new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]),i};
            }
            else map.put(nums[i],i);
        }
        return new int[0];
    }

    /**
     * 2. 两数相加
     * @param l1 链表1
     * @param l2 链表2
     * @return 新链表
     * @see https://leetcode-cn.com/problems/add-two-numbers/
     * 题解思路：
     * 1. 模拟：加法器
     *
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p=l1;
        ListNode q=l2;
        ListNode res=new ListNode();
        ListNode result=res;
        int carry=0;//进位位
        while(p!=null&&q!=null){
            res.next=new ListNode((p.val+q.val+carry)%10);
            res=res.next;
            carry=(p.val+q.val+carry)/10;
            p=p.next;
            q=q.next;
        }
        while(p!=null){
            res.next=new ListNode((p.val+carry)%10);
            res=res.next;
            carry=(p.val+carry)/10;
            p=p.next;
        }
        while(q!=null){
            res.next=new ListNode((q.val+carry)%10);
            res=res.next;
            carry=(q.val+carry)/10;
            q=q.next;
        }
        if(carry>0)res.next=new ListNode(carry);
        return result.next;
    }

    /**
     * 3. 无重复字符的最长子串
     * @param s 源字符串
     * @return 最长子串的长度
     * @see https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 题解思路：
     * 首先要明确子串与子序列的区别
     * 1. 滑动窗口
     */
    public int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    /**
     * 4. 寻找两个正序列数组的中位数
     * @param nums1 数组1
     * @param nums2 数组2
     * @return 中位数
     * @see https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
     * 题解思路：
     * 1. 模拟，先合并数组，然后在求中位数，需要O(m+n)的空间复杂度，时间复杂度也是O(m+n)
     * 2. 模拟，不需要合并数组，只需要找到中位数的索引即可，通过维护两个指针。
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int sum=nums1.length+nums2.length;
        int len=sum/2;
        int i = 0,j=0;
        if(nums1.length==0){
            if(nums2.length%2==0)return (nums2[nums2.length/2]+nums2[nums2.length/2-1])/2.0;
            else return nums2[nums2.length/2];
        }
        if(nums2.length==0){
            if(nums1.length%2==0)return (nums1[nums1.length/2]+nums1[nums1.length/2-1])/2.0;
            else return nums1[nums1.length/2];
        }
        while(i+j<=len-1){
            if(nums1[i]<=nums2[j]){
                i++;
            }
            else j++;
        }
        if(sum%2==0){
            return (nums1[i]+nums2[j])/2.0;
        }
        else return Math.max(nums1[i], nums2[j]);
    }

    public static void main(String[] args) {
        // test 1
//        int[] nums={3,3,2,4};
//        final int[] sum = new Solution().twoSum(nums, 6);
//        System.out.println(Arrays.toString(sum));

        // test 2
//        ListNode l1=new ListNode(2,new ListNode(4,new ListNode(3)));
//        ListNode l2=new ListNode(5,new ListNode(6,new ListNode(4)));
//        final ListNode node = new Solution().addTwoNumbers(l1, l2);
//        node.print();

        //test 3
        int[] n={3};
        int[] m={-2,-1};
        final double arrays = new Solution().findMedianSortedArrays(n, m);
        System.out.println(arrays);

    }
}
