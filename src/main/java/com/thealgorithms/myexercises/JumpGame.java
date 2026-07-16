package com.thealgorithms.myexercises;

/**
 * 55. 跳跃游戏 II (Jump Game II)
 * 贪心算法：维护当前可到达的最远距离，到达边界时步数 +1
 */
public class JumpGame {
    public int jump(int[] nums) {
        int length = nums.length;
        // 创建了一个免费区,代表着当前即使不需要多迈出一步也能直接到达的最远距离
        int curend = 0, maxFar = 0;
        // 使用一个step来记录当前走了多远
        int step = 0;
        for (int i = 0; i < length - 1; i++) {
            // 每次进来之后首先更新一下在免费区中所有元素最远能到达的距离
            maxFar = Math.max(maxFar, i + nums[i]);
            // 当到达免费区的边界时,就用maxFar更新免费区的边界
            // 并且step++
            if (i == curend) {
                step++;
                curend = maxFar;
            }
        }
        return step;
    }
}
