package com.bd.java.se.study.point_game;

import java.util.*;

/*
 * 方法1 穷举，计算所有的可能，若能得到24，则输出
 * 方法2 分治，引入集合去重。利用x=5=(0101)表示(a0,a1,a2,a3)的子集(a1,a3)
 */
public class PointGame {
    private static final double DIFF=1E-6F;
    private static final int cardsNumber = 4;
    //扑克最大值是13
    private static final int cardMax = 13;
    private static final int resultValue = 24;
    private static int counter = 0;

    public static int getCardMax() {
        return cardMax;
    }

    public static int getCardsNumber() {
        return cardsNumber;
    }

    public static int getCounter() {
        return counter;
    }

    //方法1 穷举
    public static boolean compute(double[] number, String[] result, int n) {
        if(n == 1) {
            //计算结果等于预期值
            if((Math.abs(number[0] - resultValue) < DIFF)) {
                System.out.println("1. " + result[0]);
                counter++;
                return true;
            } else {
                return false;
            }
        }

        boolean ret = false;
        //对number的每个数字进行遍历
        for(int i = 0; i < n; i++){
            //每个数字与之后的数字两两进行组合
            for(int j = i + 1; j < n; j++) {
                double a = number[i];
                double b = number[j];

                String expA = result[i];
                String expB = result[j];

                //每计算一次，参与运算的数字就少一个
                //将最后一个数字复制到位置j，达到缩小数组的效果
                number[j] = number[n - 1];
                result[j] = result[n - 1];

                //加法操作a+b
                number[i] = a+b;
                result[i] = '('+expA+'+'+expB+')';
                ret = compute(number, result, n-1) || ret;

                //减法操作a-b
                number[i] = a-b;
                result[i] = '('+expA+'-'+expB+')';
                ret = compute(number, result, n-1) || ret;

                //减法操作b-a
                number[i] = b-a;
                result[i] = '('+expB+'-'+expA+')';
                ret = compute(number, result, n-1) || ret;

                //乘法操作a*b
                number[i] = a*b;
                result[i] = '('+expA+'*'+expB+')';
                ret = compute(number, result, n-1) || ret;

                //除法操作a/b, 如果除数不为0
                if(b != 0) {
                    number[i] = a/b;
                    result[i] = '('+expA+'/'+expB+')';
                    ret = compute(number, result, n-1) || ret;
                }

                //除法操作b/a , 如果除数不为0
                if(a != 0) {
                    number[i] = b/a;
                    result[i] = '('+expB+'/'+expA+')';
                    ret = compute(number, result, n-1) || ret;
                }

                //将选出来的两个数字和表达式放回原位
                number[i] = a;
                number[j] = b;
                result[i] = expA;
                result[j] = expB;
            }
        }

        return ret;
    }

    //方法2 分治
    private static final int n = cardsNumber;
    //16(二进制表示10000，1左移4位)-1=1111(二进制表示)=15
    private static final int m = (1<<n)-1;
    //存储所有真子集运算结果的集合
    private static List<Node>[] S;

    public static boolean compute(int[] number){
        boolean ret = false;
        S = new ArrayList[m+1];
        //对每个只有一个元素的真子集((a0,a1,a2,a3)的子集(a0),(a1),(a2),(a3))赋值
        for(int j = 0; j<n; j++) {
            int v = number[j];
            Node node = new Node((0.0+v), (""+v));
            List<Node> Si = new ArrayList<Node>();
            Si.add(node);
            int i = 1<<j;
            S[i] = Si;
        }

        //对每个真子集进行计算
        for(int i = 1; i<=m; i++) {
            List<Node> Si = fun(i);
            S[i] = Si;
        }

        List<Node> Sm = S[m];
        for(Node v:Sm) {
            //打印满足结果的解法
            if(Math.abs(v.value - resultValue) < DIFF) {
                System.out.println("2. " + v.exp);
                counter++;
                ret = true;
            }
        }

        return ret;
    }

    public static List<Node> fun(int i) {
        //i的二进制表示集合的一个真子集
        List<Node> si = S[i];
        if(si != null && !si.isEmpty()) {
            return si;
        }

        si = new ArrayList<Node>();
        //只有小于i的x才可能成为i的真子集
        for(int x = 1; x<i; x++) {
            //确保x是i的子集，i-x为i的另一个真子集，x与i-x共同构成i的一个划分
            if((x & i) == x) {
                List<Node> sx = fun(x);
                int other = i-x;
                List<Node> s_x = fun(other);
                //System.out.println("x " + x + " i - x " + other);
                //fork为集合的并运算
                si.addAll(fork(sx, s_x));
            }
        }

        return si;
    }

    public static List<Node> fork(List<Node> sx, List<Node> s_x){
        Set<Node> set=new HashSet<Node>();

        for(int i = 0, m = sx.size(); i < m; i++) {
            for(int j = 0, n = s_x.size(); j < n; j++) {
                Node a = sx.get(i);
                Node b = s_x.get(j);

                //集合里元素两两运算并通过HashSet去重
                set.add(new Node(a.value + b.value, "(" + a.exp + "+" + b.exp + ")"));
                set.add(new Node(a.value - b.value, "(" + a.exp + "-" + b.exp + ")"));
                set.add(new Node(b.value - a.value, "(" + b.exp + "-" + a.exp + ")"));
                set.add(new Node(a.value * b.value, "(" + a.exp + "*" + b.exp + ")"));
                if(b.value != 0) {
                    set.add(new Node(a.value / b.value, "(" + a.exp + "/" + b.exp + ")"));
                }
                if(a.value != 0){
                    set.add(new Node(b.value / a.value, "(" + b.exp + "/" + a.exp + ")"));
                }
            }
        }

        List<Node> si=new ArrayList<Node>(set);
        return si;
    }

    static class Node{
        //表达式的值
        double value;
        //表达式
        String exp;

        Node(double value, String exp){
            this.value = value;
            this.exp = exp;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Node)) {
                return false;
            }

            Node node = (Node) o;
            return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(node.value);
        }

        public int hashCode() {
            int result = 17;
            long tolong = Double.doubleToLongBits(value);
            result = 37 * result + (int) (tolong ^ (tolong >>> 32));
            //result = 37 * result + exp.hashCode();
            return result;
        }
    }
}
