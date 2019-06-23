package org.chain.current.demo.parallelpattern.sort;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 奇偶交换排序并行模式
 */
public class ParallelOddEvenSortDemo {

    private static int[] arr;

    private static ExecutorService pool = Executors.newCachedThreadPool();

    private static int exchFlag = 1;

    private static synchronized int getExchFlag() {
        return exchFlag;
    }

    private static synchronized void setExchFlag(int v) {
        exchFlag = v;
    }

    private static class ExchangeTask implements Runnable {
        int i;
        CountDownLatch cdl;

        ExchangeTask(int i, CountDownLatch cdl) {
            this.i = i;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            if (arr[i] > arr[i + 1]) {
                arr[i] = arr[i] ^ arr[i + 1];
                arr[i + 1] = arr[i] ^ arr[i + 1];
                arr[i] = arr[i] ^ arr[i + 1];
                setExchFlag(1);
            }
            cdl.countDown();
        }
    }

    private static void parallelOddEvenSort() throws InterruptedException {
        int start = 0;
        while (getExchFlag() == 1 || start == 1) {
            setExchFlag(0);
            CountDownLatch cdl = new CountDownLatch(arr.length / 2 - (arr.length % 2 == 0 ? start : 0));
            for (int i = start; i < arr.length - 1; i += 2) {
                pool.submit(new ExchangeTask(i, cdl));
            }
            cdl.await();
            start = start == 0 ? 1 : 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        arr = DemoUtil.init(10, 100);
        System.out.println("the origin arr is:" + Arrays.toString(arr));
        parallelOddEvenSort();
        System.out.println("the sorted arr is:" + Arrays.toString(arr));
        pool.shutdown();
    }
}
