package dataStructure.list;

// 实现了Iterable接口的类可以拥有增强的for循环
public class MyArrayList<AnyType> implements Iterable<AnyType>{
    private static final int DEFAULT_CAPACITY = 10; // 表的默认容量
    private AnyType [ ] theItems; // 整个表的引用
    private int theSize; // 表的尺寸（大小）

    // 构造器
    public MyArrayList( ) {
        doClear( );
    }
    // 返回表的大小
    public int size( ) {
        return theSize;
    }
    // 判空
    public boolean isEmpty( ) {
        return size( ) == 0;
    }
    // 返回idx索引处的元素；如果索引超界，抛出异常
    public AnyType get( int idx ) {
        if( idx < 0 || idx >= size( ) )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; size " + size( ) );
        return theItems[ idx ];
    }
    // 设置索引idx处的元素为newVal，并返回原本idx处的元素；如果索引超界，抛出异常
    public AnyType set( int idx, AnyType newVal ) {
        if( idx < 0 || idx >= size( ) )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; size " + size( ) );
        AnyType old = theItems[ idx ];
        theItems[ idx ] = newVal;
        return old;
    }
    //  在强制类型转换的时候编译器会给出警告；下面一行语句告诉编译器忽略不检查警告信息
    @SuppressWarnings("unchecked")
    // 扩容。如果新容量小于现有尺寸，不做改变；否则，按新容量扩容
    public void ensureCapacity( int newCapacity ) {
        if( newCapacity < theSize )
            return;
        AnyType [ ] old = theItems;
        theItems = (AnyType []) new Object[ newCapacity ];
        for( int i = 0; i < size( ); i++ )
            theItems[ i ] = old[ i ];
    }
    // 在列表的末尾增加元素x
    public boolean add( AnyType x ) {
        add( size( ), x );
        return true;
    }
    // 在索引idx处增加元素x
    public void add( int idx, AnyType x ) {
        // 当列表中元素放满时，将列表容量扩大1倍
        if( theItems.length == size( ) )
            ensureCapacity( size( ) * 2 + 1 );
        for( int i = theSize; i > idx; i-- )
            theItems[ i ] = theItems[ i - 1 ];
        theItems[ idx ] = x;
        theSize++;
    }
    // 删除idx索引处的元素，并返回这个元素
    public AnyType remove( int idx ) {
        AnyType removedItem = theItems[ idx ];
        for( int i = idx; i < size( ) - 1; i++ )
            theItems[ i ] = theItems[ i + 1 ];
        theSize--;
        return removedItem;
    }
    // 清理列表中所有元素；将列表尺寸置0
    public void clear( ) {
        doClear( );
    }
    // 列表尺寸置0；容量设置为默认容量
    private void doClear( ) {
        theSize = 0;
        ensureCapacity( DEFAULT_CAPACITY );
    }
    // 实现Iterable接口的集合必须提供一个称为iterator 的方法，
    // 该方法返回一个Iterator类型的对象（此时该对象指向集合的首元素）
    public java.util.Iterator<AnyType> iterator( ) {
        return new ArrayListIterator( );
    }
    private class ArrayListIterator implements java.util.Iterator<AnyType> {
        private int current = 0;
        private boolean okToRemove = false;
        public boolean hasNext( ) {
            return current < size( );
        }
        public AnyType next( ) {
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( );
            okToRemove = true;
            return theItems[ current++ ];
        }
        public void remove( ) {
            if( !okToRemove )
                throw new IllegalStateException( );
            // 删除的是current前面的元素
            MyArrayList.this.remove( --current );
            okToRemove = false;
        }
    }
    // 系统输出
    public String toString( ) {
        StringBuilder sb = new StringBuilder( "[ " );
        // 注意这里，this实际是一个容器，并不是一个普通的数组。
        // 但由于实现了Iterable接口，所以可以使用这种增强的for循环，
        // 这里会自动调用iterator( )方法
        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );
        return new String( sb );
    }
}
class TestArrayList {
    public static void main( String [ ] args ) {
        MyArrayList<Integer> lst = new MyArrayList<>( );
        for( int i = 0; i < 10; i++ )
            lst.add( i );
        for( int i = 20; i < 30; i++ )
            lst.add( 0, i );
        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );
        System.out.println( lst );
    }
}
