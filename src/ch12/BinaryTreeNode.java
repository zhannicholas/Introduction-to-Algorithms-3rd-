package ch12;

/**
 * 二叉树节点类
 */
class BinaryTreeNode {
    Object key = null;   // 键
    BinaryTreeNode lchild = null;    // 左孩子
    BinaryTreeNode rchild = null;    // 右孩子
    BinaryTreeNode parent = null;    // 父节点

    public BinaryTreeNode(Object keyValue){
        key = keyValue;
    }

    /**
     * 向树中插入左孩子
     * @param lc    左孩子
     * @return      父节点
     */
    public BinaryTreeNode insertLchild(BinaryTreeNode lc){
        if(lc != null){
            lchild = lc;
            lc.parent = this;

        }
        return this;
    }

    /**
     * 向树中插入右孩子
     * @param rc    右孩子
     * @return      父节点
     */
    public BinaryTreeNode insertRchild(BinaryTreeNode rc){
        if(rc != null){
            rchild = rc;
            rc.parent = this;
        }
        return this;
    }
}
