package com.thealgorithms.sorts;

/**
 * 快速排序的简化实现（Lomuto 分区方案）。
 *
 * <p>这是学习用的简化版，核心逻辑：
 * <ol>
 *   <li>选基准 pivot（这里取数组最右端元素）</li>
 *   <li>分区：比 pivot 小的放左，大的放右，pivot 放中间</li>
 *   <li>对左右两半递归排序</li>
 * </ol>
 *
 * <p>时间复杂度：平均 O(n log n)，最坏 O(n²)（数组已有序时）
 * 空间复杂度：O(log n)（递归栈）
 *
 * @see QuickSort 项目里更通用的泛型版本（使用随机化 + Hoare 分区）
 */
public final class QuickSortSimple {

    private QuickSortSimple() {
        // 工具类禁止实例化
    }

    /**
     * 对整型数组升序排序。
     *
     * @param arr 待排序数组
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快排递归主体：先分区，再递归处理左右两半。
     *
     * @param arr  待排序数组
     * @param low  当前区间左边界（含）
     * @param high 当前区间右边界（含）
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high); // 二分点：pivot 的最终位置
            quickSort(arr, low, p - 1);        // 排左半（比 pivot 小的）
            quickSort(arr, p + 1, high);       // 排右半（比 pivot 大的）
        }
    }

    /**
     * Lomuto 分区方案：选最右端为 pivot，把小于等于 pivot 的全部挪到左边。
     *
     * @param arr  待排序数组
     * @param low  区间左边界
     * @param high 区间右边界
     * @return pivot 排序后所在的索引（此时它左边全 ≤ pivot，右边全 > pivot）
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // 选基准：最右端元素
        int i = low - 1;       // i 指向"小于等于 pivot 区间"的末尾

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j); // 把当前小元素挪到左边
            }
        }
        swap(arr, i + 1, high); // 把 pivot 放到中间，它现在归位了
        return i + 1;           // 返回 pivot 的最终位置
    }

    /**
     * 交换数组中两个位置的元素。
     */
    private static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
