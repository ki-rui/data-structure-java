package dataStructure.graph.edgeSet;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

class Edge<Type2 extends Comparable<? super Type2>>{
    private int begin;
    private int end;
    private Type2 weight;
    Edge(int begin,int end,Type2 weight){
        this.begin=begin;
        this.end=end;
        this.weight=weight;
    }
    public int getBegin(){return begin;}
    public int getEnd(){return end;}
    public Type2 getWeight(){return weight;}
}
// 边集数组表示法
public class GraphEdgeSet<Type1,Type2 extends Comparable<? super Type2>> {
    private static final int MAXVEX=15; // 最大顶点数
    private static final int MAXEDGE=50; // 最大边数
    private static final int INF=65535; // 无穷大
    private Type1[] vexs;  // 顶点表
    private Edge<Type2>[] edgeSet; // 边集数组
    private int numVertexs,numEdges; // 图中当前的顶点数和边数
    private int sym; // 标志位，0为无向图，否则为有向图
    GraphEdgeSet()throws IOException{
        vexs= (Type1[]) new Object[ MAXVEX ];
        edgeSet=new Edge[MAXEDGE];
        createEdgeSet();
    }
    public int getNumVertexs(){return numVertexs;}
    public int getNumEdges(){
        if(sym==0)
            return 2*numEdges; // 如果是无向图，每条边交换起点和终点后再读入，共读入两次
        return numEdges;
    }
    public Edge<Type2>[] getEdgeSet(){return edgeSet;}
    // 创建一个图（空图是非法的，图的顶点数一定是有穷非空的）
    public void createEdgeSet() throws IOException {
        String path="E:\\project_file\\IntelliJ_IDEA\\in.txt";
        // 读取输入流
        Scanner in =new Scanner(Paths.get(path),"UTF-8");
        numVertexs=in.nextInt();
        numEdges=in.nextInt();
        sym=in.nextInt();
        for(int i=0;i<numVertexs;i++){
            vexs[i]=(Type1) in.next();
        }
        int be,en;
        Type2 wei;
        for(int i=0;i<getNumEdges();){
            be=in.nextInt();
            en=in.nextInt();
            wei=(Type2) in.next();
            edgeSet[i++]=new Edge<>(be,en,wei);
            if(sym==0){
                edgeSet[i++]=new Edge<>(en,be,wei);
            }
        }
    }

    public void printEdges(){
        for(int i=0;i<getNumEdges();i++){
            System.out.println(edgeSet[i].getBegin()+"--"+edgeSet[i].getEnd()+",weight="+edgeSet[i].getWeight());
        }
    }
    // 排序统一接口
    public void sort(){
//        sortWeight(0,getNumEdges()-1);
        insertSort(0,getNumEdges()-1);
    }
    // 插入排序
    private void insertSort(int left,int right){
        int tmp;
        int i;
        for(int p=left+1;p<=right;p++){
            tmp=p;
            for(i=p;i>left && Integer.parseInt((String)edgeSet[i-1].getWeight())>
                    Integer.parseInt((String)edgeSet[tmp].getWeight());i--){
                edgeSet[i]=edgeSet[i-1];
            }
            edgeSet[i]=edgeSet[tmp];
        }
    }
    // 快速排序
    private void sortWeight(int left,int right){
        if (left>=right) return;
        int low,high;
        Type2 pivot; // 主元
        pivot=median(left,right);
        if(right-left<3) return;
        low=left;
        high=right-1;
        while (true){
            while(Integer.parseInt((String)edgeSet[++low].getWeight())<Integer.parseInt((String)pivot))
                if(low>=right-2)break;
            while(Integer.parseInt((String)edgeSet[--high].getWeight())>Integer.parseInt((String)pivot))
                if(high<=left+1)break;
            if(low<high)
                swap(low,high);
            else
                break;
        }
        swap(low,right-1);
        sortWeight(left,low-1);
        sortWeight(low+1,right);
    }
    // 快排选主元
    private Type2 median(int left,int right){
        int middle=(left+right)/2;
        if(Integer.parseInt((String) edgeSet[left].getWeight())>Integer.parseInt((String) edgeSet[middle].getWeight()))
            swap(left,middle);
        if(Integer.parseInt((String) edgeSet[left].getWeight())>Integer.parseInt((String) edgeSet[right].getWeight()))
            swap(left,right);
        if(Integer.parseInt((String) edgeSet[middle].getWeight())>Integer.parseInt((String) edgeSet[right].getWeight()))
            swap(middle,right);
        swap(middle,right-1);
        return edgeSet[right-1].getWeight();
    }
    private void swap(int i,int j){
        if(i!=j) {
            Edge<Type2> temp;
            temp = edgeSet[i];
            edgeSet[i] = edgeSet[j];
            edgeSet[j] = temp;
        }
    }
    public static void main(String[] args)throws IOException{
        GraphEdgeSet<String,Integer> g=new GraphEdgeSet<>();
        g.printEdges();
        g.sort();
        System.out.println("------------------------------");
        g.printEdges();
        System.out.println("yes:");
    }
}
