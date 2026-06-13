package com.thealgorithms.sorts;

/* 冒泡排序 */

/**
 * @author Varun Upadhyay (https://github.com/varunu28)
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 * @see SortAlgorithm
 */
class BubbleSort implements SortAlgorithm {

  /**
   * Implements generic bubble sort algorithm.
   *
   * Time Complexity:
   * - Best case: O(n) – array is already sorted.
   * - Average case: O(n^2)
   * - Worst case: O(n^2)
   *
   * Space Complexity: O(1) – in-place sorting.
   *
   * @param array the array to be sorted.
   * @param <T>   the type of elements in the array.
   * @return the sorted array.
   */
  @Override
  public <T extends Comparable<T>> T[] sort(T[] array) {
    // 每一轮都会有至少一个元素到达了正确的位置,所以最坏的情况就是执行(n^2)次
    for (int i = 1, size = array.length; i < size; ++i) {
      boolean swapped = false;
      for (int j = 0; j < size - i; ++j) {
        if (SortUtils.greater(array[j], array[j + 1])) {
          SortUtils.swap(array, j, j + 1);
          swapped = true;
        }
      }
      if (!swapped) {
        break;
      }
    }
    return array;
  }
}
