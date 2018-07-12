package ch04;

import java.util.Random;
import java.util.Scanner;

class Matrix {
    // 生成矩阵
    public int[][] generateMatrix(int row, int col) {
        int[][] A = new int[row][col];
        Random random = new Random();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                A[i][j] = random.nextInt(2 * 100) * (-1) + 100;
            }
        }
        return A;
    }

    // 打印矩阵
    public void printMatrix(int[][] A) {
        int row = A.length, col = A[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf("%8d", A[i][j]);
            }
            System.out.println();
        }
    }

    // 判断矩阵是否为2的幂次方阵
    private boolean isSquareMatrix(int[][] A) {
        int rowa = A.length, cola = A[0].length;
        if ((rowa == cola) && ((rowa & (rowa - 1)) == 0)) return true;
        else return false;
    }

    // 获取比a大，最接近a的2的幂次方数
    private int nextP2(int a){
        int t = 1;
        while(t < a) t <<= 1;
        return t;
    }

    // 将不是2的幂次方的矩阵改为2的幂次方的方阵
    // 方法： 在其它的行或列补0。
    private int[][] convertMatrixToStandardSquareMatrix(int[][] A, int n){
        int rowa = A.length, cola = A[0].length;
        int[][] C = new int[n][n];
        for(int i = 0;i < rowa;i++){
            for(int j = 0;j < cola;j++){
                C[i][j] = A[i][j];
            }
        }
        return C;
    }

    // 处理不是2的指数次幂的矩阵变成方阵后运算多出来的那一大堆0
    private int[][] removeZerosInMatrix(int[][] A, int rowa, int cola){
        int[][] C = new int[rowa][cola];
        for(int i = 0;i < rowa;i++){
            for(int j = 0;j < cola;j++){
                C[i][j] = A[i][j];
            }
        }
        return C;
    }

    // 判断两矩阵能否相加减
    public boolean canAdd(int[][] A, int[][] B) {
        int rowa = A.length, cola = A[0].length, rowb = B.length, colb = B[0].length;
        if (rowa != rowb || cola != colb) return false;
        else return true;
    }

    // 判断两矩阵能否相乘
    public boolean canMultiply(int[][] A, int[][] B) {
        int cola = A[0].length, rowb = B.length;
        if (cola != rowb) return false;
        else return true;
    }

    // 矩阵加法---一般表示法
    public int[][] matrixAdd(int[][] A, int[][] B) {
        int rowa = A.length, cola = A[0].length;
        int[][] C = new int[rowa][cola];
        for (int i = 0; i < rowa; i++) {
            for (int j = 0; j < cola; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    /**
     * 矩阵加减法
     *
     * @param flag true->加法     false->减法
     */
    public int[][] squareMatrixAddorSub(int[][] A, int[][] B, int ra, int ca, int rb, int cb, int n, boolean flag) {
        int[][] C = new int[n][n];  // C为n * n 矩阵
        if (flag == true) {  // 加法
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] = A[ra + i][ca + j] + B[rb + i][cb + j];
                }
            }
        } else {  // 减法
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] = A[ra + i][ca + j] - B[rb + i][cb + j];
                }
            }
        }
        return C;
    }

    // 乘法---一般方法
    public int[][] matrixMultiply(int[][] A, int[][] B) {
        int rowc = A.length, colc = B[0].length, colab = A[0].length;
        int[][] C = new int[rowc][colc];
        for (int i = 0; i < rowc; i++) {
            for (int j = 0; j < colc; j++) {
                for (int k = 0; k < colab; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }


    // 2的幂次方阵乘法---分治--辅助函数---待封装

    /**
     * @param ra A中行开始下标
     * @param ca A中列开始下标
     * @param n  子方阵宽度
     */
    private int[][] squareMatrixMultiply(int[][] A, int[][] B, int[][] C, int ra, int ca, int rb, int cb, int rc, int cc, int n) {
        if (n == 1) {
            C[rc][cc] = A[ra][ca] * B[rb][cb];  // 只有一个元素
        } else if (n == 2) {  // 都只有四个元素
            C[rc][cc] += A[ra][ca] * B[rb][cb] + A[ra][ca + 1] * B[rb + 1][cb];  // C11 = A11 * B11 + A12 * B21
            C[rc][cc + 1] += A[ra][ca] * B[rb][cb + 1] + A[ra][ca + 1] * B[rb + 1][cb + 1];  // C12 = A11 * B12 + A12 * B22
            C[rc + 1][cc] += A[ra + 1][ca] * B[rb][cb] + A[ra + 1][ca + 1] * B[rb + 1][cb];  // C21 = A21 * B11 + A22 * B21
            C[rc + 1][cc + 1] += A[ra + 1][ca] * B[rb][cb + 1] + A[ra + 1][ca + 1] * B[rb + 1][cb + 1];  // C22 = A21 * B12 + A22 * B22
        } else {
            n /= 2; // 分解矩阵
            squareMatrixMultiply(A, B, C, ra, ca, rb, cb, rc, cc, n);  // A11 * B11
            squareMatrixMultiply(A, B, C, ra, ca + n, rb + n, cb, rc, cc, n);  // A12 * B21
            squareMatrixMultiply(A, B, C, ra, ca, rb, cb + n, rc, cc + n, n);  // A11 * B12
            squareMatrixMultiply(A, B, C, ra, ca + n, rb + n, cb + n, rc, cc + n, n);  // A12 * B22
            squareMatrixMultiply(A, B, C, ra + n, ca, rb, cb, rc + n, cc, n);  // A21 * B11
            squareMatrixMultiply(A, B, C, ra + n, ca + n, rb + n, cb, rc + n, cc, n);  // A22 * B21
            squareMatrixMultiply(A, B, C, ra + n, ca, rb, cb + n, rc + n, cc + n, n);  // A21 * B12
            squareMatrixMultiply(A, B, C, ra + n, ca + n, rb + n, cb + n, rc + n, cc + n, n); // A22 * B22
        }
        return C;
    }

    // 2的幂次方阵乘法 --- 分治
    public int[][] matrixMultiplyDC(int[][] A, int[][] B) {
        int rowa = A.length, cola = A[0].length, rowb = B.length, colb = B[0].length;
        int t1 = rowa > cola ? rowa : cola, t2 = rowb > colb ? rowb : colb;
        int n = nextP2(t1 > t2 ? t1 : t2);
        int[][] C = new int[n][n];
        if(canMultiply(A, B)){
            if(!isSquareMatrix(A)) A = convertMatrixToStandardSquareMatrix(A, n);
            if(!isSquareMatrix(B)) B = convertMatrixToStandardSquareMatrix(B, n);
            C = squareMatrixMultiply(A, B, C, 0, 0, 0, 0, 0, 0, A.length);
            C = removeZerosInMatrix(C, rowa, colb);
        }
        return C;
    }

    // Strassen方阵乘法---辅助函数---待封装
    private int[][] squareMatrixMultiplyStrassen(int[][] A, int[][] B, int ra, int ca, int rb, int cb, int n) {
        int[][] C = new int[n][n];
        if (n == 1) {
            C[0][0] = A[ra][ca] * B[rb][cb];
            return C;
        } else {
            // step 1: 分解矩阵
            n /= 2;

            // step 2: 创建10个矩阵
            int[][] S1 = squareMatrixAddorSub(B, B, rb, cb + n, rb + n, cb + n, n, false);  // S1 = B12 - B22
            int[][] S2 = squareMatrixAddorSub(A, A, ra, ca, ra, ca + n, n, true);  // S2 = A11 + A12
            int[][] S3 = squareMatrixAddorSub(A, A, ra + n, ca, ra + n, ca + n, n, true);  // S3 = A21 + A22
            int[][] S4 = squareMatrixAddorSub(B, B, rb + n, cb, rb, cb, n, false);  // S4 = B21 - B11
            int[][] S5 = squareMatrixAddorSub(A, A, ra, ca, ra + n, ca + n, n, true);  // S5 = A11 + A22
            int[][] S6 = squareMatrixAddorSub(B, B, rb, cb, rb + n, cb + n, n, true);  // S6 = B11 + B22
            int[][] S7 = squareMatrixAddorSub(A, A, ra, ca + n, ra + n, ca + n, n, false);  // S7 = A12 - A22
            int[][] S8 = squareMatrixAddorSub(B, B, rb + n, cb, rb + n, cb + n, n, true);  // S8 = B21 + B22
            int[][] S9 = squareMatrixAddorSub(A, A, ra, ca, ra + n, ca, n, false);  // S9 = A11 - A21
            int[][] S10 = squareMatrixAddorSub(B, B, rb, cb, rb, cb + n, n, true);  // S10 = B11 + B12

            // Step 3: 计算7次乘法
            int[][] P1 = squareMatrixMultiplyStrassen(A, S1, ra, ca, 0, 0, n);  // P1 = A11 * S1
            int[][] P2 = squareMatrixMultiplyStrassen(S2, B, 0, 0, rb + n, cb + n, n);  // P2 = S2 * B22
            int[][] P3 = squareMatrixMultiplyStrassen(S3, B, 0, 0, rb, cb, n);  // P3 = S3 * B11
            int[][] P4 = squareMatrixMultiplyStrassen(A, S4, ra + n, ca + n, 0, 0, n);  // P4 = A22 * S4
            int[][] P5 = squareMatrixMultiplyStrassen(S5, S6, 0, 0, 0, 0, n);  // P5 = S5 * S6
            int[][] P6 = squareMatrixMultiplyStrassen(S7, S8, 0, 0, 0, 0, n);  // P6 = S7 * S8
            int[][] P7 = squareMatrixMultiplyStrassen(S9, S10, 0, 0, 0, 0, n);  // P7 = S9 * S10

            // Step4: 根据P1-P7计算出C
            int[][] T1 = squareMatrixAddorSub(P5, P4, 0, 0, 0, 0, n, true);  // P5 + P4
            int[][] T2 = squareMatrixAddorSub(P2, P6, 0, 0, 0, 0, n, false);  // P2 - P6
            int[][] C11 = squareMatrixAddorSub(T1, T2, 0, 0, 0, 0, n, false);  // C11 = P5 + P4 - P2 + P6
            int[][] C12 = squareMatrixAddorSub(P1, P2, 0, 0, 0, 0, n, true);  // C12 = P1 + P2
            int[][] C21 = squareMatrixAddorSub(P3, P4, 0, 0, 0, 0, n, true);  // C21 = P3 + P4
            int[][] T3 = squareMatrixAddorSub(P5, P1, 0, 0, 0, 0, n, true);  // P5 + P1
            int[][] T4 = squareMatrixAddorSub(P3, P7, 0, 0, 0, 0, n, true);  // P3 + P7
            int[][] C22 = squareMatrixAddorSub(T3, T4, 0, 0, 0, 0, n, false);  // C22 = P5 + P1 - P3 - P7

            // 合并C11、C12、C21、C22为C
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] = C11[i][j];
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C[i][j + n] = C12[i][j];
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C[i + n][j] = C21[i][j];
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C[i + n][j + n] = C22[i][j];
                }
            }

        }
        return C;
    }

    // Strassen矩阵乘法
    public int[][] matrixMultiplySS(int[][] A, int[][] B) {
        int rowa = A.length, cola = A[0].length, rowb = B.length, colb = B[0].length;
        int t1 = rowa > cola ? rowa : cola, t2 = rowb > colb ? rowb : colb;
        int n = nextP2(t1 > t2 ? t1 : t2);
        if(canMultiply(A, B)){
            if(!isSquareMatrix(A)) A = convertMatrixToStandardSquareMatrix(A, n);
            if(!isSquareMatrix(B)) B = convertMatrixToStandardSquareMatrix(B, n);
        }
        return  removeZerosInMatrix(squareMatrixMultiplyStrassen(A, B, 0, 0, 0, 0, A.length), rowa, colb);
    }
}



public class MatrixMultiplicationTest {
    public static void main(String[] args){
        System.out.println("请依次输入A和B各自的行数和列数：");
        int rowa, cola, rowb, colb;
        Scanner input = new Scanner(System.in);
        rowa = input.nextInt();
        cola = input.nextInt();
        rowb = input.nextInt();
        colb = input.nextInt();

        Matrix mm = new Matrix();
        int[][] A = mm.generateMatrix(rowa, cola);
        int[][] B = mm.generateMatrix(rowb, colb);

        System.out.println("A:");
        mm.printMatrix(A);
        System.out.println("\nB:");
        mm.printMatrix(B);

        System.out.println("A * B:");
        if(mm.canMultiply(A, B)){
            System.out.println("一般方法：");
            mm.printMatrix(mm.matrixMultiply(A, B));
            System.out.println("\n分治法：");
            mm.printMatrix(mm.matrixMultiplyDC(A, B));
            System.out.println("\nStrassen:");
            mm.printMatrix(mm.matrixMultiplySS(A, B));
        }
        else System.out.println("A和B不满足矩阵相乘的条件！");
    }
}
