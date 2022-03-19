package dataStructure.graph.adjList;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

class EdgeNode<Type2 extends Comparable<? super Type2>>{ // 边表节点
    private int adjVex; // 邻接点域，存储该顶点对应的下标
    private Type2 weight; // 用于存储权值，对于非网图可以不需要
    private EdgeNode<Type2> next; // 下一个邻接点
    EdgeNode(int adjVex,Type2 weight,EdgeNode<Type2> next){
        this.adjVex=adjVex;
        this.weight=weight;
        this.next=next;
    }
    public int getAdjVex(){return adjVex; }
    public Type2 getWeight(){return weight;}
    public EdgeNode<Type2> getNext(){return next;}
}
class VertexNode<Type1,Type2 extends Comparable<? super Type2>>{ // 顶点表节点
    private Type1 data; // 当前顶点的数据域
    private EdgeNode<Type2> firstEdge; // 边表头指针
    VertexNode(Type1 data,EdgeNode<Type2> firstEdge){
        this.data=data;
        this.firstEdge=firstEdge;
    }
    public Type1 getData(){  return data; }
    public EdgeNode<Type2> getFirstEdge(){ return firstEdge; }
    public void setFirstEdge(EdgeNode<Type2> firstEdge){ this.firstEdge=firstEdge; }
}
public class GraphAdjList<Type1,Type2 extends Comparable<? super Type2>> {
    private static final int MAXVEX=15; // 最大顶点数
    private VertexNode<Type1,Type2>[] adjList;
    private int numVertexs,numEdges; // 图中当前的顶点数和边数
    // 构造函数
    GraphAdjList() throws IOException {
        adjList=new VertexNode[MAXVEX];
        createAdjList();
    }
    public int getNumVertexs(){
        return numVertexs;
    }
    public VertexNode<Type1,Type2>[] getAdjList(){return adjList;}
    // 创建一个图（空图是非法的，图的顶点数一定是有穷非空的）
    public void createAdjList() throws IOException {
        String path="E:\\project_file\\IntelliJ_IDEA\\in.txt";
        // 读取输入流
        Scanner in =new Scanner(Paths.get(path),"UTF-8");
        numVertexs=in.nextInt();
        numEdges=in.nextInt();
        int sym=in.nextInt();
        for(int i=0;i<numVertexs;i++){
            adjList[i]=new VertexNode((Type1) in.next(),null);
        }
        int i,j;
        Type2 wei;
        for(int k=0;k<numEdges;k++){
            i=in.nextInt();
            j=in.nextInt();
            // 这句并没有将权值转化为integer类型，weight中存储的是String，也就是说强制类型转换并没有起作用；
            // 但奇怪的是，该句也没有报错，可以正常运行，并且weight可以正常存储String，即便泛型Type2传进来的是Integer
            wei=(Type2) in.next();
            EdgeNode<Type2> e=new EdgeNode<>(j,wei,adjList[i].getFirstEdge());
            adjList[i].setFirstEdge(e);
            if(sym==0){
                e=new EdgeNode<>(i,wei,adjList[j].getFirstEdge());
                adjList[j].setFirstEdge(e);
            }
        }
    }
    public static void main(String[] args)throws IOException{
        GraphAdjList<String,Integer> g=new GraphAdjList<>();
        System.out.println("yes");
    }
}
