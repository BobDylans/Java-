package com.thealgorithms.greedyalgorithms;

/* 零钱兑换（动态规划） */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// Problem Link : https://en.wikipedia.org/wiki/Change-making_problem

/**
 * The Coin Change problem finds the minimum number of coins needed
 * to make a given amount using a greedy approach.
 *
 * <p>Note: This greedy approach works optimally for standard coin systems
 * (like Indian currency), but may not work for all arbitrary coin sets.
 * For arbitrary denominations, dynamic programming is preferred.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Change-making_problem">Change-making problem</a>
 */
public final class CoinChange {
    private CoinChange() {
    }

    /**
     * Returns the list of coins used to make the given amount
     * using a greedy algorithm with standard denominations.
     *
     * <p>Time Complexity: O(n log n) where n is the number of coin denominations
     * <p>Space Complexity: O(n)
     *
     * @param amount the total amount to make change for
     * @return list of coins used to make the amount
     */
    public static ArrayList<Integer> coinChangeProblem(int amount) {
        // Define an array of coin denominations in descending order
        Integer[] coins = {1, 2, 5, 10, 20, 50, 100, 500, 2000};

        // Sort the coin denominations in descending order
        // 降序排序一下,将面值大的钱放到前面 
        Arrays.sort(coins, Comparator.reverseOrder());

        ArrayList<Integer> ans = new ArrayList<>(); // List to store selected coins

        // Iterate through the coin denominations
        for (int i = 0; i < coins.length; i++) {
            // Check if the current coin denomination can be used to reduce the remaining amount
            // 判断当前面值最大的硬币是否小于要找的零钱数.比如我们要找543元,排除2000,找到500 
            if (coins[i] <= amount) {
                // Repeatedly subtract the coin denomination from the remaining amount
                // 当零钱数依旧大于目前的面额时,while循环 
                while (coins[i] <= amount) {
                    ans.add(coins[i]); // Add the coin to the list of selected coins
                    // 零钱数减去当前面额,即543-500=即43
                    amount -= coins[i]; // Update the remaining amount
                }
                // 之后重复,算法的整体思想就行优先用面额大的钱
            }
        }
        return ans;
    }
}
