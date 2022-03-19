package dataStructure.graph.adjList;

import dataStructure.list.MyArrayQueue;
import java.io.IOException;

public class BfsAdjList<Type1,Type2 extends Comparable<? super Type2>> {
    private GraphAdjList<Type1,Type2> g;
    boolean[] visited; // 用以存储每个顶点是否被访问过
    BfsAdjList(GraphAdjList<Type1,Type2> g){
        this.g=g;
        visited=new boolean[g.getNumVertexs()];
        initVisited();
    }
    // 初始化访问状态数组，都置为false，即没有被访问过
    private void initVisited(){
        for(int i=0;i<visited.length;i++){
            visited[i]=false;
        }
    }
    // 对整个图进行广度优先搜索
    public void BFSTraverse(){
        initVisited();
        for(int i=0;i<g.getNumVertexs();i++){
            if(!visited[i]){
                BFS(i);
            }
        }
    }
    // 从顶点i开始，进行广度优先搜索
    private void BFS(int i){
        MyArrayQueue<Integer> Q=new MyArrayQueue<>();
        EdgeNode<Type2> p;
        visited[i]=true;
        System.out.println(g.getAdjList()[i].getData());
        Q.enQueue(i); // 对当前顶点入队
        while(!Q.isEmpty()){
            // 取出队头顶点，指向该顶点的边表表头
            p=g.getAdjList()[Q.deQueue()].getFirstEdge();
            // 循环遍历该顶点的整个边表
            while(p!=null){
                if(!visited[p.getAdjVex()]){
                    visited[p.getAdjVex()]=true;
                    System.out.println(g.getAdjList()[p.getAdjVex()].getData());
                    // 对该顶点执行完输出操作后，将该顶点入队
                    Q.enQueue(p.getAdjVex());
                }
                p=p.getNext();
            }
        }
    }
    public static void main(String [] args) throws IOException {
        GraphAdjList<String,Integer> g=new GraphAdjList<>();
        BfsAdjList<String,Integer> bfs=new BfsAdjList<>(g);
        bfs.BFSTraverse();
    }
}