package com.thealgorithms.maths;

/* 标准分数（Z分数） */


public final class StandardScore {
    private StandardScore() {
    }

    public static double zScore(double num, double mean, double stdDev) {
        return (num - mean) / stdDev;
    }
}
