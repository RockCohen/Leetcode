package bitOperation;

public class Bit {
    /**
     * 题目：寻找只出现一次的数字
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/single-number/
     * 题解思路：
     * 1.位运算，直接利用异或运算即可得到最终的结果。
     * 异或运算的规则：
     * 1^1=0;
     * 0^0=0;
     * 1^0=1;
     * a^a=0;
     * 0^a=a;
     *
     * 2. 哈希表，通过记录每个元素的个数来判定只出现一次的元素（适合更加一般的情况）
     * 3. 既然所有的数字除了其中一个都出现两次，那么就可以加减法来搞定了。
     *    因为除了答案元素，其他的元素均各自加了一次，也建议了。
     *    最终剩下的答案便是答案元素。甚至规定如果其他元素出现的次数为偶数，该方法依然适合。
     *    原理和异或运算一样。其实本质东西是一样的。
     *    加法器的实现就是通过异或运算得到。
     */
    public int singleNumber(int[] nums) {
        int res=0;
        for (int num : nums) res ^= num;
        return res;
    }

    /**
     * 题目：子数组异或查询
     * @param arr
     * @param queries
     * @return
     * @see https://leetcode-cn.com/problems/xor-queries-of-a-subarray/
     *
     * 题解：
     * 1. 暴力模拟（显然是一种可能超时的算法）
     * 2. 存储部分结果（显然存在已经计算的结果作为后续计算结果的部分，
     *    应该利用这部分计算过的结果来减少计算重复性）。
     *    该题目需要了解一个叫做数组前缀异或。
     *    我们定义一个长度为n+1个元素的前缀异或数组xors。
     *    令 xors[0]=0，对于0≤i<n，xors[i+1]=xors[i]⊕arr[i]，其中 ⊕ 是异或运算符。
     *    xors[i]=arr[0]⊕…⊕arr[i−1]。
     *    有了上述的数组的前缀异或，我们就可以计算任意子区间的异或结果。我们直到有异或运算的特点：
     *    0^0=0;
     *    1^1=0;
     *    0^1=1;
     *    1^0=1;
     *    于是基于上述的特点我们可以利用数组的前缀异或来计算任意子数组的异或结果。
     *    存在数组arr[n]我们需要计算[left,right]区间的异或结果。
     *    显然：Xor[left,right]=xors[left]^xors[right+1].
     *    没啥可说的，异或运算，yyds.
     */
    public int[] xorQueries(int[] arr, int[][] queries) {
        int n = arr.length;
        int[] xors = new int[n + 1];
        for (int i = 0; i < n; i++) {
            xors[i + 1] = xors[i] ^ arr[i];
        }
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            ans[i] = xors[queries[i][0]] ^ xors[queries[i][1] + 1];
        }
        return ans;
    }
}
