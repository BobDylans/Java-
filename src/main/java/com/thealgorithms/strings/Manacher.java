package com.thealgorithms.strings;

/*
 * Manacher - 求解最长回文子串算法
 * 
 * 使用Manacher算法在O(n)时间复杂度内找出字符串中最长的回文子串。
 * 通过预处理字符串(插入分隔符)避免奇偶讨论,并利用已计算的回文半径信息加速后续计算。
 */

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
 */
public final class Manacher {

    private Manacher() {
    }

    /**
     * Finds the longest palindromic substring using Manacher's Algorithm
     *
     * @param s The input string
     * @return The longest palindromic substring in {@code s}
     */
    public static String longestPalindrome(String s) {
        final String processedString = preprocess(s);
        int[] palindromeLengths = new int[processedString.length()];
        int center = 0;
        int rightBoundary = 0;
        int maxLen = 0;
        int centerIndex = 0;

        for (int i = 1; i < processedString.length() - 1; i++) {
            int mirror = 2 * center - i;

            if (i < rightBoundary) {
                palindromeLengths[i] = Math.min(rightBoundary - i, palindromeLengths[mirror]);
            }

            while (processedString.charAt(i + 1 + palindromeLengths[i]) == processedString.charAt(i - 1 - palindromeLengths[i])) {
                palindromeLengths[i]++;
            }

            if (i + palindromeLengths[i] > rightBoundary) {
                center = i;
                rightBoundary = i + palindromeLengths[i];
            }

            if (palindromeLengths[i] > maxLen) {
                maxLen = palindromeLengths[i];
                centerIndex = i;
            }
        }

        final int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }

    /**
     * Preprocesses the input string by inserting a special character ('#') between each character
     * and adding '^' at the start and '$' at the end to avoid boundary conditions.
     *
     * @param s The original string
     * @return The preprocessed string with additional characters
     */
    private static String preprocess(String s) {
        if (s.isEmpty()) {
            return "^$";
        }
        StringBuilder sb = new StringBuilder("^");
        for (char c : s.toCharArray()) {
            sb.append('#').append(c);
        }
        sb.append("#$");
        return sb.toString();
    }
}
