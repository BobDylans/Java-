package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/*
 * LongestNonRepetitiveSubstring - 查找最长无重复字符子串
 * 
 * 使用滑动窗口和HashMap记录字符最后出现的位置,
 * 当遇到重复字符时调整窗口起始位置。
 * 时间复杂度: O(n), 空间复杂度: O(min(n,m))
 */

/**
 * Class for finding the length of the longest substring without repeating characters.
 */
final class LongestNonRepetitiveSubstring {
    private LongestNonRepetitiveSubstring() {
    }

    /**
     * Finds the length of the longest substring without repeating characters.
     *
     * Uses the sliding window technique with a HashMap to track
     * the last seen index of each character.
     *
     * Time Complexity: O(n), where n is the length of the input string.
     * Space Complexity: O(min(n, m)), where m is the size of the character set.
     *
     * @param s the input string
     * @return the length of the longest non-repetitive substring
     */
    public static int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int start = 0;
        Map<Character, Integer> charIndexMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);

            // If the character is already in the map and its index is within the current window
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= start) {
                // Move the start to the position right after the last occurrence of the current character
                start = charIndexMap.get(currentChar) + 1;
            }

            // Update the last seen index of the current character
            charIndexMap.put(currentChar, i);

            // Calculate the maximum length of the substring without repeating characters
            maxLength = Math.max(maxLength, i - start + 1);
        }

        return maxLength;
    }
}
