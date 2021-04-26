package divideConquer;

import java.util.Arrays;

public class BinarySearch {
    /**
     * 题目：搜索插入位置
     * @param nums
     * @param target
     * @return
     * @see https://leetcode-cn.com/problems/search-insert-position/
     *
     * 题解思路：二分查找，没啥可说的，最经典的题目了。
     * 特别注意上下界的变化。
     */
    public int searchInsert(int[] nums, int target) {
          return search(nums,target,0,nums.length);
    }
    private int search(int[] nums,int target,int left,int right){
        if(left==right)return left;
        int mid=(left+right)/2;
        if(nums[mid]==target)return mid;
        else if(nums[mid]>target){
            return search(nums,target,left,mid);
        }
        else return search(nums,target,mid+1,right);
    }

    /**
     * 题目：平方根
     * @param x
     * @return
     * @see https://leetcode-cn.com/problems/sqrtx/
     *
     * 题解：直接二分
     */
    public int mySqrt(int x) {
        int l = 0, r = x, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }
    public boolean isPerfectSquare(int x) {
        int l = 0, r = x;
        boolean flag=true;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if((long)mid*mid==x){
                return true;
            }
            if ((long) mid * mid <x) {
                flag=false;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return flag;
    }
    public int firstBadVersion(int n) {
        int left=1;
        int right=n;
        int mid=(left+right);
        while(left<right){
            if(!isBadVersion(mid)){
                left=mid+1;
            }
            else{
                right=mid;
            }
        }
        return left;
    }

    private boolean isBadVersion(int mid) {
return true;}

    /**
     * 题目：在 D 天内送达包裹的能力
     * @param weights
     * @param D
     * @return
     * @see https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/
     * 题解思路：二分法
     * 分析问题得知，一定存在一个最小运货载重，使得货物在D天内被运走，于是我们采用如下的办法：
     * 我们将「最少需要运送的天数」与 DD 进行比较，就可以解决这个判定问题。
     * 当其小于等于 D 时，我们就忽略二分的右半部分区间；
     * 当其大于 D 时，我们就忽略二分的左半部分区间。
     */
    public int shipWithinDays(int[] weights, int D) {
        // 确定二分查找左右边界
        int left = Arrays.stream(weights).max().getAsInt(), right = Arrays.stream(weights).sum();
        while (left < right) {
            int mid = (left + right) / 2;
            // need 为需要运送的天数
            // cur 为当前这一天已经运送的包裹重量之和
            int need = 1, cur = 0;
            for (int weight : weights) {
                if (cur + weight > mid) {
                    ++need;
                    cur = 0;
                }
                cur += weight;
            }
            if (need <= D) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] nums={1,3,5,6};
        int target=7;
        System.out.println(new BinarySearch().isPerfectSquare(1));
    }
}
