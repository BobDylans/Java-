package com.thealgorithms.greedyalgorithms;

/* K中心问题（贪心） */


import java.util.Arrays;

/**
 * Given a set of points and a number k.
 * The goal is to minimize the maximum distance between any point and its nearest center.
 * Each point is assigned to the nearest center.
 * The distance between two points is the Euclidean distance.
 * The problem is NP-hard.
 *
 * @author Hardvan
 */
public final class KCenters {
    private KCenters() {
    }

    /**
     * Finds the maximum distance to the nearest center given k centers.
     * Steps:
     * 1. Initialize an array {@code selected} of size n and an array {@code maxDist} of size n.
     * 2. Set the first node as selected and update the maxDist array.
     * 3. For each center, find the farthest node from the selected centers.
     * 4. Update the maxDist array.
     * 5. Return the maximum distance to the nearest center.
     *
     * @param distances matrix representing distances between nodes
     * @param k         the number of centers
     * @return the maximum distance to the nearest center
     */
    /*
     * 还是先从入参的角度入手看看题目是如何定义的
     * 假设现在有一个数轴,上面分布着n的点,题目要求选择k的点,保证n个点中的任意一个点到k个点中的最长距离最短
     * 题目有点绕,可以简单画个图理解一点,比如有 2,4,10,18 这几个值,现在选择2个点,让所有4个点到2点中最长距离最短
     * 入参目前就是一个二维数组,一维的长度代表有n个点,[i][j]则是i点到j点之间的距离
     * 
     * */
    public static int findKCenters(int[][] distances, int k) {
        int n = distances.length;
        boolean[] selected = new boolean[n];
        int[] maxDist = new int[n];

        Arrays.fill(maxDist, Integer.MAX_VALUE);

        selected[0] = true;
        for (int i = 1; i < n; i++) {
            maxDist[i] = Math.min(maxDist[i], distances[0][i]);
        }

        for (int centers = 1; centers < k; centers++) {
            int farthest = -1;
            for (int i = 0; i < n; i++) {
                if (!selected[i] && (farthest == -1 || maxDist[i] > maxDist[farthest])) {
                    farthest = i;
                }
            }
            selected[farthest] = true;
            for (int i = 0; i < n; i++) {
                maxDist[i] = Math.min(maxDist[i], distances[farthest][i]);
            }
        }

        int result = 0;
        for (int dist : maxDist) {
            result = Math.max(result, dist);
        }
        return result;
    }
}
