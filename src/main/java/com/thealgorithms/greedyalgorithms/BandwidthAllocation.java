package com.thealgorithms.greedyalgorithms;

/* 带宽分配（贪心） */


import java.util.Arrays;

/**
 * Class to solve the Bandwidth Allocation Problem.
 * The goal is to maximize the value gained by allocating bandwidth to users.
 * Example:
 * Bandwidth = 10
 * Users = [3, 5, 7]
 * Values = [10, 20, 30]
 * The maximum value achievable is 40 by allocating 3 units to user 0 and 7 units to user 2.
 *
 * @author Hardvan
 */
public final class BandwidthAllocation {
    private BandwidthAllocation() {
    }

    /**
     * Allocates bandwidth to maximize value.
     * Steps:
     * 1. Calculate the ratio of value/demand for each user.
     * 2. Sort the users in descending order of the ratio.
     * 3. Allocate bandwidth to users in order of the sorted list.
     * 4. If the bandwidth is not enough to allocate the full demand of a user, allocate a fraction of the demand.
     * 5. Return the maximum value achievable.
     *
     * @param bandwidth total available bandwidth to allocate
     * @param users     array of user demands
     * @param values    array of values associated with each user's demand
     * @return the maximum value achievable
     */
    public static int maxValue(int bandwidth, int[] users, int[] values) {
        int n = users.length;
        // 面对这种二维数组你可以这么去理解
        // 第一个维度用来区分不同的用户,第二个维度则是用户的参数(宽带量,消费量等),对应的值则是用户的参数信息 
        // 我们创建的二维数组double[n][2] 说明有n个用户每个用户有2个维度
        double[][] ratio = new double[n][2]; // {index, ratio}

        for (int i = 0; i < n; i++) {
            ratio[i][0] = i;
            // 这里就是计算用户愿意支付的价格以及所需求的宽带,二者相除获取对应的ratio
            // 比如用户需要带宽10,愿意支付20,那么单位消费就是20/10=2.  
            ratio[i][1] = (double) values[i] / users[i];
        }
        // 进行降序排序,也就是单位支付量大的用户会被排到前面
        Arrays.sort(ratio, (a, b) -> Double.compare(b[1], a[1]));

        int maxValue = 0;
        for (int i = 0; i < n; i++) {
            int index = (int) ratio[i][0];
            // 如果我们的带宽足够,就优先满足当前单位支付量最大的客户
            if (bandwidth >= users[index]) {
                maxValue += values[index];
                bandwidth -= users[index];
            } else {
                // 否则就说明满足不了,尽管满足不了,还是全部给它,用当前的bandwidth*当前用户的单价
                // 然后加到我们的maxvaluie中
                maxValue += (int) (ratio[i][1] * bandwidth);
                break;
            }
        }
        return maxValue;
    }
}
