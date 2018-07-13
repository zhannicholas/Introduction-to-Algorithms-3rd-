package ch10;

/**
 * 具有哨兵的双向链表
 * 哨兵元素介于头尾之间，可以简化边界判断条件
 */
public class DoubleLinkedListWithSentinel {
    private Node nil;     // 哨兵

    public DoubleLinkedListWithSentinel(){
        nil = new Node(null);
        nil.prev = nil;
        nil.next = nil;
    }

    /**
     * 在链表前端插入
     * @param object    待插入元素
     */
    public void insert(Object object){
        Node node = new Node(object);
        node.next = nil.next;
        nil.next.prev = node;
        nil.next = node;
        node.prev = nil;
    }

    /**
     * 搜索
     * @param object    搜索键
     * @return          如果链表中有该元素，则返回指向该元素的指针，否者返回nil。
     */
    public Node search(Object object){
        Node node = nil.next;
        while(node != nil && node.data != object){
            node = node.next;
        }
        return node;
    }

    /**
     * 删除
     * @param object    待删除元素
     */
    public void delete(Object object){
        Node node = search(object);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void main(String[] args){
        DoubleLinkedListWithSentinel dlls = new DoubleLinkedListWithSentinel();
        dlls.insert('A');
        dlls.insert('B');
        dlls.insert('C');
        dlls.insert('D');
        System.out.println(dlls.search('C').data);
        dlls.delete('C');
        System.out.println(dlls.search('C').data);
    }
}
