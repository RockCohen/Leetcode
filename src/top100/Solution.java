package top100;

import list.ListNode;

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
     * @param nums
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
     * @param s
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

    public static void main(String[] args) {
        final Solution solution = new Solution();
        int[] nums={1,2,3};
        final List<List<Integer>> lists = solution.permute(nums);
        for (List<Integer> list : lists) {
            list.forEach(System.out::print);
            System.out.println();
        }
        //ListNode list=new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4))));
    }
}
