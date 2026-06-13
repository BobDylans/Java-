package com.thealgorithms.sorts;

/* 二分插入排序 */

/**
 * BinaryInsertionSort class implements the SortAlgorithm interface using the
 * binary insertion sort technique.
 * Binary Insertion Sort improves upon the simple insertion sort by using binary
 * search to find the appropriate
 * location to insert the new element, reducing the number of comparisons in the
 * insertion step.
 */
public class BinaryInsertionSort implements SortAlgorithm {

  /**
   * Sorts the given array using the Binary Insertion Sort algorithm.
   *
   * @param <T>   the type of elements in the array, which must implement the
   *              Comparable interface
   * @param array the array to be sorted
   * @return the sorted array
   */
  public <T extends Comparable<T>> T[] sort(T[] array) {
    for (int i = 1; i < array.length; i++) {
      final T temp = array[i];
      // 将其分为已排序区
      int low = 0;
      int high = i - 1;
      // 这里使用的是二分查找,找到temp应该插入的位置,本质上还是插入算法
      while (low <= high) {
        // 这些写法的作用就是无符号右移,也就是➗2
        // 左右相加获取到中间值
        final int mid = (low + high) >>> 1;
        if (SortUtils.less(temp, array[mid])) {
          high = mid - 1;
        } else {
          low = mid + 1;
        }
      }
      // 从temp的位置开始整体向右移动,也就是要为temp的位置空出空间
      for (int j = i; j >= low + 1; j--) {
        array[j] = array[j - 1];
      }

      array[low] = temp;
    }
    return array;
  }
}
