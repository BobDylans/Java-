package com.thealgorithms.greedyalgorithms;

/* 合并区间（贪心） */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem Statement:
 * Given an array of intervals where intervals[i] = [starti, endi].
 *
 * Merge all overlapping intervals and return an array of the non-overlapping
 * intervals
 * that cover all the intervals in the input.
 */
public final class MergeIntervals {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private MergeIntervals() {
    }

    /**
     * Merges overlapping intervals from the given array of intervals.
     *
     * The method sorts the intervals by their start time, then iterates through the
     * sorted intervals
     * and merges overlapping intervals. If an interval overlaps with the last
     * merged interval,
     * it updates the end time of the last merged interval. Otherwise, it adds the
     * interval as a new entry.
     *
     * @param intervals A 2D array representing intervals where each element is an
     *                  interval [starti, endi].
     * @return A 2D array of merged intervals where no intervals overlap.
     *
     *         Example:
     *         Input: {{1, 3}, {2, 6}, {8, 10}, {15, 18}}
     *         Output: {{1, 6}, {8, 10}, {15, 18}}
     */
    /*
     * 这个算法的核心就是首先将多个区间按照起点的大小从小到大排序
     * */
    public static int[][] merge(int[][] intervals) {
        // Sort the intervals by their start time (ascending order)
        // 这里也有一个函数式编程的例子,也就是因为这个sort需要传入一个方法方便直接调用
        // 但是java中不允许直接将func作为基本类型,所以使用接口(函数式接口)
        // 前一个参数就是数组,第二个是函数式接口,最后的效果就是将这个数组排序
        // 排序的规则按照传入的接口来,也就是每个区间的起始量的大小 
        // 每个区间其实就只有两个值,也就是起点和终点 
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // List to store merged intervals
        List<int[]> merged = new ArrayList<>();
        // 对排好序的数组list进行便利
        for (int[] interval : intervals) { // Each interval
            // If the merged list is empty or the current interval does not overlap with
            // the last merged interval, add it to the merged list.
            // 如果merged(即目标数组)为null,或者当前的数组的起点大于merged的终点,直接加到merged的最后
            if (merged.isEmpty() || interval[0] > merged.get(merged.size() - 1)[1]) {
                merged.add(interval);
            } else {
                // If there is an overlap, merge the intervals by updating the end time
                // of the last merged interval to the maximum end time between the two
                // intervals.
                // 否则就需要更新一下了,将merged的终点改成当前终点和第二个区间的终点中较大值
                // 这样是为了避免后面那个是个小区间被包含进去的情况 
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
            }
        }

        // Convert the list of merged intervals back to a 2D array and return it
        return merged.toArray(new int[merged.size()][]);
    }
}
