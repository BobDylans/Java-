package com.thealgorithms.greedyalgorithms;

/* 活动选择问题（贪心） */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// Problem Link: https://en.wikipedia.org/wiki/Activity_selection_problem

public final class ActivitySelection {

    // Private constructor to prevent instantiation of the utility class
    private ActivitySelection() {
    }

    /**
     * Function to perform activity selection using a greedy approach.
     *
     * The goal is to select the maximum number of activities that don't overlap
     * with each other, based on their start and end times. Activities are chosen
     * such that no two selected activities overlap.
     *
     * @param startTimes Array containing the start times of the activities.
     * @param endTimes   Array containing the end times of the activities.
     * @return A list of indices representing the selected activities that can be
     *         performed without overlap.
     */

    // 这里传入了两个数组,分别是各个活动的开始和结束时间
    public static ArrayList<Integer> activitySelection(int[] startTimes, int[] endTimes) {
        int n = startTimes.length;

        // Create a 2D array to store activity indices along with their start and end
        // times.
        // Each row represents an activity in the format: [activity index, start time,
        // end time].
        int[][] activities = new int[n][3];

        // Populate the 2D array with the activity index, start time, and end time.
        // 使用一个二维数组记录一下每个活动的编号以及开始结束时间 
        for (int i = 0; i < n; i++) {
            activities[i][0] = i; // Assign the activity index
            activities[i][1] = startTimes[i]; // Assign the start time of the activity
            activities[i][2] = endTimes[i]; // Assign the end time of the activity
        }

        // Sort activities based on their end times in ascending order.
        // This ensures that we always try to finish earlier activities first.
        /*
         * 这里是贪心算法的重点
         * 也就是我们根据结束时间的早晚进行一个排序,将结束时间早的放到前面
         * */
        Arrays.sort(activities, Comparator.comparingDouble(activity -> activity[2]));
        int lastEndTime; // Variable to store the end time of the last selected activity
        // List to store the indices of selected activities
        ArrayList<Integer> selectedActivities = new ArrayList<>();

        // Select the first activity (as it has the earliest end time after sorting)
        // 因为我们已经按照结束时间拍好序了,直接取第一个活动,就是结束时间最早的那个 
        // 记录下来对应的信息
        selectedActivities.add(activities[0][0]); // Add the first activity index to the result
        lastEndTime = activities[0][2]; // Keep track of the end time of the last selected activity

        // Iterate over the sorted activities to select the maximum number of compatible
        // activities.
        // 从第二个开始(也就是下标为1的),我们比较和最早结束时间的关系,
        // 将开始时间晚于最早结束时间的活动都加入选中的数组中,并且更新lastEndTime(上一个最早结束时间)
        for (int i = 1; i < n; i++) {
            // If the start time of the current activity is greater than or equal to the
            // end time of the last selected activity, it means there's no overlap.
            if (activities[i][1] >= lastEndTime) {
                selectedActivities.add(activities[i][0]); // Select this activity
                lastEndTime = activities[i][2]; // Update the end time of the last selected activity
            }
        }
        // 返回即可
        // Return the list of selected activity indices.
        return selectedActivities;
    }
}
