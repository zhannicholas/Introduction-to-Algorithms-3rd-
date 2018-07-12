package ch02;
import auxClassForSorting.GenerateTestData;

import java.util.*;

class Sort{
    // 插入排序
    public int[] insertionSort(int left, int right, int[] data){
        for(int i = left + 1;i <= right;i++){
            int key = data[i];
            int j = i - 1;
            while(j >= left && data[j] > key){
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = key;
        }
        return data;
    }

}


public class SortTest{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.printf("输入随机数个数：");
        try{
            int n = input.nextInt();
            GenerateTestData g = new GenerateTestData();
            int[] array = g.generateTestData(10000, n);
//            for(int i = 0;i < n;i++) System.out.printf("%-6d", array[i]);
//            System.out.println();
            Sort sort = new Sort();
            int[] ans = sort.insertionSort(0, n - 1, array);
            for(int i = 0, j = 0;i < n;i++){
                System.out.printf("%-6d", ans[i]);
                j++;
                if(j % 10 == 0) System.out.println();
            }
        }
        finally{
            input.close();
        }
    }
}