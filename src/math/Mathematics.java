package math;

public class Mathematics {
    public boolean judgeSquareSum(int c) {
        int len=(int)Math.sqrt(c);
        for(int i=0;i<=len;i++){
            if(i*i+len*len==c)return true;
            if(i*i+len*len>c)len--;
        }
        return false;
    }

    /**
     * 题目：4的幂
     * @param n
     * @return
     * @see https://leetcode-cn.com/problems/power-of-four/
     * 题解思路：
     * 模拟即可
     */
    public boolean isPowerOfFour(int n) {
        if(n<=1)return false;
        while(n!=1){
            if(n%4!=0)return false;
            else n/=4;
        }
        return true;
    }
}
