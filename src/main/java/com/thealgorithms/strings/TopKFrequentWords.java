package com.thealgorithms.strings;

/*
 * Top-K高频词算法。
 * 从给定的单词数组中找出出现频率最高的K个词。
 * 结果按频率降序排列，频率相同时按字典序升序排列。
 * 参考资料：https://en.wikipedia.org/wiki/Top-k_problem
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to find the top-k most frequent words.
 *
 * <p>Words are ranked by frequency in descending order. For equal frequencies,
 * words are ranked in lexicographical ascending order.
 *
 * <p>Reference:
 * https://en.wikipedia.org/wiki/Top-k_problem
 *
 */
public final class TopKFrequentWords {
    private TopKFrequentWords() {
    }

    /**
     * Finds the k most frequent words.
     *
     * @param words input array of words
     * @param k number of words to return
     * @return list of top-k words ordered by frequency then lexicographical order
     * @throws IllegalArgumentException if words is null, k is negative, or words contains null
     */
    public static List<String> findTopKFrequentWords(String[] words, int k) {
        if (words == null) {
            throw new IllegalArgumentException("Input words array cannot be null.");
        }
        if (k < 0) {
            throw new IllegalArgumentException("k cannot be negative.");
        }
        if (k == 0 || words.length == 0) {
            return List.of();
        }

        Map<String, Integer> frequency = new HashMap<>();
        for (String word : words) {
            if (word == null) {
                throw new IllegalArgumentException("Input words cannot contain null values.");
            }
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        List<String> candidates = new ArrayList<>(frequency.keySet());
        candidates.sort(Comparator.<String>comparingInt(frequency::get).reversed().thenComparing(Comparator.naturalOrder()));

        int limit = Math.min(k, candidates.size());
        return new ArrayList<>(candidates.subList(0, limit));
    }
}
