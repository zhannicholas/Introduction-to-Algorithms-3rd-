package ch10;

public class ArrayStack {
    private Object[] stack;     // 对象数组
    private int size;           // 元素个数

    /**
     * 为栈分配存储空间
     * @param len   容量
     */
    public ArrayStack(int len){
        stack = new Object[len];
    }

    /**
     * 获取栈内元素个数
     * @return  栈内元素个数
     */
    public int getSize(){
        return size;
    }

    /**
     * 获取栈的容量
     * @return  栈的容量
     */
    public int getCapacity(){
        return stack.length;
    }

    /**
     * 确保栈具有足够的容量
     */
    public void ensureCapacity(){
        if(getSize() == getCapacity()){
            Object[] newStack = new Object[size * 3 / 2 + 1];
            System.arraycopy(stack, 0, newStack, 0, getSize());
            stack = newStack;
        }
    }

    /**
     * 判空
     * @return 栈是否为空
     */
    public boolean stackEmpty(){
        return size == 0;
    }

    /**
     * 入栈
     * @param object    入栈元素
     */
    public void push(Object object){
        size ++;
        ensureCapacity();
        stack[size - 1] = object;
    }

    /**
     * 出栈
     * @return  栈顶元素
     */
    public Object pop(){
        if(stackEmpty()){
            throw new ArrayIndexOutOfBoundsException("stack underflow!");
        }
        Object object = stack[-- size];
        stack[size] = null;
        return object;
    }

    public static void main(String[] args){
        ArrayStack arrayStack = new ArrayStack(4);
        String[] data = new String[]{"A", "B", "C", "D"};
        for(int i = 0;i < data.length;i++){
            arrayStack.push(data[i]);
            System.out.println("push " + data[i] + " into stack");
        }
        while(!arrayStack.stackEmpty()){
            System.out.println("pop " + arrayStack.pop() + " from stack");
        }
    }
}
