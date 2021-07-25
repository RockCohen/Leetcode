package top100;

import list.ListNode;
import tree.TreeNode;
import java.util.*;

public class Solution {
    /**
     * 1. 两数之和
     * @param nums 原数组
     * @param target 目标值
     * @return
     * 参考:  https://leetcode-cn.com/problems/two-sum/
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
     * @return
     * 参考:  https://leetcode-cn.com/problems/3sum/
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
     * 参考:  https://leetcode-cn.com/problems/add-two-numbers/
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
     * 参考:  https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 题解思路：
     * 首先要明确子串与子序列的区别
     * 1. 滑动窗口
     */
    public int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
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
     * @return 中位数
     * 参考:  https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
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
     * @return
     * 参考:  https://leetcode-cn.com/problems/longest-palindromic-substring/
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
     * 参考:  https://leetcode-cn.com/problems/container-with-most-water/
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
     * 参考:  https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
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
     * 参考:  https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
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
     * 参考:  https://leetcode-cn.com/problems/valid-parentheses/
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
     * 参考:  https://leetcode-cn.com/problems/merge-two-sorted-lists/
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

    /**
     * 22. 括号生成
     * @param n
     * @return
     * 参考:  https://leetcode-cn.com/problems/generate-parentheses/
     * 题解思路：
     * ()
     * ()(),(())
     * ()()(),(())(),()(()),((())),(()())
     * ()()()(),()(())(),(())()(),()()(()),()(())(),()((())),((()))(),()(()()),(()())(),(()()()),((())()),(()(())),(((()))),((()()))
     *
     * 1. 动态规划，基本思路:在已有的前面，后面，外面分别再添加一个括号得到新的括号数量。只有第一种情况存在重复（前面和后面的结果是一样的，最终减去一即可）
     * 递推公式：f(n)=3*f(n-1)-1
     * 下面的题解思路思路上一定是对的，但是提交通过不了力扣，原因在于：元素的顺序，我认为这是不重要的，所以下面的方案应该也是可行的。
     */
    public List<String> generateParenthesis(int n) {
        if(n<1)return new ArrayList<>();
        List<String> strings= Collections.singletonList("()");
        for(int i=1;i<n;i++){
            List<String> item=new ArrayList<>();
            for (String string : strings) {
                item.add("(" + string + ")");
                item.add(string + "()");
                item.add("()" + string);
            }
            item.remove(item.size()-1);
            strings=item;
        }
        return strings;
    }

    /**
     * 23. 合并k个升序链表
     * @param lists
     * @return
     * 参考:  https://leetcode-cn.com/problems/merge-k-sorted-lists/
     *
     * 题解思路：
     * 1. 将所有元素放入数组中，排序重新构造链表
     * 2. 将所有元素放入堆中，从堆中取出元素构造链表
     * 3. 将所有元素放入堆中，从堆中去除元素（节点）并建立他们的链式关系
     * 4. ......
     */
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> priorityQueue=new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
        // 入队
        for (ListNode list : lists) {
            ListNode node = list;
            while (node != null) {
                priorityQueue.add(node);
                node = node.next;
            }
        }
        // 出队
        ListNode head=new ListNode(0);
        ListNode top=head;
        while(!priorityQueue.isEmpty()){
            head.next= priorityQueue.poll();
            head=head.next;
        }
        head.next=null;
        return top.next;
    }

    /**
     * 31.下一个排列
     * @param nums
     * 参考:  https://leetcode-cn.com/problems/next-permutation/
     * 题解思路：
     * a. 1,2,3  -->  1,3,2
     * b. 1,3,2  -->  2,1,3     3,4,2  -->  4,2,3
     * c. 3,2,1 --->  1,2,3
     * 我们需要将一个左边的「较小数」与一个右边的「较大数」交换，以能够让当前排列变大，从而得到下一个排列。
     * 同时我们要让这个「较小数」尽量靠右，而「较大数」尽可能小。
     * 当交换完成后，「较大数」右边的数需要按照升序重新排列。
     * 这样可以在保证新排列大于原来排列的情况下，使变大的幅度尽可能小。
     */
    public void nextPermutation(int[] nums) {

        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    /**
     * 32. 最长有效括号
     * @param s
     * @return
     * 参考：https://leetcode-cn.com/problems/longest-valid-parentheses/
     * 题解思路：
     * 1. https://leetcode-cn.com/problems/longest-valid-parentheses/solution/zui-chang-you-xiao-gua-hao-by-leetcode-solution/
     */
    public int longestValidParentheses(String s) {
        int maxans = 0;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

    /**
     * 33. 搜索旋转排序数组
     * @param nums
     * @param target
     * @return
     * 参考：https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
     * 题解思路：
     * 1. 暴力搜索
     * 2. 首尾比较，如果大于首元素，正序查找，如果小于尾元素，逆序查找，结束标志：存在跳跃的地方，如果小于首元素，且大于尾元素，不存在该元素。
     * 3. 二分查找
     *
     *
     */
    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[n - 1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * @param nums
     * @param target
     * @return
     * 参考：https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/
     * 题解思路：
     * 1. 暴力检索，时间复杂度O(n)
     * 2. 二分法,通过设置标志位来确定在那一段进行配置
     */
    public int[] searchRange(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false) - 1;
        if (leftIdx <= rightIdx && rightIdx < nums.length && nums[leftIdx] == target && nums[rightIdx] == target) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }
    private int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            // 标志位的设置将搜索路径一分为二
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    /**
     * 39. 组合总和
     * @param candidates
     * @param target
     * @return
     * 参考：https://leetcode-cn.com/problems/combination-sum/
     * 题解思路：
     * 回溯法
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        int len=candidates.length;
        List<List<Integer>>res=new ArrayList<>();
        if(len==0)return res;
        Arrays.sort(candidates);
        Deque<Integer> path=new ArrayDeque<>();
        dfs(candidates,0,len,target,path,res);
        return res;
    }

    private void dfs(int[] candidates,int begin,int len,int target,Deque<Integer> path,List<List<Integer>> res){
        if(target==0){
            res.add(new ArrayList<>(path));
        }
        for(int i=begin;i<len;i++){
            if(target-candidates[i]<0)break;
            path.add(candidates[i]);
            dfs(candidates,i,len,target-candidates[i],path,res);
            path.removeLast();
        }
    }

    /**
     * 46. 全排列
     * @param nums
     * @return
     * 参考：https://leetcode-cn.com/problems/permutations/
     * 题解思路：
     * 1. 回溯法
     */
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> path=new ArrayList<>();
        List<List<Integer>> res=new ArrayList<>();
        boolean[] used=new boolean[nums.length];
        for(boolean x:used){
            x=false;
        }
        permuteHelper(nums,nums.length,0,path,used,res);
        return res;
    }
    private void permuteHelper(int[] nums, int len, int depth,
                     List<Integer> path, boolean[] used,
                     List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 在非叶子结点处，产生不同的分支，这一操作的语义是：在还未选择的数中依次选择一个元素作为下一个位置的元素，这显然得通过一个循环实现。
        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.add(nums[i]);
                used[i] = true;
                permuteHelper(nums, len, depth + 1, path, used, res);
                // 注意：下面这两行代码发生 「回溯」，回溯发生在从 深层结点 回到 浅层结点 的过程，代码在形式上和递归之前是对称的
                used[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }
    /**
     * 42. 接雨水
     * @param height
     * @return
     * 参考：https://leetcode-cn.com/problems/trapping-rain-water/
     * 题解思路：
     * 1. 曲线模拟，显然只有波峰与波峰之间才能装雨水。然后再在连续的波峰之间计算凹下去的面积。
     */
    public int trap(int[] height) {
        int res=0;
        Queue<Map.Entry<Integer,Integer>> queue=new LinkedList<>();
        for(int i=0;i< height.length;i++){
            if(i==0){//左边界
                if(height[i]>height[i+1]){
                    queue.add(new AbstractMap.SimpleEntry<>(i,height[i]));
                }
            }
            else if(i==height.length-1){//右边界
                if(height[i]>height[i-1]){
                    queue.add(new AbstractMap.SimpleEntry<>(i,height[i]));
                }
            }
            else{//一般情况
                if(height[i]>height[i-1]&&height[i]>height[i+1]){
                    queue.add(new AbstractMap.SimpleEntry<>(i,height[i]));
                }
            }
        }
        if(queue.size()==0)return res;
        Map.Entry<Integer,Integer> left=queue.poll();
        while(!queue.isEmpty()){
            Map.Entry<Integer,Integer> right=queue.poll();
            int high=Math.min(left.getValue(),right.getValue());
            int sum=0;
            for(int i=left.getKey();i<right.getKey();i++){
                int mid= Math.max(high-height[i], 0);
                sum+= mid;
            }
            res+=sum;
            left=right;
        }
        return res;
    }

    /**
     * 53. 最大子序和
     * @param nums
     * @return
     * 参考：https://leetcode-cn.com/problems/maximum-subarray/
     * 题解思路：
     * 1. 分支法
     * 2. 动态规划 f(i)=max{ f(i-1)+nums[i],nums[i] }
     */
    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    /**
     * 121. 买卖股票的最佳时机
     * @param prices
     * @return
     * 参考：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
     * 题解思路：
     * 1. 动态规划
     *    首先处理数组，获得每天的股票基于前一天的差额，然后利用最大子序和即可求解。
     */
    public int maxProfit(int[] prices) {
        int pre = 0, maxAns = 0;
        for (int i=1;i<prices.length;i++) {
            int x=prices[i]-prices[i-1];
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }



    /**
     * 55. 跳跃游戏
     * @param nums
     * @return
     * 参考：https://leetcode-cn.com/problems/jump-game/
     * 题解思路：
     * 1. 动态规划
     */
    public boolean canJump(int[] nums) {

        boolean[] dp=new boolean[nums.length];
        for(boolean x:dp){
            x=false;
        }
        dp[0]=true;
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(dp[j] &&nums[j]>=(i-j)){
                    dp[i]=true;
                    break;
                }
            }
        }
        return dp[nums.length-1];
    }
    public boolean canJump_II(int[] nums) {
        int n = nums.length;
        int rightmost = 0;
        for (int i = 0; i < n; ++i) {
            if (i <= rightmost) {
                rightmost = Math.max(rightmost, i + nums[i]);
                if (rightmost >= n - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 62. 不同路径
     * @param m
     * @param n
     * @return
     * 参考：https://leetcode-cn.com/problems/unique-paths/
     * 题解思路：
     * 1. 动态规划：f[i][j]=f[i-1][j]+f[i][j-1]
     * 2. 组合数学  (m+n-2)! / [(m-1)! *(n-1)!]
     */
    public int uniquePaths(int m, int n) {
        long ans = 1;
        for (int x = n, y = 1; y < m; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }
    public int uniquePaths_Dynamic(int m, int n) {
        int[][] f = new int[m][n];
        for (int i = 0; i < m; ++i) {
            f[i][0] = 1;
        }
        for (int j = 0; j < n; ++j) {
            f[0][j] = 1;
        }
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                f[i][j] = f[i - 1][j] + f[i][j - 1];
            }
        }
        return f[m - 1][n - 1];
    }

    /**
     * 64.最小路径和
     * @param grid
     * @return
     * 参考：https://leetcode-cn.com/problems/minimum-path-sum/
     * 题解思路：
     * 1.动态规划，与上题一样
     *  f[i][j]=min(f[i-1][j]+nums[i][j],f[i][j-1]+nums[i][j])
     *
     */
    public int minPathSum(int[][] grid) {
        int iLen=grid.length;
        int jLen=grid[0].length;
        int[][] dp=new int[iLen][jLen];
        dp[0][0]=grid[0][0];
        for(int i=1;i<jLen;i++){
            dp[0][i]=dp[0][i-1]+grid[0][i];
        }
        for(int i=1;i<iLen;i++){
            dp[i][0]=dp[i-1][0]+grid[i][0];
        }
        for(int i=1;i<iLen;i++){
            for(int j=1;j<jLen;j++){
                dp[i][j]=Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j];
            }
        }
        return dp[iLen-1][jLen-1];
    }

    /**
     * 70. 爬楼梯
     * @param n
     * @return
     * 参考：https://leetcode-cn.com/problems/climbing-stairs/
     * 题解思路：
     * 1. 迭代
     * 2. 斐波拉契数列
     */
    public int climbStairs(int n) {
        int f0=1;
        int f1=1;
        int res=0;
        for(int i=1;i<n;i++){
            res=f0+f1;
            f0=f1;
            f1=res;
        }
        return res;
    }

    /**
     * 78.子集
     * @param nums
     * @return
     * 参考：https://leetcode-cn.com/problems/subsets/
     * 题解思路：
     * 1. 数学推论，已知存在n个相异元素的集合可以得到2^n个子集。刚好对应0--->2^n-1.
     * 可以将二进制数与选择的元素对应，二进制该位为1表示选择该元素，否则不选择该元素。
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        int n=nums.length;
        int mask=1<<n;
        for(int i=0;i<mask;i++){
            int number=i;
            List<Integer> list=new ArrayList<>();
            for (int num : nums) {
                if ((number & 1) == 1) {
                    list.add(num);
                }
                number>>=1;
            }
            ans.add(list);
        }
        return ans;
    }

    /**
     * 79. 单词搜索
     * @param board
     * @param word
     * @return
     * 参考：https://leetcode-cn.com/problems/word-search/
     * 题解思路：
     * 1. 回溯
     */
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean exist = false;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if (board[i][j] == word.charAt(0)){
                    exist = dfs(board, visited, i, j, word, 0);
                    if (exist){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, boolean[][] visited, int i, int j, String word, int index){

        int m = board.length;
        int n = board[0].length;
        if (i >= 0 && i < m && j >= 0 && j < n && !visited[i][j] && board[i][j] == word.charAt(index)){
            visited[i][j] = true;
            if (index == word.length() - 1){
                return true;
            }
            boolean b1 = dfs(board, visited, i + 1, j, word, index + 1);
            boolean b2 = dfs(board, visited, i - 1, j, word, index + 1);
            boolean b3 = dfs(board, visited, i , j + 1, word, index + 1);
            boolean b4 = dfs(board, visited, i, j - 1, word, index + 1);
            visited[i][j] = false;
            return b1 || b2 || b3 || b4;
        }
        return false;
    }
    /**
     * 模拟回溯的过程，首先检查当前位置的字符是否满足要求，满足要求继续向下检索，
     * 此时则存在向那个方向检索的问题，于是通过数组向量的方式给出方向。
     * 然后需要在不同的方向的下一个字符做同样的操作，需要注意的是，一次完整的检索链应该保证
     * 已经被检索过的元素不能被再一次检索，通过设置访问数组实现检索元素的状态记录。
     * 检索匹配成功的标志是最后一个字符也匹配成功，此时直接返回true，如果当前的字符不匹配，那么直接放回false。
     */
    private boolean check(char[][] board, boolean[][] visited, int i, int j, String s, int k){
        if(board[i][j]!=s.charAt(k))return false;
        else if(k==s.length()-1)return true;
        visited[i][j]=true;
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};//定义查找方向
        boolean res=false;
        for(int[] direction:directions){
            int iNew = direction[0]+i,jNew=direction[1]+j;//获取当前方向
            if(iNew>0&&iNew<board.length&&jNew>0&&jNew<board[0].length){
                if(!visited[iNew][jNew]){
                    boolean flag=check(board,visited,iNew,jNew,s,k+1);
                    if(flag){
                        res=true;
                        break;
                    }
                }
            }
        }
        visited[i][j]=false;
        return res;
    }
    /**
     * 200. 岛屿数量
     * @param grid
     * @return
     * 参考：https://leetcode-cn.com/problems/number-of-islands/
     * 题解思路：
     * 1. dfs
     * 2. bfs
     */
    void dfs_NumIslands(char[][] grid, int r, int c) {
        int nr = grid.length;
        int nc = grid[0].length;
        // 这一步处理真是妙啊
        if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
            return;
        }
        grid[r][c] = '0';
        dfs_NumIslands(grid, r - 1, c);
        dfs_NumIslands(grid, r + 1, c);
        dfs_NumIslands(grid, r, c - 1);
        dfs_NumIslands(grid, r, c + 1);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    dfs_NumIslands(grid, r, c);
                }
            }
        }
        return num_islands;
    }

    /**
     * 94. 二叉树的中序遍历
     * @param root
     * @return
     * 参考：https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
     * 题解思路：
     * 1. 递归
     * 2. 迭代法（栈）
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        Deque<TreeNode> treeNodeStack = new LinkedList<>();//存储节点的栈
        TreeNode node = root;//获取根节点
        List<Integer> res=new ArrayList<>();//存储遍历节点（遍历的结果）
        //只有当前节点与栈不为空才结束操作。
        while (node != null || !treeNodeStack.isEmpty()) {
            //左子树疯狂入栈。（递归的本质）
            while (node != null) {
                treeNodeStack.push(node);
                node = node.left;
            }
            //当左子树为空，并且这个时候应该栈不为空。
            // 获取栈顶元素，访问之，然后将其右子树入栈
            // 重复上述的操作。
            node = treeNodeStack.pop();
            //遍历节点
            res.add(node.val);
            node = node.right;
        }
        return res;
    }

    /**
     * 96. 不同的二叉搜索树
     * @param n
     * @return
     * 参考：https://leetcode-cn.com/problems/unique-binary-search-trees/
     * 题解思路：
     * 1. 动态规划，以i为树根的树是唯一的。其构成是G(1,i)与G(i+1,n)的笛卡尔积。其中
     *          G(0)=1;
     *          G(1)=1;
     * 2. 数学组合
     */
    public int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;
        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }

    /**
     * 128. 最长连续序列
     * @param nums
     * @return
     * 参考：https://leetcode-cn.com/problems/longest-consecutive-sequence/
     * 题解思路：
     * 1. 排序
     * 2. 哈希（哈希表的选择比较难搞）
     * 3. 堆
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }

    /**
     * 136. 只出现一次的数字
     * @param nums nums
     * @return
     * 参考：https://leetcode-cn.com/problems/single-number/
     * 题解思路：
     * 1. 亦或（亦或yyds）
     */
    public int singleNumber(int[] nums) {
        int ans=0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }

    /**
     * 139. 单词拆分
     * @return
     * 参考：https://leetcode-cn.com/problems/word-break/
     * 题解思路：
     * 遍历wordDict然后匹配每个子串看看他们之间是否存在交区间
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        return false;
    }

    /**
     * 141. 环形链表
     * @param head
     * @return
     * 参考：https://leetcode-cn.com/problems/linked-list-cycle/
     * 题解思路：
     * 1. 快慢指针
     * 2. 哈希表
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
     * 参考：https://leetcode-cn.com/problems/linked-list-cycle-ii/
     * 题解思路：
     * 1. 哈希表
     * 2. 快慢指针
     */
    public ListNode detectCycle(ListNode head) {
        ListNode pos = head;
        Set<ListNode> visited = new HashSet<>();
        while (pos != null) {
            if (visited.contains(pos)) {
                return pos;
            } else {
                visited.add(pos);
            }
            pos = pos.next;
        }
        return null;
    }

    /**
     *
     */
    class LRUCache extends LinkedHashMap<Integer, Integer>{
        private final int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }

    /**
     *
     */
    static class LRUCacheSelfDefine {
        static class DLinkedNode {
            int key;
            int value;
            DLinkedNode prev;
            DLinkedNode next;
            public DLinkedNode() {}
            public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
        }

        private final Map<Integer, DLinkedNode> cache = new HashMap<>();
        private int size;
        private final int capacity;
        private final DLinkedNode head;
        private final DLinkedNode tail;

        public LRUCacheSelfDefine(int capacity) {
            this.size = 0;
            this.capacity = capacity;
            // 使用伪头部和伪尾部节点
            head = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            // 如果 key 存在，先通过哈希表定位，再移到头部
            moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                // 如果 key 不存在，创建一个新的节点
                DLinkedNode newNode = new DLinkedNode(key, value);
                // 添加进哈希表
                cache.put(key, newNode);
                // 添加至双向链表的头部
                addToHead(newNode);
                ++size;
                if (size > capacity) {
                    // 如果超出容量，删除双向链表的尾部节点
                    DLinkedNode tail = removeTail();
                    // 删除哈希表中对应的项
                    cache.remove(tail.key);
                    --size;
                }
            }
            else {
                // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
                node.value = value;
                moveToHead(node);
            }
        }

        private void addToHead(DLinkedNode node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        private void removeNode(DLinkedNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void moveToHead(DLinkedNode node) {
            removeNode(node);
            addToHead(node);
        }

        private DLinkedNode removeTail() {
            DLinkedNode res = tail.prev;
            removeNode(res);
            return res;
        }
    }

    /**
     *148. 排序链表
     * 参考：https://leetcode-cn.com/problems/sort-list/
     * 题解思路：
     * 分治归并排序
     * 首先利用快慢指针找到链表的中点，然后调用归并排序合并前后
     */
    public ListNode sortList(ListNode head) {
        return sortList(head, null);
    }
    public ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return null;
        }
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        //找到中点
        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        //分治
        ListNode mid = slow;
        ListNode list1 = sortList(head, mid);
        ListNode list2 = sortList(mid, tail);
        return merge(list1, list2);
    }
    // 可以采用递归的写法简化
    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode temp = dummyHead, temp1 = head1, temp2 = head2;
        while (temp1 != null && temp2 != null) {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }

    /**
     * 152. 乘积最大子数组
     * 参考：https://leetcode-cn.com/problems/maximum-product-subarray/
     * 题解思路：
     * 1. 动态规划，关键在于：符号的存在影响了判断最大值的方式。
     *            有可能当前的负数乘以一个负数反而成了最大值。于是有了下面的接替方案
     */
    public int maxProduct(int[] nums) {
        int maxF = nums[0], minF = nums[0], ans = nums[0];
        int length = nums.length;
        for (int i = 1; i < length; ++i) {
            int mx = maxF, mn = minF;
            maxF = Math.max(mx * nums[i], Math.max(nums[i], mn * nums[i]));
            minF = Math.min(mn * nums[i], Math.min(nums[i], mx * nums[i]));
            ans = Math.max(maxF, ans);
        }
        return ans;
    }

    /**
     * 160. 相交链表
     * @param headA
     * @param headB
     * @return
     * 参考：https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
     * 题解思路：
     * 1. 哈希表
     * 2. 栈
     * 3. 双指针（具体的做法是链表A与链表B。将链表A的循环指针循环之后连接到B的首部，同样将B的连接到A的首部。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> set=new HashSet<>();
        while(headA!=null) {
            set.add(headA);
            headA=headA.next;
        }
        while(headB!=null) {
            if (set.contains(headB)) return headB;
            headB = headB.next;
        }
        return null;
    }

    /**
     *198. 打家劫舍
     * 参考：https://leetcode-cn.com/problems/house-robber/
     * 题解思路：
     * 1. 动态规划
     */
    public int rob(int[] nums) {
        if(nums.length==0)return 0;
        int[] dp=new int[nums.length+1];
        dp[1]=nums[0];
        if(nums.length==1)return dp[1];
        dp[2]=Math.max(nums[0],nums[1]);
        if(nums.length==2)return dp[2];
        for(int i=3;i<=nums.length;i++){
                dp[i]=Math.max(dp[i-1],dp[i-2]+nums[i-1]);
        }
        return dp[nums.length];
    }

    /**
     * 206. 反转链表
     * @param head
     * @return
     * 参考：https://leetcode-cn.com/problems/reverse-linked-list/
     * 题解思路：
     * 1. 栈
     * 2. 头插法
     * 3. 递归
     */
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null)return head;
        ListNode node=reverseList(head.next);
        head.next.next=head;
        head.next=null;
        return node;
    }

    /**
     * 207. 课程表
     * @return
     * 参考：https://leetcode-cn.com/problems/course-schedule/
     * 题解思路：拓扑排序
     * 1. dfs
     * 2. bfs
     *
     *
     */
    List<List<Integer>> edges;// 连接表
    int[] indigo;// 节点入度
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 建立连接表图
        edges = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            edges.add(new ArrayList<>());
        }
        indigo = new int[numCourses];
        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
            ++indigo[info[0]];//每增加边，对应节点的入度加一
        }
        // 初始化队列，BFS的基本数据结构
        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            if(indigo[i]==0){//首先将入度为0的节点加入队列
                queue.offer(i);
            }
        }
        int visited = 0;
        while(!queue.isEmpty()){
            ++visited;
            int u = queue.poll();
            for (int v: edges.get(u)) {
                --indigo[v];
                if (indigo[v] == 0) {
                    queue.offer(v);
                }
            }
        }
        return visited==numCourses;
    }

    /**
     * 前缀树
     * 字典树
     */
    static class Trie {
        private final Trie[] children;//子节点，由于子节点的数量确定，直接使用数组进行存储
        private boolean isEnd;//结尾标志

        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }

        /**
         * 字典树插入
         * @param word 待插入单词
         */
        public void insert(String word) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new Trie();
                }
                node = node.children[index];
            }
            node.isEnd = true;
        }

        public boolean search(String word) {
            Trie node = searchPrefix(word);
            return node != null && node.isEnd;
        }

        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }

        private Trie searchPrefix(String prefix) {
            Trie node = this;
            for (int i = 0; i < prefix.length(); i++) {
                char ch = prefix.charAt(i);
                int index = ch - 'a';
                if (node.children[index] == null) {
                    return null;
                }
                node = node.children[index];
            }
            return node;
        }
    }

    /**
     * 226. 翻转二叉树
     * @param root 树根
     * @return
     * 参考：https://leetcode-cn.com/problems/invert-binary-tree/
     * 题解思路：
     * 1. 递归交换
     */
    public TreeNode invertTree(TreeNode root) {
        if(root==null|| root.left==null&&root.right==null) {
            return  root;
        }
        else{
            TreeNode right=invertTree(root.left);
            root.left= invertTree(root.right);
            root.right=right;
        }
        return root;//第二种情况也不需要判断
    }
    /**
     * 448. 找到所有数组中消失的数字
     * @return
     * 参考：https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array/
     * 题解思路：
     * 1. 哈希 统计每个桶的元素的数量，显然
     * 2. 排序 排序之后，将相邻元素之间的元素直接添加到结果链表中
     *
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res=new ArrayList<>();
        Arrays.sort(nums);
        for(int i=1;i<=nums.length;i++){
            if(i!=nums[i-1]){
                res.add(i);
            }
        }
        return res;

    }

    /**
     * 234. 回文链表
     * @param head
     * @return
     * 参考：https://leetcode-cn.com/problems/palindrome-linked-list/
     * 题解思路：
     * 1. 栈
     * 2. 反转链表
     * 3. 使用快慢指针找到中间节点，然后将其中一部分链表翻转，再判断。
     */
    public boolean isPalindrome(ListNode head) {
        ListNode middle=middleNode(head);
        ListNode reverseList = reverseList(middle);
        while(head!=middle){
            if(head.val==reverseList.val){
                head=head.next;
                reverseList=reverseList.next;
            }else return false;
        }
        return true;
    }
    // 对于奇数（1除外）个节点的链表，slow指向的是正中间的节点，显然后半段比前半段多一个节点。
    private ListNode middleNode(ListNode head){
        ListNode fast=head.next;
        ListNode slow=head;
        while(fast!=null){
            fast=fast.next;
            slow=slow.next;
            if(fast!=null){
                fast=fast.next;
            }
        }
        return slow;
    }

    /**
     * 236. 二叉树的最近公共祖先
     * 参考：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
     * 题解思路：
     * 1. 从根开始查找，如果两个节点都在根的同一棵子树下，更改树根节点，否则直接返回根。但是这种思路显然耗时
     * 2. 记录父亲节点
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null||root==p||root==q)return root;
        boolean rightP = search(root.right, p);
        boolean rightQ=search(root.right,q);
        boolean leftP = search(root.left,p);
        boolean leftQ= search(root.left,q);
        if(rightP&&leftQ||leftP&&rightQ)return root;
        else if(leftP&&leftQ){
            return lowestCommonAncestor(root.left,p,q);
        }else return lowestCommonAncestor(root.right,p,q);
    }
    private boolean search(TreeNode root,TreeNode p){
        if(root==null||p==null)return false;
        if(root==p)return true;
        else return search(root.left,p)||search(root.right,p);
    }

    /**
     * 通过parent来记录每个节点的父亲节点，然后从访问所有的p的祖先记录在数组中，
     * 然后再访问q的祖先，看是否再已经访问的数组中。
     */
    Map<Integer, TreeNode> parent = new HashMap<>();
    Set<Integer> visited = new HashSet<>();
    public void dfs_low(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            dfs_low(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            dfs_low(root.right);
        }
    }
    public TreeNode lowestCommonAncestor_father(TreeNode root, TreeNode p, TreeNode q) {
        dfs_low(root);
        while (p != null) {
            visited.add(p.val);
            p = parent.get(p.val);
        }
        while (q != null) {
            if (visited.contains(q.val)) {
                return q;
            }
            q = parent.get(q.val);
        }
        return null;
    }

    /**
     * 238. 除自身以外数组的乘积
     * 参考：https://leetcode-cn.com/problems/product-of-array-except-self/
     * 题解思路：
     * 1. 左一遍，又一遍
     */
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] answer = new int[length];

        // answer[i] 表示索引 i 左侧所有元素的乘积
        // 因为索引为 '0' 的元素左侧没有元素， 所以 answer[0] = 1
        answer[0] = 1;
        for (int i = 1; i < length; i++) {
            answer[i] = nums[i - 1] * answer[i - 1];
        }

        // R 为右侧所有元素的乘积
        // 刚开始右边没有元素，所以 R = 1
        int R = 1;
        for (int i = length - 1; i >= 0; i--) {
            // 对于索引 i，左边的乘积为 answer[i]，右边的乘积为 R
            answer[i] = answer[i] * R;
            // R 需要包含右边所有的乘积，所以计算下一个结果时需要将当前值乘到 R 上
            R *= nums[i];
        }
        return answer;
    }

    /**
     * 279. 完全平方数
     * 参考：https://leetcode-cn.com/problems/perfect-squares/
     * 题解思路：
     * 1. 动态规划
     *      f(i)= min(f(i-j*j.....)(j*j<i)+1
     */
    public int numSquares(int n) {
        int[] f = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minn = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                minn = Math.min(minn, f[i - j * j]);
            }
            f[i] = minn + 1;
        }
        return f[n];
    }

    /**
     * 283. 移动零
     * 参考：https://leetcode-cn.com/problems/move-zeroes/
     * 题解思路：
     * 1. 双指针
     */
    public void moveZeroes(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        while (right < n) {
            if (nums[right] != 0) {
                if(left==right)continue;
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }

    /**
     * 287. 寻找重复数
     * 参考：https://leetcode-cn.com/problems/find-the-duplicate-number/
     * 题解思路：
     * 1. 数学，高斯求和,当然这种方法只适合求解只有一个重复数，并且(1-n)的所有数都有。
     * 2. 哈希
     * 3. 排序
     */
    public int findDuplicate(int[] nums) {
        int except=nums.length*(nums.length+1)/2;
        int facts=0;
        for (int num : nums) {
            facts += num;
        }
        return nums.length-(except-facts);
    }
    public int findDuplicate_slow_fast(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    /**
     * 300. 最长递增子序列
     * 参考：https://leetcode-cn.com/problems/longest-increasing-subsequence/
     * 题解思路：
     * 1. 动态规划
     */
    public int lengthOfLIS(int[] nums) {
        int[] dp=new int[nums.length];
        if(nums.length==0)return 0;
        dp[0]=1;
        int maxSum=1;
        for(int i=0;i<nums.length;i++){
            dp[i]=1;
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
            maxSum=Math.max(maxSum,dp[i]);
        }
        return maxSum;
    }

    /**
     * 309. 最佳买卖股票时机含冷冻期
     * 参考：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
     * 题解思路：
     * 1. 动态规划，由于存在冷冻期，有点打家劫舍的感觉
     */
    public int maxProfit_rob(int[] prices) {
        if(prices.length==0)return 0;
        int tmp=prices[0];
        prices[0]=0;
        for(int i=1;i<prices.length;i++){
            int current=prices[i];
            prices[i]=prices[i]-tmp;
            tmp=current;
        }
        return rob(prices);
    }

    /**
     * 394. 字符串解码
     * 参考：https://leetcode-cn.com/problems/decode-string/
     * 题解思路：
     * 1. 栈
     */
    public String decodeString(String s) {
        Deque<Character> digits=new LinkedList<>();
        Deque<Character> letters=new LinkedList<>();
        int i=0;
        while(i<s.length()){
            if(Character.isDigit(s.charAt(i))){
                digits.push(s.charAt(i));
            }
            else{
                if(s.charAt(i)==']'){
                    int times=digits.pop()-'0';
                    StringBuilder stringBuilder=new StringBuilder();
                    while(!letters.isEmpty()&&letters.peek()!='['){
                        stringBuilder.append(letters.pop());
                    }
                    stringBuilder.reverse();
                    letters.pop();
                    String str=stringBuilder.toString();
                    for(int j=1;j<times;j++){
                        stringBuilder.append(str);
                    }
                    int k=0;
                    while(k<stringBuilder.length()){
                        letters.push(stringBuilder.charAt(k));
                    }
                }
            }
            i++;
        }
        StringBuilder res=new StringBuilder();
        while(!letters.isEmpty()){
            res.append(letters.pop());
        }
        return res.reverse().toString();
    }

    /**
     * 560.和为K的子数组
     * 参考：https://leetcode-cn.com/problems/subarray-sum-equals-k/
     * 题解思路:
     * 1. 前缀和+哈希表优化
     */
    public int subarraySum(int[] nums, int k) {
        int count = 0, pre = 0;
        HashMap < Integer, Integer > mp = new HashMap < > ();
        mp.put(0, 1);
        for (int num : nums) {
            pre += num;
            if (mp.containsKey(pre - k)) {
                count += mp.get(pre - k);
            }
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }
        return count;
    }

    /**
     * 437. 路径总和 III
     * 参考：https://leetcode-cn.com/problems/path-sum-iii/
     * 题解思路：
     * 1. 前缀和+dfs
     */
    public int pathSum(TreeNode root, int sum) {
        // key是前缀和, value是大小为key的前缀和出现的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        // 前缀和为0的一条路径
        prefixSumCount.put(0, 1);
        // 前缀和的递归回溯思路
        return recursionPathSum(root, prefixSumCount, sum, 0);
    }

    /**
     * 前缀和的递归回溯思路
     * 从当前节点反推到根节点(反推比较好理解，正向其实也只有一条)，有且仅有一条路径，因为这是一棵树
     * 如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
     * 所以前缀和对于当前路径来说是唯一的，当前记录的前缀和，在回溯结束，回到本层时去除，保证其不影响其他分支的结果
     * @param node 树节点
     * @param prefixSumCount 前缀和Map
     * @param target 目标值
     * @param currSum 当前路径和
     * @return 满足题意的解
     */
    private int recursionPathSum(TreeNode node, Map<Integer, Integer> prefixSumCount, int target, int currSum) {
        // 1.递归终止条件
        if (node == null) {
            return 0;
        }
        // 2.本层要做的事情
        int res = 0;
        // 当前路径上的和
        currSum += node.val;

        //---核心代码
        // 看看root到当前节点这条路上是否存在节点前缀和加target为currSum的路径
        // 当前节点->root节点反推，有且仅有一条路径，如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
        // currSum-target相当于找路径的起点，起点的sum+target=currSum，当前点到起点的距离就是target
        res += prefixSumCount.getOrDefault(currSum - target, 0);
        // 更新路径上当前节点前缀和的个数
        prefixSumCount.put(currSum, prefixSumCount.getOrDefault(currSum, 0) + 1);
        //---核心代码

        // 3.进入下一层
        res += recursionPathSum(node.left, prefixSumCount, target, currSum);
        res += recursionPathSum(node.right, prefixSumCount, target, currSum);

        // 4.回到本层，恢复状态，去除当前节点的前缀和数量
        prefixSumCount.put(currSum, prefixSumCount.get(currSum) - 1);
        return res;
    }
    /**
     * 461. 汉明距离
     * @param x x
     * @param y y
     * @return
     * 参考：https://leetcode-cn.com/problems/hamming-distance/
     * 题解思路：
     * 1. bit相异的计算方法最好不过：亦或。计算x^y的bit位为1的个数即可。
     */
    public int hammingDistance(int x, int y) {
        int s = x ^ y, ret = 0;
        while (s != 0) {
            ret += s & 1;
            s >>= 1;
        }
        return ret;
    }
    /**
     * 543. 二叉树的直径
     * @param root
     * @return
     * 参考：https://leetcode-cn.com/problems/diameter-of-binary-tree/
     * 题解思路：
     * 1. 计算每个节点作为根节点左右子树的深度之和的最大值便是二叉树的直径
     *    具体的做法：
     *             维护一个全局变量，记录当前遍历的节点中的直径的最大值。
     *             而且这个全局变量应该是在计算节点左右子树深度的时候维护的。
     */
    public int diameterOfBinaryTree(TreeNode root) {
        helper_diameterOfBinaryTree(root);
        return ans;
    }
    private int ans=0;
    public int helper_diameterOfBinaryTree(TreeNode root){
        if(root==null)return 0;
        int l=helper_diameterOfBinaryTree(root.left);
        int r=helper_diameterOfBinaryTree(root.right);
        ans=Math.max(ans,l+r);
        return Math.max(l,r)+1;
    }

    /**
     * 494. 目标和
     * 参考：https://leetcode-cn.com/problems/target-sum/
     * 题解思路：
     * 1.
     */
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int diff = sum - target;
        if (diff < 0 || diff % 2 != 0) {
            return 0;
        }
        int neg = diff / 2;
        int[] dp = new int[neg + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int j = neg; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        return dp[neg];
    }

    /**
     * 合并二叉树，其思想与合并排序链表一样。
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode merged = new TreeNode(t1.val + t2.val);
        merged.left = mergeTrees(t1.left, t2.left);
        merged.right = mergeTrees(t1.right, t2.right);
        return merged;
    }

    /**
     *
     * @param s1
     * @param s2
     * @return
     */
    public int minimumDeleteSum(String s1, String s2) {
        int m=s1.length();
        int n=s2.length();
        int mSum=0;
        int nSum=0;
        for(int i=0;i<m;i++){
            mSum+=s1.charAt(i);
        }
        for(int i=0;i<n;i++){
            nSum+=s2.charAt(i);
        }
        if(m==0)return nSum;
        if(n==0)return mSum;
        int[][] dp = new int[m+1][n+1];
        for(int i=1;i<=m;i++){
            char c1=s1.charAt(i-1);
            for(int j=1;j<=n;j++){
                char c2=s2.charAt(j-1);
                if(c1==c2){
                    dp[i][j]=dp[i-1][j-1]+c1;
                }else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return nSum+mSum-2*dp[m][n];
    }
    public static void main(String[] args) {
        //TreeNode root=new TreeNode(6,new TreeNode(3,new TreeNode(1),new TreeNode(2)),new TreeNode(9,new TreeNode(7),new TreeNode(8)));
        final Solution solution = new Solution();
        System.out.println(solution.minimumDeleteSum("sea", "eat"));


    }
}
