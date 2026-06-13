package com.thealgorithms.misc;

/* 运行数组的中位数（Long） */


public final class MedianOfRunningArrayLong extends MedianOfRunningArray<Long> {
    @Override
    public Long calculateAverage(final Long a, final Long b) {
        return (a + b) / 2L;
    }
}
