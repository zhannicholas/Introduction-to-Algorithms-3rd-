package ch15;


public class RodCutting {
    private int[] prices = new int[]{0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
    /**
     * 自顶向下的朴素递归求解
     * Rn = max(Pi + Rn-1)
     * @param p     价格表
     * @param n     长度
     * @return      最有解
     */
    private int rodCuttingWithTopDownRecursion(int[] p, int n){
        if(n == 0){
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for(int i = 1;i <= n;i++){
            if(p[i] + rodCuttingWithTopDownRecursion(p, n - i) > q){
                q = p[i] + rodCuttingWithTopDownRecursion(p, n - i);
            }
        }
        return q;
    }

    /**
     * 自顶向下递归计算备忘录
     * @param p     价格表
     * @param n     长度
     * @param r     备忘录
     * @return      最优解
     */
    private int memoizedCutRodAux(int[] p, int n, int[] r){
        if(r[n] > 0){   // 已计算过，直接返回值
            return r[n];
        }
        int q = Integer.MIN_VALUE;
        if(n == 0){
            q = 0;
        }
        for(int i = 1;i <= n;i++){
            if(p[i] + memoizedCutRodAux(p, n - i, r) > q){
                q = p[i] + memoizedCutRodAux(p, n - i, r);
            }
        }
        r[n] = q;   // 写入备忘录
        return q;   // 返回
    }

    /**
     * 计算最优解
     * @param p     价格表
     * @param n     长度
     * @return      最优解
     */
    private int memoizedCutRod(int[] p, int n){
        int r[] = new int[n + 1];
        for(int i = 0;i <= n;i++){
            r[i] = Integer.MIN_VALUE;
        }
        return memoizedCutRodAux(p, n, r);
    }

    /**
     * 自底向上依次求解规模为i(0, 1, 2, ..., n)的子问题的解
     * 直接从数组中获得规模为（i - j)的子问题的解
     * @param p     价格表
     * @param n     长度
     * @return      最优解
     */
    private int bottomTopCutRod(int[] p, int n){
        int[] r = new int[n + 1];
        r[0] = 0;
        for(int i = 1;i <= n;i++){
            int q = Integer.MIN_VALUE;
            for(int j = 1;j <= i;j++){
                if(p[j] + r[i - j] > q){
                    q = p[j] + r[i - j];
                }
            }
            r[i] = q;
        }
        return r[n];
    }

    /**
     * 返回切割方法
     * @param p     价格表
     * @param n     长度
     * @return      第一段钢条的最优切割长度
     */
    private int[] extendedBottomUpCutRod(int[] p, int n){
        int[] r = new int[n + 1];   // 收益
        int[] s = new int[n + 1];   //切割长度
        r[0] = 0;
        for(int i = 1;i <= n;i++){
            int q = Integer.MIN_VALUE;
            for(int j = 1;j <= i;j++){
                if(p[j] + r[i - j] > q){
                    q = p[j] + r[i - j];
                    s[i] = j;   // 保存第一段钢条的最优切割长度
                }
            }
            r[i] = q;
        }
        return s;
    }

    private void printCutRodSolution(int[] p, int n){
        int[] s = extendedBottomUpCutRod(p, n);
        while (n > 0){
            System.out.printf(s[n] + "\t");
            n -= s[n];
        }
    }

    public static void main(String[] args){
        RodCutting rc = new RodCutting();
        System.out.println("自顶向下递归求解：" + rc.rodCuttingWithTopDownRecursion(rc.prices, 7));
        System.out.println("带备忘录的自顶向下求解：" + rc.memoizedCutRod(rc.prices, 7));
        System.out.println("自底向上求解：" + rc.bottomTopCutRod(rc.prices, 7));
        System.out.println("切割方法：");
        rc.printCutRodSolution(rc.prices, 7);
    }
}
