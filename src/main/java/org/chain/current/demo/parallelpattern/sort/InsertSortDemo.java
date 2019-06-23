package org.chain.current.demo.parallelpattern.sort;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertSortDemo {
    private static void insertSort(int[] arr) {
        int i, j, key;
        int len = arr.length;
        for (i = 1; i < len; i++) {
            key = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j] > key) {
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                }
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] arr = DemoUtil.init(10, 100);
        System.out.println("origin array:" + Arrays.toString(arr));
        insertSort(arr);
        System.out.println("sorted array:" + Arrays.toString(arr));
    }
}
