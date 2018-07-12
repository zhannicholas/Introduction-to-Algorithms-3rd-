package ch06;
import auxClassForSorting.GenerateTestData;
import auxClassForSorting.Sort;

class MaxHeap extends Sort{

    /**
     * 使A[i]为根的子树成为最大堆
     * 堆的大小不大于存放堆数组的大小
     * @param A     表示堆的数组
     * @param i     下标
     * @param lastHeapIndex     堆中最后一个元素在A中的下标
     */
    public void maxHeapify(int[] A, int i, int lastHeapIndex){
        int l = 2 * i + 1;  // 左孩子
        int r = 2 * i + 2;  // 右孩子
        int largest = i;
        if(l <= lastHeapIndex && A[l] > A[i]){  // 左孩子更大
            largest = l;
        }
        if(r <= lastHeapIndex && A[r] > A[largest]){  // 右孩子更大
            largest = r;
        }
        if(largest != i){  // 根不都大于两个孩子
            exchange(A, i, largest);  // 交换根和大孩子
            maxHeapify(A, largest, lastHeapIndex);  // 对该子树递归调用maxHeapify
        }
    }

    /**
     * 建最大堆
     * @param A
     */
    public void buildMaxHeap(int[] A){
        int lastHeapIndex = A.length - 1;
        for(int i = lastHeapIndex / 2;i >= 0;i--){
            maxHeapify(A, i, lastHeapIndex);
        }

    }
}

class HeapSort extends MaxHeap{


    /**
     * 堆排序
     * 建最大堆 -> 取出根节点放到数组末尾 -> 调整
     * @param A 待排序数组
     */
    public void heapSort(int[] A){
        buildMaxHeap(A);
        int lenA = A.length;
        int lastHeapIndex = lenA - 1;
        for(int i = lenA - 1;i > 0;i--){
            exchange(A, 0, i);  // 把堆顶元素移出堆
            lastHeapIndex --;  // “去掉”根节点
            maxHeapify(A, 0, lastHeapIndex);
        }
    }

}

class MaxPriorityQueue extends MaxHeap{
    /**
     * 返回A中具有最大关键字的元素
     * @param A
     * @return
     */
    public int heapMaximum(int[] A){
        return A[0];
    }

    /**
     * 去掉并返回A中具有最大关键字的原色
     * @param A
     * @param lastHeapIndex     堆中最后一个元素在数组中的位置
     * @return
     */
    public int heapExtractMax(int[] A, int lastHeapIndex){
        if(lastHeapIndex < 0){
            System.out.println("heap underflow!");
            return -1;
        }
        int maxElement = A[0];
        A[0] = A[lastHeapIndex];
        lastHeapIndex --;
        maxHeapify(A, 0, lastHeapIndex);  // 调整
        return  maxElement;
    }

    /**
     * 将元素i的键值增加到key， key值不能小于i的键值
     * @param A
     * @param i
     * @param key
     */
    public void heapIncreaseKey(int[] A, int i, int key){
        if(key < A[i]){
            System.out.println("new key is smaller than current key!");
            return;
        }
        A[i] = key;
        while(i > 0 && A[i / 2] < A[i]){  // 当元素的键值小于父节点的键值时，最大堆性质成立
            exchange(A, i / 2, i);
            i = i / 2;
        }
    }

    /**
     * 向最大堆中插入一个元素
     * @param A     存放堆的数组
     * @param key    插入的元素
     * @param lastHeapIndex     堆中最后一个元素在数组中的下标
     */
    public void maxHeapInsert(int[] A, int key, int lastHeapIndex){
        lastHeapIndex ++;
        A[lastHeapIndex] = Integer.MIN_VALUE;
        heapIncreaseKey(A, lastHeapIndex, key);
    }
}

public class HeapSortTest {
    public static void main(String[] args){
        GenerateTestData g = new GenerateTestData();
        int A[] = g.generateTestData(100, 30);
        System.out.println("A, not sorted:");
        HeapSort h = new HeapSort();
        h.printArray(A);
        h.heapSort(A);
        System.out.println("A, sorted:");
        h.printArray(A);
    }
}
