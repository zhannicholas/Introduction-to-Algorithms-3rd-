package ch15;

public class LongestIncreaseSequence {
    /**
     * 设dp[i]表示以A[i]结尾的LIS的长度
     * dp[i] = max{dp[j] + 1} (0 <= j < i and a[j] < a[i])
     * 复杂度： O(n^2)
     * s: 2 1 5 3 6 4 8 9 7
     * dp:1 1 2 2 3 3 4 5 4
     * @param A     序列
     * @return      LIS
     */
    private String lis1(String A){
        int n = A.length();
        int[] dp = new int[n];
        dp[0] = 1;
        for(int i = 1;i < n;i++){
            dp[i] = 1;
            for(int j = 0;j < i;j++){
                if(A.charAt(j) < A.charAt(i) && dp[j] + 1 > dp[i]){
                    dp[i] = dp[j] + 1;
                }
            }
        }
        int maxLength= Integer.MIN_VALUE;
        for(int i = 0;i < n;i++){
            if(maxLength < dp[i]){
                maxLength = dp[i];
            }
        }
        // 因为dp[i]表示以A[i]结尾的子序列的最大上升子序列的长度，
        // 所以递减maxLength, 当maxlength和dp[i]相等的时候，dp[i]对应的就是结果中的一个元素
        String res = "";
        for(int i = n - 1;i >= 0;i--){
            if(dp[i] == maxLength){
                res = A.charAt(i) + res;
                maxLength --;
            }
        }
        return res;
    }

    /**
     * 二分查找
     * @param A     序列
     * @param ch    字符
     * @return      若找到，返回元素位置；否则，返回插入位置
     */
    private int binarySearch(String A, char ch){
        int left = 0, right = A.length() - 1;
        int mid;
        while(left <= right){
            mid = (left + right) / 2;
            if(A.charAt(mid) > ch){
                right = mid - 1;
            }
            else if(A.charAt(mid) < ch){
                left = mid + 1;
            }
            else{
                return mid;     // 找到元素，直接返回位置
            }
        }
        return left;        // 未找到，返回插入位置
    }

    /**
     * 能够正确的返回长度
     * 复杂度： O(nlgn)
     * @param A     序列
     * @return      LIS
     */
    private int lis2(String A){
        StringBuilder B = new StringBuilder();
        int len = 1;
        int n = A.length();
        B.append(A.charAt(0));
        int pos = 0;
        for(int i = 1;i < n;i++){
            if(A.charAt(i) > B.charAt(len - 1)){    // 如果大于B中最大的元素，追加到B的末尾
                B.append(A.charAt(i));
                len ++;
            }
            else{   // 查找插入位置
                pos = binarySearch(B.toString(), A.charAt(i));
                B.replace(pos, pos + 1, A.charAt(i) + "");
            }
        }
        return B.toString().length();
    }

    public static void main(String[] args){
        String A = "215364897";
        LongestIncreaseSequence lis = new LongestIncreaseSequence();
        System.out.println("动态规划法：" + lis.lis1(A));
        System.out.println("二分法插入法计算出来的长度：" + lis.lis2(A));
    }
}
