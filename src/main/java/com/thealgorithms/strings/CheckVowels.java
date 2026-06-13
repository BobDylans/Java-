package com.thealgorithms.strings;

import java.util.Set;

/*
 * CheckVowels - 元音字母检测工具
 * 
 * 检测字符串中是否包含元音字母(a,e,i,o,u)。
 * 辅助用于字典排序等字符串处理功能。
 */

/**
 * Vowel Count is a system whereby character strings are placed in order based
 * on the position of the characters in the conventional ordering of an
 * alphabet. Wikipedia: https://en.wikipedia.org/wiki/Alphabetical_order
 */
public final class CheckVowels {
    private static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u');

    private CheckVowels() {
    }

    /**
     * Checks if a string contains any vowels.
     *
     * @param input a string to check
     * @return {@code true} if the given string contains at least one vowel, otherwise {@code false}
     */
    public static boolean hasVowels(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (char c : input.toLowerCase().toCharArray()) {
            if (VOWELS.contains(c)) {
                return true;
            }
        }
        return false;
    }
}
