package com.thealgorithms.sorts;

/* 排序随机数生成器 */

/* 排序工具随机数生成器
 * 提供用于测试排序算法的随机数生成功能
 */
import java.util.Random;

public final class SortUtilsRandomGenerator {
    private SortUtilsRandomGenerator() {
    }

    private static final Random RANDOM;
    private static final long SEED;

    static {
        SEED = System.currentTimeMillis();
        RANDOM = new Random(SEED);
    }

    /**
     * Function to generate array of double values, with predefined size.
     *
     * @param size result array size
     * @return array of Double values, randomly generated, each element is between [0, 1)
     */
    public static Double[] generateArray(int size) {
        Double[] arr = new Double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = generateDouble();
        }
        return arr;
    }

    /**
     * Function to generate Double value.
     *
     * @return Double value [0, 1)
     */
    public static Double generateDouble() {
        return RANDOM.nextDouble();
    }

    /**
     * Function to generate int value.
     *
     * @return int value [0, n)
     */
    public static int generateInt(int n) {
        return RANDOM.nextInt(n);
    }
}
