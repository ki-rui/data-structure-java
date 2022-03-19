package dataStructure.graph.adjMatrix;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class GraphAdjMatrix<Type1,Type2 extends Comparable<? super Type2>> {
    private static final int MAXVEX=15; // 最大顶点数
    private static final int INF=65535; // 无穷大
    private Type1[] vexs;  // 顶点表
    private Type2[][] adjM; // 邻接矩阵
    private int numVertexs,numEdges; // 图中当前的顶点数和边数
    // 构造函数
    GraphAdjMatrix() throws IOException {
        vexs= (Type1[]) new Object[ MAXVEX ];
        adjM=(Type2[][]) new Comparable[ MAXVEX ][ MAXVEX ];
        createAdjMatrix();
    }
    // 创建一个图（空图是非法的，图的顶点数一定是有穷非空的）
    public void createAdjMatrix() throws IOException {
        String path="E:\\project_file\\IntelliJ_IDEA\\in.txt";
        // 读取输入流
        Scanner in =new Scanner(Paths.get(path),"UTF-8");
        numVertexs=in.nextInt();
        numEdges=in.nextInt();
        int sym=in.nextInt();
        for(int i=0;i<numVertexs;i++){
            vexs[i]=(Type1) in.next();
        }
        for(int i=0;i<numVertexs;i++){
            for(int j=0;j<numVertexs;j++){
                if(i!=j)  adjM[i][j]=(Type2)(Integer) INF;
                else adjM[i][j]=(Type2)(Integer) 0;
            }
        }
        int i,j;
        for(int k=0;k<numEdges;k++){
            i=in.nextInt();
            j=in.nextInt();
            adjM[i][j]=(Type2) in.next();
            if(sym==0){
                adjM[j][i]=adjM[i][j];
            }
        }
    }
    public static void main(String[] args)throws IOException{
        GraphAdjMatrix<String,Integer> g=new GraphAdjMatrix<>();
        System.out.println("yes");
    }
}

