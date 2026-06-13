package com.thealgorithms.sorts;

/* 臭皮匠排序 */

/* Stooge排序（幽默命名）
 * 一种递归但非常低效的排序算法，时间复杂度为O(n^(log3/log1.5))
 * 不断将数组分成三部分并递归交换排序
 */
import java.util.Arrays;

/**
 * @author Amir Hassan (https://github.com/ahsNT)
 * @see SortAlgorithm
 */
public class StoogeSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }
        sort(array, 0, array.length);
        return array;
    }

    public <T extends Comparable<T>> T[] sort(final T[] array, final int start, final int end) {
        if (SortUtils.less(array[end - 1], array[start])) {
            final T temp = array[start];
            array[start] = array[end - 1];
            array[end - 1] = temp;
        }

        final int length = end - start;
        if (length > 2) {
            int third = length / 3;
            sort(array, start, end - third);
            sort(array, start + third, end);
            sort(array, start, end - third);
        }
        return array;
    }
}
