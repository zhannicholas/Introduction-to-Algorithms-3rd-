package auxClassForSorting;

import java.util.Random;

public class GenerateTestData{
    /**
     * 生成[-limit, limit]内的n个测试数据
     * @param limit     正负范围
     * @param n         生成测试数据的个数
     * @return          包含测试数据的数组
     */
    public int[] generateTestData(int limit, int n){
        int[] testData = new int[n];
        Random rand = new Random();
        for(int i = 0;i < n;i++) testData[i] = (rand.nextInt(2 * limit)) * (-1) + limit ;
        return testData;
    }

    /**
     * 生成[0, limit]内的n个测试数据
     * @param limit     范围
     * @param n         测试数据个数
     * @return          测试数据
     */
    public int[] generatePositiveTestData(int limit, int n){
        int[] testData = new int[n];
        Random rand = new Random();
        for(int i = 0;i < n;i++) testData[i] = rand.nextInt(limit + 1);
        return testData;
    }
}
