package ch16;

public class ZeroOneKnapsackProblem {
    /**
     * 动态规划求解0-1背包问题
     * 对于第i个物品，装或者不装
     * 用m[i][j]表示将前i个物品装入到容量为j的背包中能获得的最大价值
     *               /
     *               |  0                                                   i == 0 or j == 0
     *      m[i][j] =|  m[i - 1][j]                                         装不下第i个物品(w[i] > j)
     *               |  max{m[i - 1][j], v[i] + m[i - 1][j - w[i]]}         能够装下第i个物品(w[i] < j)
     *               \
     * @param weight                重量数组
     * @param value                 价值数组
     * @param knapsackCapacity      背包容量
     * @return                      装载方案(0: 不装; 1: 装)
     */
    private int[] DPSolution(int[] weight, int[] value, int knapsackCapacity){
        int n = weight.length;
        int[] result = new int[n];    // 装入方案
        int[][] m = new int[n + 1][knapsackCapacity + 1];
        // 初始条件----java中可省略
        for(int i = 0;i <= n;i++){
            m[i][0] = 0;        //背包容量为0
        }
        for(int i = 0;i <= knapsackCapacity;i++){
            m[0][i] = 0;        // 没有物品
        }
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= knapsackCapacity;j++){
                m[i][j] = m[i - 1][j];
                if(weight[i - 1] < j){
                    if(value[i - 1] + m[i - 1][j - weight[i - 1]] > m[i - 1][j]){
                        m[i][j] += value[i - 1];
                    }
                }
            }
        }
        // 求装入方案
        int k = knapsackCapacity;
        for(int i = n;i > 0;i--){
            if(m[i][k] > m[i - 1][k]){  // 说明装入了第i个物品
                result[i - 1] = 1;
                k -= weight[i - 1];
            }
        }
     return result;
    }

    public static void main(String[] args){
        int[] weight = {2, 2, 6, 4, 5};
        int[] value = {6, 3, 5, 4, 6};
        int knapsackCapacity = 10;
        ZeroOneKnapsackProblem zokp = new ZeroOneKnapsackProblem();
        System.out.println("装入方案：");
        int [] rst = zokp.DPSolution(weight, value, knapsackCapacity);
        for(int i: rst){
            System.out.print(i + "\t");
        }
        System.out.println("\n最大价值：");
        int sum = 0;
        for(int i = 0;i < weight.length;i++){
            sum += value[i] * rst[i];
        }
        System.out.print(sum);
    }
}
