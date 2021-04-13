package tree;
import javax.xml.stream.events.StartDocument;
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
     * 算法细节：
     * 概念前提：先序遍历的顺序：根--->左子树--->右子树。
     * 基本步骤：为了寻求到根所在的右子树，我们需要将根节点借助数据结构栈来存储。并且先遍历栈顶元素，然后再考虑其左子树与右子树。
     * 1. 根节点入栈，并且访问之。
     * 2. 当前栈顶节点是否存在左子树？存在入栈，转1，否则转3
     * 3. 当前节点的右子树是否存在？存在，退出当前栈顶元素，右子树根节点入栈，转1.
     */
    public List<Integer> stackFirstOrderTraversal(TreeNode root){//先序遍历
        Stack<TreeNode> treeNodeStack =new Stack<>();
        List<Integer> res=new ArrayList<>();
        TreeNode node=root;
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
     * 题目：二叉树展开为链表
     * @param root
     * @see https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/
     * 题解思路：
     * 1. 先序遍历：使用列表存储先序遍历结果。（递归方式与迭代方式）
     * 2. 寻找前驱节点：https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/solution/er-cha-shu-zhan-kai-wei-lian-biao-by-leetcode-solu/
     *    关于寻找二叉树的前驱节点类似于建立线索二叉树。所以直接使用线索二叉树的思想来解决此问题。
     */
    public void flatten(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                TreeNode next = curr.left;
                TreeNode predecessor = next;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                predecessor.right = curr.right;
                curr.left = null;
                curr.right = next;
            }
            curr = curr.right;
        }
    }

    /**
     * 后序遍历
     * @param root 树根
     * @return 后序遍历列表
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
     * 题目：二叉树的两数之和
     * 参考：
     * 首先想到的解题思路便是：借助增序数组来解决这个问题。
     * 根据BST的元素的特征，通过中序遍历将元素放入一个有序数组中，然后再利用双指针的方式对增序数组进行检索。
     * 这样一来，主要的问题便是引入了O(n)的空间复杂度。
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
     * @auhtor Rock
     * @param root 树根
     * @return 列表
     * @see  https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/
     * 题解思路：使用队列来记录子节点的访问顺序。并且设置计数器来决定每层子节点的个数。
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

    /**
     * 题目：路径总和
     * @author Rock
     * @param root
     * @param targetSum
     * @return
     * @see https://leetcode-cn.com/problems/path-sum/
     * 题解思路：
     * 1. 广度优先搜索
     * 2. 深度优先搜索
     * 3. 递归（子问题的组合是关键）
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) {
            return false;
        }
        if(root.left==null&&root.right==null){
            return targetSum==root.val;
        }
        else {
           return  hasPathSum(root.left,targetSum-root.val)||hasPathSum(root.right,targetSum-root.val);
        }
    }
    /**
     * 题目：路径总和II
     * @author Rock
     * @param root
     * @param targetSum
     * @return
     * @see https://leetcode-cn.com/problems/path-sum-ii/
     * 题解思路：
     * 1. 深度优先搜索 :枚举每一条从根节点到叶子节点的路径。
     *   当遍历到叶子节点，且此时路径和恰为目标和时，就找到了一条满足条件的路径。
     * 2. 广度优先搜索
     */
    //全局变量
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Deque<Integer> path = new LinkedList<Integer>();
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        dfs(root, sum);
        return ret;
    }
    /**
     * 这里提供了一种思路：通过定义全局变量，利用void类型的方法直接对该全局变量进行操作处理。
     * @param root
     * @param sum
     */
    public void dfs(TreeNode root, int sum) {
        if (root == null) return;
        path.offerLast(root.val);
        sum -= root.val;
        if (root.left == null && root.right == null && sum == 0) {
            ret.add(new LinkedList<Integer>(path));
        }
        dfs(root.left, sum);
        dfs(root.right, sum);
        path.pollLast();
    }

    /**
     * 题目:根节点到叶节点的总和
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/sum-root-to-leaf-numbers/
     * 题解思路：深度优先搜索 直接参考上述的模板来解决这个问题。
     */
    public int sumNumbers(TreeNode root) {
        int res=0;
        dfsHelper(root);
        for (List<Integer> integers : ret) {
            int sum = 0;
            for (Integer integer : integers) {
                sum = (sum * 10 + integer);
            }
            res += sum;
        }
        return res;
    }
    private void dfsHelper(TreeNode root){
        if(root==null)return ;
        path.offerLast(root.val);
        if(root.left==null&&root.right==null){
            ret.add(new LinkedList<Integer>(path));
        }
        dfsHelper(root.left);
        dfsHelper(root.right);
        path.pollLast();
    }

    /**
     * 题目：翻转二叉树
     * @author Rock
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/invert-binary-tree/
     * 题解思路：简单的递归可解
     */
    public TreeNode invertTree(TreeNode root) {
        if(root==null)return null;
        else{
            TreeNode left=invertTree(root.left);
            root.left= invertTree(root.right);
            root.right=left;
            return root;
        }
    }

    /**
     * 题目：二叉搜索树中第k小的节点
     * @author Rock
     * @param root
     * @param k
     * @return
     * @see https://leetcode-cn.com/problems/kth-smallest-element-in-a-bst/
     * 题解思路：该题目也是引入全局变量的方式来记录答案以及行为轨迹。
     *         与求二叉树路径总和的题目类似，该种思想值得借鉴，也值得总结。
     */
    int count=0;
    int ans=-1;
    boolean flag=false;
    public int kthSmallest(TreeNode root, int k) {
        help(root,k);
        return ans;
    }
    void help(TreeNode root,int k){
        if(root==null)
            return;
        help(root.left,k);
        // 中序遍历是有序序列
        // count 记录已遍历的长度
        // k==count 时 就是要找的值
        // 但是我们要提前返回，右边分支就不用遍历了
        // 右边不遍历，所以count值不会再变化了，所以 ans 可能会被覆盖，需要加判断，只修改一次
        count++;
        if(k==count){
            if(!flag){
                ans=root.val;
                flag=true;
            }
            return;
        }
        if(k>count)
            help(root.right,k);
    }

    /**
     * 题目：二叉搜索树中相连两点之间差值最小值。
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/minimum-distance-between-bst-nodes/
     * 题解思路：
     * 1. 深度优先搜索，显然这个题目需要借助中序遍历实现，那么最重要的思想就是利用深度优先搜索了。
     *    在遍历过程中，我们显然记录两个信息，一个是当前遍历过程中的最小值；
     *    另一个是当前遍历节点的上一个节点，做差时需要的数据。
     */
    int pre;//记录前一个节点
    int res;//记录当前的最小值
    public int minDiffInBST(TreeNode root) {
        pre=-1;
        res=Integer.MAX_VALUE;
        minDiffInBSTDFS(root);
        return res;
    }
    private void minDiffInBSTDFS(TreeNode root){
        if(root==null)return ;
        minDiffInBSTDFS(root.left);
        if (pre != -1) {
            ans = Math.min(ans, root.val - pre);
        }
        pre = root.val;
        minDiffInBSTDFS(root.right);
    }

    /**
     * 二叉搜索树两节点的公共祖先
     * @author Rock
     * @param root
     * @param p
     * @param q
     * @return
     * @see https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
     * 题解思路：充分利用二叉搜索树的性质
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val < p.val && root.val < q.val) return lowestCommonAncestor(root.right, p, q);
        if(root.val > p.val && root.val > q.val) return lowestCommonAncestor(root.left, p, q);
        return root;
    }

    /**
     * 题目：N叉树的层遍历
     * @author Rock
     * @param root
     * @return
     * @see https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/
     * 题解思路：二叉树的层遍历同样的思路。
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res=new ArrayList<>();
        Queue<Node> queue=new LinkedList<>();
        if(root==null)return res;
        queue.offer(root);
        int count=1;
        int childCount=0;
        while(!queue.isEmpty()){
            List<Integer> list=new ArrayList<>();
            while(count>0){
                Node node=queue.poll();
                assert node != null;//逻辑判断其实根本不肯能产生null指针。
                list.add(node.val);
                for(Node node1:node.children){
                    queue.offer(node1);
                    childCount++;
                }
                count--;
            }
            res.add(list);
            count=childCount;
            childCount=0;
        }
        return res;
    }

    /**
     * 题目：删除搜索二叉树中的节点
     * @param root
     * @param key
     * @return
     * @see https://leetcode-cn.com/problems/delete-node-in-a-bst/
     * 题解思路：
     * 1. 让被删除节点的右子节点补位，如果其右子节点的左孩子为空，完成；不为空时，将其作为被删除节点的左孩子的最右子节点。
     *    如果其左子树为空，结束，如果不为空，按照上面给的操作即可。
     *    如果其右子节点不存在，直接让其左子节点补位，完成。
     *
     * 技巧：与链表操作中引入头节点增加操作的便利性一样，这里也引入一个虚拟的根节点，这样在删除根节点的情况下，与删除其他节点一样。
     *
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode head=new TreeNode(0);
        head.left=root;
        TreeNode cur=root;
        TreeNode pre=null;
        int flag=0;
        //寻找需要删除的节点与其父亲节点。
        while(cur!=null){
            if(key==cur.val)break;
            else{
                if(key>cur.val){
                    pre=cur;
                    cur=cur.right;
                    flag=1;
                }
                else{
                    pre=cur;
                    cur=cur.left;
                    flag=2;
                }
            }
        }
        if(cur==null)return root;//不存在需要删除的节点，这种情况通常不存在。
        TreeNode right=cur.right;//记录需要删除节点的右节点
        TreeNode left=cur.left;//需要删除节点的左节点
        if(pre==null){
            pre=head;
            flag=2;
        }
        if(left==null&&right==null){
            if(flag==1){
                pre.right=null;
            }
            else pre.left=null;
        }
        else if(left==null){
            if(flag==1)pre.right=right;
            else pre.left=right;
        }
        else if(right==null){
            if(flag==1)pre.right=left;
            else pre.left=left;
        }
        else {
            if(flag==1){
                pre.right=right;
            }
            else{
                pre.left=right;
            }
            TreeNode node=right.left;
            right.left=left;
            while(left.right!=null){
                left=left.right;
            }
            left.right=node;
        }
        return head.left;
    }
    private TreeNode find(TreeNode root,int key){
        if(root.val==key)return root;
        else if(root.val<key)return find(root.right,key);
        else return find(root.left,key);
    }
    public static void main(String[] args){
        TreeNode root=new TreeNode(90,new TreeNode(69,new TreeNode(49,null,new TreeNode(52)),new TreeNode(89)),null);
        int res=new TreeOperating().minDiffInBST(root);
        System.out.println(res);
    }
}


