package ch04;

import java.util.Arrays;
import java.util.Scanner;

class MaxSubarraySum{
    public int sumArray(int left, int right, int[] data){  // 数组求和
        int sum = 0;
        for(int i = left;i <= right;i++) sum += data[i];
        return sum;
    }

    public void printArray( int[] array){  // 打印数组
        for(int i = 0, j = 0;i <= array.length - 1;i++){
                System.out.printf("%-6d", array[i]);
                j++;
                if(j % 10 == 0) System.out.println();
        }
    }
}

// 最大子数组问题--分治法
class MaxSubarraySumDC extends MaxSubarraySum{
    public int[] findMaxCrossingArray(int left, int mid, int right, int[] data){
        int maxLeftSum = Integer.MIN_VALUE;
        int sum = 0;
        int i, j, maxLeft = mid, maxRight = mid + 1;
        for(i = mid;i >= left;i--){  // [left, mid]的maxSum
            sum += data[i];
            if(sum > maxLeftSum){
                maxLeftSum = sum;
                maxLeft = i;
            }
        }
        sum = 0;
        int maxRightSum = Integer.MIN_VALUE;
        for(j = mid + 1;j <= right;j++){  // [mid + 1, right] 的maxSum
            sum += data[j];
            if(sum > maxRightSum){
                maxRightSum = sum;
                maxRight = j;
            }
        }
        return Arrays.copyOfRange(data, maxLeft, maxRight + 1);
    }


    public int[] findMaxSubarray(int left, int right, int[] data){  // 寻找最大子数组
        if(left == right) return Arrays.copyOfRange(data, left, right + 1);
        else{
            int mid = (left + right) / 2;
            int[] maxLeftArray = findMaxSubarray(left, mid, data);
            int[] maxRightArray = findMaxSubarray(mid + 1, right, data);
            int[] maxCrossingArray = findMaxCrossingArray(left, mid, right, data);

            int sumLeft = sumArray(0, maxLeftArray.length - 1, maxLeftArray);
            int sumRight = sumArray(0, maxRightArray.length - 1, maxRightArray);
            int sumCrossing = sumArray(0, maxCrossingArray.length - 1, maxCrossingArray);

            if(sumLeft >= sumRight && sumLeft >= sumCrossing) return maxLeftArray;
            else if(sumRight >= sumLeft && sumRight >= sumCrossing) return maxRightArray;
            else return maxCrossingArray;
        }
    }
}

// 最大子数组问题--暴力法
class MaxSubarraySumBF extends MaxSubarraySum{
    public int[] findMaxSubarray(int left, int right, int[] data){
        int i, j, maxLeft = left, maxRight = left, sum = Integer.MIN_VALUE, tempSum;
        for(i = left;i <= right;i++){
            tempSum = 0;  // data[i...j]的和
            for(j = i;j <= right;j++){
                tempSum += data[j];
                if(tempSum > sum){
                    sum = tempSum;
                    maxLeft = i;
                    maxRight = j;
                }
            }
        }
        return Arrays.copyOfRange(data, maxLeft, maxRight + 1);
    }
}

// 最大子数组问题--动态规划
class MaxSubarraySumDP extends MaxSubarraySum{
    // 若已知A[1...j]的最大子数组，基于如下性质可以得到A[1...j + 1]的最大子数组：A[1...j + 1]的最大子数组要么是A[1...j]的最大子数组，
    // 要么是某个子数组A[i...j + 1] (1 =< i <= j + 1)。
    public int[] findMaxSubarray(int left, int right, int[] data){
        int maxLeft = left, maxRight = left, sum = Integer.MIN_VALUE, temp = left;
        int[] tempSum = new int[right - left + 1];
        tempSum[0] = data[left];
        for(int i = left + 1;i <= right;i++){
            if(tempSum[i - left - 1] < 0){  // 前面的最大和小于0，直接丢弃, 从下一个开始考虑
                tempSum[i] = data[i];
                temp = i;
            }
            else{
                tempSum[i - left] = tempSum[i - left - 1] + data[i];
            }
            if(tempSum[i - left] > sum){
                sum = tempSum[i - left];
                maxRight = i;
                maxLeft = temp;
            }
        }
        return Arrays.copyOfRange(data, maxLeft, maxRight + 1);

    }
}

public class MaxSubarraySumTest{  // 测试
    public static void main(String[] args){
        System.out.println("输入随机数的个数：");
        Scanner input = new Scanner(System.in);
        try{
            int n = input.nextInt();
            GenerateTestData g = new GenerateTestData();
            int[] array = g.generateTestData(10000, n);
             MaxSubarraySumDC mssdc = new MaxSubarraySumDC();
             MaxSubarraySumBF mssbf = new MaxSubarraySumBF();
             MaxSubarraySumDP mssdp = new MaxSubarraySumDP();

             System.out.println("original array:");
             mssdc.printArray(array);

             int[] result1 = mssdc.findMaxSubarray(0, n - 1, array);
             int[] result2 = mssbf.findMaxSubarray(0, n - 1, array);
             int[] result3 = mssdp.findMaxSubarray(0, n - 1, array);

             System.out.println("\nmaxSubarray1:");
             mssdc.printArray(result1);
             System.out.println("\nsum1 = " + mssdc.sumArray(0, result1.length - 1, result1));

             System.out.println("\nmaxSubarray2:");
             mssbf.printArray(result2);
             System.out.println("\nsum2 = " + mssbf.sumArray(0, result2.length - 1, result2));

             System.out.println("\nmaxSubarray3: ");
             mssdp.printArray(result3);
             System.out.println("\nsum3 = " + mssdp.sumArray(0, result3.length - 1, result3));
        }
       finally {
            input.close();
        }
    }
}


