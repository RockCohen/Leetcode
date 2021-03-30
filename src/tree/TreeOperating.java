package tree;
import java.util.*;


/**
 * 二叉树的操作类
 */
public class TreeOperating {
    /**
     * 递归中序遍历二叉树，分别调整res.add()的位置实现先根遍历，后根遍历，中序遍历。
     * @param root 树根
     * @param res 显然需要通过参数化去实现结果的输出，无法在方法中声明变量来传递。
     */
    public void recursionMiddleOrderTraversal(TreeNode root, List<Integer> res){
        if(root!=null){
            recursionMiddleOrderTraversal(root.left,res);
            res.add(root.val);
            recursionMiddleOrderTraversal(root.right,res);
        }
    }

    /**
     * 中序的遍历之堆栈实现。
     * @param root 树根
     * @return 返回中序遍历列表。
     *
     * 算法细节：
     * 中序遍历的基本步骤就是：左子树--->根--->右子树。
     * 概念前提：由于树的递归定义本质，所以每一步其实需要严格执行上述定义的访问顺序，每一个当前的节点都是其子树的根节点。（明白这一点非常重要）。
     * 基本步骤：为了先遍历到根的左子树，通过数据结构栈来存储当前的根节点，然后访问其左子树。
     * 1. 将根节点存储栈中
     * 2. 如果栈顶的节点存在左子树左子树根节点入栈，直到左子树为空。
     * 3. 当前的左子树为空，遍历（这里的遍历泛指，可以对节点做其他的操作）当前节点。
     * 4. 栈顶节点退栈，将其右子树的节点入栈。（如果右子树为空，并且栈空，结束）
     * 5. 重复2-4的过程。
     *
     */
    public List<Integer> stackMiddleOrderTraversal(TreeNode root) {//中序遍历
        Stack<TreeNode> treeNodeStack = new Stack<>();//存储节点的栈
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
            if (!treeNodeStack.isEmpty()) {
                node = treeNodeStack.pop();
                //遍历节点
                res.add(node.val);
                node = node.right;
            }
        }
        return res;
    }

    /**
     * 先序遍历
     * @param root 树根
     * @return 先序遍历列表
     *
     *
     * 算法细节：
     * 概念前提：先序遍历的顺序：根--->左子树--->右子树。
     * 基本步骤：为了寻求到根所在的右子树，我们需要将根节点借助数据结构栈来存储。并且先遍历栈顶元素，然后再考虑其左子树与右子树。
     * 1. 根节点入栈，并且访问之。
     * 2. 当前栈顶节点是否存在左子树？存在入栈，转1，否则转3
     * 3. 当前节点的右子树是否存在？存在，退出当前栈顶元素，右子树根节点入栈，转1.
     */
    public List<Integer> stackFirstOrderTraversal(TreeNode root){//先序遍历
        Stack<TreeNode> treeNodeStack =new Stack<>();
        TreeNode node=root;
        List<Integer> res=new ArrayList<>();
        while(node!=null||!treeNodeStack.isEmpty()){
            //根节点入栈，并且访问之
            if(node!=null){
                res.add(node.val);//访问的具体步骤
                treeNodeStack.push(node);
                node=node.left;//左子树入栈
            }
            //否则右子树入栈，入栈前，需要将栈顶节点退出。
            else{
                node=treeNodeStack.pop();
                node=node.right;
            }
        }
        return res;
    }

    /**
     * 后序遍历
     * @param root 树根
     * @return 后序遍历列表
     *
     *
     *
     * 算法思想：
     * 概念前提：访问顺序：左子树--->右子树--->根
     * 基本步骤：根节点入栈，左子树入栈访问之，左子树退栈，右子树入栈，访问之，最后访问根节点。
     * 1. 根节点入栈
     * 2. 左子树为空？为空，转3，否则转1.
     * 3. 右子树为空？为空，访问栈顶节点退栈，否则转1.
     * 4. 栈为空退出。
     */
    public List<Integer> stackLastOrderTraversal(TreeNode root){//后序遍历
        List<Integer> result=new ArrayList<>();
        if(root == null) return result;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);   //首先将根节点压栈
        while(!stack.isEmpty()) {
            TreeNode ele = stack.pop(); //首先出栈的为根节点，其后先出右子节点，后出左子节点
            if(ele.left != null)
                stack.push(ele.left);  //将左子节点压栈
            if(ele.right != null) {
                stack.push(ele.right); //将右子节点压栈
            }
            result.add(0, ele.val); //因为出栈顺序为“根右左”，所以需要每次将元素插入list开头
        }
        return result;
    }
    //该种方法，还需要设计一个map来记录访问记录。
    public List<Integer> stackLastOrderTraversalII(TreeNode root){
        List<Integer> result=new ArrayList<>();
        if(root == null) return result;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);   //首先将根节点压栈
        TreeNode node=root.left;
        Map<TreeNode,Boolean> map=new HashMap<>();
        map.put(node,false);
        while(!stack.isEmpty()){
            TreeNode p=null;
            while(node!=null&&!map.get(node)) {
                stack.push(node);
                p=node;
                node=node.left;
            }
            //这里的逻辑有待重新审视。
            if(p.right==null){
                result.add(stack.pop().val);
                map.put(p,true);
            }
            node=p.right;
            map.put(node,false);
        }
        return result;
    }


    /**
     * 层遍历：
     * @param root 树根
     * @return 层遍历列表
     */
    public List<Integer> layerOrderTraversal(TreeNode root){
        List<Integer> result=new ArrayList<>();
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode node=queue.poll();
            result.add(node.val);
            if(node.left!=null)queue.add(node.left);
            if(node.right!=null)queue.add(node.right);
        }
        return result;
    }
    /**
     * 二叉树的两数之和
     * 给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
     * 案例 1:
     *
     * 输入:
     *     5
     *    / \
     *   3   6
     *  / \   \
     * 2   4   7
     *
     * Target = 9
     *
     * 输出: True
     * 案例 2:
     *
     * 输入:
     *     5
     *    / \
     *   3   6
     *  / \   \
     * 2   4   7
     *
     * Target = 28
     *
     * 输出: False
     * 首先想到的解题思路便是：借助增序数组来解决这个问题。
     * 根据BST的元素的特征，通过中序遍历将元素放入一个有序数组中，然后再利用双指针的方式对增序数组进行检索。这样一来，主要的问题便是引入了O(n)的空间复杂度。
     * 有没有原位操作的算法呢？目前来看，原为操作的成本显然很大。
     */
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> treeArray=new ArrayList<>();
        (new TreeOperating()).recursionMiddleOrderTraversal(root,treeArray);
        int i=0;
        int j=treeArray.size()-1;
        while(i<j){
            if(treeArray.get(i)+treeArray.get(j)==k)return true;
            else if(treeArray.get(i)+treeArray.get(j)>k)j--;
            else i++;
        }
        return false;
    }

    /**
     * 103. 二叉树的锯齿形层序遍历
     * 给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     *
     * 例如：
     * 给定二叉树 [3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回锯齿形层序遍历如下：
     *
     * [
     *   [3],
     *   [20,9],
     *   [15,7]
     * ]
     * 题解思路：使用队列来记录子节点的访问顺序。并且设置计数器来决定每层子节点的个数。
     * 算法步骤：
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res=new ArrayList<>();
        if(root==null)return res;
        Queue<TreeNode> queue=new LinkedList<>();
        boolean isPreface=true;
        int arrayCount=1;
        queue.add(root);
        while(!queue.isEmpty()){
            List<Integer> list=new ArrayList<>();
            int count=0;
            while(!queue.isEmpty()&&arrayCount>0){
                TreeNode node=queue.poll();
                list.add(node.val);
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
            if(!isPreface)Collections.reverse(list);
            res.add(list);
            arrayCount=count;
            isPreface=!isPreface;
            }
        return res;
    }

    /**
     * 331. 验证二叉树的前序序列化
     * 序列化二叉树的一种方法是使用前序遍历。当我们遇到一个非空节点时，我们可以记录下这个节点的值。如果它是一个空节点，我们可以使用一个标记值记录，例如 #。
     *
     *      _9_
     *     /   \
     *    3     2
     *   / \   / \
     *  4   1  #  6
     * / \ / \   / \
     * # # # #   # #
     * 例如，上面的二叉树可以被序列化为字符串 "9,3,4,#,#,1,#,#,2,#,6,#,#"，其中 # 代表一个空节点。
     *
     * 给定一串以逗号分隔的序列，验证它是否是正确的二叉树的前序序列化。编写一个在不重构树的条件下的可行算法。
     *
     * 每个以逗号分隔的字符或为一个整数或为一个表示 null 指针的 '#' 。
     *
     * 你可以认为输入格式总是有效的，例如它永远不会包含两个连续的逗号，比如 "1,,3" 。
     *
     * 示例 1:
     *
     * 输入: "9,3,4,#,#,1,#,#,2,#,6,#,#"
     * 输出: true
     * 示例 2:
     *
     * 输入: "1,#"
     * 输出: false
     * 示例 3:
     *
     * 输入: "9,#,#,1"
     * 输出: false
     * 题解思路就是：一个数字跟随两个#抵消之后形成一个#。然后将其入栈，最后判断栈是否为#。
     * 显然这个题充分体现了二叉树的递归性质。这也是为什么与栈有关系的原因。这个题值得深刻的思考。
     * @param preorder
     * @return
     */
    //9,3,4,#,#,1,#,#,2,#,6,#,#
    //9,3,#,1,#,#,2,#,6,#,#
    //9,3,#,1,#,#,2,#,6,#,#
    //9,3,#,#,2,#,6,#,#
    //9,#,2,#,6,#,#
    // 9,#,2,#,#
    //9,#,#
    //#
    public boolean isValidSerialization(String preorder) {
       List<String> pre=preProcess(preorder);
       int i=0;
       while(pre.size()>2&&i<pre.size()-2){
           if(!pre.get(i).equals("#")&&pre.get(i+1).equals("#")&&pre.get(i+2).equals("#")){
               pre.remove(i);
               pre.remove(i);
               i--;
               if(i<0)return false;
           }
           else i++;
           if(i==pre.size()-2)i=0;
       }
       if(pre.size()==1&&pre.get(0).equals("#")) return true;
       else return false;
    }
    //9,3,4,#,#,1,#,#,2,#,6,#,#
    public List<String> preProcess(String preorder){
        List<String> strings=new ArrayList<>();
        int i=0;
        int j=0;
        while(i<preorder.length()){
            if(String.valueOf(preorder.charAt(i)).equals(",")){
                i++;
            }
            else if(String.valueOf(preorder.charAt(i)).equals("#")){
                strings.add("#");
                i++;

            }
            else {
                j = i;
                while (j < preorder.length() && Character.isDigit(preorder.charAt(j))) j++;
                strings.add(preorder.substring(i, j));
                i = j;
            }
        }
        return strings;
    }
    /**
     * 题解思路：层遍历，记录最右边的元素添加到结果数组即可。
     * @author Rock                           
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/binary-tree-right-side-view/
     */
    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> queue=new LinkedList<>();
        List<Integer> res=new ArrayList<>();
        if(root==null)return res;
        queue.add(root);
        int count=1;
        while(!queue.isEmpty()){
           int counter=count;
           count=0;
            int[] mid=new int[1];
            while(counter>0){
                TreeNode node=queue.poll();
                mid[0]=node.val;
                if (node.left != null){
                    queue.add(node.left);
                    count++;
                }
                if(node.right!=null){
                    queue.add(node.right);
                    count++;
                }
                counter--;
            }
            res.add(mid[0]);
        }
        return res;
    }

    /**
     * 题解思路：
     * 1. 通过层遍历的方式判断每一个节点是否满足二叉搜索树的定义。
     * 2. 直接递归实现。
     * 3. 中序遍历。
     * @author Rock
     * @param root 树根
     * @return
     * @see https://leetcode-cn.com/problems/validate-binary-search-tree/
     */
    public boolean isValidBST(TreeNode root) {
        List<Integer> list=stackMiddleOrderTraversal(root);
        for(int i=0;i<list.size()-1;i++){
            if(list.get(i)>=list.get(i+1))return false;
        }
        return true;
    }

public static void main(String[] args){
    TreeNode root=new TreeNode(4,new TreeNode(2,null,new TreeNode(3)),new TreeNode(5,null,new TreeNode(6)));
    boolean res=new TreeOperating().isValidBST(root);
    System.out.println(res);
}
}


