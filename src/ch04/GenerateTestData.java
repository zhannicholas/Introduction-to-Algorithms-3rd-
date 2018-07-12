package ch04;

import java.util.Random;

class GenerateTestData {
    public int[] generateTestData(int limit, int n){
        int[] testData = new int[n];
        Random rand = new Random();
        for(int i = 0;i < n;i++) testData[i] = (rand.nextInt(2 * limit)) * (-1) + limit ;
        return testData;
    }
}
