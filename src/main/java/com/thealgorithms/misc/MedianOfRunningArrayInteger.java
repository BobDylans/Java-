package com.thealgorithms.misc;

/* 运行数组的中位数（Integer） */


public final class MedianOfRunningArrayInteger extends MedianOfRunningArray<Integer> {
    @Override
    public Integer calculateAverage(final Integer a, final Integer b) {
        return (a + b) / 2;
    }
}
