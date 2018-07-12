package auxClassForSorting;

import java.util.Random;

public class Sort {
    /**
     * @param a     下界
     * @param b     上界
     * @return      [a, b]之间的一个随机整数
     */
    public int rand(int a, int b){
        Random random = new Random();
        int c = b - a;
        return random.nextInt(c + 1) + a;
    }

    /**
     * 交换A[i]和A[j]的值
     * @param A
     * @param i
     * @param j
     */
    public void exchange(int[] A, int i, int j){
        int t = A[i];
        A[i] = A[j];
        A[j] = t;
    }

    /**
     * 打印数组A
     * @param A
     */
    public void printArray(int[] A){
        int n = A.length;
        for(int i = 0;i < n;i++){
            System.out.printf("%-6d", A[i]);
            if((i + 1) % 10 == 0) System.out.println();
        }
    }
}

