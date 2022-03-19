package dataStructure.graph.adjList;

import java.io.IOException;

public class MiniSpanTree_Prim<Type1,Type2 extends Comparable<? super Type2>> {
    private static final int INF=65535; // 无穷大
    private GraphAdjList<Type1,Type2> g;
    private boolean[] inTree; // 存储已经加入最小生成树的顶点
    MiniSpanTree_Prim(GraphAdjList<Type1,Type2> g){
        this.g=g;
        inTree=new boolean[g.getNumVertexs()];
        initInTree();
    }
    private void initInTree(){
        for(int i=0;i<inTree.length;i++){
            inTree[i]=false;
        }
    }
    // 最小生成树之Prim算法
    public void spanTreePrim(int index){
        inTree[index]=true;
        int min;
        int minBegin=0,minEnd=0;
        EdgeNode<Type2> p;
        for(int i=1;i<g.getNumVertexs();i++){
            min=INF;
            // 找到最小权值边
            for(int j=0;j<inTree.length;j++){
                if(inTree[j]){
                    p=g.getAdjList()[j].getFirstEdge();
                    while(p!=null){
                        // 字符串转整型
                        int temp=Integer.parseInt((String) p.getWeight());
                        if(!inTree[p.getAdjVex()]&&temp<min){
                            min=temp;
                            minBegin=j;
                            minEnd=p.getAdjVex();
                        }
                        p=p.getNext();
                    }
                }
            }
            inTree[minEnd]=true;
            System.out.println(minBegin+"--"+minEnd+"，weight="+min);
        }
    }
    public static void main(String[] args)throws IOException {
        GraphAdjList<String,Integer> g=new GraphAdjList<>();
        MiniSpanTree_Prim<String,Integer> prim=new MiniSpanTree_Prim<>(g);
        prim.spanTreePrim(0);
    }
}
