package dataStructure.tree;

import java.nio.BufferUnderflowException;
/*
    Comparable是一个interface，其中声明了compareTo方法，任何实现Comparable接口
    的类都需要包含compareTo方法。
    compareTo方法的参数必须是一个 Object 对象，并且该方法返回一个整型数值
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    // 二叉树结点定义
    private static class BinaryNode<AnyType>{
        BinaryNode(AnyType theElement){
            this(theElement,null,null);
        }
        BinaryNode(AnyType theElement,BinaryNode<AnyType> lt,BinaryNode<AnyType> rt){
            element=theElement;
            left=lt;
            right=rt;
        }
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
    }
    private BinaryNode<AnyType> root; // 二叉树根节点

    // 初始化，根节点设为null
    public BinarySearchTree(){
        root=null;
    }
    // 置空，根节点置为null
    public void makeEmpty(){
        root=null;
    }
    // 判空，判断根节点是否等于null
    public boolean isEmpty(){
        return root==null;
    }
    // 判断该二叉搜索树中是否包含x
    public boolean contains(AnyType x){
        return contains(x,root);
    }
    // 找到二叉搜索树的最小值
    public AnyType findMin(){
        if(isEmpty()) throw new BufferUnderflowException();
        return findMin(root).element;
    }
    // 找到二叉搜索树的最大值
    public AnyType findMax(){
        if(isEmpty()) throw new BufferUnderflowException();
        return findMax(root).element;
    }
    // 打印该二叉树
    public void printTree(){
        if(isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }
    // 判断二叉树t中是否包含x
    private boolean contains(AnyType x,BinaryNode<AnyType> t){
        if(t==null) return false;
        int compareResult =x.compareTo(t.element);
        if(compareResult<0)
            return contains(x,t.left);
        else if(compareResult>0)
            return contains(x,t.right);
        else
            return true;
    }
    // 查找最小值的递归实现
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t){
        if(t==null)
            return null;
        else if(t.left==null)
            return t;
        return findMin(t.left);
    }
    // 查找最大值的非递归实现
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t){
        // 在这个方法体内，t只是root的拷贝，t所指向的内容不断被改变，
        // 但root指向的内容从未改变，因为root从未被传进来。
        if(t!=null)
            while(t.right!=null)
                t=t.right;
        return t;
    }
    // 插入元素
    private BinaryNode<AnyType> insert(AnyType x,BinaryNode<AnyType> t){
        if(t==null)
            return new BinaryNode<>(x,null,null);
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            t.left=insert(x,t.left);
        else if(compareResult>0)
            t.right=insert(x,t.right);
        else
            ;  //这代表插入元素与树中某一元素相等
        return t;
    }
    // 删除元素
    private BinaryNode<AnyType> remove(AnyType x,BinaryNode<AnyType> t){
        if(t==null)
            return t;
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            t.left=remove(x,t.left);
        else if(compareResult>0)
            t.right=remove(x,t.right);
        else if(t.left!=null && t.right!=null){
            // 如果t只有左右子树，在右子树中找到最小元素，填入当前结点，
            // 然后在右子树中删除该最小元素
            t.element=findMin(t.right).element;
            t.right=remove(t.element,t.right);
        }
        else
            // 如果t只有左子树，执行赋值操作t=t.left即可
            // 如果t只有右子树，执行赋值操作t=t.right即可
            // 如果t没有子树，说明t为叶结点，直接删掉即可。t=null
            t=(t.left!=null) ? t.left : t.right;
        return t;
    }
    // 打印二叉树t
    private void printTree(BinaryNode<AnyType> t){
        // 根-左-右：先序遍历
        if(t!=null){
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }
}
