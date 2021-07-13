package top100;

import list.ListNode;

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
     * 15. 三数之和
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/3sum/
     * 题解思路：
     * 1. 暴力循环，三数之和必然存在O(n^3)的时间复杂度。显然不能通过。
     * 2. 在 {1. 两数之和} 的基础之上，通过增加一重循环来实现快速查找。当前版本还存在bug
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res=new ArrayList<>();
        for(int i=0;i<nums.length-1;i++){
            //{-4,-1,-1,0,1,2}
            Set<Integer> set=new HashSet<>();
            for(int j=i+1;j<nums.length;j++){
                int element= -nums[i] - nums[j];
                if(set.contains(element)){
                    if(nums[j]==element)break;
                    List<Integer> item=Arrays.asList(nums[i],nums[j],element);
                    res.add(item);
                }else{
                    set.add(nums[j]);
                }
            }
            if(nums[i]==nums[i+1])i++;
        }
        return res;
        // 通过排序之后依然存在问题：[0,0,0,0],运行结果为：{[0,0,0],[0,0,0]}
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

    /**
     * 11. 盛最多的水
     * @param height
     * @return
     * @see https://leetcode-cn.com/problems/container-with-most-water/
     * 题解思路：
     * 1. 暴力求解，双重循环
     * 2. 双指针，指针的移动策略是：选择当前指针指向的高度值最小的指针进行移动。
     */
    public int maxArea(int[] height) {
        int left=0;
        int right=height.length-1;
        int res=area(height,left,right);
        while(left<right){
            if(height[left]>=height[right]){
                right--;
            }else{
                left++;
            }
            res=Math.max(res,area(height,left,right));
        }
        return res;
    }
    private int area(int[] h,int l,int r){
        return Math.min(h[l],h[r])*(r-l);
    }

    /**
     * 17. 电话号码的字母组合
     * @param digits
     * @return
     * @see https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
     * 题解思路：
     * 1. DFS+回溯
     */
    //一个映射表，第二个位置是"abc“,第三个位置是"def"。。。
    //这里也可以用map，用数组可以更节省点内存
    String[] letter_map = {" ","*","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    public List<String> letterCombinations(String digits) {
        //注意边界条件
        if(digits==null || digits.length()==0) {
            return new ArrayList<>();
        }
        iterStr(digits, new StringBuilder(), 0);
        return res;
    }
    //最终输出结果的list
    List<String> res = new ArrayList<>();

    //递归函数
    void iterStr(String str, StringBuilder letter, int index) {
        //递归的终止条件，注意这里的终止条件看上去跟动态演示图有些不同，主要是做了点优化
        //动态图中是每次截取字符串的一部分，"234"，变成"23"，再变成"3"，最后变成""，这样性能不佳
        //而用index记录每次遍历到字符串的位置，这样性能更好
        if(index == str.length()) {
            res.add(letter.toString());
            return;
        }
        //获取index位置的字符，假设输入的字符是"234"
        //第一次递归时index为0所以c=2，第二次index为1所以c=3，第三次c=4
        //subString每次都会生成新的字符串，而index则是取当前的一个字符，所以效率更高一点
        char c = str.charAt(index);
        //map_string的下表是从0开始一直到9， c-'0'就可以取到相对的数组下标位置
        //比如c=2时候，2-'0'，获取下标为2,letter_map[2]就是"abc"
        int pos = c - '0';
        String map_string = letter_map[pos];
        //遍历字符串，比如第一次得到的是2，页就是遍历"abc"
        for(int i=0;i<map_string.length();i++) {
            //调用下一层递归，用文字很难描述，请配合动态图理解
            letter.append(map_string.charAt(i));
            //如果是String类型做拼接效率会比较低
            //iterStr(str, letter+map_string.charAt(i), index+1);
            iterStr(str, letter, index+1);
            letter.deleteCharAt(letter.length()-1);
        }
    }

    /**
     * 19. 删除链表中的倒数第N个节点
     * @param head
     * @param n
     * @return
     * @see https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
     * 题解思路：
     * 1. 滑动窗口
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode top=new ListNode(0);
        top.next=head;
        ListNode p=top;
        for(int i=0;i<n;i++){
            if(p==null)return null;
            else p=p.next;
        }
        ListNode h=top;
        while(p.next!=null){
            h=h.next;
            p=p.next;
        }
        h.next=h.next.next;
        return top.next;
    }

    /**
     * 20. 有效的括号
     * @param s
     * @return
     * @see https://leetcode-cn.com/problems/valid-parentheses/
     * 题解思路：
     * 1. 栈的应用
     */
    public boolean isValid(String s) {
        List<Character> stack=new LinkedList<>();
        int i=0;
        while(i<s.length()){
            if(stack.isEmpty())stack.add(s.charAt(i));
            else{
                if(isMatch(stack.get(stack.size()-1),s.charAt(i))){
                    stack.remove(stack.size()-1);
                }
                else stack.add(s.charAt(i));
            }
            i++;
        }
        return stack.size()==0;

    }
    private boolean isMatch(char a,char b){
        return a == '(' && b == ')' || a == '[' && b == ']' || a == '{' && b == '}';
    }

    /**
     * 21. 合并两个有序链表
     * @param l1
     * @param l2
     * @return
     * @see https://leetcode-cn.com/problems/merge-two-sorted-lists/
     * 题解思路：
     * 1. 归并排序
     * 2. 递归
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null)return l2;
        if(l2==null)return l1;
        if(l1.val<l2.val){
            l1.next=mergeTwoLists(l1.next,l2);
            return l1;
        }else{
            l2.next=mergeTwoLists(l1,l2.next);
            return l2;
        }
    }


    public static void main(String[] args) {
        final Solution solution = new Solution();

        ListNode list=new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4))));
        ListNode list2=new ListNode(5,new ListNode(6,new ListNode(7,new ListNode(8))));

        final ListNode node = solution.mergeTwoLists(list, list2);
        node.print();


    }
}
