package org.chain.current.demo.producerandconsumer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Resource1 implements Resource{
    private int num;
    private ReentrantLock lock;
    private Condition getCondition;
    private Condition putCondition;

    public Resource1(int num, ReentrantLock lock, Condition getCondition, Condition putCondition) {
        this.num = num;
        this.lock = lock;
        this.getCondition = getCondition;
        this.putCondition = putCondition;
    }

    public void getOne() {
        lock.lock();
        try {
            if (num > 0) {
                TimeUnit.SECONDS.sleep(2);
                num--;
                System.out.println(Thread.currentThread().getThreadGroup() + ":" + Thread.currentThread().getName() + "取出一个货物，当前数量是" + num);
                putCondition.signalAll();
            } else {
                System.out.println(Thread.currentThread().getThreadGroup() + ":" + Thread.currentThread().getName() + "资源不足，等待。。。");
                getCondition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void putOne() {
        lock.lock();
        try {
            if (num < 10) {
                TimeUnit.SECONDS.sleep(1);
                num++;
                System.out.println(Thread.currentThread().getThreadGroup() + ":" + Thread.currentThread().getName() + "存入一个货物，当前数量是" + num);
                getCondition.signalAll();
            } else {
                System.out.println(Thread.currentThread().getThreadGroup() + ":" + Thread.currentThread().getName() + "资源满载，等待。。。");
                putCondition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }
}
