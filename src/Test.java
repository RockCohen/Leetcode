/**
 * 测试类
 */
import list.*;
import stack.StackOperating;

import java.util.Arrays;
public class Test {
    public static void main(String[] args){
        if(new StackOperating().isValid("()"))System.out.println("合法");
        else System.out.println("不合法");

    }
}
