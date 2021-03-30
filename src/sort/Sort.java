package sort;

import list.ListNode;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class Sort  {
    /**
     * 题解思路：首先将每组数据按照下界坐标进行排序，然后再依次遍历合并即可。
     * @author Rock
     * @param intervals
     * @return
     * @see https://leetcode-cn.com/problems/merge-intervals/
     */
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });
        List<int[]> merged = new ArrayList<int[]>();
        for (int[] interval : intervals) {
            int L = interval[0], R = interval[1];
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < L) {
                merged.add(new int[]{L, R});
            } else {
                merged.get(merged.size() - 1)[1] = max(merged.get(merged.size() - 1)[1], R);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    private int[] mergeMid(int[]a,int[]b){
        if(a[0]<=b[0]){
            if(a[1]>b[0]) return new int[]{a[0], max(a[1],b[1])};
        }
        else{
            if(b[1]>a[0])return new int[]{b[0], max(a[1],b[1])};
        }
        return new int[]{0,0};

    }

    /**
     * @author ROCK
     * @param intervals 无重复区间
     * @param newInterval 待插入区间
     * @return
     * @see https://leetcode-cn.com/problems/insert-interval/
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> merged = new ArrayList<int[]>();
        merged .add(intervals[0]);
        int[] r=newInterval;
        int i=1;
        while(i<intervals.length){
            int [] mid=mergeMid(merged.get(merged.size()-1), r);
            if(!Arrays.equals(mid, new int[]{0, 0})){
                merged.remove(merged.size()-1);
                merged.add(mid);
                r=intervals[i];
            }
            else{
                merged.add(intervals[i]);
            }
            i++;
        }
        return merged.toArray(new int[merged.size()][]);
    }

    /**
     * 题解思路：本质就是数组的排序，并且要求在原地进行。
     * 如果不采取原地实现，那么只需要记录每个元素的个数，然后重写数组即可。
     * 采取原地排序：
     * 1. 两遍扫描，第一遍将所有的0交换到数组的前面，第二遍将所有的1交换到数组的中间。
     * 2. 一遍扫描，使用双指针，一个指针用来交换0，一个用来交换1.
     * @author Rock
     * @param nums 待排序数组
     * @see https://leetcode-cn.com/problems/sort-colors/
     */
    public void sortColors(int[] nums) {
        int n = nums.length;
        int p0 = 0, p1 = 0;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == 1) {
                int temp = nums[i];
                nums[i] = nums[p1];
                nums[p1] = temp;
                ++p1;
            } else if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[p0];
                nums[p0] = temp;
                if (p0 < p1) {
                    temp = nums[i];
                    nums[i] = nums[p1];
                    nums[p1] = temp;
                }
                ++p0;
                ++p1;
            }
        }
    }

    /** 题解思路：插入排序,需要的关键在于更新后的链表的表尾记录指针，以及未进行排序的链表的表头指针。
     * 每次将未进行排序的元素插入到已经排序好的链表中，然后更新已排序的表尾
     * @author Rock
     * @param head 表头
     * @return 排序后的链表
     * @see https://leetcode-cn.com/problems/insertion-sort-list/
     */
    public ListNode insertionSortList(ListNode head) {
        if(head==null||head.next==null)return head;
        ListNode root=new ListNode(Integer.MIN_VALUE);//头节点，方便插入删除。
        ListNode node=head.next;
        root.next=head;
        ListNode p=root.next;
        while(node!=null){
            if(node.val<p.val){
                ListNode insert=node;//记录当前需要插入的节点
                node=node.next;//更新node的值，即下一个待插入的节点。
                //插入过程
                ListNode q=root;
                while(q.next!=p&&(q.next.val<insert.val))q=q.next;
                insert.next=q.next;
                q.next=insert;
            }
            //直接插入过程
            else {
                p.next=node;
                p=node;
                node=node.next;
            }
        }
        //记得将排序后的链表的最后一个元素的next属性置为空，否则出现循环的情况
        p.next=null;
        return root.next;
    }

    /**题解思路:要想要实现O(nlogn)的时间复杂度解决此问题，显然需要通过分治法来解决。
     * 以下提供一种O(n^2)的方法来解决此问题，那就是插入排序。
     * @author Rock
     * @param head 表头
     * @return
     * @see https://leetcode-cn.com/problems/sort-list/
     */
    public ListNode sortList(ListNode head) {
        if(head==null||head.next==null)return head;
        ListNode root=new ListNode(Integer.MIN_VALUE);//头节点，方便插入删除。
        ListNode node=head.next;
        root.next=head;
        ListNode p=root.next;
        while(node!=null){
            if(node.val<p.val){
                ListNode insert=node;//记录当前需要插入的节点
                node=node.next;//更新node的值，即下一个待插入的节点。
                //插入过程
                ListNode q=root;
                while(q.next!=p&&(q.next.val<insert.val))q=q.next;
                insert.next=q.next;
                q.next=insert;
            }
            //直接插入过程
            else {
                p.next=node;
                p=node;
                node=node.next;
            }
        }
        //记得将排序后的链表的最后一个元素的next属性置为空，否则出现循环的情况
        p.next=null;
        return root.next;
    }

    /**题解思路：自定义排序。
     * @author Rock
     * @param nums
     * @return
     * @see https://leetcode-cn.com/problems/largest-number/
     */
    private static class LargerNumberComparator implements Comparator<String> {
        @Override
        //这波自定义字符串比较函数真没想到。靠。
        public int compare(String a, String b) {
            String order1 = a + b;
            String order2 = b + a;
            return order2.compareTo(order1);
        }
    }
    public String largestNumber(int[] nums) {
        // Get input integers as strings.
        String[] asStrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            asStrs[i] = String.valueOf(nums[i]);
        }

        // Sort strings according to custom comparator.
        Arrays.sort(asStrs, new LargerNumberComparator());

        // If, after being sorted, the largest number is `0`, the entire number
        // is zero.
        if (asStrs[0].equals("0")) {
            return "0";
        }

        // Build largest number from sorted array.
        StringBuilder largestNumberStr = new StringBuilder();
        for (String numAsStr : asStrs) {
            largestNumberStr.append(numAsStr);
        }

        return largestNumberStr.toString();
    }

    /**
     * @author Rock
     * @param nums
     * @param k
     * @param t
     * @return
     * @see https://leetcode-cn.com/problems/contains-duplicate-iii/
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        for(int i=0;i<nums.length;i++){
            if(i+k<nums.length&&Math.abs(nums[i]-nums[i+k])<=t)return true;
        }
        return false;
    }
    /**
     * @author Rock
     * @param citations
     * @return
     * @see https://leetcode-cn.com/problems/h-index/
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int len=citations.length;
        return 0;
    }

    /**
     * 题解思路：
     * @author Rock
     * @param nums 待排序数组
     * @see https://leetcode-cn.com/problems/wiggle-sort-ii/
     */
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        int i=1;
        List<Integer> res=new ArrayList<>();
        while(i<(nums.length/2)){
            res.add(nums[i]);
            res.add(nums.length-1-i);
            i++;
        }
        if(nums.length%2==1)res.add(nums[i]);
    }
    public int[] intersection(int[] nums1, int[] nums2) {
        if(nums1.length>nums2.length){
            int[] temp=nums1;
            nums1=nums2;
            nums2=temp;
        }
        List<Integer> res=new ArrayList<>();
        Set<Integer> set=new HashSet<>();
        for (int si:nums2) set.add(si);
        for (int j : nums1) if (set.contains(j)) res.add(j);
        int [] r=new int[res.size()];
        for(int i=0;i<r.length;i++) r[i]=res.get(i);
        return r;
    }
    public int[] sameSection(int[] a,int[] b){
        if(a[0]>b[0]){
            int[] temp=a;
            a=b;
            b=temp;
        }
        if(a[1]>=b[0]){
            return new int[]{b[0],Math.min(a[1],b[1])};
        }
        return new int[0];
    }

    /**
     * 题解思路：将子数组按照下界的升序排序，然后求解交集，然后更新交集与下一个区间进行对比，不断更新交集；
     * 如果不存在交集，那么🗡的数量加一。
     * @author Rock
     * @param points
     * @return
     * @see https://leetcode-cn.com/problems/minimum-number-of-arrows-to-burst-balloons/submissions/
     */
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (o1, o2) -> o1[0]-o2[0]);
        int[] intersection=points[0];
        int count=1;
        for(int i=1;i<points.length;i++){
            int[] mid=sameSection(intersection,points[i]);
            if(mid.length!=0){
                intersection=mid;
            }
            else{
                count++;
                intersection=points[i];
            }
        }
        return count;
    }

    /**
     * 题解思路：
     * @author Rock
     * @param s 字符集
     * @param dictionary 带选择字符数组
     * @return
     * @see https://leetcode-cn.com/problems/longest-word-in-dictionary-through-deleting/
     */
    public String findLongestWord(String s, List<String> dictionary) {
        dictionary.sort((o1, o2) -> {
            if (o1.length() == o2.length()) return o1.compareTo(o2);//两个长度相等，按照字典序进行排序
            else return o2.length() - o1.length();//否则按照长度排序。
        });
        for (String d:dictionary) {
            if(isSubsequence(s,d))return d;
        }
        return "";
    }
    public boolean isSubsequence(String x, String y) {
        int j = 0;
        for (int i = 0; i < y.length() && j < x.length(); i++)
            if (x.charAt(j) == y.charAt(i))
                j++;
        return j == x.length();
    }
    public int[][] kClosest(int[][] points, int k) {
        int[][] res=new int[k][2];
        Arrays.sort(points, Comparator.comparingInt(o -> (o[0] * o[0] + o[1] * o[1])));
        if (k >= 0) System.arraycopy(points, 0, res, 0, k);
        return res;
    }
    public static void main(String[] args) {
        int[][] points={{1,3},{-2,2},{3,3},{4,1}};
        System.out.println(Arrays.deepToString(new Sort().kClosest(points, 2)));
    }

}
