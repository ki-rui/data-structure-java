package dataStructure.heap;

import java.nio.BufferUnderflowException;
// BinaryHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

// 二叉堆；其元素要求有可比较性
public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {
    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;      // 堆中元素数量
    private AnyType [ ] array; // 存储堆的数组。array[0]处不放元素，从1开始放

    public BinaryHeap( ) {
        this( DEFAULT_CAPACITY );
    }
    // 按容量创建一个空堆
    public BinaryHeap( int capacity ) {
        currentSize = 0;
        array = (AnyType[]) new Comparable[ capacity + 1 ];
    }
    // 按给定数组创建一个堆
    public BinaryHeap( AnyType [ ] items ) {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];
        int i = 1;
        for( AnyType item : items )
            array[ i++ ] = item;
        buildHeap( );
    }
    // 判空
    public boolean isEmpty( ) {
        return currentSize == 0;
    }
    // 置空
    public void makeEmpty( ) {
        currentSize = 0;
    }
    // 往堆中插入x
    public void insert( AnyType x ) {
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );
        // 向上过滤（小元素上浮）
        int hole = ++currentSize;
        for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 ) // 当hole=1时，条件一定为false
            array[ hole ] = array[ hole / 2 ];
        array[ hole ] = x;
    }
    // 按新尺寸扩容数组
    private void enlargeArray( int newSize ) {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }
    // 找最小元
    public AnyType findMin( ) {
        if( isEmpty( ) )
            throw new BufferUnderflowException( );
        return array[ 1 ];
    }
    // 删除最小元
    public AnyType deleteMin( ) {
        if( isEmpty( ) )
            throw new BufferUnderflowException( );
        AnyType minItem = findMin( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );
        return minItem;
    }
    // 根据当前数组元素创建堆
    private void buildHeap( ) {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }
    // 向下过滤
    private void percolateDown( int hole )  {
        int child;
        AnyType tmp = array[ hole ];
        // 最后一个结点的父结点是currentSize/2，这里可以保证hole绝对有孩子结点
        for( ; hole * 2 <= currentSize; hole = child ) {
            child = hole * 2;
            // child不是最后一个结点 and 右孩子小于左孩子
            if( child != currentSize &&
                    array[ child + 1 ].compareTo( array[ child ] ) < 0 )
                child++;
            // 这里已经筛选出了两个孩子中最小的；
            // 如果没有右孩子，左孩子自然最小；如果右孩子大于左孩子，左孩子也最小；如果右孩子小于左孩子，此时child已经指向右孩子
            if( array[ child ].compareTo( tmp ) < 0 ) // 父结点大于（最小的）孩子，故父结点与（小）孩子交换位置
                array[ hole ] = array[ child ];
            // 父结点小于两个孩子
            else
                break;
        }
        array[ hole ] = tmp;
    }

    // Test program
    public static void main( String [ ] args )
    {
        int numItems = 100;
        BinaryHeap<Integer> h = new BinaryHeap<>( );
        int i = 7,k=0;

        for( i = 7; i != 0; i = ( i + 7 ) % numItems ) {
            h.insert(i);
            k++;
        }
        System.out.println(k);
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }
}
