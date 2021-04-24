package greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Predicate;

/**
 * 贪心算法：
 */
public class Greedy {
    /**
     * @author Rock
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/jump-game/
     */
    public boolean canJump(int[] nums) {
        return false;
    }

    /**
     * 题目：炒股赚钱
     * @param prices
     * @return
     * @see https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
     * 题解思路：贪心即可，贪心策略：当前如果赚就卖掉。
     * 根据子问题论证，显然本次的贪心选择应该包含在最优解中，
     * 原因：想更多地赚，那就需要更多的每一次盈利。
     */
    public int maxProfit(int[] prices) {
        int sum=0;
        for(int i=1;i< prices.length;i++){
            int gap=0;
            if(( gap=prices[i]-prices[i-1])>0){
                sum+=gap;
            }
        }
        return sum;
    }

    /**
     * 题目：s是否为t的子序列
     * 注意：子序列的定义，满足相对顺序即可。
     * @param s
     * @param t
     * @return
     * @see https://leetcode-cn.com/problems/is-subsequence/
     *
     * 题解思路：
     * 贪心策略：首次匹配即可往后继续判断匹配。
     */
    public boolean isSubsequence(String s, String t) {
        int n = s.length(), m = t.length();
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == n;
    }

    /**
     * 题目：妈妈喂孩子饼干
     * @param g
     * @param s
     * @return
     * @see https://leetcode-cn.com/problems/assign-cookies/
     *
     * 贪心策略：尽量满足更多的孩子，那就最小满足的饼干分给对应的孩子。
     * 为了方便实施贪心策略，需要对饼干和孩子的胃口数组进行排序。
     * 该题目与上述字符串子序列的题目基本一致。
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i=0;
        int j=0;
        int count=0;
        while(i<g.length&&j<s.length){
            if(s[j]>=g[i]){
                count+=1;
                i++;
            }
            j++;
        }
        return count;
    }

    /**
     * 题目：种花（满足炮翻山规则，两株花不能相邻种植）
     * @param flowerbed
     * @param n
     * @return
     * @see https://leetcode-cn.com/problems/can-place-flowers/
     * 题解思路：
     * 为了种植更多的花，且需要满足种花规则，
     * 那我们只需要满足其最小的要求（即：隔一个位置种一株）就可以尽量种植更多的花。
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count=0;
        for(int i=0;i<flowerbed.length;i++){
            boolean able=flowerbed[i]==0;
            if(flowerbed.length==1)return able&&n==1;
            if(i==0&&able&&flowerbed[i+1]==0){
                count+=1;
                flowerbed[i]=1;
            }
            else if(i==flowerbed.length-1&&able&&flowerbed[i-1]==0){
                count+=1;
                flowerbed[i]=1;
            }
            else if(able&&flowerbed[i+1]==0&&flowerbed[i-1]==0){
                count+=1;
                flowerbed[i]=1;
            }
        }
        return count==n;
    }

    /**
     * 题目：找零
     * @param bills
     * @return
     * @see https://leetcode-cn.com/problems/lemonade-change/
     * 题解思路：
     * 利用哈希表将现在的手里的钱存储起来，然后当顾客来买雪糕时，我把手里按照最大匹配来进行找零。
     * 比如找零15元，我将查找是否有10元，再查找是否有5元。
     */
    public boolean lemonadeChange(int[] bills) {
        int[] map=new int[21];//最简单的哈希办法
        for (int bill : bills) {
            switch (bill) {
                case 5: {
                    map[5]++;
                    break;
                }
                case 10: {
                    if(map[5]==0)return false;
                    else {
                        map[5]-=1;
                        map[10]+=1;
                    }
                    break;
                }
                case 20: {
                    if(map[5]==0)return false;
                    else if(map[10]==0&&map[5]<3)return false;
                    else if(map[10]==0){
                        map[5]-=3;
                        map[20]+=1;
                    }
                    else{
                        map[10]-=1;
                        map[5]-=1;
                        map[20]+=1;
                    }
                    break;
                }
                default:
                    return false;
            }
        }
        return true;
    }

    /**
     * 题目：非升序列
     * @param strs
     * @return
     * @see https://leetcode-cn.com/problems/delete-columns-to-make-sorted/
     */
    public int minDeletionSize(String[] strs) {
        int count=0;
        for(int i=0;i<strs[0].length();i++) {
            for (int j = 0; j < strs.length - 1; j++) {
                if (strs[j].charAt(i) > strs[j + 1].charAt(i)) {
                    count += 1;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * 题目：反转k次使得和最大
     * @param A
     * @param K
     * @return
     * @see https://leetcode-cn.com/problems/maximize-sum-of-array-after-k-negations/
     *
     * 题解思路：
     * 每次选择一个最小的数值进行反转，这样来说对于求和的结果影响是最小的。
     * 证明：如果最小的数是负数，那么反转之后，求和反而变大，影响为正反馈，符合预期；
     *      如果最小的是为非负数，反转之后，其对于求和的作用最小。
     * 需要解决的问题：每次反转之后的最小元素。
     * 对于一个数组，我们首先将数组进行排序，我们将当前的最小的元素（数组首）进行反转之后，余下的元素构成的数组的最小值依然是第一个元素。
     * 我们只需要比较反转之后的元素与余下元素构成的数组的最小值比较即可。当然余下元素构成的数组的最小值就是反转元素的后一个元素。
     */
    public int largestSumAfterKNegations(int[] A, int K) {
        Arrays.sort(A);
        int minIndex=0;//记录当前最小值的索引。重排之后为0.
        A[minIndex]=-A[minIndex];
        for(int i=1;i<K;i++){
            //更新当前最小值索引
            if(minIndex<A.length-1&&A[minIndex]>A[minIndex+1]){
                minIndex+=1;
            }
            A[minIndex]=-A[minIndex];
        }
        //求和
        return Arrays.stream(A).sum();
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
        int[] A={2,-3,-1,5,-4};
        int sum=new Greedy().largestSumAfterKNegations(A,2);
        System.out.println(sum);
    }
}
