package ch12;

import java.util.Stack;

public class CommonBinaryTree extends BinaryTree{
    private Object[] preorderList;  // 前序遍历列表
    private static int count = 0;   // 用于建树

    public CommonBinaryTree(Object[] pl){
        preorderList = pl;
    }

    /**
     * 根据前序遍历列表建树
     * @param btn   根节点
     * @return      建好的树的根节点
     */
    public BinaryTreeNode buildBinaryTree(BinaryTreeNode btn){
        if(count >= preorderList.length || preorderList[count] == null){
            return null;    // 空树
        }
        btn = new BinaryTreeNode(preorderList[count]);  // 生成根节点
        count ++;
        btn.insertLchild(buildBinaryTree(btn.lchild));  // 生成左孩子
        count ++;
        btn.insertRchild(buildBinaryTree(btn.rchild));  // 生成右孩子
        return btn;
    }


    public static void main(String[] args){
        Character[] pl = new Character[]{'a', 'b', 'c', null, null, 'd', 'e', null, 'g', null, null, 'f', null, null, null};
        CommonBinaryTree bt = new CommonBinaryTree(pl);
        BinaryTreeNode root = null;
        root = bt.buildBinaryTree(root);

        System.out.println("preorder tree walk:");
        bt.preorderTreeWalk(root);
        System.out.println("\npreorder tree walk with stack:");
        bt.preorderTreeWalkWithStack(root);

        System.out.println("\ninorder tree walk:");
        bt.inorderTreeWalk(root);
        System.out.println("\ninorder tree walk with stack:");
        bt.inorderTreeWalkWithStack(root);

        System.out.println("\npostorder tree walk:");
        bt.postorderTreeWalk(root);
        System.out.println("\npostorder tree walk with stack:");
        bt.postorderTreeWalkWtithStack(root);
    }
}
