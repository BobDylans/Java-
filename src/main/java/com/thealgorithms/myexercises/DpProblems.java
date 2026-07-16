package com.thealgorithms.myexercises;

import java.util.ArrayList;
import java.util.Arrays;
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


    public int numSquares(int n) {
      int[] f = new int[n+1];
      for(int i = 0; i <= n; i++){
        int minn = Integer.MAX_VALUE;
        // i代表的当前目标数字(也就是判断这个数是有几个完全平方数构成的)
        // 当j^2大于i时就结束内层循环
        // 因为我们首先要找到比i小的最大的完全平方数(j^2)!
        for(int j = 0; j * j < i; j++){
          // 具体的逻辑是
          // 一步一步增加j,逐渐逼近i
          // 只要j^2还小于i,就尝试获取 f[i - j^2]的值,因为我们遍历的过程中就从小到大将每一位所需要的最少完全平方数记录下来了
          // 比如10,内部循环会一次遍历 j = 1,2,3(1,4,9);
          // 每一次都是假设自己拿了对应的值(比如1),那么剩下的就是9,而根据外层循环可知,组成9的最少完全平方数我们是知道的
          // 所以比较minn和f[9]的大小,尝试更新min(因为后面j =2,3时的对应f[i - j^2]可能更小!)如果有更小的选项我们就更新minn
          minn = Math.min(minn,f[i - j *j]);
        }
        // 内层循环结束时,相当于找到了i-j^2所需要的最少完全平方数,我们只需要+1即可
        // 因为实际上我们这一步选了j^2,只是需要知道 i - j^2 的所需的最少值 
        f[i] = minn + 1;
      }
      return f[n];
    }
    public int coinChange(int[] coins, int amount) {
      // 将每一位的最大值都改成amount + 1,这样就能保证更新
      int max = amount + 1;
      int[] dp = new int[amount +1];
      // 调用Arrays中的fill方法将dp中每一位都先默认置成amount + 1;
      Arrays.fill(dp,max);
      for(int i = 1; i < amount; i++){
        // 针对每一种硬币类型,尝试更新
        for(int j = 0; j< coins.length;j++){
          if(coins[j] < i){
            dp[i] = Math.min(dp[i - coins[j]] + 1, dp[i]);
          }
        } 
      }
      
      return dp[amount] > amount ? -1 : dp[amount];
    }
    public int lengthOfLIS(int[] nums) {
        if(nums.length == 0){
            return 0;
        }
        int length = nums.length;
        int[] dp = new int[length];
        dp[0] = 1;
        int maxans = 1;
        for(int i = 0; i < length; i++){
            dp[i] = 1;
            for(int j = 0; j < i; j++){
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxans = Math.max(maxans,dp[i]);
        }
        return maxans;
    }

}
