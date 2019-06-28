package org.chain.current.demo.jdk8demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 统计1--1000000内所有质数的数量
 */
public class ParallelStreamDemo {

    public static void main(String[] args) {
        //并行过滤
        IntStream.range(1, 10000000).filter(ParallelStreamDemo::isPrime).count();
        IntStream.range(1, 10000000).parallel().filter(ParallelStreamDemo::isPrime).count();
        //从集合中得到并行流
        List<Student> ss = new ArrayList<>();
        ss.stream().mapToInt(Student::getScore).average().getAsDouble();
        ss.parallelStream().mapToInt(Student::getScore).average().getAsDouble();
        //并行排序
        int[] arr = new int[100000];
        Random random = new Random();
        Arrays.setAll(arr, i -> random.nextInt());
        Arrays.parallelSetAll(arr, i -> random.nextInt());
        Arrays.parallelSort(arr);
    }

    /**
     * 判断整数是否是质数
     *
     * @param number
     * @return
     */
    private static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; Math.sqrt(number) >= i; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    class Student {
        int score;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
