package ch10;

public class Node {
    public Object data;    // 数据域
    public Node prev;      // 指向前驱元素
    public Node next;      // 指向后继元素

    public Node(Object object){
        this.data = object;
        this.prev = null;
        this.next = null;
    }
}
