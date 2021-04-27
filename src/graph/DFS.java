package graph;

import java.util.*;

import static java.util.Collections.*;

public class DFS {
    /**
     * 题目：网络延迟时间
     * @param times
     * @param n
     * @param k
     * @return
     * @see https://leetcode-cn.com/problems/network-delay-time/
     * 题解思路：从节点出发，根据深度优先搜索算法来确定所有节点的最短访问时间。
     */
    HashMap<Integer, Integer> dist;
    public int networkDelayTime(int[][] times, int N, int K) {
        //初始化图
        HashMap<Integer, List<int[]>> graph = new HashMap();
        for (int[] edge: times) {
            if (!graph.containsKey(edge[0]))
                graph.put(edge[0], new ArrayList<int[]>());
            graph.get(edge[0]).add(new int[]{edge[2], edge[1]});
        }
        for (int node: graph.keySet()) {
            graph.get(node).sort((a, b) -> a[0] - b[0]);
        }
        dist = new HashMap();
        for (int node = 1; node <= N; ++node)
            dist.put(node, Integer.MAX_VALUE);

        dfsNetworkDelayTime(graph, K, 0);
        int ans = 0;
        for (int candy: dist.values()) {
            if (candy == Integer.MAX_VALUE) return -1;
            ans = Math.max(ans, candy);
        }
        return ans;
    }
    public void dfsNetworkDelayTime(Map<Integer, List<int[]>> graph, int node, int elapsed) {
        if (elapsed >= dist.get(node)) return;
        dist.put(node, elapsed);
        if (graph.containsKey(node))
            for (int[] info: graph.get(node))
                dfsNetworkDelayTime(graph, info[1], elapsed + info[0]);
    }

    /**
     * 解法二：
     * @param times
     * @param N
     * @param K
     * @return
     */
    public int networkDelayTimeII(int[][] times, int N, int K) {
        // time[i]: node [i] receive time
        int[] time = new int[N+1];
        Arrays.fill(time, -1);
        time[K] = 0;

        // graph[i]: List<int[]>, [to node, w]
        List<int[]>[] graph = new List[N+1];
        for (int i = 1; i <= N; ++i) {
            graph[i] = new LinkedList<>();
        }
        for (int[] t : times) {
            int from = t[0], to = t[1], w = t[2];
            graph[from].add(new int[]{to, w});
        }

        dfsNetworkDelayTimeII(graph, time, K);

        int max = -1;
        for (int i = 1; i <= N; ++i) {
            if (time[i] == -1) return -1;
            max = Math.max(max, time[i]);
        }
        return max;
    }

    public void dfsNetworkDelayTimeII(List<int[]>[] graph, int[] time, int node) {
        for (int[] t : graph[node]) {
            int to = t[0], w = t[1];
            int newTime = time[node] + w;
            if (time[to] != -1 && newTime >= time[to]) {
                continue;
            }
            time[to] = newTime;
            dfsNetworkDelayTimeII(graph, time, to);
        }
    }



}
