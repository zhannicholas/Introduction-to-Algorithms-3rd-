package ch15;

import ch04.Matrix;


public class MatrixChainMultiplication extends Matrix {
    private int[] p;    //矩阵维数序列（矩阵Ai的位数是Pi x Pi-1）

    public MatrixChainMultiplication(int[] p1){
        p = p1;
    }

    private int[][] matrixChainOrder(int[] p){
        int n = p.length;
        int[][] m = new int[n][n];  // 保存代价
        int[][] s = new int[n][n];  // 记录m[i][j]取得最优代价是k的值
        for(int i = 0;i < n;i++){
            m[i][i] = 0;
        }
        for(int l = 2;l < n;l++){  // 链的长度
            for(int i = 1;i < n - l + 1;i++){   // 前半部分
                int j = i + l - 1;              // 后半部分
                m[i][j] = Integer.MAX_VALUE;
                for(int k = i;k <= j - 1;k++){
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if(q < m[i][j]){
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        return s;
    }

    private void printOptimalParents(int[][] s, int i, int j){
        if(i == j){
            System.out.printf("A" + i);
        }
        else{
            System.out.printf("(");
            printOptimalParents(s, i, s[i][j]);
            printOptimalParents(s, s[i][j] + 1, j);
            System.out.printf(")");
        }
    }

    public static void main(String[] args){
        int[] p = new int[]{30, 35, 15, 5, 10, 20, 25};
        MatrixChainMultiplication mcp = new MatrixChainMultiplication(p);
        mcp.printOptimalParents(mcp.matrixChainOrder(mcp.p), 1, p.length - 1);
    }
}
