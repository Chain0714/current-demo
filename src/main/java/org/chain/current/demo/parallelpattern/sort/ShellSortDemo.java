package org.chain.current.demo.parallelpattern.sort;

import java.util.Arrays;

/**
 * 希尔排序串行demo
 */
public class ShellSortDemo {

    public static void main(String[] args) {
        int[] arr = DemoUtil.init(19, 100);
        System.out.println("origin array:" + Arrays.toString(arr));
        shellSort(arr);
        System.out.println("sorted array:" + Arrays.toString(arr));
    }

    public static void shellSort(int[] arr) {
        //计算最大h
        int h = 1;
        while (h <= arr.length / 3) {
            h = 3 * h + 1;
        }
        while (h > 0) {
            for (int i = h; i < arr.length; i++) {
                if (arr[i] < arr[i - h]) {
                    int tmp = arr[i];
                    int j = i - h;
                    while (j >= 0 && arr[j] > tmp) {
                        arr[j + h] = arr[j];
                        j -= h;
                    }
                    arr[j + h] = tmp;
                }
            }
            h = (h - 1) / 3;
        }
    }
}
