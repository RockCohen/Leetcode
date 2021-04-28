package array;

public class ArraysOperation {
    /**
     * 题目：合并有序数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     * @see https://leetcode-cn.com/problems/merge-sorted-array/
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
}
