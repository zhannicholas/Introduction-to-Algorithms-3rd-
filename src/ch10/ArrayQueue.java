package ch10;

/**
 * 用数组A[0..n](实际上A可以容纳n+1个元素)来实现最多有n个元素的环形队列
 */
public class ArrayQueue {
    private Object[] arrayQueue;    // 对象数组
    private int capacity;           // 容量
    private int head;               // 队首
    private int tail;               // 队尾

    /**
     * 为队列分配空间，初始化head和tail
     * @param len    容量
     */
    public ArrayQueue(int len){
        arrayQueue = new Object[len];
        head = 0;
        tail = 0;
    }

    /**
     * 判空
     * @return      队列是否为空
     */
    public boolean isEmpty(){
        return head == tail;
    }

    /**
     * 判满
     * @return      队列是否已满
     */
    public boolean isFull(){
        return head == tail + 1;
    }

    /**
     * 入队
     * @param object    入队元素
     */
    public void enqueue(Object object){
        if(isFull()){
            throw new ArrayIndexOutOfBoundsException("queue overflow!");
        }
        arrayQueue[tail] = object;
        if(tail == arrayQueue.length - 1){
            tail = 0;
        }
        else{
            tail ++;
        }
    }

    /**
     * 出队
     * @return      出队元素
     */
    public Object dequeue(){
        if(isEmpty()){
            throw new ArrayIndexOutOfBoundsException("queue underflow!");
        }
        Object object = arrayQueue[head];
        if(head == arrayQueue.length - 1){
            head = 0;
        }
        else{
            head ++;
        }
        return object;
    }

    public static void main(String[] args){
        ArrayQueue aq = new ArrayQueue(5);
        String[] data = new String[]{"A", "B", "C", "D"};
        for(int i = 0;i < data.length;i++){
            aq.enqueue(data[i]);
            System.out.println("enqueue " + data[i] + ".");
        }
        while(!aq.isEmpty()){
            System.out.println("dequeue " + aq.dequeue() + ".");
        }
    }
}
