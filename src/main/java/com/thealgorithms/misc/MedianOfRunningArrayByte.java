package com.thealgorithms.misc;

/* 运行数组的中位数（Byte） */


public final class MedianOfRunningArrayByte extends MedianOfRunningArray<Byte> {
    @Override
    public Byte calculateAverage(final Byte a, final Byte b) {
        return (byte) ((a + b) / 2);
    }
}
