package ch05;
import auxClassForSorting.GenerateTestData;
import auxClassForSorting.Sort;

class PermutingArraysRandomly extends Sort {
    /**
     * 根据优先级对数组A进行选择排序
     * @param A     待排序数组
     * @param P     优先级数组
     * @return      根据优先级排序后的数组
     */
    public int[] selectSort(int[] A, int[] P){
        int n = A.length;
        for(int i = 0;i < n;i++){
            int minp = i;
            for(int j = i + 1;j < n;j++){  // 值小的优先级高
                if(P[j] < P[minp]){
                    minp = j;
                }
            }
            exchange(A, i, minp);
            exchange(P, i, minp);
        }
        return A;
    }

    /**
     * 为数组的每个元素A[i]分配一个随机的优先级P[i]，然后根据优先级对数组A中的元素进行排序。
     * @param A
     * @return 按优先级排序后的数组，即随机排列后的数组
     */
    public int[] permuteBySorting(int[] A){
        int n = A.length;
        int[] P = new int[n];  // 优先级数组
        for(int i = 0;i < n;i++){
            P[i] = rand(0, n * n * n + 1);  // 使P[i]尽可能唯一
        }
        System.out.println("\nP:");
        printArray(P);
        selectSort(A, P);  // 根据优先级排序A
        System.out.println("\nsorted:");
        printArray(A);
        return A;
    }

    /**
     * 原地排列给定数列
     * @param A
     * @return
     */
    public int[] randomizeInPlace(int[] A){
        int n = A.length;
        for(int i = 0;i < n;i++){
            exchange(A, i, rand(i, n - 1));
        }
        return A;
    }
}

public class PermutingArraysRandomlyTest {
    public static void main(String[] args){
        GenerateTestData g = new GenerateTestData();
        int[] testData = g.generateTestData(100, 10);
        PermutingArraysRandomly p = new PermutingArraysRandomly();
        System.out.println("testData:");
        p.printArray(testData);
        p.permuteBySorting(testData);

        System.out.println("randomize in place:");
        p.printArray(p.randomizeInPlace(testData));
    }
}
