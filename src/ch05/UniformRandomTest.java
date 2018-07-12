package ch05;

import java.util.Random;

/**
 * biased-random(): 随机输出1（概率为p)和0（概率为1-p)
 * 利用biased-random()等概率输出0和1
 * 方法：连续两次调用biased-random()
 */
class UniformRandom {
    private int biasedRandom(){
        Random random = new Random();
        return random.nextInt(2);  // 随机产生[0,1]内的整数
    }

    public int rand(){
        while(true){
            int i = biasedRandom(), j = biasedRandom();
            if(i == 1 && j == 0){
                return 1;
            }
            else if(i == 0 && j == 1){
                return 0;
            }
            else continue;
        }
    }

}

public class UniformRandomTest{
    public static void main(String[] args){
        UniformRandom u = new UniformRandom();
        for(int k = 0;k < 10;k++){
            System.out.println(k + ": " + u.rand());
        }
    }
}