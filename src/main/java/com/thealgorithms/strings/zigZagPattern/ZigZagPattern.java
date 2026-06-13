package com.thealgorithms.strings.zigZagPattern;

/*
 * Z字形编码（Zig-Zag Pattern）算法。
 * 将字符串按照Z字形模式排列，然后按行读取得到编码结果。
 * 常用于LeetCode"ZigZag Conversion"问题。
 */

final class ZigZagPattern {

    private ZigZagPattern() {
    }

    /**
     * Encodes a given string into a zig-zag pattern.
     *
     * @param s       the input string to be encoded
     * @param numRows the number of rows in the zigzag pattern
     * @return the encoded string in zigzag pattern format
     */
    public static String encode(String s, int numRows) {
        if (numRows < 2 || s.length() < numRows) {
            return s;
        }

        StringBuilder result = new StringBuilder(s.length());
        int cycleLength = 2 * numRows - 2;

        for (int row = 0; row < numRows; row++) {
            for (int j = row; j < s.length(); j += cycleLength) {
                result.append(s.charAt(j));

                if (row > 0 && row < numRows - 1) {
                    int diagonal = j + cycleLength - 2 * row;
                    if (diagonal < s.length()) {
                        result.append(s.charAt(diagonal));
                    }
                }
            }
        }

        return result.toString();
    }
}
