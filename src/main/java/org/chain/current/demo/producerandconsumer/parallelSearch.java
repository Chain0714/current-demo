package org.chain.current.demo.producerandconsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并行搜索demo
 */
public class parallelSearch {
    private static int[] arr;
    private static AtomicInteger result = new AtomicInteger(-1);

    private static Integer search(int val, int start, int end) {
        for (int i = start; i < end; i++) {
            if (result.get() != -1) {
                return result.get();
            }
            if (val == arr[i]) {
                result.compareAndSet(-1, i);
                return result.get();
            }
        }
        return -1;
    }

    static class SearchTask implements Callable<Integer> {
        private int val;
        private int start;
        private int end;

        public SearchTask(int val, int start, int end) {
            this.val = val;
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("ThreadName:" + Thread.currentThread().getName() + ",start:" + start + ",end:" + end);
            return search(val, start, end);
        }
    }

    private static int PSearch(int val, int threadNum) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        List<Future<Integer>> futures = new ArrayList<>();
        int step = arr.length / threadNum;
        for (int i = 0; i < arr.length; i += step) {
            int end = i + step;
            if ((end + step) > arr.length) {
                end = arr.length;
                futures.add(executorService.submit(new SearchTask(val, i, end)));
                break;
            }
            futures.add(executorService.submit(new SearchTask(val, i, end)));
        }
        executorService.shutdown();
        for (Future<Integer> future : futures) {
            if (future.get() >= 0) {
                return future.get();
            }
        }
        return -1;
    }

    private static void initArray(int i) {
        arr = new int[i];
        Random random = new Random();
        for (int j = 0; j < i; j++) {
            arr[j] = random.nextInt(10);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        initArray(10);
        System.out.println("arr:" + Arrays.toString(arr));
        System.out.println("result:" + PSearch(5, 3));
    }
}
