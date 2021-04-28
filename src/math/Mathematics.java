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
    public static void main(String[] args) {
        System.out.println(new Mathematics().judgeSquareSum(4));
    }
}
