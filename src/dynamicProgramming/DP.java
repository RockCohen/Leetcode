package dynamicProgramming;

import java.util.*;

/**
 * 动态规划：
 * 动态规划（英语：Dynamic programming，简称 DP）是一种在数学、管理科学、计算机科学、
 * 经济学和生物信息学中使用的，
 * 通过把原问题分解为相对简单的子问题的方式求解复杂问题的方法。
 *
 * 动态规划不是某一种具体的算法，而是一种算法思想：若要解一个给定问题，
 * 我们需要解其不同部分（即子问题），再根据子问题的解以得出原问题的解。
 *
 * 应用这种算法思想解决问题的可行性，对子问题与原问题的关系，
 * 以及子问题之间的关系这两方面有一些要求，它们分别对应了最优子结构和重复子问题。
 *
 *
 * 最优子结构
 *
 * 最优子结构规定的是子问题与原问题的关系
 *
 * 动态规划要解决的都是一些问题的最优解，即从很多解决问题的方案中找到最优的一个。
 * 当我们在求一个问题最优解的时候，如果可以把这个问题分解成多个子问题，
 * 然后递归地找到每个子问题的最优解，
 * 最后通过一定的数学方法对各个子问题的最优解进行组合得出最终的结果。
 * 总结来说就是一个问题的最优解是由它的各个子问题的最优解决定的。
 *
 * 将子问题的解进行组合可以得到原问题的解是动态规划可行性的关键。
 * 在解题中一般用状态转移方程描述这种组合。
 * 例如原问题的解为 f(n)，其中 f(n) 也叫状态。
 * 状态转移方程 f(n)=f(n−1)+f(n−2)描述了一种原问题与子问题的组合关系 。
 * 在原问题上有一些选择，不同选择可能对应不同的子问题或者不同的组合方式。
 * 例如
 * f(n)=f(n−1)+f(n−2) n=2k
 *     =f(n−1) n=2k+1
 * n=2k和 n=2k+1对应了原问题 n上不同的选择，分别对应了不同的子问题和组合方式。
 *
 * 找到了最优子结构，也就能推导出一个状态转移方程 f(n)，通过这个状态转移方程，
 * 我们能很快的写出问题的递归实现方法。
 *
 * 重复子问题
 *
 * 重复子问题规定的是子问题与子问题的关系。
 *
 * 当我们在递归地寻找每个子问题的最优解的时候，有可能会会重复地遇到一些更小的子问题，
 * 而且这些子问题会重叠地出现在子问题里，出现这样的情况，会有很多重复的计算，
 * 动态规划可以保证每个重叠的子问题只会被求解一次。
 * 当重复的问题很多的时候，动态规划可以减少很多重复的计算。
 *
 * 重复子问题不是保证解的正确性必须的，但是如果递归求解子问题时，
 * 没有出现重复子问题，则没有必要用动态规划，直接普通的递归就可以了。
 *
 * 例如，斐波那契问题的状态转移方程 f(n)=f(n−1)+f(n−2)。
 * 在求 f(5)f(5)f(5) 时，需要先求子问题 f(4)和 f(3)，得到结果后再组合成原问题 f(5)的解。
 * 递归地求 f(4)时，又要先求子问题 f(3)和 f(2)，这里的 f(3) 与求 f(5)时的子问题重复了。
 *
 * 解决动态规划问题的核心：找出子问题及其子问题与原问题的关系
 *
 * 找到了子问题以及子问题与原问题的关系，就可以递归地求解子问题了。
 * 但重叠的子问题使得直接递归会有很多重复计算，
 * 于是就想到记忆化递归法：若能事先确定子问题的范围就可以建表存储子问题的答案。
 *
 * 动态规划算法中关于最优子结构和重复子问题的理解的关键点：
 *
 *     证明问题的方案中包含一种选择，选择之后留下一个或多个子问题
 *     设计子问题的递归描述方式
 *     证明对原问题的最优解包括了对所有子问题的最优解
 *     证明子问题是重叠的（这一步不是动态规划正确性必需的，但是如果子问题无重叠，则效率与一般递归是相同的）
 */
public class DP {
    /**
     * @author Rock
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/longest-increasing-subsequence/
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;//最小子问题
        int maxans = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxans = Math.max(maxans, dp[i]);
        }
        return maxans;
    }

    /**
     * 题解思路：
     * @author Rock
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/number-of-longest-increasing-subsequence/
     */
    public int findNumberOfLIS(int[] nums) {
        int N = nums.length;
        if (N <= 1) return N;
        int[] lengths = new int[N]; //lengths[i] = length of longest ending in nums[i]
        int[] counts = new int[N]; //count[i] = number of longest ending in nums[i]
        Arrays.fill(counts, 1);

        for (int j = 0; j < N; ++j) {
            for (int i = 0; i < j; ++i) if (nums[i] < nums[j]) {
                if (lengths[i] >= lengths[j]) {
                    lengths[j] = lengths[i] + 1;
                    counts[j] = counts[i];
                } else if (lengths[i] + 1 == lengths[j]) {
                    counts[j] += counts[i];
                }
            }
        }

        int longest = 0, ans = 0;
        for (int length: lengths) {
            longest = Math.max(longest, length);
        }
        for (int i = 0; i < N; ++i) {
            if (lengths[i] == longest) {
                ans += counts[i];
            }
        }
        return ans;
    }

    /**
     * 题目：译码
     * @param s
     * @return
     * @see https://leetcode-cn.com/problems/decode-ways/
     * 题解思路：
     * 仔细观察子问题结构，显然这个题目可以动态规划来求解。下面给出递推公式。
     * f[i]=s[i]!=0?f[i-1]:0+(s[i-2]!0&&decodable([i-1][i]))?f[i-2]:0;(2<=i<=n)
     * f[0]=1;
     * f[1]=1;
     * 解释：
     */
    public int numDecodings(String s) {
        int [] f=new int[s.length()+1];
        f[0]=1;
        for(int i=1;i<=s.length();i++){
            if (s.charAt(i - 1) != '0') {
                f[i] += f[i - 1];
            }
            if (i > 1 && s.charAt(i - 2) != '0' && ((s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0') <= 26)) {
                f[i] += f[i - 2];
            }
        }
        return f[s.length()];
    }

    /**
     * 题目：最大整除数集
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/largest-divisible-subset/
     * 题解思路：
     * 动态规划,需要记录当前最大整除数集的最大元素与个数。
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int len = nums.length;
        Arrays.sort(nums);

        // 第 1 步：动态规划找出最大子集的个数、最大子集中的最大整数
        int[] dp = new int[len];
        Arrays.fill(dp, 1);
        int maxSize = 1;
        int maxVal = dp[0];
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                // 题目中说「没有重复元素」很重要
                if (nums[i] % nums[j] == 0) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            if (dp[i] > maxSize) {
                maxSize = dp[i];
                maxVal = nums[i];
            }
        }

        // 第 2 步：倒推获得最大子集
        List<Integer> res = new ArrayList<Integer>();
        if (maxSize == 1) {
            res.add(nums[0]);
            return res;
        }

        for (int i = len - 1; i >= 0 && maxSize > 0; i--) {
            if (dp[i] == maxSize && maxVal % nums[i] == 0) {
                res.add(nums[i]);
                maxVal = nums[i];
                maxSize--;
            }
        }
        return res;
    }

    /**
     * 题目：组合总和IV
     * @param nums
     * @param target
     * @return
     * @see https://leetcode-cn.com/problems/combination-sum-iv/
     *
     * 题解思路：
     * 给出例子：[1,2,3]----->4
     * 上述的问题可以分为子问题：
     *                     [1,2,3]----->3;    ====>[1,....]
     *                     [1,2,3]----->2;    ====>[2,....]
     *                     [1,2,3]----->1;    ====>[3,....]
     * 上述的分析显然已经看出，可以通过递归，动态规划的方法来解决。于是我们可以建立如下的递推公式：
     *
     *   combinationSum(num,target)= ∑ coninationSum(num,target-num[i])   (0<=i<n,n=num.length)
     * 显然上述建立了递归的地推公式，但是通过上述的分析，我们不难发现，存在子问题重复求解的问题。
     * 为了避免重复子问题的求解，我们提出了动态规划的方案。
     *   上述的问题可以抽象成：
     *   dp[i]=∑ (dp[i-num[j]]) (0<=j<=i,1<=i<=target)
     *   初始化 dp[0]=1；
     * 遍历 i 从 1到 target，对于每个 i，进行如下操作：
     * 遍历数组 nums 中的每个元素 num，当 num≤i 时，将 dp[i−num] 的值加到 dp[i]。
     * 最终得到dp[target] 的值即为答案。
     */
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (num <= i) {
                    dp[i] += dp[i - num];
                }
            }
        }
        return dp[target];
    }
    public static void main(String[] args) {
       int number= new DP().numDecodings("06");
       System.out.println(number);
    }
    /**
     * 题目：最长公共子序列
     * @see ： https://leetcode-cn.com/problems/longest-common-subsequence/
     * 题解思路：
     * 动态规划
     */
    public int longestCommonSubsequence(String text1, String text2) {

    }
    /**
     * 题目：不相交的线
     * @param : nums1
     * @param : nums2
     * @see ：https://leetcode-cn.com/problems/uncrossed-lines/
     * 题解思路：
     * [1,2,4] 
     * [1,4,2]
     * [[0,0],[1,2],[2,1]]
     * 检索上述的中间值，获取非重合区间的个数。可以按照其中一位进行排序，然后检索过程中，记录当前区间的右界。
     * 
     * 
     * 将数组2存入到哈希表中，按序检索数组1中的元素，记录当前的右边界，但是该方法还是无法解决。显然这个问题需要使用动态规划来解决、
     */
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        Map<Integer,Integer> map=new HashMap<>();
        int[][] l=new int[num1.length][2];
        for(int i=0;i<nums1.length;i++){
            map.put(nums1[i],i);
        }
        for(int i=0;i<nums2.length;i++){
            l[i]=new int[]{i,map.get(nums2[i])};
        }
        Arrays.sort(l,(a,b)->a[0]-b[0]);
        //[[0,0],[1,4],[2,2],[3,3],[4,1]] 
        for(int i=0;i<l.length;i++){

        }

        

    }

}
