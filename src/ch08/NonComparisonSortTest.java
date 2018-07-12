package ch08;

import auxClassForSorting.GenerateTestData;
import auxClassForSorting.Sort;

class CountingSort extends Sort{
    /**
     * 计数排序--在没有相同元素的情况下，如果比A[i]小的元素有k个,那么A[i]在排序之后应该处于第k+1个位置
     * @param A     待排序数组
     * @param B     存放排序结果
     * @param k     A[i]的取值范围为[0,k];
     */
    public void countingSort(int[] A, int[] B, int k){
        int lenA = A.length;
        int[] C = new int[k + 1];  // C[i]存放小于等于i的元素的个数
        for(int i = 0;i <= k;i++){
            C[i] = 0;
        }
        for(int j = 0;j < lenA;j++){
            C[A[j]] ++;  // 此时C[i]包含的是等于i的元素的个数
        }
        for(int i = 1;i <= k;i++){
            C[i] += C[i - 1];  // 此时C[i]包含的是不大于i的元素的个数
        }
        for(int j = lenA - 1;j >= 0;j--) {
            C[A[j]]--;  // 处理相同元素的情况
            B[C[A[j]]] = A[j];  // 放入结果数组
        }
    }
}


public class NonComparisonSortTest {
    public static void main(String[] args) {
        GenerateTestData g = new GenerateTestData();
        int[] A = g.generatePositiveTestData(100, 20);
        CountingSort c = new CountingSort();
        System.out.println("not sorted:");
        c.printArray(A);
        System.out.println("\ncounting sort:");
        int[] B = new int[A.length];
        c.countingSort(A, B, 100);
        c.printArray(B);
    }
}