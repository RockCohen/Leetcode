package array;

import java.util.*;

public class ArraysOperation {
    /**
     * 题目：合并有序数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     * 参考:  https://leetcode-cn.com/problems/merge-sorted-array/
     * 题解思路：
     * 1. 从尾部开始扫描，对比nums1与nums2中最大的元素放入nums1的末尾，一次填满即可。
     *
     * 2. 直接将nums2的元素放入nums1中，然后排序进行重排序。
     *
     * 3. 或者申请O(m+n)的内存空间，然后使用有序合并的方式进行。
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1, p2 = n - 1;
        int tail = m + n - 1;
        int cur;
        while (p1 >= 0 || p2 >= 0) {
            if (p1 == -1) {
                cur = nums2[p2--];
            } else if (p2 == -1) {
                cur = nums1[p1--];
            } else if (nums1[p1] > nums2[p2]) {
                cur = nums1[p1--];
            } else {
                cur = nums2[p2--];
            }
            nums1[tail--] = cur;
        }
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        // 关键属性：1、字符串长度一致；2、字母排序后是同一个单词(根据hashCode识别）。
        // 字符串切分之后排序，然后将其HashCode作为Key存储到Map中。
        Map<Integer,List<String>> resMap = new HashMap<>();
        for(String s : strs){
            Integer hashKey = hashHandler(s);
            if(resMap.get(hashKey) == null){
                List<String> arrayList = new ArrayList<>();
                arrayList.add(s);
                resMap.put(hashKey,arrayList);
            }else{
                resMap.get(hashKey).add(s);
            }
        }
        List<List<String>>  res = new ArrayList<>();
        resMap.forEach((key,value)->{
            res.add(value);
        });
        return res;
    }

    private Integer hashHandler(String s){
        char[] chars = new char[s.length()];
        s.getChars(0,s.length(),chars,0);
        Arrays.sort(chars);
        return String.valueOf(chars).hashCode();
    }

    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        ArraysOperation arraysOperation = new ArraysOperation();
        List<List<String>> lists = arraysOperation.groupAnagrams(strs);
        System.out.println(lists);
    }
}
