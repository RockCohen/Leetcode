package graph;

/**
 * 并查集相关题目
 */
public class DisjointSetUnion {
    /**
     * 题目：冗余链接
     * @param edges 图
     * @return
     * @see https://leetcode-cn.com/problems/redundant-connection/
     * 题解思路：直接套用并查集模板即可搞定。
     */
    int[] res;
    public int[] findRedundantConnection(int[][] edges) {
        int[] parent=new int[edges.length+1];//边的条数便是节点的个数
        for(int i=1;i<parent.length;i++){
            parent[i]=i;
        }
        for(int[] x:edges){
            if(union(x[0],x[1],parent))
                return res;
        }
        return res;
    }
    public int findRoot(int x,int[] parent){
        while(x!=parent[x]){
            parent[x]=parent[parent[x]];
            x=parent[x];
        }
        return x;
    }
    public boolean union(int a, int b, int[] parent){
        int min=Math.min(a,b);
        int max=Math.max(a,b);
        a=findRoot(a,parent);
        b=findRoot(b,parent);
        if(a==b){
            res=new int[]{min,max};
            return true;
        }
        else{
            parent[a]=b;
            return false;
        }
    }
}
