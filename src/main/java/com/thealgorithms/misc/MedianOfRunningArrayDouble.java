package com.thealgorithms.misc;

/* 运行数组的中位数（Double） */


public final class MedianOfRunningArrayDouble extends MedianOfRunningArray<Double> {
    @Override
    public Double calculateAverage(final Double a, final Double b) {
        return (a + b) / 2.0d;
    }
}
