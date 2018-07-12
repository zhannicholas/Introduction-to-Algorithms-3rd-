package ch07;

import auxClassForSorting.GenerateTestData;
import auxClassForSorting.Sort;

import java.util.Arrays;

class QuickSort extends Sort {
    /**********************************常规快速排序***************************************/

    /**
     * 总是使用最右端的元素A[r]将数组A[p..r]划分为A[p..q - 1]、A[q]、A[q + 1..r]
     * A[p..q - 1] <= A[q]
     * A[q + 1..r] >= A[q]
     * @param A     待划分数组
     * @param p     下界
     * @param r     上界
     * @return      q
     */
    public int Partition(int[] A, int p, int r){
        int x = A[r];  // 保存最右端元素
        int i = p - 1;  // i最终指向A[p..r]左部分的最后一个元素
        for(int j = p;j <= r - 1;j++){
            if(A[j] <= x){
                i ++;
                exchange(A, i, j);  // 将A[j]放到A[p..q - 1]中，同时一个不小于A[q]的元素被放到A[q + 1..r]中
            }
        }
        exchange(A, i + 1, r);  // 将最右端的元素放到正确位置
        return i + 1;
    }


    /**
     * 对A[p..r]进行快速排序
     * @param A
     * @param p
     * @param r
     */
    public void quickSort(int[] A, int p, int r){
        if(p < r){
            int q = Partition(A, p, r);
            quickSort(A, p, q - 1);
            quickSort(A, q + 1, r);
        }
    }

    /**
     * 使用尾递归技术的快速排序版本
     * @param A
     * @param p
     * @param r
     */
    public void quickSortWithTailRecursion(int[] A, int p, int r){
        while(p < r){
            int q = Partition(A, p, r);
            quickSortWithTailRecursion(A, p, q - 1);
            p = q + 1;
        }
    }

    /***************************************快速排序的随机化版本**************************************/

    public int randomizePartition(int[] A, int p, int r){
        int i = rand(p, r);
        exchange(A, i, r);
        return Partition(A, p, r);
    }

    public void randomizeQuickSort(int[] A, int p, int r){
        if(p < r){
            int q = randomizePartition(A, p, r);
            randomizeQuickSort(A, p, q - 1);
            randomizeQuickSort(A, q + 1, r);
        }
    }

    /*************************************Hoare版本************************************************/
    /**
     * Hoare划分——最初的划分方法
     * A[q]总在A[p..j]和A[j + 1..r]的某一个之中
     * @param A
     * @param p
     * @param r
     * @return
     */
    public int hoarePartition(int[] A, int p, int r){
        int x = A[p];
        int i = p - 1;
        int j = r + 1;
        while(true){
            while(A[--j] > x && j >= p); // 从右向左寻找第一个比x小的元素
            while(A[++i] < x && i <= r); // 从左向右寻找一个比x大的元素
            if(i < j){
                exchange(A, i, j);
            }
            else{
                return j;
            }
        }
    }

    /**
     * 使用Hoare划分方法的快速排序
     * @param A
     * @param p
     * @param r
     */
    public void hoareQuickSort(int[] A, int p, int r){
        if(p < r){
            int q = hoarePartition(A, p, r);
            hoareQuickSort(A, p, q);
            hoareQuickSort(A, q + 1, r);
        }
    }


}

class StoogeSort extends Sort{
    public void stoogeSort(int[] A, int i, int j){
        if(A[i] > A[j]){
            exchange(A, i, j);
        }
        if(i + 1 >= j){  // 不多于两个元素
            return;
        }
        int k = (j - i + 1) / 3;
        stoogeSort(A, i, j - k);  // 前2/3
        stoogeSort(A, i + k, j);  // 后2/3
        stoogeSort(A, i, j - k);  // 再次前2/3
    }
}

public class QuickSortTest {
    public static void main(String[] args){
        GenerateTestData g = new GenerateTestData();
        int[] A = g.generateTestData(1000, 20);
        int[] B = Arrays.copyOfRange(A, 0, A.length);
        int[] C = Arrays.copyOfRange(A, 0, A.length);
        int[] D = Arrays.copyOfRange(A, 0, A.length);
        int[] E = Arrays.copyOfRange(A, 0, A.length);
        //int[] A = {-39, 75, 17, 52, -76, -75, -15, 16, -3, 21};
        QuickSort q = new QuickSort();
        System.out.println("not sorted:");
        q.printArray(A);
        q.quickSort(A, 0, A.length - 1);
        System.out.println("\nquick sort:");
        q.printArray(A);

        q.randomizeQuickSort(B, 0, B.length - 1);
        System.out.println("\nrandomize quick sort:");
        q.printArray(B);

        q.hoareQuickSort(C, 0, C.length - 1);
        System.out.println("\nHoare quick sort:");
        q.printArray(C);

        StoogeSort s = new StoogeSort();
        s.stoogeSort(D, 0, D.length - 1);
        System.out.println("\nStooge sort:");
        s.printArray(D);

        q.quickSortWithTailRecursion(E, 0, E.length - 1);
        System.out.println("\nquick sort with tail recursion");
        q.printArray(E);
    }

}
