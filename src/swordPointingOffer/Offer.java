package swordPointingOffer;

import list.ListNode;

import tree.TreeNode;

import java.util.*;

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

    /**
     * 题目：调整数组顺序使奇数位于偶数前面
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/diao-zheng-shu-zu-shun-xu-shi-qi-shu-wei-yu-ou-shu-qian-mian-lcof/
     * 题解思路：
     * 双指针。
     * 左右指针进行检索；
     * 左指针负责检索未交换的偶数，
     * 右指针负责检索未交换的奇数。
     */
    public int[] exchange(int[] nums) {
        int l=0;
        int r=nums.length-1;
        while(l<r){
            //左指针检索偶数
            if(nums[l]%2==1){
                l++;
                continue;
            }
            //右指针检索奇数
            if(nums[r]%2==0){
                r--;
                continue;
            }
            //交换过程
            if(nums[l]%2==0&&nums[r]%2==1){
                int temp=nums[l];
                nums[l]=nums[r];
                nums[r]=temp;
            }
        }
        return nums;
    }
    /**
     * 题目：链表的倒数第k个节点
     * @see https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/
     * @param head 头节点
     * @param k 参数k
     * @return node
     * 题解思路：
     * 1. 滑动窗口
     * 2. 栈（入栈出栈即可）
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode p=head;
        for(int i=1;i<k;i++){
            if(p==null)return null;
            else p=p.next;
        }
        ListNode node=head;
        while(p.next!=null){
            node=node.next;
            p=p.next;
        }
        return node;
    }

    /**
     * 题目：反转链表
     * @param head
     * @return
     * @see https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/
     * 题解思路：
     * 1. 递归法
     * 2. 头插法
     * 3. 栈模拟
     * 这里提供两种方式：
     * 1. 递归法
     * 2. 头插法
     */
    public ListNode reverseListRecursion(ListNode head) {//递归
        if(head==null||head.next==null)return head;
        ListNode list=reverseListRecursion(head.next);
        head.next.next=head;
        head.next=null;
        return list;
    }
    public ListNode reverseListHeadInsert(ListNode head) {//头插
        if(head==null||head.next==null)return head;
        ListNode p=head;
        ListNode q=null;
        while(p!=null){
            ListNode node=p.next;
            p.next=q;
            q=p;
            p=node;
        }
        return q;
    }

    /**
     * 题目：合并两个排序链表
     * @param l1
     * @param l2
     * @return
     * @see https://leetcode-cn.com/problems/he-bing-liang-ge-pai-xu-de-lian-biao-lcof/
     * 题解思路：
     * 1. 递归合并，代码优雅
     * 2. 模拟合并过程，思路清晰直观
     * 此处提供优雅的代码：递归
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null)return l2;
        if(l2==null)return l1;
        else if(l1.val<l2.val){
            l1.next=mergeTwoLists(l1.next,l2);
            return l1;
        }
        else{
            l2.next=mergeTwoLists(l1,l2.next);
            return l2;
        }
    }
    /**
     * 题目：二叉树的镜像
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/er-cha-shu-de-jing-xiang-lcof/
     * 题解思路：DFS
     */
    public TreeNode mirrorTree(TreeNode root) {
        if(root==null||(root.left==null&&root.right==null))return root;
        else {
            TreeNode temp=root.left;
            root.left=root.right;
            root.right=temp;
            mirrorTree(root.left);
            mirrorTree(root.right);
            return root;
        }
    }
    /**
     * 题目：判断是否为对称二叉树
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/dui-cheng-de-er-cha-shu-lcof/
     * 题解思路：
     * 1. 递归
     * 借助帮助函数，从根节点对照左右孩子的对称性
     */
    public boolean isSymmetric(TreeNode root) {
        return root == null || recur(root.left, root.right);
    }
    boolean recur(TreeNode L, TreeNode R) {
        if(L == null && R == null) return true;
        if(L == null || R == null || L.val != R.val) return false;
        //对称要求
        //左孩子的左孩子与右孩子的右孩子
        //左孩子的右孩子与右孩子的左孩子
        return recur(L.left, R.right) && recur(L.right, R.left);
    }

    /**
     * 题目：顺时针打印矩阵
     * @param matrix
     * @return
     * @see https://leetcode-cn.com/problems/shun-shi-zhen-da-yin-ju-zhen-lcof/
     * 题解思路：
     *  [1,2,3]
     *  [4,5,6]
     *  [7,8,9]
     *  模拟：控制边界值进行循环
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) {
            return new int[0];
        }
        //返回数组内存申请
        int[] res = new int[matrix.length * matrix[0].length];

        int u = 0, d = matrix.length - 1, l = 0, r = matrix[0].length - 1;
        int idx = 0;//结果数组的索引
        while (true) {
            for (int i = l; i <= r; i++) {
                res[idx++] = matrix[u][i];
            }
            if (++u > d) {
                break;
            }
            for (int i = u; i <= d; i++) {
                res[idx++] = matrix[i][r];
            }
            if (--r < l) {
                break;
            }
            for (int i = r; i >= l; i--) {
                res[idx++] = matrix[d][i];
            }
            if (--d < u) {
                break;
            }
            for (int i = d; i >= u; i--) {
                res[idx++] = matrix[i][l];
            }
            if (++l > r) {
                break;
            }
        }
        return res;
    }

    /**
     * 题目：从上到下打印二叉树 II
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-ii-lcof/
     * 题解思路：
     * 层遍历
     * 关键数据结构：队列
     * 
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res=new ArrayList<>();
        if(root==null)return res;
        Queue<TreeNode> queue=new LinkedList<>();
        int arrayCount=1;
        queue.add(root);
        while(!queue.isEmpty()){
            List<Integer> arrayList=new ArrayList<>();
            int count=0;
            while(!queue.isEmpty()&&arrayCount>0){
                TreeNode node=queue.poll();
                arrayList.add(node.val);
                if(node.left!=null)
                {
                    queue.add(node.left);
                    count++;
                }
                if(node.right!=null)
                {
                    queue.add(node.right);
                    count++;
                }
                arrayCount--;
            }
            res.add(arrayList);
            arrayCount=count;
        }
        return res;
    }

    /**
     * 题目：数组中出现次数超过一半的数字
     * @param nums
     * @return
     * 题解思路：
     * 1. 排序中位数
     * 2. 哈希求众数
     * 3. 摩尔投票，相同+1，不同-1，占一半的数必然留到最后
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }

    /**
     * 题目：最小的k个数
     * @param arr
     * @param k
     * @return
     * @see https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/
     * 题解思路：
     * 优先队列
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        int[] vec = new int[k];
        if (k == 0) { // 排除 0 的情况
            return vec;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            public int compare(Integer num1, Integer num2) {
                return num2 - num1;
            }
        });
        for (int i = 0; i < k; ++i) {
            queue.offer(arr[i]);
        }
        for (int i = k; i < arr.length; ++i) {
            if (queue.peek() > arr[i]) {
                queue.poll();
                queue.offer(arr[i]);
            }
        }
        for (int i = 0; i < k; ++i) {
            vec[i] = queue.poll();
        }
        return vec;
    }

    /**
     * 题目：连续子数组的最大和
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/
     * 题解思路：
     * 动态规划
     */
    public int maxSubArray(int[] nums) {
        int res = nums[0];
        for(int i = 1; i < nums.length; i++) {
            nums[i] += Math.max(nums[i - 1], 0);
            res = Math.max(res, nums[i]);
        }
        return res;
    }
}
