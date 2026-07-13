package com.thealgorithms.sorts;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * {@link QuickSortSimple} 的单元测试。
 */
class QuickSortSimpleTest {

    @Test
    void testNormalCase() {
        int[] arr = {5, 3, 8, 1, 9, 2, 7};
        QuickSortSimple.sort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 5, 7, 8, 9}, arr);
    }

    @Test
    void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        QuickSortSimple.sort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        QuickSortSimple.sort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testWithDuplicates() {
        int[] arr = {3, 1, 2, 3, 1, 2};
        QuickSortSimple.sort(arr);
        assertArrayEquals(new int[] {1, 1, 2, 2, 3, 3}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        QuickSortSimple.sort(arr);
        assertArrayEquals(new int[] {42}, arr);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        QuickSortSimple.sort(arr);
        assertArrayEquals(new int[] {}, arr);
    }

    @Test
    void testNullArray() {
        assertDoesNotThrow(() -> QuickSortSimple.sort(null));
    }
}
