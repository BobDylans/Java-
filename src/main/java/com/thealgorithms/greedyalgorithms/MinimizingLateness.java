package com.thealgorithms.greedyalgorithms;

/* 最小化延迟调度（贪心） */


import java.util.Arrays;

public final class MinimizingLateness {
    private MinimizingLateness() {
    }

    public static class Job {
        String jobName;
        int startTime = 0;
        int lateness = 0;
        int processingTime;
        int deadline;

        public Job(String jobName, int processingTime, int deadline) {
            this.jobName = jobName;
            this.processingTime = processingTime;
            this.deadline = deadline;
        }

        public static Job of(String jobName, int processingTime, int deadline) {
            return new Job(jobName, processingTime, deadline);
        }

        @Override
        public String toString() {
            return String.format("%s, startTime: %d, endTime: %d, lateness: %d", jobName, startTime, processingTime + startTime, lateness);
        }
    }

    static void calculateLateness(Job... jobs) {

        // sort the jobs based on their deadline
        // 首先根据jobs的截至期限进行排序,截至时间越早排的越前 
        // 看到了还是想说明一下,sort方法有两个入参,一个是需要排序的数据,一个是进行排序所依据的函数接口,可以使用lambda语法直接实现
        Arrays.sort(jobs, (a, b) -> a.deadline - b.deadline);
        // 默认起始时间是0
        int startTime = 0;

        for (Job job : jobs) {
            job.startTime = startTime;
            // 更新下一个任务的开始时间,也就是当前任务的开始时间+执行所需时间
            startTime += job.processingTime;
            // 计算一下延迟,比较0和 任务下一个开始时间(也就是当前任务完成时间)减去任务截至时间,后者如果为负数说明没有延迟
            job.lateness = Math.max(0, startTime - job.deadline); // if the job finishes before deadline the lateness is 0
        }
    }
}
