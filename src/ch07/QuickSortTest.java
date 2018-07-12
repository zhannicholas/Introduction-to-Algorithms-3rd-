package ch07;

import auxClassForSorting.GenerateTestData;
import auxClassForSorting.Sort;

import java.util.Arrays;

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
