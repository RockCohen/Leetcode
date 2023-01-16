package array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 两数之和：
给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
示例 1：
输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
解题思路：
1. 将数组哈希化，然后首先查询当前元素的对应元素（nums[i]的对应元素target-nums[i]）是否在哈希表中，如果在直接返回两个元素的V，这样一来还可以避免重复查询。
2. 对于增序数组，直接使用双指针可以快速的进行求解，时间复杂度为O(n).当然也可以采用二分查找的方法来做，但是问题在于，这样便引入了第三个变量，mid，反而增加了时间复杂度。
该题收获：
1. 熟悉一些哈希表的操作。
2. 使用双指针来来解决问题。一般针对有序数组。
 */
public class SumOfTwo {
    public int[] twoSum1(int[] nums, int target) {//nums为无序数组
        Map<Integer,Integer> hashTable=new HashMap<>();
        for(int i=0;i<nums.length;++i){
            if(hashTable.containsKey(target-nums[i]))
                return new int[]{hashTable.get(target-nums[i]),i};
            hashTable.put(nums[i],i);
        }
        // 需要返回默认值 保证分支逻辑都有返回。
        return new int[0];
    }
    public int[] twoSum2(int[] nums,int target){//nums为有序数组
        int i=0;
        int j=nums.length-1;
        while (i<=j){
            if(nums[i]+nums[j]==target)return new int[]{i+1,j+1};
            else if(nums[i]+nums[j]>target)j--;
            else i++;
        }
        return new int[0];
    }
}
