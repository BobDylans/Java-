package com.thealgorithms.sorts;

/* 选择排序 */

/* 选择排序（Selection Sort）
 * 每次从未排序部分选择最小（或最大）元素放到已排序部分
 * 时间复杂度O(n²)，空间复杂度O(1)
 */
public class SelectionSort implements SortAlgorithm {
    /**
     * Generic Selection Sort algorithm.
     *
     * Time Complexity:
     * - Best case: O(n^2)
     * - Average case: O(n^2)
     * - Worst case: O(n^2)
     *
     * Space Complexity: O(1) – in-place sorting.
     *
     * @see SortAlgorithm
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            final int minIndex = findIndexOfMin(array, i);
            SortUtils.swap(array, i, minIndex);
        }
        return array;
    }

    private static <T extends Comparable<T>> int findIndexOfMin(T[] array, final int startIndex) {
        int minIndex = startIndex;
        for (int i = startIndex + 1; i < array.length; i++) {
            if (SortUtils.less(array[i], array[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }
}
