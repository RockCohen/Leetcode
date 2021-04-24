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
    /**
     * 题目：石头碰撞
     * @param stones
     * @return
     * @see https://leetcode-cn.com/problems/last-stone-weight/
     * 题解思路：
     * 该题与上述题目反转k次得到最大和的思想一致。
     * 首先将数组正序排序。
     * 选择最后两块（最大的两块）进行碰撞，更新碰撞的结果，将碰撞后生下的石头的结果保存在次末尾的位置，而将完全粉碎的石头置为0（末尾位置）.
     * 显然上述的办法没法保证每次较为轻易的获得最大的两块的石头，原因在于：碰撞后的剩下的石头有可能比原本的石头大，这个时候还需要去维护一个碰撞后石头的最大值，如果采取数组的方式去维护，时间空间复杂度较大。
     * 于是并采取大顶堆来实现，大顶堆可以保证每次拿出来进行碰撞的石头是最大的两块。
     * 在Java中，优先队列就是天然的堆实现方式。
     *
     */
    public int lastStoneWeight(int[] stones) throws NullPointerException{
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);//指明Comparator
        //建立堆的过程
        for (int stone : stones) {
            pq.offer(stone);
        }
        //碰撞石头过程
        while (pq.size() > 1) {
            int a = pq.poll();
            int b = pq.poll();
            if (a > b) {
                pq.offer(a - b);
            }
        }
        return pq.isEmpty() ? 0 : pq.poll();
    }

    public static void main(String[] args) {
        int[] num={1,1,1,2,2,3};
       int[]res= new Heap().topKFrequent(num,2);
        for (int re : res) {
            System.out.println(re);
        }
    }
}
