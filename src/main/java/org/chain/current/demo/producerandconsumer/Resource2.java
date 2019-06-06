package org.chain.current.demo.producerandconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Resource2 implements Resource {
    private BlockingQueue<Integer> queue;

    public Resource2(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void getOne() {
        try {
            TimeUnit.SECONDS.sleep(5);
            queue.take();
            System.out.println(Thread.currentThread().getThreadGroup() + ":" + Thread.currentThread().getName() + "取出一个货物，当前数量是" + queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void putOne() {
        try {
            TimeUnit.SECONDS.sleep(1);
            queue.put(0);
            System.out.println(Thread.currentThread().getThreadGroup() + ":" + Thread.currentThread().getName() + "存入一个货物，当前数量是" + queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
