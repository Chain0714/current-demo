package org.chain.current.demo.parallelpattern.sort;

import java.util.Arrays;

/**
 * 奇偶交换排序的串行实现
 */
public class OddEvenSortDemo {

    public static void main(String[] args) {
        int[] arr = DemoUtil.init(10, 100);
        System.out.println("the origin arr is:" + Arrays.toString(arr));
        oddEvenSort(arr);
        System.out.println("the sorted arr is:" + Arrays.toString(arr));
    }

    /**
     * exchFlag ：1 表示发生了数字调换,0 未发生数据交换
     * start ：0 偶排序 1 奇排序
     *
     * @param arr
     */
    private static void oddEvenSort(int[] arr) {
        int exchFlag = 1, start = 0;
        while (exchFlag == 1 || start == 1) {
            exchFlag = 0;
            for (int i = start; i < arr.length - 1; i += 2) {
                if (arr[i] > arr[i + 1]) {
                    arr[i] = arr[i] ^ arr[i + 1];
                    arr[i + 1] = arr[i] ^ arr[i + 1];
                    arr[i] = arr[i] ^ arr[i + 1];
                    exchFlag = 1;
                }
            }
            start = start == 0 ? 1 : 0;
        }
    }
}
