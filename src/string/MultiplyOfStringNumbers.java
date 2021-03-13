package string;

/**
 * 乘法器
 */
public class MultiplyOfStringNumbers {
    public String multiply(String num1, String num2) {
        int i = 0,j=0;
        int carry=0;
        StringBuilder res= new StringBuilder();
        while(i++<num1.length()&&j++<num2.length()){
            int number=((num1.charAt(i) - '0') * (num2.charAt(j) - '0') + carry);
            res.append(number/ 10);
            carry=number % 10;
        }
        //while()
        return res.toString();

    }
}
