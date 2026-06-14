package com.thealgorithms.greedyalgorithms;

/* 作业排序（贪心） */


import java.util.ArrayList;
import java.util.Arrays;

// Problem Link: https://en.wikipedia.org/wiki/Job-shop_scheduling

public final class JobSequencing {
    private JobSequencing() {
    }

    // Define a Job class that implements Comparable for sorting by profit in descending order
    // 我的理解是,现在有一批任务,有其自身的价值和最终期限,我们需要在最终期限前最大化收益 
    // 并且每一个任务完成所需要的时间都是固定一个单位 
    static class Job implements Comparable<Job> {
        char id;
        int deadline;
        int profit;

        // Compare jobs by profit in descending order
        @Override
        public int compareTo(Job otherJob) {
            return otherJob.profit - this.profit;
        }

        Job(char id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    // Function to print the job sequence
    // 先简单阐述思路,现在有一批任务,按照profit的大小倒叙排序
    // 然后遍历这个任务数组,我们将任务的分配一个截止日期前的一个单位时间;如果已经当前时间已经被分配
    // 说明有更大的profits的任务已经申请了,我们以相同的逻辑申请占用前一天的(因为截止日期就是当前日期)
    // 然后以同样的方法判断,向前不断遍历
    public static String findJobSequence(ArrayList<Job> jobs, int size) {
        // 初始化一个bool类型的数组代表时间是否占有,false表示默认为空闲的
        Boolean[] slots = new Boolean[size];
        Arrays.fill(slots, Boolean.FALSE);
        // 创建一个results数组,这就是我们最终分配好的任务的日期
        int[] result = new int[size];

        // Iterate through jobs to find the optimal job sequence
        for (int i = 0; i < size; i++) {
            // 实际上这里从i=0开始遍历,默认就是jobs已经按照profit排好序了,我们直接使用即可
            // 里面这个for循环的含义是拿到当前jobs最后截止时间内容,将时间分配给ta 
            // 也就是将j天时(也就是当前任务最后的结束期限)的任务分配给i(也就是当前任务),否则就继续向前便利直到找到一个空闲的时间
            // 也有可能最后也没有去执行,因为都给别的任务了 
            for (int j = jobs.get(i).deadline - 1; j >= 0; j--) {
                if (!slots[j]) {
                    result[j] = i;
                    slots[j] = Boolean.TRUE;
                    // 如果找到的话就直接退出即可
                    break;
                }
            }
        }

        // Create a StringBuilder to build the job sequence string
        StringBuilder jobSequenceBuilder = new StringBuilder();
        jobSequenceBuilder.append("Job Sequence: ");
        for (int i = 0; i < jobs.size(); i++) {
            if (slots[i]) {
                jobSequenceBuilder.append(jobs.get(result[i]).id).append(" -> ");
            }
        }

        // Remove the trailing " -> " from the job sequence
        if (jobSequenceBuilder.length() >= 4) {
            jobSequenceBuilder.setLength(jobSequenceBuilder.length() - 4);
        }

        // Return the job sequence as a string
        return jobSequenceBuilder.toString();
    }
}
