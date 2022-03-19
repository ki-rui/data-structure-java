package dataStructure.list;

// 双链表
public class MyLinkedList<AnyType> implements Iterable<AnyType>{
    // 链表节点定义
    private static class Node<AnyType> {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n ) {
            data = d; prev = p; next = n;
        }
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }
    private int theSize;
    private int modCount = 0; // 该变量记录构造以来对链表所做改变的次数。用以帮助迭代器检测集合是否发生了变化
    private Node<AnyType> beginMarker; // 头节点
    private Node<AnyType> endMarker; // 尾节点
    // 构造器：构造一个空链表
    public MyLinkedList( ) {
        doClear( );
    }
    // 清空链表
    private void clear( ) {
        doClear( );
    }
    // 清空链表的实现
    public void doClear( ) {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;
        theSize = 0;
        modCount++;
    }
    // 返回链表尺寸
    public int size( ) {
        return theSize;
    }
    // 判空
    public boolean isEmpty( ) {
        return size( ) == 0;
    }
    // 在链表的末尾增加元素x
    public boolean add( AnyType x ) {
        add( size( ), x );
        return true;
    }
    // 在链表的idx索引处增加元素x
    public void add( int idx, AnyType x ) {
        addBefore( getNode( idx, 0, size( ) ), x );
    }
    // 在节点p之前增添元素x
    private void addBefore( Node<AnyType> p, AnyType x ) {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }
    // 获取idx索引处的数据
    public AnyType get( int idx ) {
        return getNode( idx ).data;
    }
    // 将idx索引处设置为新元素newVal，并返回旧元素
    public AnyType set( int idx, AnyType newVal ) {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        p.data = newVal;
        return oldVal;
    }
    // 获取idx索引处的节点
    private Node<AnyType> getNode( int idx ) {
        return getNode( idx, 0, size( ) - 1 );
    }
    // 获取idx索引处的节点；索引idx必须在lower和upper之间，否则抛出异常
    private Node<AnyType> getNode( int idx, int lower, int upper ) {
        Node<AnyType> p;
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
        if( idx < size( ) / 2 ) {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;
        }
        else  {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        }
        return p;
    }
    // 删除idx索引处的节点
    public AnyType remove( int idx ) {
        return remove( getNode( idx ) );
    }
    // 删除节点p
    private AnyType remove( Node<AnyType> p ) {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        return p.data;
    }
    // 系统输出
    public String toString( )  {
        StringBuilder sb = new StringBuilder( "[ " );
        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );
        return new String( sb );
    }
    // 实现了Iterable接口的集合中必须有iterator方法，且该方法必须返回Iterator
    public java.util.Iterator<AnyType> iterator( ) {
        return new LinkedListIterator( );
    }
    private class LinkedListIterator implements java.util.Iterator<AnyType> {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        public boolean hasNext( ) {
            return current != endMarker;
        }
        public AnyType next( ) {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( );
            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }
        public void remove( ) {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;
        }
    }
}

class TestLinkedList{
    public static void main( String [ ] args ) {
        MyLinkedList<Integer> lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++ )
            lst.add( i );
        for( int i = 20; i < 30; i++ )
            lst.add( 0, i );
        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );
        System.out.println( lst );
        java.util.Iterator<Integer> itr = lst.iterator( );
        while( itr.hasNext( ) ) {
            itr.next( );
            itr.remove( );
            System.out.println( lst );
        }
    }
}