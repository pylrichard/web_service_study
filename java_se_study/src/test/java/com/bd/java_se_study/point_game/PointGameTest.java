package com.bd.java_se_study.point_game;

import java.util.Arrays;
import java.util.Random;

public class PointGameTest {
    public static void main(String[] args) throws Exception {
        int k = 0;

        while (k < 1) {
            //int[] source = new int[CardsNumber];
            int[] source = {7, 3, 13, 1};
            double[] number = new double[PointGame.getCardsNumber()];
            //记录number[i]是通过什么运算表达式得到的
            String[] result = new String[PointGame.getCardsNumber()];
            Random random = new Random();
            boolean ret = false;

            //随机生成CardMax范围内的4个数字
            for (int i = 0 ; i < PointGame.getCardsNumber(); i++) {
                //int x = random.nextInt(PointGame.getCardMax()) + 1;
                //source[i] = x;
                int x = source[i];
                number[i] = x;
                result[i] = "" + x;
            }
            //打印参与运算的数字
            System.out.println(Arrays.toString(source));

            //方法1 穷举
//            ret = PointGame.compute(number, result, PointGame.getCardsNumber());

            //方法2 分治
            ret = PointGame.compute(source);

            System.out.println("counter " + PointGame.getCounter());
            if (!ret) {
                System.out.println("no solution");
            }

            System.out.println("===================================");
            k++;
        }
    }
}
