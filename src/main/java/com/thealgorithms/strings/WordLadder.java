package com.thealgorithms.strings;

/*
 * 词语阶梯（Word Ladder）算法。
 * 给定起始词和目标词，以及一个词典，找出从起始词转换到目标词的最短序列。
 * 每次转换只能改变一个字母，且转换过程中的每个词都必须存在于词典中。
 * 使用广度优先搜索（BFS）找到最短路径。
 */

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Class to find the shortest transformation sequence from a beginWord to an endWord using a dictionary of words.
 * A transformation sequence is a sequence of words where each adjacent pair differs by exactly one letter.
 */
public final class WordLadder {
    private WordLadder() {
    }

    /**
     * Finds the shortest transformation sequence from beginWord to endWord.
     *
     * @param beginWord the starting word of the transformation sequence
     * @param endWord the target word of the transformation sequence
     * @param wordList a list of words that can be used in the transformation sequence
     * @return the number of words in the shortest transformation sequence, or 0 if no such sequence exists
     */
    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                char[] currentWordChars = currentWord.toCharArray();
                for (int j = 0; j < currentWordChars.length; j++) {
                    char originalChar = currentWordChars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (currentWordChars[j] == c) {
                            continue;
                        }
                        currentWordChars[j] = c;
                        String newWord = new String(currentWordChars);

                        if (newWord.equals(endWord)) {
                            return level + 1;
                        }
                        if (wordSet.remove(newWord)) {
                            queue.offer(newWord);
                        }
                    }
                    currentWordChars[j] = originalChar;
                }
            }
            level++;
        }
        return 0;
    }
}
