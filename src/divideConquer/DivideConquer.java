package divideConquer;

import java.util.Arrays;

/**
 * 分治算法：分支算法要求问题本质是递归的，算法多次调用自身去解决子问题，然后将子问题的解进行合并得到问题的解。
 * 分治法的思想本质：
 * 将原问题分解为几个规模较小的子问题，递归地求解这些子问题，合并子问题的解得到原问题的解。
 * 分治算法的几个步骤：
 * 1. 分解：原问题分解为几个规模较小的子问题。
 * 2. 递归：递归求解子问题
 * 3. 合并：将子问题的解合并得到原问题的解。
 *
 * 分治算法需要注意的是：在问题进行分解的时候可能产生不同于原问题的子问题，这部分子问题需要单独处理。
 */
public class DivideConquer {
    /**
     * 题解思路：
     * 分治法的思路是这样的，其实也是分类讨论。
     * 连续子序列的最大和主要由这三部分子区间里元素的最大和得到：
     * 第 1 部分：子区间 [left, mid]；
     * 第 2 部分：子区间 [mid + 1, right]；
     * 第 3 部分：包含子区间 [mid , mid + 1] 的子区间，即 nums[mid] 与 nums[mid + 1] 一定会被选取。
     * 对这三个部分求最大值即可。
     * @author Rock
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/maximum-subarray/
     */
    public int maxSubArray(int[] nums) {
       if(nums.length==0)return 0;
       return  subArraySum(nums,0,nums.length-1);
    }
    public int crossingSum(int[] nums,int left,int mid,int right){
        // 一定会包含 nums[mid] 这个元素
        int sum = 0;
        int leftSum = Integer.MIN_VALUE;
        // 左半边包含 nums[mid] 元素，最多可以到什么地方
        // 走到最边界，看看最值是什么
        // 计算以 mid 结尾的最大的子数组的和
        //这里不仅可以维护最大和，还可以维护最大和产生的索引
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            if (sum > leftSum) {
                leftSum = sum;
            }
        }
        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        // 右半边不包含 nums[mid] 元素，最多可以到什么地方
        // 计算以 mid+1 开始的最大的子数组的和
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            if (sum > rightSum) {
                rightSum = sum;
            }
        }
        return leftSum + rightSum;
    }
    public int subArraySum(int[] num,int left,int right){
        if(left==right)return num[left];//子问题递归出口。
        int mid=(left+right)>>1;//子问题分解标志。
        return maxSum(subArraySum(num,left,mid),
                subArraySum(num,mid+1,right),crossingSum(num,left,mid,right));//递归体
    }
    private int maxSum(int sum1,int sum2,int sum3){
        return Math.max(sum1,Math.max(sum2,sum3));
    }

    /**
     * 题解思路：
     * 1.哈希表计数
     * 2.排序返回中间数
     * 3.Boyer-Moore 投票算法
     * 4.分治算法
     * @author Rock
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/majority-element/
     */
    public int majorityElement(int[] nums) {
        int count = 0;
        int candidate =0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
    private int countInRange(int[] nums, int num, int lo, int hi) {
        int count = 0;
        for (int i = lo; i <= hi; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }
    private int majorityElementRec(int[] nums, int lo, int hi) {
        // base case; the only element in an array of size 1 is the majority
        // element.
        if (lo == hi) {
            return nums[lo];
        }
        // recurse on left and right halves of this slice.
        int mid = (hi - lo) / 2 + lo;
        int left = majorityElementRec(nums, lo, mid);
        int right = majorityElementRec(nums, mid + 1, hi);
        // if the two halves agree on the majority element, return it.
        if (left == right) {
            return left;
        }
        // otherwise, count each element and return the "winner".
        int leftCount = countInRange(nums, left, lo, hi);
        int rightCount = countInRange(nums, right, lo, hi);

        return leftCount > rightCount ? left : right;
    }

    /**
     *题解思路：分支算法实现
     * 将问题分为两个部分去解决，如果两个部分的众数相等，那么一定相等，否则获取数量多的那个。
     * @author Rock
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/majority-element/
     */
    public int majorityElementDivide(int[] nums) {
        return majorityElementRec(nums, 0, nums.length - 1);
    }

    /**
     * 题解思路：
     * 1. 排序算法，寻找第k大的数即可。时间复杂度O(nlgn)
     * 2. 分治算法，
     * @author Rock
     * @param nums
     * @param k
     * @return
     * @see https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
     */
    public int findKthLargest(int[] nums, int k) {
        return 0;

    }
    public static void main(String[] args) {
        int[] nums={-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(new DivideConquer().maxSubArray(nums));
    }
}
