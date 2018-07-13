package ch12;

public class BinarySearchTree extends BinaryTree {
    private Object[] nodeList;
    private static int count = 0;


    public BinaryTreeNode buildBinarySearchTree(BinaryTreeNode btn){
        if(count >= nodeList.length || nodeList[count] == null){
            return null;
        }
        return btn;
    }
}
