package dataStructure.graph.edgeSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MiniSpanTree_Kruskal<Type1,Type2 extends Comparable<? super Type2>> {
    private static final int INF=65535; // 无穷大
    private GraphEdgeSet<Type1,Type2> g;
    private boolean[] inTree; // 存储已经加入最小生成树的顶点，true为已经加入
    private ArrayList<HashSet<Integer>> subGraph; // 存储已经放入最小生成树的连通子图的顶点
    MiniSpanTree_Kruskal(GraphEdgeSet<Type1,Type2> g){
        this.g=g;
        inTree=new boolean[g.getNumVertexs()];
        subGraph=new ArrayList<>();
        initInTree();
    }
    private void initInTree(){
        for(int i=0;i<inTree.length;i++){
            inTree[i]=false;
        }
    }
    // 判断新加入的边是否成环
    private boolean isLoop(Edge e){
        int begin=e.getBegin();
        int end=e.getEnd();
        for(int i=0;i<subGraph.size();i++){
            HashSet<Integer> s=subGraph.get(i);
            if(s.contains(begin) && s.contains(end)) // 成环
                return true;
        }
        return false;
    }
    // 最小生成树之Kruskal算法
    public void spanTreeKruskal(){
        g.sort();// 先按权值进行排序
        for(int i=0;i<g.getNumEdges();i++){
            Edge e=g.getEdgeSet()[i];
            // 如果新边没有形成环
            if(!isLoop(e)){
                putEdge(e);
            }
        }
    }
    // 将一条边放入最小生成树
    private void putEdge(Edge e){
        int begin=e.getBegin();
        int end=e.getEnd();
        Type2 weight= (Type2) e.getWeight();
        // 两个顶点都还没有加入生成树，这时会形成新的连通子图
        if(!inTree[begin] && !inTree[end]){
            inTree[begin]=true;
            inTree[end]=true;
            // 构建一个新的连通子图的顶点集
            HashSet<Integer> set=new HashSet<>();
            set.add(begin);
            set.add(end);
            subGraph.add(set);
        }
        // 两个顶点都在生成树中，这时会发生连通子图的合并。是否成环已在上层函数中判断过
        else if(inTree[begin] && inTree[end]){
            int bi=0,ei=0;
            for (int i=0;i<subGraph.size();i++){
                if(subGraph.get(i).contains(begin)) bi=i;
                if(subGraph.get(i).contains(end)) ei=i;
            }
            HashSet<Integer> s=new HashSet<>();
            s.addAll(subGraph.get(bi));
            s.addAll(subGraph.get(ei));
            subGraph.set(bi,s);
            subGraph.remove(ei);
        }
        // 只有一个顶点都在生成树中；连通子图的个数不变，但需加入新顶点
        else {
            int i;
            // end未在生成树中
            if(inTree[begin]){
                inTree[end] = true;
                for (i=0;i<subGraph.size();i++){
                    if(subGraph.get(i).contains(begin)) break;
                }
                HashSet<Integer> s=new HashSet<>();
                s.add(end); // 将end加入生成树
                s.addAll(subGraph.get(i));
                subGraph.set(i,s);
            }
            // begin未在生成树中
            else{
                inTree[begin] = true;
                for (i=0;i<subGraph.size();i++){
                    if(subGraph.get(i).contains(end)) break;
                }
                HashSet<Integer> s=new HashSet<>();
                s.add(begin); // 将end加入生成树
                s.addAll(subGraph.get(i));
                subGraph.set(i,s);
            }
        }
        System.out.println(begin+"--"+end+",weight="+weight);
    }
    public static void main(String [] args)throws IOException {
        GraphEdgeSet<String,Integer> g=new GraphEdgeSet<>();
        MiniSpanTree_Kruskal<String,Integer> kruskal=new MiniSpanTree_Kruskal<>(g);
        kruskal.spanTreeKruskal();
    }
}
