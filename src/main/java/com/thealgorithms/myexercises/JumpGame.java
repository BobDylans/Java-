package com.thealgorithms.myexercises;

import java.util.Deque;
import java.util.LinkedList;

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

    public int maxProduct(int[] nums) {
        // 这个题目的核心思想是因为负数*负数反而会得到一个很大的数
        // 因此需要同时记录最大值和最小值,针对正负执行不同的逻辑
        int length = nums.length;
        long[] maxF = new long[length];
        long[] minF = new long[length];
        for (int i = 0; i < length; i++) {
            // 将正负dp的值都置为对应位置的值
            maxF[i] = nums[i];
            minF[i] = nums[i];
        }
        for (int i = 1; i < length; ++i) {
            maxF[i] = Math.max(maxF[i - 1] * nums[i], Math.max(nums[i], minF[i - 1] * nums[i]));
            minF[i] = Math.min(minF[i - 1] * nums[i], Math.min(nums[i], maxF[i - 1] * nums[i]));
            if (minF[i] < (-1 << 31)) {
                minF[i] = nums[i];
            }
        }
        int ans = (int) maxF[0];
        for (int i = 1; i < length; ++i) {
            ans = Math.max(ans, (int) maxF[i]);
        }
        return ans;
    }
    // 上方代码的简化版本
    public int maxProductSimple(int[] nums) {
      long maxF = nums[0], minF = nums[0], ans = nums[0];
      for (int i = 1; i < nums.length; i++) {
          //  每次都以目前的最大值和最小值乘以对应的数
          //  也就是上一位的最大值和最小值分别乘当前的数
          long a = maxF * nums[i];
          long b = minF * nums[i];
          // 三选一取最大,分别是当前值,a,b 
          // 实际上是因为我们不确定当前正负关系,直接比较就行
          maxF = Math.max(a, Math.max(b, nums[i]));
          // 三选一取最小
          minF = Math.min(a, Math.min(b, nums[i]));
          // 更新一下ans 
          ans  = Math.max(ans, maxF);
      }
      return (int) ans;
    }
    public boolean canPartition(int[] nums) {
        // 在进行变成前提前进行剪枝操作
        // 如果nums的长度小于2,说明不可能存在子数组
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        // 计算sum,如果sum为奇数说明不可能均分成两份
        int sum = 0, maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        // 直接返回即可
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        // 比较最大值和 sum/2 的大小,如果maxNum 大于 sum/2,说明剩余的所有其它元素加起来也不能等于单一个maxNum
        // 直接返回即可
        if (maxNum > target) {
            return false;
        }
        // 之后的问题就变成了背包问题
        // 即一个数组中能不能选出一个大小刚好为 sum/2 数 
        // dp[i]代表 i 对应的值能不能被凑出来 
        boolean[] dp = new boolean[target + 1];
        // 默认0一定是能被凑出来的
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            // 倒叙进行检索,target就是通过剪枝后剩下的 sum/2 合理的结果
            // 对于容量j,如果本来就能凑到j(即dp[j]为true)或者dp[j - num]
            // 核心思想是不直接计算能否得到target,而是每次添加num(也就是外层的循环),添加之后就尝试去更新 j - num 的值,
            // 实际上就是更新j的值,
            for (int j = target; j >= num; --j) {
                dp[j] |= dp[j - num];
            }
        }
        return dp[target];
    }
    public int longestValidParentheses(String s) {
        int maxans = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        stack.push(-1);
        // 循环遍历整个s
        for (int i = 0; i < s.length(); i++) {
            // 如果为 "(" 左括号,直接入栈即可
            // 这里入栈的实际上是左括号的下标
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                // 否则说明是右括号
                // 吐出栈顶元素
                stack.pop();
                // 如果直接导致栈为空,说明左右括号不匹配,
                // 将当前的下标作为新的起点
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    // 如果栈不为空,说明匹配上了,用当前的下标 i-栈顶尚未匹配的元素的下标 
                    // 并且和maxans进行比较,若变大就更新
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int rows = grid.length, columns = grid[0].length;
        int[][] dp = new int[rows][columns];
        dp[0][0] = grid[0][0];
        // 第一行只能从左往右走,直接累加就行
        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        // 第一列是同理,只能从上往下走,依旧累加
        // 上面的两部都在进行初始化
        for (int j = 1; j < columns; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        // 之后进行一次二次循环,从上,右两个方向中选择一个最小的
        // 加上当前节点的值即可
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[rows - 1][columns - 1];
    }
    // 背包问题,外包内物,从1到n便利每一个数,
    // 然后进入内循环判断每一个完全平方数加入后是否能减小组合的上限
    public int numSquares(int n) {
      int[] dp = new int[n+1];
      for(int i = 1; i <=n; i++){
        int minn = Integer.MAX_VALUE;
        for(int j = 1;j * j< i; j++){
          // 尝试更新dp,就是按照i - j*j的值是否存在之前的dp中
          dp[i] = Math.min(minn,dp[i - j*j]); 
        }
        dp[i] = minn +1;
      }
      return dp[n];
    }
  
    public int maxValue(int W, int[] weights, int[] values) {
        // dp[i] 表示背包容量为 i 时能装的最大价值
        int[] dp = new int[W + 1];

        // 外层遍历物品
        for (int i = 0; i < weights.length; i++) {
            int weight = weights[i];
            int value = values[i];
            
            // 内层必须从大到小逆序遍历背包容量！
            // 只要剩余容量 i 大于等于当前物品的重量，就尝试装入
            for (int i1 = W; i1 >= weight; i1--) {
                // 核心抉择：Math.max(不放当前物品, 放当前物品并累加剩余容量的最大价值)
                // 尝试更新dp,具体的逻辑是当前的价值dp[i]和dp[i - weight] + value
                // 这里的weight和value分别对应的是当前选中的物体.
                // 也就是假设如果选取了之后,是否能放背包中的value更大
                // 重点是这里一定是逆序的,因为我们在更新的过程中依赖较小的值的dp
                // 如果正序,会导致之后的dp会被自己所污染
                dp[i1] = Math.max(dp[i1], dp[i1 - weight] + value);
            }
        }

        // 最终答案就在 dp[W]
        return dp[W];
    }

   public int majorityElement(int[] nums) {
      // 这个题目的重点是算出来那一个数占数组中较多
      // 首先规定一个winner(默认假设为nums[0])
      // 麾下的小弟有0个
      int winner = nums[0];
      int count = 0;
      // 开始便利数组
      for(int num: nums){
        if(count == 0){
          winner = num;
        }
        // 如果当前进来的num和winner相同,
        // 说明是一个阵营的,count++
        if(num == winner){
          count++;
        }else{
          // 否则--(相当于派一个小弟同归于尽了)
          count--;
        }
      }
      return winner;
   }
}
