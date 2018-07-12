package ch09;

import auxClassForSorting.GenerateTestData;
import ch07.QuickSort;

import java.util.Arrays;

public class RandomizeSelect {
    QuickSort quickSort = new QuickSort();

    /**
     * 基于划分的随机选择——找出第i小的元素
     * @param A     数组
     * @param p     下界
     * @param r     上界
     * @param i     i
     * @return      第i小的元素值
     */
    public int randomizeSelect(int[] A, int p, int r, int i){
        if(p == r){
            return A[p];
        }
        int q = quickSort.randomizePartition(A, p, r);  // 随机划分
        int k = q - p + 1;
        if(i == k){  // 结果刚好在划分点
            return A[q];
        }
        else if(i < k){
            return randomizeSelect(A, p, q - 1, i);  // 结果在划分点左边
        }
        else{
            return randomizeSelect(A, q + 1, r, i - k);  // 结果在划分点右边
        }
    }

    // randomizeSelect的迭代版本
    public int randomizeSelectWithIteration(int[] A, int p, int r, int i){
        if(p == r){
            return A[p];
        }
        int q = quickSort.randomizePartition(A, p, r);
        while(i != q + 1){
                if(q + 1 > i){
                    q = quickSort.randomizePartition(A, p, q - 1);
                }
            else{
                q = quickSort.randomizePartition(A, q + 1, r);
            }
        }
        return A[q];
    }

    public static void main(String[] args){
        GenerateTestData g = new GenerateTestData();
        int[] A = g.generateTestData(100, 30);
        int[] B = Arrays.copyOfRange(A, 0, A.length);
        QuickSort quickSort = new QuickSort();
        System.out.println("not sorted:");
        quickSort.printArray(A);
        quickSort.quickSort(A, 0, A.length - 1);
        System.out.println("\nquick sort:");
        quickSort.printArray(A);

        System.out.println("\nthe 5th number:");
        RandomizeSelect r = new RandomizeSelect();
        System.out.println(r.randomizeSelectWithIteration(B, 0, B.length - 1, 5));

    }
}
