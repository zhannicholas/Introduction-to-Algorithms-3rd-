package ch10;

public class DoubleLinkedList {
    private Node head;

    public DoubleLinkedList(){
        head = new Node(null);
    }

    /**
     * 插入
     * @param object    待插入元素
     */
    public void insert(Object object){
        Node node = new Node(object);
        node.next = head;
        if(head != null){
            head.prev = node;
        }
        head = node;
        node.prev = null;
    }

    /**
     * 搜索
     * @param object    搜索键
     * @return          如果匹配，返回对应的指针；否则，返回null
     */
    public Node search(Object object){
        Node node = head;
        while (node != null && node.data != object){
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
        if(node.prev != null){
            node.prev.next = node.next;
        }
        else{
            head = node.next;
        }
        if(node.next != null){
            node.next.prev = node.prev;
        }
    }

    public static void main(String[] args){
        DoubleLinkedList dll = new DoubleLinkedList();
        dll.insert('A');
        dll.insert('B');
        dll.insert('C');
        dll.insert('D');
        System.out.println(dll.search('C').data);
        dll.delete('C');
        try{
            System.out.println(dll.search('C').data);
        }catch (Exception e){
            throw new NullPointerException("null");
        }
    }
}
