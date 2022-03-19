package dataStructure.list;

public class MyArrayQueue<AnyType> {
    private static final int DEFAULT_CAPACITY = 4; // 栈的默认容量
    private AnyType [ ] theItems; // 存储队列元素的数组
    private int front; // 队头指针
    private int rear; // 队尾指针
    private int currentSize; // 队列中元素个数
    // 构造器
    public MyArrayQueue( ) {
        doClear( );
    }
    // 返回队列的大小
    public int size( ) {
        return currentSize;
    }
    // 判空
    public boolean isEmpty( ) {
        return size( ) == 0;
    }
    // 在强制类型转换的时候编译器会给出警告；下面一行语句告诉编译器忽略不检查警告信息
    @SuppressWarnings("unchecked")
    // 扩容。如果新容量小于现有尺寸，不做改变；否则，按新容量扩容
    public void ensureCapacity( int newCapacity ) {
        if( newCapacity < currentSize )
            return;
        AnyType [ ] old = theItems;
        theItems = (AnyType []) new Object[ newCapacity ];
        if(old==null)
            return;
        int i,k; // 新数组的索引位
        if(rear<=front){
            int j;
            for(i = 0,j=front; i < size( )&&j<old.length; i++,j++ )
                theItems[ i ] = old[ j ];
            for(k=0; i < size( )&k<rear; i++,k++ )
                theItems[ i ] = old[ k ];
        }
        else {
            i=0;
            for (k = front; k < rear; k++)
                theItems[i++] = old[k];
        }
        front=0;
        rear=i;
    }
    // 清理队列中所有元素；
    public void clear( ) {
        doClear( );
    }
    // 队列容量设置为默认容量
    private void doClear( ) {
        front=0;
        rear=0;
        ensureCapacity( DEFAULT_CAPACITY );
    }
    // 入队
    public boolean enQueue(AnyType x){
        if(currentSize==theItems.length){
            ensureCapacity( currentSize*2+1 );
        }
        theItems[rear]=x;
        rear=(rear+1)%theItems.length;
        currentSize++;
        return true;
    }
    // 出队
    public AnyType deQueue(){
        AnyType res=theItems[front];
        front=(front+1)%theItems.length;
        currentSize--;
        if( theItems.length > size( )*3 )
            ensureCapacity( size( )*2+1 );
        return res;
    }
}
class TestArrayQueue {
    public static void main( String [ ] args ) {
        MyArrayQueue<Integer> queue = new MyArrayQueue<>( );
        for( int i = 0; i < 4; i++ )
            queue.enQueue( i );
        queue.enQueue( 5 );
        for( int i = 20; i < 30; i++ )
            queue.enQueue(i);
        queue.enQueue(99);
        for( int i = 0; i < 9; i++ )
            queue.deQueue();
        queue.deQueue();
        queue.deQueue();
    }
}
