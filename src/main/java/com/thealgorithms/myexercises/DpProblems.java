package com.thealgorithms.myexercises;

import java.util.ArrayList;
import java.util.List;

/**
 * 收集动态规划相关题目
 */
public class DpProblems {

    /**
     * 763. 划分字母区间
     */
    public List<Integer> partitionLabels(String s) {
        // 相当于使用一个数组来存储每一个字符最后出现的位置
        int[] last = new int[26];
        int length = s.length();
        for (int i = 0; i < length; i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        List<Integer> partition = new ArrayList<>();
        int start = 0, end = 0;
        for (int i = 0; i < length; i++) {
            // 我们要时刻更新end的值.加入第一个字母出现的位置为 m,那么实际上从下标 0 ~ m我们必须都加进去
            // 但是在加进去的过程中,新的字母的最后出现位置可能还会右移
            // 因此时刻更新
            end = Math.max(end, last[s.charAt(i) - 'a']);
            // 知道某一次,end是所有已被选中的字母的end边界
            if (i == end) {
                // 我们更新一下res中的终点
                // 已经对应的起点 start
                partition.add(end - start + 1);
                start = end + 1;
            }
        }
        return partition;
    }

    /**
     * 70. 爬楼梯 (滚动数组优化版)
     */
    public int climbStairs(int n) {
        int p = 0, q = 0, r = 1;
        for (int i = 1; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }

    /**
     * 70. 爬楼梯 (标准DP版)
     */
    public int climbStairsDP(int n) {
        if (n <= 1) return 1;
        int[] DP = new int[n + 1];
        DP[0] = 1;
        DP[1] = 1;
        for (int i = 2; i <= n; i++) {
            DP[i] = DP[i - 1] + DP[i - 2];
        }
        return DP[n];
    }

    /**
     * 118. 杨辉三角
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < numRows; ++i) {
            // 进入后先创建一个用来记录当前层的row,其长度和当前所在的i层有关(也就是j = i)
            List<Integer> row = new ArrayList<>();
            // 从0到i分别算出对应的值
            for (int j = 0; j <= i; ++j) {
                // 默认的0,i位置对应的值就是0
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    // 使用dp,当前的值之和前两个位置的值有关
                    row.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
                }
            }
            ret.add(row);
        }
        return ret;
    }

    /**
     * 198. 打家劫舍
     */
    public int rob(int[] nums) {
        int length = nums.length;
        if (length == 0) return 0;
        if (length == 1) return nums[0]; // 修复了原代码中 nums[1] 的越界风险
        
        int[] most = new int[length];
        // 为之后的更新做准备
        most[0] = nums[0];
        most[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            most[i] = Math.max(most[i - 1], nums[i] + most[i - 2]);
        }
        // 因为most的语义是获取前i+1间房最大的金额
        return most[length - 1];
    }
}
