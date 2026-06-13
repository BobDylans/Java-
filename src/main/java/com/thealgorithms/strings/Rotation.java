package com.thealgorithms.strings;

/*
 * 字符串旋转算法。
 * 将字符串前n个字符移动到末尾，或对字符数组进行原地旋转。
 * 例如："abcdef"旋转2位后变为"cdefab"。
 */

public final class Rotation {
    private Rotation() {
    }

    public static void main(String[] args) {
        assert rotation("abcdef", 2).equals("cdefab");

        char[] values = "abcdef".toCharArray();
        rotation(values, 2);
        assert new String(values).equals("cdefab");
    }

    /**
     * Move {@code n} characters in front of given string to the end of string
     * time complexity: O(n) space complexity: O(n)
     *
     * @param s given string
     * @param n the total characters to be moved
     * @return string after rotation
     */
    public static String rotation(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    /**
     * Move {@code n} characters in front of given character array to the end of
     * array time complexity: O(n) space complexity: O(1)
     *
     * @param values given character array
     * @param n the total characters to be moved
     */
    public static void rotation(char[] values, int n) {
        reverse(values, 0, n - 1);
        reverse(values, n, values.length - 1);
        reverse(values, 0, values.length - 1);
    }

    /**
     * Reverse character array
     *
     * @param values character array
     * @param from begin index of given array
     * @param to end index of given array
     */
    public static void reverse(char[] values, int from, int to) {
        while (from < to) {
            char temp = values[from];
            values[from] = values[to];
            values[to] = temp;
            from++;
            to--;
        }
    }
}
