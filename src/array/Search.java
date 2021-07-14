package array;

public class Search {
    /**
     * 题目：二维矩阵的搜索
     * @param matrix
     * @param target
     * @return
     * 参考:  https://leetcode-cn.com/problems/search-a-2d-matrix-ii/、
     *
     * 题解思路：从左下角或者右上角开始寻找，类似于二分查找
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int col = 0, row = matrix.length - 1;
        while(row >= 0 && col < matrix[0].length){
            if(target > matrix[row][col]){
                col++;
            }else if(target < matrix[row][col]){
                row--;
            }else{
                return true;
            }
        }
        return false;
    }
}
