package com.thealgorithms.sorts;

/**
 * 快速排序的简化实现（Lomuto 分区方案）。
 *
 * <p>这是学习用的简化版，核心逻辑：
 * <ol>
 *   <li>选基准 pivot（这里取数组最右端元素）</li>
 *   <li>分区：比 pivot 小的放左，大的放右，pivot 放中间</li>
 *   <li>对左右两半递归排序</li>
 * </ol>
 *
 * <p>时间复杂度：平均 O(n log n)，最坏 O(n²)（数组已有序时）
 * 空间复杂度：O(log n)（递归栈）
 *
 * @see QuickSort 项目里更通用的泛型版本（使用随机化 + Hoare 分区）
 */
public final class QuickSortSimple {

    private QuickSortSimple() {
        // 工具类禁止实例化
    }

    /**
     * 对整型数组升序排序。
     *
     * @param arr 待排序数组
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快排递归主体：先分区，再递归处理左右两半。
     *
     * @param arr  待排序数组
     * @param low  当前区间左边界（含）
     * @param high 当前区间右边界（含）
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 找到二分点(即该点右边均大于,左边均小于)
            int p = partition(arr, low, high); // 二分点：pivot 的最终位置
            quickSort(arr, low, p - 1);        // 排左半（比 pivot 小的）
            quickSort(arr, p + 1, high);       // 排右半（比 pivot 大的）
        }
    }

    /**
     * Lomuto 分区方案：选最右端为 pivot，把小于等于 pivot 的全部挪到左边。
     *
     * @param arr  待排序数组
     * @param low  区间左边界
     * @param high 区间右边界
     * @return pivot 排序后所在的索引（此时它左边全 ≤ pivot，右边全 > pivot）
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // 选基准：最右端元素
        int i = low - 1;       // i 指向"小于等于 pivot 区间"的末尾

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j); // 把当前小元素挪到左边
            }
        }
        swap(arr, i + 1, high); // 把 pivot 放到中间，它现在归位了
        return i + 1;           // 返回 pivot 的最终位置
    }

    /**
     * 交换数组中两个位置的元素。
     */
    private static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }



    public int search(int[] nums, int target) {
        if(nums == null || nums.length == 0){
          return -1;
        }
        if(nums.length == 1){
          return target == nums[0] ? 0: -1;
        }
        int n = nums.length;
        int l = 0,r = n - 1;

        while(l < r){
          int mid = l + ((r-l)/2);
          // 实际上每次都是在这里尝试进行检索
          if (nums[mid] == target) {
                return mid;
          }
          // 这里永远是和下标为0的位置开始对比!!
          if(nums[0] < nums[mid]){
            // 成立的话说明target位于左半段,更改r的值为mid - 1
            if(nums[l] <=  target && target <= nums[mid]){
                r = mid - 1;
            }else{
                // 否则说明target位于右半段,更新一下
                l = mid + 1;
            }
          }else{
            // 重点主要是这里的内容不一样!根据我们判断的位于哪一个有序的区间来决定target的判断逻辑
            if(nums[mid] <= target && target <= nums[r]){
              l = mid + 1;
            }else{
              r = mid - 1;
            }
          }
        }
        return -1;


    }
    // 使用二分法查找旋转后的数组的最小的元素
    public int findMin(int[] nums) {
      int l = 0,r = nums.length;
      while(l < r){
        int mid = l + ((r - l)/2);
        if(nums[mid] > nums[r]){
          l = mid + 1;
        }else{
          r = mid;
        }
      }
      return nums[l];
    }


    // 题目描述,给定一个m * n的矩阵,找出其中的最长递增路径的长度
    // 每个单元格都可以向上下左右移动(不超过边界)
    public int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public int rows, columns;

    public int longestIncreasingPath(int[][] matrix) {
        // 判断矩阵的的规格是否符合基本的输入要求
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        // 分别记录长和宽
        rows = matrix.length;
        columns = matrix[0].length;
        // memory 存储一下
        // 具体的含义是以当前row,columns为坐标节点,以它作为起始点最长的递增路径
        int[][] memo = new int[rows][columns];
        int ans = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                // 每次进行递归时我们还会将memo作为参数传入,为的就是方便比较
                // 假设memo中存储了 (i,j) 节点为起点的最大递增序列
                // 当 (i-1,j)(或者任何相邻且合法的节点)访问到它时,可以直接返回结果而不需要重新走一遍
                // 也就是 (i-1,j)节点的最大递增序列就是使用memo中(i,j)对应的值加一即可
                ans = Math.max(ans, dfs(matrix, i, j, memo));
            }
        }
        return ans;
    }

    public int dfs(int[][] matrix, int row, int column, int[][] memo) {
        // 这个是一个快速的剪枝操作,即当我们的此次需要检索的(i,j)已经存在在memo中,我们直接返回即可
        if (memo[row][column] != 0) {
            return memo[row][column];
        }
        // 如果memo没有存储当前的数据,先置为1
        ++memo[row][column];
        // 这里实际上是用了一个小巧的思路,我们定义好了四个方向移动的dirs,分别遍历具体的内容尝试看看是否可以移动
        for (int[] dir : dirs) {
            int newRow = row + dir[0], newColumn = column + dir[1];
            // if中的四个条件就是新的长(宽)不能触发边界,并且新的相邻节点的值大于当前节点
            if (newRow >= 0 && newRow < rows && newColumn >= 0 && newColumn < columns && matrix[newRow][newColumn] > matrix[row][column]) {
                // 这里实际上是核心思想,尝试更新当前节点的memo,更新的策略就是获取相邻节点的最大递增序列并加1,和当前的数据进行比较
                // 取其中的更大值
                memo[row][column] = Math.max(memo[row][column], dfs(matrix, newRow, newColumn, memo) + 1);
            }
        }
        return memo[row][column];
    }
    public boolean canJump(int[] nums) {
        int length = nums.length;
        // 维持一个变量,一直记录当前能到达的最远距离
        // 每一次循环,实际上是将上一个数据能达到的最远下标和当前的下标进行对比
        // 如果小于,说明上一个(及其之前的所有节点)都达到不了,返回失败
        int maxReach = 0;
        for(int i = 0;i < length; i++){
          if(i > maxReach){
            return false;
          }
          // 否则将数据更新成为当前节点所能到达的最远下标
          // i + nums[i] 以及之前的做一个对比,哪一个长就定哪一个为最远下标
          maxReach = Math.max(maxReach,i+nums[i]);
          // 更新后,如果最远下标已经大于n-1,我们直接返回即可
          if(maxReach > length - 1){
            return true;
          }
        }
        
        return true;
    } 
    public int jump(int[] nums) {
        int length = nums.length;
        int curend,maxFar = 0;
        int step = 0;
        for(int i = 0; i ＜length - 1;i++){
          maxFar = Math.max(maxFar,i + nums[i]);
          if(i == curend){
            step++;
            curend = maxFar;
          }
        }
        return step;
    }

}
