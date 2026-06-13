package com.thealgorithms.strings;

/*
 * 删除星号及其左侧字符算法。
 * 遍历字符串，每个'*'会删除其左侧最近的非星号字符。
 * 例如："leet**cod*e" -> "lecoe"（第一个**删除两个e，*e删除c）
 */

public final class RemoveStars {

    private RemoveStars() {
    }

    public static String removeStars(String s) {
        StringBuilder result = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (c == '*') {
                if (result.length() > 0) {
                    result.deleteCharAt(result.length() - 1);
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
