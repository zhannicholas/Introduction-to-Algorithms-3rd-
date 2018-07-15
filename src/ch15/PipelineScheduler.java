package ch15;

/**
 * 算法导论（第二版）：装配线调度
 */
public class PipelineScheduler {
    private int[][] l;
    private int f = 0;
    private int lf = 0;
    public void fastestWay(int[][] a, int[][] t, int[] e, int[] x, int n){
        int[] f0 = new int[n];      // line0 最快路径
        int[] f1 = new int[n];      // line1 最快路径
        l = new int[2][n];
        f0[0] = e[0] + a[0][0];
        f1[0] = e[1] + a[1][0];
        for(int i = 1;i < n;i++){
            if(f0[i - 1] + a[0][i] <= f1[i - 1] + a[0][i] + t[1][i - 1]){
                f0[i] = f0[i - 1] + a[0][i];
                l[0][i] = 0;
            }
            else{
                f0[i] = f1[i - 1] + a[0][i] + t[1][i - 1];
                l[0][i] = 1;
            }
            if(f1[i - 1] + a[1][i] <= f0[i - 1] + a[1][i] + t[0][i - 1]){
                f1[i] = f1[i - 1] + a[1][i];
                l[1][i] = 1;
            }
            else{
                f1[i] = f0[i - 1] + a[1][i] + t[0][i - 1];
                l[0][i] = 0;
            }
        }
        if(f0[n - 1] + x[0] <= f1[n - 1] + x[1]){
            f = f0[n - 1] + x[0];       // 最终从line0出
            lf = 0;
        }
        else{
            f = f1[n - 1] + x[1];       // 最终从line1出
            lf = 1;
        }
    }

    public void printStation(int[][] l, int ll, int n){
        int i = ll;
        System.out.println("line " + i + ", station " + (n));
        for(int j = n - 1;j >= 1;j--){
            i = l[i][j];
            System.out.println("line " + i + ", station " + (j));
        }
    }

    public static void main(String[] args){
        PipelineScheduler ps = new PipelineScheduler();
        int[] e = new int[]{2, 4};
        int[] x = new int[]{3, 2};
        int[][] a = new int[][]{{7, 9, 3, 4, 8, 4}, {8, 5, 6, 4, 5, 7}};
        int[][] t = new int[][]{{2, 3, 1, 3, 4}, {2, 1, 2, 2, 1}};
        ps.fastestWay(a, t, e, x, 6);
        ps.printStation(ps.l,ps.lf , 6);
    }
}
