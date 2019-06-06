package org.chain.current.demo.exceptiondemo;

import java.util.concurrent.*;

public class DemoOne {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            //submit 会丢失异常信息
//            Future<Integer> future = executor.submit(new DivTask(100, i));
            executor.execute(new DivTask(100, i));
        }
        executor.shutdown();
        while (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            System.out.println("等待线程池关闭。。。");
        }
        if (executor.isTerminated()) {
            System.out.println("线程池已关闭！");
        }
        System.out.println("exit!");
    }
}
