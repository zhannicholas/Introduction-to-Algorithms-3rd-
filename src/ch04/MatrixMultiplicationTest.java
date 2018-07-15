package ch04;
import java.util.Scanner;

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
