package ch12;

import javax.swing.text.Position;

/**
 * 二叉树节点类
 */
class BinaryTreeNode<T> {
    T key = null;   // 键
    BinaryTreeNode<T> lchild = null;    // 左孩子
    BinaryTreeNode<T> rchild = null;    // 右孩子
    BinaryTreeNode<T> parent = null;    // 父节点

    public BinaryTreeNode(T keyValue){
        key = keyValue;
    }

    /**
     * 向树中插入左孩子
     * @param lc    左孩子
     * @return      父节点
     */
    public BinaryTreeNode<T> insertLchild(BinaryTreeNode<T> lc){
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
    public BinaryTreeNode<T> insertRchild(BinaryTreeNode<T> rc){
        if(rc != null){
            rchild = rc;
            rc.parent = this;
        }
        return this;
    }

    public void deleteNode(){
        lchild = null;
        rchild = null;
        parent = null;
    }
}
