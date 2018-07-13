package ch12;

import java.util.Stack;

public class BinaryTree {
    /**
     * 前序遍历---递归版
     * @param btn   根节点
     */
    public void preorderTreeWalk(BinaryTreeNode btn){
        if(btn != null){
            System.out.printf(btn.key + "\t");
            preorderTreeWalk(btn.lchild);
            preorderTreeWalk(btn.rchild);
        }
    }

    /**
     * 前序遍历---使用栈的非递归版
     * @param btn
     */
    public void preorderTreeWalkWithStack(BinaryTreeNode btn){
        Stack<BinaryTreeNode> stack = new Stack<>();
        BinaryTreeNode node = null;
        if(btn != null){
            stack.push(btn);
            while (!stack.isEmpty()){
            node = stack.pop();
                if(node != null){
                    System.out.printf(node.key + "\t");
                    if(node.rchild != null){
                        stack.push(node.rchild);  // 右孩子入栈
                    }
                    if(node.lchild != null){
                    stack.push(node.lchild);  // 左孩子入栈
                    }
                }

            }
        }
    }

    /**
     * 中序遍历---递归版
     * @param btn   根节点
     */
    public void inorderTreeWalk(BinaryTreeNode btn){
        if(btn != null){
            inorderTreeWalk(btn.lchild);
            System.out.printf(btn.key + "\t");
            inorderTreeWalk(btn.rchild);
        }
    }

    /**
     * 中序遍历---使用栈的非递归版
     * @param btn
     */
    public void inorderTreeWalkWithStack(BinaryTreeNode btn){
        Stack<BinaryTreeNode> stack = new Stack<>();
        BinaryTreeNode node = btn;
        while(node != null || !stack.isEmpty()){    //node非空或栈非空
            if(node != null){   // node非空
                stack.push(node);
                // 将左孩子入栈，直到抵达最左叶子节点
                node = node.lchild;
            }
            else{   // node为空栈非空
                node = stack.pop();
                if(node != null){
                    System.out.printf(node.key + "\t");
                    node = node.rchild;     // 进入右子树
                }
            }
        }
    }

    /**
     * 后序遍历---递归版
     * @param btn   根节点
     */
    public void postorderTreeWalk(BinaryTreeNode btn){
        if(btn != null){
            postorderTreeWalk(btn.lchild);
            postorderTreeWalk(btn.rchild);
            System.out.printf(btn.key + "\t");
        }
    }

    /**
     * 后序遍历---使用栈的非递归版
     * @param btn   根节点
     */
    public void postorderTreeWalkWtithStack(BinaryTreeNode btn) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        BinaryTreeNode node = btn;
        while (btn != null) {
            for (; btn.lchild != null; btn = btn.lchild) {
                stack.push(btn);  // 左子树入栈
            }
            while (btn != null && (btn.rchild == null || btn.rchild == node)) {   // 当前节点无右孩子或者右孩子已经访问过
                System.out.printf(btn.key + "\t");  // 访问当前节点
                node = btn;     // 记录上一个已输出节点
                if (stack.isEmpty()) {
                    return;
                }
                btn = stack.pop();
            }
            stack.push(btn);
            btn = btn.rchild;
        }
    }
}
