package dataStructure.list;

public class MyArrayStack<AnyType>{
    private static final int DEFAULT_CAPACITY = 5; // 栈的默认容量
    private AnyType [ ] theItems; // 存储栈元素的数组
    private int topOfStack; // 栈顶指针

    // 构造器
    public MyArrayStack( ) {
        doClear( );
    }
    // 返回栈的大小
    public int size( ) {
        return topOfStack+1;
    }
    // 判空
    public boolean isEmpty( ) {
        return size( ) == 0;
    }
    // 在强制类型转换的时候编译器会给出警告；下面一行语句告诉编译器忽略不检查警告信息
    @SuppressWarnings("unchecked")
    // 扩容。如果新容量小于现有尺寸，不做改变；否则，按新容量扩容
    public void ensureCapacity( int newCapacity ) {
        if( newCapacity < topOfStack+1 )
            return;
        AnyType [ ] old = theItems;
        theItems = (AnyType []) new Object[ newCapacity ];
        for( int i = 0; i < size( ); i++ )
            theItems[ i ] = old[ i ];
    }
    // 清理栈中所有元素；
    public void clear( ) {
        doClear( );
    }
    // 栈顶指针置为-1，即此时为空栈；容量设置为默认容量
    private void doClear( ) {
        topOfStack = -1;
        ensureCapacity( DEFAULT_CAPACITY );
    }
    // 入栈
    public void push(AnyType x){
        // 当栈中元素放满时，将容量扩大1倍
        if( theItems.length == size( ) )
            ensureCapacity( size( ) * 2 + 1 );
        theItems[ (topOfStack++)+1 ] = x;
    }
    // 出栈
    public AnyType pop() {
        // 这里是要判断数组超界的
//        if(topOfStack<0)
//            throw new Exception("栈已空，无法pop");
        // 当给栈分配的空间有大量空余时，缩减容量
        if( theItems.length > size( )*4 )
            ensureCapacity( size( )*2 );
        return theItems[ topOfStack-- ];
    }
    public AnyType top() {
//        if(topOfStack<0)
//            throw new Exception("栈已空，无法top");
        return theItems[ topOfStack ];
    }
}
class TestArrayStack {
    public static void main( String [ ] args ) {
        MyArrayStack<Integer> stack = new MyArrayStack<>( );
        for( int i = 0; i < 13; i++ )
            stack.push( i );
        for( int i = 0; i < 13; i++ )
            stack.pop();
        stack.push(99);
        System.out.println( stack );
    }
}
