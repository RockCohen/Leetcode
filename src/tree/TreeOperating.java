package tree;

import tree.TreeNode;

import java.lang.reflect.Array;
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
    public static void main(String[] args){
        TreeNode root=new TreeNode(1,new TreeNode(2,new TreeNode(4),new TreeNode(5)),new TreeNode(3,new TreeNode(6),new TreeNode(7)));
        List<Integer> list=new TreeOperating().stackLastOrderTraversalII(root);
        list.forEach(System.out::println);
    }

}


