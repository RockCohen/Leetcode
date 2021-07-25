package prefixSum;

/**
 * 前缀和
 */
public class PrefixSum {
    /**
     * 一维数组前缀和
     */
    static class NumArray {
        int[] sum;

        public NumArray(int[] nums) {
            int m=nums.length;
            if(m>0){
                sum=new int[m+1];
                int h=0;
                for(int i=0;i<m;i++){
                    h+=nums[i];
                    sum[i+1]=h;
                }
            }
        }
        public int sumRange(int left, int right){
            return sum[right+1]-sum[left];
        }
    }

    /**
     * 二维矩阵前缀和
     */
    static class NumMatrix{
        int[][] sums;
        public NumMatrix(int[][] matrix){
            int m=matrix.length;
            if(m>0){
                int n=matrix[0].length;
                sums=new int[m+1][n+1];
                for(int i=0;i<m;i++){
                    for(int j=0;j<n;j++){
                        sums[i+1][j+1]=sums[i][j+1]+sums[i+1][j]-sums[i][j]+matrix[i][j];
                    }
                }
            }
        }
        public int sumRegion(int row1, int col1, int row2, int col2){
            return sums[row2+1][col2+1]-sums[row1][col2+1]-sums[row2+1][col1]+sums[row1][col1];
        }
    }
}
