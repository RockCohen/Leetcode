package string;

import java.util.*;

/**
 * 字符串相关题目
 */
public class StringOperation {
    /**
     * 题目：判断是否为回文串
     * @param s
     * @return
     * @see https://leetcode-cn.com/problems/valid-palindrome/
     * 题解思路：
     * 直接使用双指针，一个指向首部一个指向尾部，然后判断两者的字符是否相等。
     * 其中跳过空白字符与其他字符，只接受数字与字母
     */
    public boolean isPalindrome(String s) {
        int n = s.length();
        int left = 0, right = n - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                ++left;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                --right;
            }
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                ++left;
                --right;
            }
        }
        return true;
    }

    /**
     * 题目：分割回文串
     * @param s
     * @return
     * @see https://leetcode-cn.com/problems/palindrome-partitioning/
     * 题解思路：
     * 动态规划。f[i]=f[i-1]+isPalindrom([res[i-1]+s[i]])?1:0;
     * 将上述的递推式改写成：
     * if(isPalindrom([res[i-1]+s[i]]))
     *   f[i]=max(f[i],f[i-1]+1)
     *
     * 问题在于：问题答案的构建
     */
    boolean[][] f;
    List<List<String>> ret = new ArrayList<List<String>>();
    List<String> ans = new ArrayList<String>();
    int n;

    public List<List<String>> partition(String s) {
        n = s.length();
        f = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(f[i], true);
        }

        for (int i = n - 1; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                f[i][j] = (s.charAt(i) == s.charAt(j)) && f[i + 1][j - 1];
            }
        }

        dfs(s, 0);
        return ret;
    }
    public void dfs(String s, int i) {
        if (i == n) {
            ret.add(new ArrayList<String>(ans));
            return;
        }
        for (int j = i; j < n; ++j) {
            if (f[i][j]) {
                ans.add(s.substring(i, j + 1));
                dfs(s, j + 1);
                ans.remove(ans.size() - 1);
            }
        }
    }
    public static void main(String[] args) {
       System.out.println(new StringOperation().isPalindrome("cbc     "));
    }
}
