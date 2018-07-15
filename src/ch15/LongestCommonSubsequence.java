package ch15;

import java.util.ArrayList;
import java.util.Arrays;

/**              _
 *              /
 *              |   0                               i = 0 或 j = 0
 *  c[i, j] =   |   c[i - 1, j - 1] + 1             i, j > 0 和 X[i] = Y[j]
 *              |   max(c[i - 1, j], c[i, j - 1]    i, j > 0 和 X[i] != Y[j]
 *              \_
 */

public class LongestCommonSubsequence {
    /**
     * 计算LCS的长度
     * @param X     序列X
     * @param Y     序列Y
     * @return      构造最优解的数组
     */
    private ArrayList<int[][]> lcsLength(String X, String Y){
        int m = X.length();
        int n = Y.length();
        int[][] c = new int[m + 1][n + 1];  //c[i][j]为X[i]和X[j]的一个LCS长度
        int[][] b = new int[m + 1][n + 1];  // 用来构造最优解
        for(int i = 0;i <= m;i++){
            c[i][0] = 0;    //Y的长度为0
        }
        for(int j = 0;j <= n;j++){
            c[0][j] = 0;    // X的长度为0
        }
        for(int i = 1;i <= m;i++){  // 递增X长度
            for(int j = 1;j <= n;j++){  // 递增Y长度
                if(X.charAt(i - 1) == Y.charAt(j - 1)){
                    c[i][j] = c[i - 1][j - 1] + 1;  //X[i] = Y[j]
                    b[i][j] = 1;    // 标记，这是最优解的一个元素
                }
                else if(c[i - 1][j] >= c[i][j - 1]){
                    c[i][j] = c[i - 1][j];
                    b[i][j] = 2;
                }
                else{
                    c[i][j] = c[i][j - 1];
                }
            }
        }
        ArrayList<int[][]> al = new ArrayList<>();
        al.add(c);
        al.add(b);
        return al;
    }

    /**
     * 从右下角开始追踪，递归调用，打印LCS
     * @param b     关于最优解元素位置的数组
     * @param X     X序列
     * @param i     i
     * @param j     j
     */
    private void printLCS(int[][] b, String X, int i, int j){
        if(i == 0 || j == 0){
            return;
        }
        if(b[i][j] == 1){
            printLCS(b, X, i - 1, j - 1);
            System.out.printf(X.charAt(i - 1) + "\t");
        }
        else if(b[i][j] == 2){
            printLCS(b, X, i - 1, j);
        }
        else{
            printLCS(b, X, i, j - 1);
        }
    }

    /**
     * 只是用数组c[][]。从后向前查找
     * 迭代版
     * @param c     c[][]
     * @param X     X
     * @param Y     Y
     */
    private void printLCSWithC(int[][] c, String X, String Y){
        StringBuilder sb = new StringBuilder();
        int i = X.length();
        int j = Y.length();
        while(i >= 1 && j >= 1){
            if(X.charAt(i - 1) == Y.charAt(j - 1)){
                sb.append(X.charAt(i - 1) + "\t");
                i --;
                j --;
            }
            else{
                if(c[i - 1][j] >= c[i][j - 1]){
                    i --;
                }
                else{
                    j --;
                }
            }
        }
        System.out.println(sb.reverse());
    }

    /**
     * 只用数组c,递归版
     * @param c     c[][]
     * @param X     X
     * @param Y     Y
     * @param i     i
     * @param j     j
     */
    private void printLCSWithCIteratively(int[][] c, String X, String Y, int i, int j){
        if(i == 0 || j == 0){
            return;
        }
        if(X.charAt(i - 1) == Y.charAt(j - 1)){
            printLCSWithCIteratively(c, X, Y, i - 1, j - 1);
            System.out.printf(X.charAt(i - 1) + "\t");
        }
        else if(c[i - 1][j] >= c[i][j - 1]){
            printLCSWithCIteratively(c, X, Y, i - 1, j);
        }
        else{
            printLCSWithCIteratively(c, X, Y, i, j - 1);
        }
    }

    private ArrayList<int[][]> lcsWithMemoization(String X, String Y){
        int m = X.length();
        int n = Y.length();
        int[][] c = new int[m + 1][n + 1];
        int[][] b = new int[m + 1][n + 1];
        lookupFromMemoization(X, Y, c, b, X.length(), Y.length());
        ArrayList<int[][]> al = new ArrayList<>();
        al.add(c);
        al.add(b);
        return al;
    }

    /**
     * 备忘录版
     * @param X     X
     * @param Y     Y
     * @param c     c
     * @param b     b
     * @param i     i
     * @param j     j
     * @return      c[i][j]
     */
    private int lookupFromMemoization(String X, String Y, int[][] c, int[][] b, int i, int j){
        if(c[i][j] > 0){
            return c[i][j];         // 已计算
        }
        if(i == 0 || j == 0){
            return c[i][j] = 0;     // 初始条件
        }
        if(X.charAt(i - 1) == Y.charAt(j - 1)){
            c[i][j] = lookupFromMemoization(X, Y, c, b, i - 1, j - 1) + 1;
            b[i][j] = 1;
        }
        else{
            int u = lookupFromMemoization(X, Y, c, b, i - 1, j);
            int l = lookupFromMemoization(X, Y, c, b, i, j - 1);
            if(u >= l){
                c[i][j] = u;
                b[i][j] = 2;
            }
            else{
                c[i][j] = l;
            }
        }
        return c[i][j];
    }

    /**
     * 只用2*min(m, n) + O(1)的空间来计算出lcs的长度
     * 只需用到表格中的两行，正在被计算的一行和上一行
     * @param X     X
     * @param Y     Y
     * @return      lcs的长度
     */
    private int lcsLength2(String X, String Y){
        int m = X.length();
        int n = Y.length();
        String X1, Y1;
        int mn = m < n ? m : n;     // min(m, n)
        int MN = m > n ? m : n;     // max(m, n)
        int[] c1 = new int[mn];     // 上一行
        int[] c2 = new int[mn];     // 当前行
        if(mn == m){
            X1 = X;
            Y1 = Y;
        }
        else{
            X1 = Y;
            Y1 = X;
        }
        for(int i = 1;i < MN;i++){
            for(int j = 1;j < mn;j++){
                if(Y1.charAt(i - 1) == X1.charAt(j - 1)){
                    c2[j] = c1[j] + 1;
                }
                else{
                    if(c2[j - 1] > c1[j]){
                        c2[j] = c2[j - 1];
                    }
                    else{
                        c2[j] = c1[j];
                    }
                }
            }
            c1 = Arrays.copyOfRange(c2, 0, c2.length + 1);  // 当前行计算完毕，进入下一行
        }
        return c2[mn - 1];
    }

    /**
     * 只用min(m, n) + O(1)的空间来计算出lcs的长度
     * 只需正在计算的当前行和c[i - 1][j]和c[i - 1][j - 1]
     * @param X     X
     * @param Y     Y
     * @return      lcs的长度
     */
    private int lcsLength3(String X, String Y){
        int m = X.length();
        int n = Y.length();
        String X1, Y1;
        int mn = m < n ? m : n;     // min(m, n)
        int MN = m > n ? m : n;     // max(m, n)
        int[] c = new int[mn];     // 当前行
        int c1 = 0;     //c[i - 1][j - 1]
        int c2 = 0;     //c[i - 1][j]
        if(mn == m){
            X1 = X;
            Y1 = Y;
        }
        else{
            X1 = Y;
            Y1 = X;
        }
        for(int i = 1;i < MN;i++){
            for(int j = 1;j < mn;j++){
                c1 = c2;    //
                c2 = c[j];  // 在修改c[j]之前，从c[j]保存的其实是上一行第j个位置的值
                if(X1.charAt(i - 1) == Y1.charAt(j - 1)){
                    c[j] = c1 + 1;
                }
                else{
                    if(c[j - 1] >= c2){
                        c[j] = c[j - 1];
                    }
                    else{
                        c[j] = c2;
                    }
                }
            }
        }
        return c[mn - 1];
    }

    public static void main(String[] args){
        String X = "ABCBDAB";
        String Y = "BDCABA";
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        System.out.println("使用辅助数组b: ");
        lcs.printLCS(lcs.lcsLength(X, Y).get(1), X, X.length(), Y.length());
        System.out.println("\n只用数组c(迭代版)：");
        lcs.printLCSWithC(lcs.lcsLength(X, Y).get(0), X, Y);
        System.out.println("\n只用数组c(递归版):");
        lcs.printLCSWithCIteratively(lcs.lcsLength(X, Y).get(0), X, Y, X.length(), Y.length());
        System.out.println("\n备忘录版：");
        lcs.printLCSWithCIteratively(lcs.lcsWithMemoization(X, Y).get(0), X, Y, X.length(), Y.length());
        System.out.println("\n使用 2*min(m,n) + O(1) 的空间：" + lcs.lcsLength2(X, Y));
        System.out.println("\n使用 min(m,n) + O(1) 的空间：" + lcs.lcsLength3(X, Y));
    }
}
