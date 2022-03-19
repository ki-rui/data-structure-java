package dataStructure.tree;
import java.nio.BufferUnderflowException;
// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws BufferUnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {
    /** 实例域 */
    private static class AvlNode<AnyType> {  // 子类：AVL树结点
        AvlNode( AnyType theElement )  {
            this( theElement, null, null );
        }
        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt ) {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }
        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }
    private AvlNode<AnyType> root; // 根节点

    /** 公有方法 */
    // AVL树的构造方法
    public AvlTree( ) {
        root = null;
    }
    // 向树中插入x，如果树中已有相同的x，则新的插入的x会被忽略
    public void insert( AnyType x ) {
        root = insert( x, root );
    }
    // 从树中删除x，如果x没被找到，那就什么也不做
    public void remove( AnyType x ) {
        root = remove( x, root );
    }

    // 找到树的最小元素；如果树空，则返回null
    public AnyType findMin( ) {
        if( isEmpty( ) )
            throw new BufferUnderflowException( );
        return findMin( root ).element;
    }
    // 找到树的最大元素；如果树空，则返回null
    public AnyType findMax( ) {
        if( isEmpty( ) )
            throw new BufferUnderflowException( );
        return findMax( root ).element;
    }
    // 判断树中是否包含x
    public boolean contains( AnyType x ) {
        return contains( x, root );
    }
    // 将树置空
    public void makeEmpty( ) {
        root = null;
    }
    // 判空
    public boolean isEmpty( ) {
        return root == null;
    }
    // 检查root是否平衡
    public void checkBalance( ) {
        checkBalance( root );
    }
    // 按序打印树的每一个元素
    public void printTree( ) {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /** 以下为AvlTree的私有方法和私有变量  */
    // 不平衡高度的阈值为1；AVL树中任何一个结点的左右子树高度差都不能大于1
    private static final int ALLOWED_IMBALANCE = 1;
    // t此时刚被插入或删除元素，尚未确定是否平衡，需要执行balance操作
    // 这里假设t为从叶结点向上查询，首次出现不平衡的结点
    private AvlNode<AnyType> balance( AvlNode<AnyType> t ) {
        if( t == null )  // 如果树为空，则不需要平衡
            return t;
        // 如果t的左子树比右子树高
        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rotateWithLeftChild( t ); // 单次左旋转（适用于插入左儿子的左子树）
            else
                t = doubleWithLeftChild( t ); // 双次左-右旋转（适用于插入左儿子的右子树）
        // 如果t的右子树比左子树高
        else if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t ); // 单次右旋转（适用于插入右儿子的右子树）
            else
                t = doubleWithRightChild( t ); // 双次右-左旋转（适用于插入右儿子的左子树）
        // 重新计算t的高度
        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }
    // 检查root是否平衡
    private int checkBalance( AvlNode<AnyType> t ) {
        if( t == null )
            return -1;
        else {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            /** height( t.left ) != hl可能为true吗？ 不太理解 */
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOPS!!" ); // 树不平衡
        }
        return height( t );
    }
    // 向子树t中插入x，并返回这个子树新的根节点
    private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t ) {
        if( t == null )
            return new AvlNode<>( x, null, null );
        int compareResult = x.compareTo( t.element );
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return balance( t );
    }
    // 从一个子树t中删除x，并返回这个子树新的根节点
    private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t ) {
        if( t == null )
            return t;   // Item not found; do nothing
        int compareResult = x.compareTo( t.element );
        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ){ // Two children
            // 如果删除结点处有两个子树，则在右子树中找到最小元素，放在当前树的根节点，
            // 右子树递归实现删除这个被提出来的最小元素；左子树不需要改变
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            // 如果t有左子树，没有右子树，将t的左子树替代当前结点，
            // 如果t没有左子树，有右子树，将t的右子树替代当前结点，
            // 左右子树都无，则t=null
            t = ( t.left != null ) ? t.left : t.right;
        return balance( t ); // AVL树在删除元素后必须实现balance操作
    }
    // 找到树的最小元素；如果树空，则返回null（私有方法）
    private AvlNode<AnyType> findMin( AvlNode<AnyType> t ) {
        if( t == null )
            return t;
        while( t.left != null )
            t = t.left;
        return t;
    }
    // 找到树的最大元素；如果树空，则返回null（私有方法）
    private AvlNode<AnyType> findMax( AvlNode<AnyType> t ) {
        if( t == null )
            return t;
        while( t.right != null )
            t = t.right;
        return t;
    }
    // 判断树t中是否包含x（私有方法）
    private boolean contains( AnyType x, AvlNode<AnyType> t ) {
        while( t != null ) {
            int compareResult = x.compareTo( t.element );
            if( compareResult < 0 )
                t = t.left;
            else if( compareResult > 0 )
                t = t.right;
            else
                return true;    // Match
        }
        return false;   // No match
    }
    // 按序打印树的每一个元素（私有方法）
    private void printTree( AvlNode<AnyType> t ) {
        // 左-根-右：中序遍历
        if( t != null ) {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }
    // 计算树的高度
    private int height( AvlNode<AnyType> t ) {
        return t == null ? -1 : t.height;
    }
    // 单次左旋转（左儿子左子树）；k2是从下往上首个不平衡结点
    private AvlNode<AnyType> rotateWithLeftChild( AvlNode<AnyType> k2 ) {
        AvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }
    // 单次右旋转（右儿子右子树）；k1是从下往上首个不平衡结点
    private AvlNode<AnyType> rotateWithRightChild( AvlNode<AnyType> k1 ) {
        AvlNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        return k2;
    }
    // 双次左-右旋转（适用于插入左儿子的右子树）（先单右，再单左）
    // k3是从下往上首个不平衡结点
    private AvlNode<AnyType> doubleWithLeftChild( AvlNode<AnyType> k3 ) {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }
    // 双次右-左旋转（适用于插入右儿子的左子树）（先单左，再单右）
    // k1是从下往上首个不平衡结点
    private AvlNode<AnyType> doubleWithRightChild( AvlNode<AnyType> k1 )  {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }

    /**  测试程序   */
    public static void main( String[] args ) {
        AvlTree<Integer> t = new AvlTree<>( ); // 创建一棵空的AVL树
        final int SMALL = 20;
        final int NUMS = 100;  // must be even
        final int GAP  =   11;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS ) {
//            System.out.println( "INSERT: " + i );
            t.insert( i );
            if( NUMS < SMALL )
                t.checkBalance( );
        }

        for( int i = 1; i < NUMS; i+= 2 )  {
//            System.out.println( "REMOVE: " + i );
            t.remove( i );
            if( NUMS < SMALL )
                t.checkBalance( );
        }

        if( NUMS < SMALL ) // 这代表发生了某些意料之外的事情
            t.printTree( );
        if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
            System.out.println( "FindMin or FindMax error!" );

        // t中包含2-NUMS之间的所有偶数，如果有一个偶数不被包含，输出错误信息
        for( int i = 2; i < NUMS; i+=2 )
            if( !t.contains( i ) )
                System.out.println( "Find error1!" );
        // t中不包含2-NUMS之间的所有奇数，如果有一个奇数被包含，输出错误信息
        for( int i = 1; i < NUMS; i+=2 ) {
            if( t.contains( i ) )
                System.out.println( "Find error2!" );
        }
    }
}

