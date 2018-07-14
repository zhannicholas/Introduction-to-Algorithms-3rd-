package ch12;

public class BinarySearchTree extends BinaryTree {
    private BinaryTreeNode<Integer> root = new BinaryTreeNode<>(null);

    /**
     * 插入
     * @param newNode   待插入节点
     */
    private void insert(BinaryTreeNode<Integer> newNode){
        BinaryTreeNode<Integer> y = new BinaryTreeNode<>(null);
        BinaryTreeNode<Integer> x = root;
        while(x.key != null){
            y = x;
            if(newNode.key < x.key && x.lchild != null){
                x = x.lchild;   // 进入左子树
            }
            else if(x.rchild != null){
                x = x.rchild;   // 进入右子树
            }
            else{
                x = new BinaryTreeNode<>(null);     // 抵达叶子节点，跳出
            }
        }
        newNode.parent = y;
        if(y.key == null){  // 空树
            root = newNode;
        }
        else if(newNode.key < y.key){
            y.insertLchild(newNode);    // 插入为左孩子
        }
        else{
            y.insertRchild(newNode);    // 插入为右孩子
        }
    }

    /**
     * 建树
     * @param nl    节点插入顺序列表
     */
    public void buildBinarySearchTree(Integer[] nl){
        int n = nl.length;
        for(int i = 0;i < n;i++){
            insert(new BinaryTreeNode<>(nl[i]));
        }
    }

    /**
     * 递归查找
     * @param rt    根节点
     * @param target    查找键
     * @return  如果成功，返回目标节点；否则，返回null
     */
    public BinaryTreeNode<Integer> search(BinaryTreeNode<Integer> rt, Integer target){
        if(rt == null){
            return new BinaryTreeNode<>(null);
        }
        if(target == rt.key){
            return rt;
        }
        if(target < rt.key){
            return search(rt.lchild, target);
        }
        else{
            return search(rt.rchild, target);
        }
    }

    /**
     * 迭代查找
     * @param rt    根节点
     * @param target    查找键
     * @return  如果成功，返回目标节点；否则，返回null
     */
    public BinaryTreeNode<Integer> iterativeSearch(BinaryTreeNode<Integer> rt, Integer target){
        while(rt != null && target != rt.key){
            if(target < rt.key){
                rt = rt.lchild;
            }
            else{
                rt = rt.rchild;
            }
        }
        if(rt == null){
            return new BinaryTreeNode<>(null);
        }
        else{
            return rt;
        }
    }

    /**
     * 查找最小关键字元素
     * @return  具有最小关键字的节点
     */
    public BinaryTreeNode<Integer> minimumNode(BinaryTreeNode rt){
        while(rt.lchild != null){     // 进到最左叶节点
            rt = rt.lchild;
        }
        if(rt == null){
            return new BinaryTreeNode<>(null);
        }
        else{
            return rt;
        }
    }

    /**
     * 查找最大关键字元素
     * @return  具有最大关键字的节点
     */
    public BinaryTreeNode<Integer> maximumNode(BinaryTreeNode<Integer> rt){
        while(rt.rchild != null){     // 进到最右叶节点
            rt = rt.rchild;
        }
        if(root == null){
            return new BinaryTreeNode<>(null);
        }
        else{
            return rt;
        }
    }

    /**
     * 查找node的在中序遍历下的后继节点
     * @param node      node
     * @return          node在中序遍历下的后继节点
     */
    public BinaryTreeNode<Integer> successor(BinaryTreeNode<Integer> node){
        if(node.rchild != null){    // node的右子树不为空，后继为右子树的最左叶节点
            return minimumNode(node.rchild);
        }
        // node的右子树为空且node有后继，则pnode是node的最低祖先节点，并且pnode的左儿子也是node的祖先。
        BinaryTreeNode pnode = node.parent;
        while(pnode != null && node == pnode.rchild){
            node = pnode;
            pnode = pnode.parent;
        }
        if(pnode == null){
            return new BinaryTreeNode<>(null);
        }
        else{
            return pnode;
        }
    }

    /**
     * 查找node在中序遍历下的前驱节点
     * @param node  node
     * @return      node在中序遍历下的前驱节点
     */
    public BinaryTreeNode<Integer> predecessor(BinaryTreeNode<Integer> node){
        if(node.lchild != null){    // node左子树不为空，前驱为左子树的最右叶节点
            return maximumNode(node.lchild);
        }
        // node的左子树为空，则pnode是node的最低最先节点，并且pnode的右儿子也是node的祖先
        // 和过程successor对称
        BinaryTreeNode<Integer> pnode = node.parent;
        while (pnode != null && node == pnode.lchild){
            node = pnode;
            pnode = pnode.parent;
        }
        if(pnode == null){
            return new BinaryTreeNode<>(null);
        }
        else{
            return pnode;
        }

    }

    /**
     * 更新oldNode父节点的指针，供删除节点函数调用
     * @param oldNode   待删除节点
     * @param newChild     新孩子
     */
    private void updateParent(BinaryTreeNode<Integer> oldNode, BinaryTreeNode<Integer> newChild){
        if(oldNode.parent != null){     // 非根节点
            if(oldNode.parent.lchild == oldNode){
                oldNode.parent.lchild = newChild;
            }
            else{   // 根节点
                oldNode.parent.rchild = newChild;
            }
        }
        else{
            root = newChild;
        }
    }

    /**
     * 删除节点
     * 1. 如果oldNode没有孩子，则修改其父节点，是null成为其孩子；
     * 2. 如果oldNode只有一个孩子， 则通过在oldNode的父节点和oldNode的孩子之间建立链接来删除oldNode；
     * 3. 如果oldNode有两个孩子，则先删除它的后继b，再用b代替oldNode;根据二叉搜索树的性质，后继b没有左孩子，只可能有右孩子
     * @param keyValue      欲删除的键值
     * @return              操作结果
     */
    public boolean delete(Integer keyValue){
        BinaryTreeNode<Integer> rt = search(root, keyValue);
        if(rt == null){
            return false;     // 空树
        }
        if(rt.lchild == null && rt.rchild == null){     // 没有孩子
            updateParent(rt, null);
            rt.deleteNode();
        }
        else if(rt.lchild != null && rt.rchild == null){    // 只有左孩子
            updateParent(rt, rt.lchild);
            rt.deleteNode();
        }
        else if(rt.lchild == null && rt.rchild != null){    // 只有右孩子
            updateParent(rt, rt.rchild);
            rt.deleteNode();
        }
        else{   // 有两个孩子
            BinaryTreeNode<Integer> successor = successor(rt);   // 找到后继
            delete(successor.key);      // 删除后继
            rt.key = successor.key;     // 用后继的内容代替待删除节点的内容
        }

        return true;
    }



    public static void main(String[] args){
        Integer[] nl = {12, 5, 2, 9, 18, 15, 13, 17, 19};
        BinarySearchTree bst = new BinarySearchTree();
        bst.buildBinarySearchTree(nl);

        System.out.println("preorder tree walk:");
        bst.preorderTreeWalk(bst.root);
        System.out.println("\ninorder tree walk:");
        bst.inorderTreeWalk(bst.root);
        System.out.println("\npostorder tree walk:");
        bst.postorderTreeWalk(bst.root);

        System.out.println("\nsearching for 18: " + bst.search(bst.root, 18).key);
        System.out.println("iterative searching for 122: " + bst.iterativeSearch(bst.root, 122).key);

        System.out.println("minimum: " + bst.minimumNode(bst.root).key);
        System.out.println("maximum: " + bst.maximumNode(bst.root).key);

        System.out.println("successor of 9: " + bst.successor(bst.search(bst.root, 9)).key);
        System.out.println("predecessor of 13: " + bst.predecessor(bst.search(bst.root, 13)).key);

        System.out.println("delete 12: " + bst.delete(12));
        System.out.println("preorder tree walk:");
        bst.preorderTreeWalk(bst.root);
    }
}
