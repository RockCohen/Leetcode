package heap;

import java.util.*;

public class Heap {
    /**
     * 题目：数组中第k个大的元素
     * @param nums
     * @param k
     * @return
     * @see https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
     */
    public int findKthLargest(int[] nums, int k) {
        if(k>nums.length) try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PriorityQueue<Integer> queue=new PriorityQueue<>((a,b)->b-a);
        int res=0;
        for(int n:nums){
            queue.offer(n);
        }
        int j=0;
        while(j<k){
            res=queue.poll();
            j++;
        }
        return res;
    }

    /**
     * 题目：统计数组中前k个高频元素
     * @param nums
     * @param k
     * @return
     * @see https://leetcode-cn.com/problems/top-k-frequent-elements/
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> occurrences = new HashMap<Integer, Integer>();
        for (int num : nums) {
            //Map的getOrDefault方法返回对应key的value,如果对应key值不存在返回defaultValue.这便于统计.
            occurrences.put(num, occurrences.getOrDefault(num, 0) + 1);
        }
        //定义以map的value为排序标准的方法
        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<>((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        priorityQueue.addAll(occurrences.entrySet());
        int[] res=new int[k];
        for (int j = 0; j < k; j++) {
            res[j]=priorityQueue.poll().getKey();
        }
        return res;
    }

    public static void main(String[] args) {
        int[] num={1,1,1,2,2,3};
       int[]res= new Heap().topKFrequent(num,2);
        for (int re : res) {
            System.out.println(re);
        }
    }
}
