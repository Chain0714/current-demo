package org.chain.current.demo.parallelpattern.sort;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 希尔排序并行demo
 */
public class ParallelShellSortDemo {
    private static int[] arr;

    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        arr = DemoUtil.init(19, 100);
        System.out.println("origin array:" + Arrays.toString(arr));
        parallelShellSort();
        System.out.println("sorted array:" + Arrays.toString(arr));
    }

    private static class ShellTask implements Runnable {
        int i;
        int h;
        CountDownLatch cdl;

        public ShellTask(int i, int h, CountDownLatch cdl) {
            this.i = i;
            this.h = h;
            this.cdl = cdl;
        }


        @Override
        public void run() {
            if (arr[i] < arr[i - h]) {
                int tmp = arr[i];
                int j = i - h;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + h] = arr[j];
                    j -= h;
                }
                arr[j + h] = tmp;
            }
            cdl.countDown();
        }
    }

    private static void parallelShellSort() throws InterruptedException {
        int h = 1;
        CountDownLatch cdl = null;

        while (h <= arr.length / 3) {
            h = h * 3 + 1;
        }

        while (h > 0) {
            System.out.println("h = " + h);
            if (h > 4) {
                cdl = new CountDownLatch(arr.length - h);
            }
            for (int i = h; i < arr.length; i++) {
                if (h > 4) {
                    pool.submit(new ShellTask(i, h, cdl));
                } else {
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
            }
            if (cdl != null) {
                cdl.await();
            }
            h = (h - 1) / 3;
        }
    }
}
