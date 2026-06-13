package com.thealgorithms.sorts;

/* 慢速排序 */

/* 慢速排序（Slow Sort）
 * 一种递归但幽默地低效的排序算法
 * 不断分治并交换元素，时间复杂度较高
 */
import java.util.Arrays;

/**
 * @author Amir Hassan (https://github.com/ahsNT)
 * @see SortAlgorithm
 */
public class SlowSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsortedArray) {
        sort(unsortedArray, 0, unsortedArray.length - 1);
        return unsortedArray;
    }

    private <T extends Comparable<T>> void sort(T[] array, int i, int j) {
        if (SortUtils.greaterOrEqual(i, j)) {
            return;
        }
        final int m = (i + j) >>> 1;
        sort(array, i, m);
        sort(array, m + 1, j);
        if (SortUtils.less(array[j], array[m])) {
            SortUtils.swap(array, j, m);
        }
        sort(array, i, j - 1);
    }
}
