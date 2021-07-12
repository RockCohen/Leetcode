package top100;

import list.ListNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        int len = m + n;
        int left = -1, right = -1;
        int aStart = 0, bStart = 0;
        for (int i = 0; i <= len / 2; i++) {
            left = right;
            if (aStart < m && (bStart >= n || A[aStart] < B[bStart])) {
                right = A[aStart++];
            } else {
                right = B[bStart++];
            }
        }
        if ((len & 1) == 0)
            return (left + right) / 2.0;
        else
            return right;
    }

    /**
     * 5. 最长回文子串
     * @param s
     * @return
     * @see https://leetcode-cn.com/problems/longest-palindromic-substring/
     * 题解思路：
     * 1. 动态规划 ：P(i,j)=P(i+1,j−1)∧(Si==Sj),P(i,i)=true;
     * 2. 中心扩展算法 ：回文串的特性决定
     */
    public String longestPalindrome(String s) {

        int len=s.length();
        if(len<2)return s;// 特殊情况，一定是回文串

        int maxLen=1;
        int begin=0;
        // dp[i][j]表示s[i,....j]是否为回文子串
        boolean[][] dp =new boolean[len][len];
        for(int i=0;i<len;i++){
            dp[i][i]=true;
        }

        char[] charArray = s.toCharArray();

        for(int L=2;L<=len;L++){
            for(int i=0;i<len;i++){
                int j=L+i-1;
                if(j>=len){
                    break;
                }
                if(charArray[i]!=charArray[j]){//首尾字符不相等，一定不是回文串
                    dp[i][j]=false;
                }
                else{
                    // 如果i,j之间不存在字符，并且s[i]==s[j]，那么一定是回文串
                    if(j-i<3){
                        dp[i][j]=true;
                    }
                    else{
                        dp[i][j]=dp[i+1][j-1];
                    }
                }
                // 只要 dp[i][j] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    public String longestPalindrome_II(String s){
        if(s==null||s.length()<1){
            return "";
        }
        int start=0,end=0;
        for(int i=0;i<s.length();i++){
            int len1=expandAroundCenter(s,i,i);//回文串长度为奇数的情况
            int len2=expandAroundCenter(s,i,i+1);//回文串长度为偶数的情况
            int len=Math.max(len1,len2);
            //根据回文串长度与中心位置算出子串的起始位置与结尾位置。
            if(len>end-start){
                start=i-(len-1)/2;
                end=i+len/2;
            }
        }
        return s.substring(start,end+1);
    }

    private int expandAroundCenter(String s,int left,int right){
        while(left>=0&&right<s.length()&&s.charAt(left)==s.charAt(right)){
            --left;
            ++right;
        }
        return right-left+1;
    }

    public static void main(String[] args) {
        final Solution solution = new Solution();

    }
}
