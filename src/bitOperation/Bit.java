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
}
