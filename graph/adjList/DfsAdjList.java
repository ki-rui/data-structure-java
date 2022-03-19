package dataStructure.graph.adjList;

import java.io.IOException;

public class DfsAdjList<Type1,Type2 extends Comparable<? super Type2>> {
    private GraphAdjList<Type1,Type2> g;
    boolean[] visited; // 用以存储每个顶点是否被访问过
    DfsAdjList(GraphAdjList<Type1,Type2> g){
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
    // 对整个图进行深度优先搜索
    public void DFSTraverse(){
        initVisited();
        for(int i=0;i<g.getNumVertexs();i++){
            if(!visited[i])  DFS(i);
        }
    }
    // 从顶点i开始，进行深度优先搜索，只能搜索到所有连通的顶点
    private void DFS(int i){
        visited[i]=true;
        System.out.println(g.getAdjList()[i].getData());
        EdgeNode<Type2> p=g.getAdjList()[i].getFirstEdge();
        while(p!=null){
            if(!visited[p.getAdjVex()]) DFS(p.getAdjVex());
            p=p.getNext();
        }
    }
    public static void main(String [] args) throws IOException {
        GraphAdjList<String,Integer> g=new GraphAdjList<>();
        DfsAdjList<String,Integer> dfs=new DfsAdjList<>(g);
        dfs.DFSTraverse();
    }
}
