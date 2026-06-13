package com.thealgorithms.strings;

/*
 * 移除字符串中的重复字符算法。
 * 从字符串中删除所有重复出现的字符，仅保留首次出现的位置，
 * 保持原有字符顺序不变。
 */

/**
 * @author Varun Upadhyay (https://github.com/varunu28)
 */
public final class RemoveDuplicateFromString {
    private RemoveDuplicateFromString() {
    }

    /**
     * Removes duplicate characters from the given string.
     *
     * @param input The input string from which duplicate characters need to be removed.
     * @return A string containing only unique characters from the input, in their original order.
     */
    public static String removeDuplicate(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder uniqueChars = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (uniqueChars.indexOf(String.valueOf(c)) == -1) {
                uniqueChars.append(c); // Append character if it's not already in the StringBuilder
            }
        }

        return uniqueChars.toString();
    }
}
