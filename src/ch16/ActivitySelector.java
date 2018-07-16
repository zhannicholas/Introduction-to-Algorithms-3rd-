package ch16;

import java.util.*;

public class ActivitySelector {
    /**
     * 使用动态规划求解活动选择问题
     * S[i][j]是S活动的子集，其中的每个活动在a[i]结束之后开始，a[j]开始之前结束。
     *      S[i][j] = {a[k] ∈ S: f[i] <= s[k] < f[k] <= s[j]};
     * 为了能够完整的表示问题，加入两个辅助活动a[0]和a[n + 1]，那么f[0] = 0, s[n + 1] = ∞;
     * 于是S = S[0][n + 1],i和j的取值范围为：0 <= i, j <= n + 1;
     * 若活动已经按照结束顺序单调递增排序，则：f[0] <= f[1] <= f[2] <= ... <= f[n] < f[n + 1];
     * 考虑非空子问题S[i][j]，假设S[i][j]包括活动a[k],其中：f[i] <= s[k] < f[k] <= s[j];
     * 用a[k]生成两个子问题：S[i][k]和S[k][j]。它们是S[i][j]的子集，于是S[i][j]的解就是a[k]和S[i][k]、S[k][j]的解的并集;
     *
     * 假设S[i][j]的解为A[i][j]并且A[i][j]包含a[k]，则：
     *      A[i][j] = A[i][k] ∪ A[k][j] ∪ {a[k]};
     * 设c[i][j]表示S[i][j]中最大兼容子集的活动数，那么：
     *               /
     *               |  0                                         if(S[i][j] == Ø)
     *      c[i][j] =|
     *               |  max{c[i][k] + c[k][j] + 1} (i < k < j)    if(S[i][j] != Ø)
     *               \
     * @param start         开始时间数组
     * @param finished      结束时间数组
     * @param count         c[i][j]为s[i][j]中最大兼容子集的活动数
     * @param separation    选择方案
     * @return              选择方案
     */
    private Set<Integer> activitySelectorUsingDP(int[] start, int[] finished, int[][] count, int separation[][]){
        int n = start.length - 2;   // 真正活动个数为: n
        for(int i = 0;i < n + 2;i++){
            for(int j = i + 1;j < n + 2;j++){
                for(int k = i + 1;k < j;k++){
                    if(start[k] >= finished[i] && finished[k] <= start[j]){
                        if(count[i][j] < count[i][k] + count[k][j] + 1){
                            count[i][j] = count[i][k] + count[k][j] + 1;
                            separation[i][j] = k;
                        }
                    }
                }
            }
        }
        // 转为集合返回
        Set result = new HashSet();
        for(int i = 0;i < separation[0].length;i++){
            if(separation[0][i] != 0){
                result.add(separation[0][i]);
            }
        }
        return result;
    }

    /**
     * 贪心策略---总是选取和当前集合相容的最早结束的活动，使得剩下的时间最大化。
     *      子问题S[k] = {a[i] ∈ S, s[i] >= f[k]}     表示a[k]结束之后开始的活动的集合
     * 为解决S[i][j]，选择S[i][j]中具有最早结束时间的a[m]，并将S[m][j]的最优解加到S[i][j]的解中；
     * @param start         开始时间
     * @param finished      结束时间
     * @param k             k
     * @param n             活动总数
     * @return              选择方案
     */
    private Set<Integer> recursiveActivitySelector(int[] start, int[] finished, int k, int n){
        Set<Integer> result = new HashSet<>();
        int m = k + 1;
        while(m <= n && start[m] < finished[k]){    // 找到第一个和集合相容的活动
            m ++;
        }
        if(m <= n){
            result.add(m);
            result.addAll(recursiveActivitySelector(start, finished, m, n));
        }
        return result;
    }

    /**
     * 迭代版
     * @param start         开始时间
     * @param finished      结束时间
     * @return              选择方案
     */
    private Set<Integer> iterativeActivitySelector(int[] start, int[] finished){
        Set<Integer> result = new HashSet<>();
        int n = start.length - 1;
        result.add(1);
        int i = 1;      // 标记最近加入result的活动， 对应于result中具有最大结束时间的活动
        for(int m = 2;m < n;m++){
            if(start[m] >= finished[i]){
                result.add(m);  // 选择
                i = m;
            }
        }
        return result;
    }

    /**
     * 贪心策略---每次选择具有最晚开始时间的相容的活动
     * @param start         开始时间
     * @param finished      结束时间
     * @return              选择方案
     */
    private Set<Integer> iterativeActivitySelectorUsingLatestStartTime(int[] start, int[] finished){
        Set<Integer> result = new HashSet<>();
        int n = start.length - 1;
        result.add(n);
        int i = n;  // 标记最近加入result的活动，它具有result中所有活动最早开始时间
        for(int m = n - 1;m > 0;m--){
            if(finished[m] <= start[i]){
                result.add(m);
                i = m;
            }
        }
        return result;
    }

    /**
     * 根据选择方案集合打印区间结果
     * @param start         开始时间
     * @param finished      结束时间
     * @param result        选择方案集合
     */
    private void printResult(int[] start, int[] finished, Set<Integer> result){
        for(Integer i : result){
            System.out.printf("[" + start[i] + ", " + finished[i] + "]" + "\t");
        }
    }

    /**
     * 带权活动选择问题，目标是在活动不冲突的情况下获得最大权重和
     * 这个时候贪心算法不再起作用了，使用动态规划
     * p[n]: 在活动n之前与活动n相容并且离n最近的那个活动；
     * opt[n]: 活动1到活动n之间能获得的最大权重和
     *                /
     *               |      0               (n == 0)
     *     opt(n) =  |
     *               |      max{w[i] + opt(n), opt(n - 1)}        (n > 0)       是否放弃活动n?
     *                \
     *
     * @param start     开始时间
     * @param finished  结束时间
     * @param weights   权重数组
     * @return          选择方案
     */
    private Set<Integer> iterativeWeightedActivitySelector(int[] start, int[] finished, int[] weights){
        Set<Integer> result = new HashSet<>();
        int n = start.length - 1;
        int[] p = new int[n];
        int[][] select = new int[n][2];
        // 计算p[]
        for(int i = 1;i < n;i++){
            for(int j = i - 1;j >= 0;j--){
                if(finished[j] <= start[i]){
                    p[i] = j;
                    break;
                }
            }
        }
        int[] opt = new int[n];
        for(int i = 1;i < n;i++){
            if(weights[i] + opt[p[i]] > opt[i - 1]){
                opt[i] = weights[i] + opt[p[i]];    // 选择活动i
                select[i][0] = 1;       // 标记选择当前活动
                select[i][1] = p[i];    // 记录当前活动的上一个活动
            }
            else{
                opt[i] = opt[i - 1];        //放弃活动i
                select[i][0] = 0;           // 标记放弃当前活动
                select[i][1] = i - 1;    // 记录当前活动的上一个活动
            }
        }
        int j = n - 1;
        while (j != 0){
            if(select[j][0] == 1){
                result.add(j);
            }
            j = select[j][1];
        }
        System.out.println("最大权重和：" + opt[n - 1]);
        return result;
    }


    public static void main(String[] args){
        // 已按活动结束时间递增排序
        int[] start = new int[]{0, 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12, Integer.MAX_VALUE};
        int[] finished = new int[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, Integer.MAX_VALUE};
        int[] weight = new int[]{0, 3, 2, 4, 8, 2, 5, 6, 10, 7, 4, 5, Integer.MAX_VALUE};
        int[][] count = new int[start.length][start.length];
        int[][] separation = new int[start.length][start.length];
        // 按活动开始时间递增排序
        int[] start1 = new int[]{0, 0, 1, 2, 3, 3, 5, 5, 6, 8, 8, 12};
        int[] finished1 = new int[]{0, 6, 4, 13, 5, 8, 7, 9, 10, 11, 12, 14};
        ActivitySelector as = new ActivitySelector();
        System.out.println("自底向上动态规划：");
        as.printResult(start, finished, as.activitySelectorUsingDP(start, finished, count, separation));
        System.out.println("\n递归贪心策略：");
        as.printResult(start, finished, as.recursiveActivitySelector(start, finished, 0, start.length - 2));
        System.out.println("\n迭代求解：");
        as.printResult(start, finished, as.iterativeActivitySelector(start, finished));
        System.out.println("\n选择最晚开始的活动策略：");
        as.printResult(start1, finished1, as.iterativeActivitySelectorUsingLatestStartTime(start1, finished1));
        System.out.println("\n加权选择结果：");
        as.printResult(start, finished, as.iterativeWeightedActivitySelector(start, finished, weight));
    }
}
