package com.thealgorithms.misc;

/* 运行数组的中位数（Float） */


public final class MedianOfRunningArrayFloat extends MedianOfRunningArray<Float> {
    @Override
    public Float calculateAverage(final Float a, final Float b) {
        return (a + b) / 2.0f;
    }
}
