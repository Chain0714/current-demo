package org.chain.current.demo.parallelpattern.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSortDemo {

    private static void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    arr[j] = arr[j] ^ arr[j + 1];
                    arr[j + 1] = arr[j] ^ arr[j + 1];
                    arr[j] = arr[j] ^ arr[j + 1];
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = DemoUtil.init(19, 100);
        System.out.println("origin array:" + Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println("sorted array:" + Arrays.toString(arr));
    }
}
