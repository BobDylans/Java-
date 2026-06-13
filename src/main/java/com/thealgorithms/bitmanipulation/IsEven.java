package com.thealgorithms.bitmanipulation;

/* 判断偶数（位运算） */


/**
 * Checks whether a number is even
 * @author Bama Charan Chhandogi (https://github.com/BamaCharanChhandogi)
 */

public final class IsEven {
    private IsEven() {
    }
    public static boolean isEven(int number) {
        return (number & 1) == 0;
    }
}
