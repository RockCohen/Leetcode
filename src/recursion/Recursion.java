package recursion;

import list.ListNode;
import tree.TreeNode;

import java.util.*;


/**
 * 递归：本质就在于调用自己来解决子问题。直到子问题无需再继续递归求解。
 * 递归的属性：
 * 1.一个简单的基本案例，无需使用递归来解决的子问题，也就是递归的出口。
 * 2.一组规则，地推关系，可将所有的问题推到基本案例。、
 *    递推关系：一个问题的结果与其子问题的结果之间的关系。
 *    基本问题： 不需要进一步的递归调用就可以直接计算答案的情况。
 *             有时，基本案例也被称为 bottom cases，因为它们往往是问题被减少到最小规模的情况，
 *             也就是如果我们认为将问题划分为子问题是一种自上而下的方式的最下层。
 *
 * 递归问题的求解过程：
 * 对于一个问题，如果存在递归解决方案，我们可以按照以下步骤来实施它。
 *
 * 举个例子，我们将问题定义为有待实现的函数 F(X)，其中 X是函数的输入，同时也定义了问题的范围。
 *
 * 然后，在函数 F(X) 中，我们将会：
 *
 * 1. 将问题逐步分解成较小的范围，例如 x0∈X, x1∈X, ..., xn∈X；
 * 2. 调用函数 F(x0), F(x1), ..., F(xn) 递归地 解决 X 的这些子问题；
 * 3. 最后，处理调用递归函数得到的结果来解决对应 X 的问题。
 *
 *
 * 递归问题的重复计算问题，使用缓存来暂存中间结果，然后让下一次递归计算直接利用缓存的结果来计算。
 * 该技术叫做记忆化。也可以直接使用参数化的形式。
 *
 */
public class Recursion {
    int sum=0;//累加树初始变量
    /**
     * 题目：符串反转
     * 题解思路：
     * 我们需要一个帮助函数来实现递归。
     * 为什么需要一个helper函数呢，因为我们需要记录当前的索引位置，需要引入参数。
     * 根据索引参数来执行当前的动作。关于参数引入的思想，再别的题目中也会经常用到。
     * @author Rock
     * @param str 待反转打印字符串。
     */
    public  void printReverse(char [] str) {
        helper(str,0);
    }
    private void helper(char[] str,int index){
        if(str==null||index>=str.length) {
            return ;
        }
        helper(str,index+1);
        System.out.println(str[index]);
    }
    public void reverseString(char[] s) {
        reverseHelper(s,0,s.length-1);
    }

    /**
     * 同样的道理，在反转字符串的时候，我们也需要直接操作当前上下界的字符。
     * 所以需要利用索引来记录当前字符。
     * @param s 待反转字符串
     * @param left 下界
     * @param right 上界
     */
    private void reverseHelper(char[] s,int left,int right){
        if(left>=right)return;
        char temp=s[right];
        s[right]=s[left];
        s[left]=temp;
        reverseHelper(s,left+1,right-1);
    }

    /**
     * 题目：链表节点两两交换
     *
     * 题解思路：
     * 我们需要处理的是head与head.next两个节点之间的连接关系。
     * @param head 表头
     * @return 交换后的表头节点
     */
    public ListNode swapPairs(ListNode head) {
        if(head==null||head.next==null)return head;//基本子问题
        ListNode third=swapPairs(head.next.next);//得到子问题的解
        ListNode second=head.next;
        head.next=third;
        second.next=head;
        return  second;
    }

    /**
     * 题目：杨辉三角创建
     * 题解思路：找到基本问题与递推关系
     *         递推关系：f(i,j)=f(i−1,j−1)+f(i−1,j)
     *         基本问题：f(i,j)=1(if j=1 or i=j)
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list=new ArrayList<>(numRows);
        for(int i=1;i<=numRows;i++){
            list.add(generateHelper(i));
        }
        return list;
    }
    private List<Integer> generateHelper(int numRows){
        List<Integer> list=new ArrayList<>(numRows);
        for(int i=1;i<=numRows;i++){
            list.add(YoungHelper(numRows,i));
        }
        return list;
    }
    private int YoungHelper(int i,int j){
        if(i==j||j==1)return 1;
        else return YoungHelper(i-1,j-1)+YoungHelper(i-1,j);
    }

    /**
     * 题目：反转链表
     * 题解思路：获取头节点，将头节点与下一个子问题的返回值之间进行顺序的反转。
     * @author Rock
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null)return head;
        ListNode second=reverseList(head.next);
        head.next.next=head;//头节点的下一个节点的下一个节点指向本身，实现依次交换。
        head.next=null;//为了放置产生循环，需要进行尾部节点的截断工作。
        return second;
    }

    /**
     * 题目：斐波拉契数列
     * 题解思路：
     *        使用缓存技术来暂存中间结果，减少递归的次数。
     *        但是如果递归路径是单一的，貌似下面的方法也不能有什么用。
     *        针对上述的问题，通常的办法是将递归操作转换为迭代操作。
     * @param map 缓存哈希表
     * @param N 斐波拉契数列大小
     * @return
     */
    public int fib(int n) {
        Map<Integer,Integer> map=new HashMap<>();
        return fibHelper(map,n);
    }
    public  int fibHelper(Map<Integer,Integer> map, int N){
        if(map.containsKey(N))return map.get(N);//缓存技术
        int res=0;
        if(N<2) res=N;
        else res=fibHelper(map,N-1)+fibHelper(map,N-2);
        map.put(N,res);
        return res;
    }

    /**
     * 题目：二叉树的最大深度
     * 题解思路：
     *        基本问题：当前根节点没有子树，返回1即可。
     *        递归关系：max(depth(left),depth(right))
     *        最后再加上根节点的高度即可。
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if(root==null)return 0;//基本问题
        int left=maxDepth(root.left);//子问题1
        int right=maxDepth(root.right);//子问题2
        return Math.max(left,right)+1;//子问题递推关系
    }

    /**
     * 题目：幂函数
     * 题解思路：使用记忆化的思路来存储结果。
     * 以下版本充分说明：java不支持尾递归，原因很简单：java都是值传递的方式进行参数的传递。
     *     public double myPow(double x, int n) {
     *         double res=1.0;
     *         myPowHelper(x,n,res);
     *         return res;
     *     }
     *     public void myPowHelper(double x,int n,Double result){
     *         if(n==0) result= 1.0;
     *         if(n==1) result=x;
     *         else myPowHelper(x,n-1,x*result);
     *     }
     *     上述程序，如果是在C++中直接采用引用的方式直接修改引用的值即可。
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
      return  myPowHelper(x,n);
    }
    public double myPowHelper(double x,int n){
        if(n==0)return 1.0;
        if(Math.abs(n)==1)return n<0?1/x:x;
        else {
            double res=myPowHelper(x,Math.abs(n/2))*myPowHelper(x,Math.abs(n-n/2));
            return n<0?1/res:res;
        }
    }

    /**
     * 题目：合并有序链表
     * 题解思路：链表的定义本身就是递归性质的。
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        else if (l2 == null) {
            return l1;
        }
        else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
        else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    /**
     * 题目：所有二叉搜索树的创建
     * 题解思路：递归
                 * 首先，让我们来数一数需要构建多少棵树。
                 * 在不同的二叉搜索树中，我们了解到，需要构建的二叉搜索树的数量实际上满足卡特兰数。
                 * 我们将会顺着上一篇文章的思路继续，不过这一次我们不需要计数，而是实际构造树。
     * 解题步骤：
                 * 从序列 1 ..n 取出数字 i 并以它作为当前树的根节点。
                 * 那么就有 i - 1 个元素可以用来构造左子树，而另外的 n - i 个元素可以用于构造右子树。最后我们将会得到 G(i - 1) 棵不同的左子树，以及 G(n - i)
                 * 棵不同的右子树，其中 G 为卡特兰数。
                 *现在，我们将会在序列 1 ... i - 1 上重复前面的步骤来构造所有的左子树，之后对序列 i + 1 ... n 也这样做以得到所有的右子树。
                 * 这么一来，我们就得到了根节点 i 和两个可能的左右子树列表。
                 * 最后一步是遍历两个列表，将左右子树和根节点链接起来。

     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generate_trees(1, n);
    }

    public LinkedList<TreeNode> generate_trees(int start, int end) {
        LinkedList<TreeNode> all_trees = new LinkedList<TreeNode>();
        if (start > end) {
            all_trees.add(null);
            return all_trees;
        }

        // pick up a root
        for (int i = start; i <= end; i++) {
            // all possible left subtrees if i is choosen to be a root
            LinkedList<TreeNode> left_trees = generate_trees(start, i - 1);

            // all possible right subtrees if i is choosen to be a root
            LinkedList<TreeNode> right_trees = generate_trees(i + 1, end);

            // connect left and right trees to the root i
            for (TreeNode l : left_trees) {
                for (TreeNode r : right_trees) {
                    TreeNode current_tree = new TreeNode(i);
                    current_tree.left = l;
                    current_tree.right = r;
                    all_trees.add(current_tree);
                }
            }
        }
        return all_trees;
    }

    /**
     * 题解思路：判断当前的根节点的左孩子与右孩子的深度之差是否满足平衡二叉树的定义，然后递归判断左子树与右子树的平衡性。
     * @author Rock
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/balanced-binary-tree/
     */
    public boolean isBalanced(TreeNode root) {
        if(root==null)return true;
        boolean flag= Math.abs(maxDepth(root.left)-maxDepth(root.right))<=1;
        return flag&&isBalanced(root.left)&&isBalanced(root.right);
    }

    /**
     * 题解思路：找最右边视角，递归
     * f(root) = root + f(right)+f(left)[f(right).size:]
     * 简单来说结果为根节点加上右子树的结果，然后加上左子树的结果去掉右子树高度那一部分的结果
     * @author Rock
     * @param root
     * @return
     * @see  https://leetcode-cn.com/problems/binary-tree-right-side-view/
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> rs = new ArrayList<>();
        if(root==null)return rs;
        rs.add(root.val);
        List<Integer> right = rightSideView(root.right);
        List<Integer> left = rightSideView(root.left);
        rs.addAll(right);
        for(int i = right.size();i<left.size();i++) rs.add(left.get(i));
        return rs;
    }

    /**
     * 题解思路：
     * 实质就是重新定义遍历顺序：右根左。
     * @auhtor Rock
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/convert-bst-to-greater-tree/
     */
    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST(root.right);
            sum += root.val;
            root.val = sum;
            convertBST(root.left);
        }
        return root;
    }

    /**
     * 题解思路：中序遍历
     * @author Rock
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/binode-lcci/
     */
    static class Solution {
        TreeNode head = new TreeNode(-1);   // 为了返回单向链表的头节点而多设的一个节点
        TreeNode perv = null;               // 指向当前节点的前一个节点
        public TreeNode convertBiNode(TreeNode root) {
            helper(root);
            return head.right;
        }
        public void helper(TreeNode root) {
            if (root == null) { return;}
            helper(root.left);
            if (perv == null) {     // 第一个节点
                perv = root;        // 记录第一个节点
                head.right = root;  // 记录它，它将作为单链表的表头
            } else {                // 第一个节点之后的节点
                perv.right = root;  // 前一个节点的右指针指向当前节点
                perv = root;        // 更新perv指向
            }
            root.left = null;       // 当前节点的左指针设为null
            helper(root.right);
        }
    }

    /**
     * @author Rock
     * @param shorter
     * @param longer
     * @param k
     * @return
     * @see https://leetcode-cn.com/problems/diving-board-lcci/
     */
    public int[] divingBoard(int shorter, int longer, int k) {
        if (k == 0) {
            return new int[0];
        }
        if (shorter == longer) {
            return new int[]{shorter * k};
        }
        Set<Integer> set=new HashSet<>();
        for(int i=0;i<=k;i++){
            set.add(i*longer+(k-i)*shorter);
        }
        int [] res=new int[set.size()];
        Iterator<Integer> iterator=set.iterator();
        int j=0;
        while(iterator.hasNext())res[j++]=iterator.next();
        return res;
    }

    /**
     * @author Rock
     * @param A
     * @param B
     * @return
     * @see https://leetcode-cn.com/problems/recursive-mulitply-lcci/
     */
    public int multiply(int A, int B) {
        int res=0;
        int i=0;
        do{
            res+=(B-(B>>1<<1))==1?A<<i:0;
            i++;
            B=B>>1;
        } while(B!=0);
        return res;
    }

    /**
     * 测试主函数
     * @param args
     */
    public static void main(String[] args) {
       int res= new Recursion().multiply(4,5);
       System.out.println(res);
    }


}

