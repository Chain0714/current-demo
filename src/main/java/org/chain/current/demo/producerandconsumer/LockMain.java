package org.chain.current.demo.producerandconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockMain {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        Condition getCondition = lock.newCondition();
        Condition putCondition = lock.newCondition();
        Resource1 resource1 = new Resource1(5, lock, getCondition, putCondition);
        ProducerConsumerUtil.start(new Producer(resource1), new Consumer(resource1));
    }

    public static class Consumer implements Runnable {

        private Resource1 resource1;

        public Consumer(Resource1 resource1) {
            this.resource1 = resource1;
        }

        @Override
        public void run() {
            while (true) {
                resource1.getOne();
            }
        }
    }

    public static class Producer implements Runnable {

        private Resource1 resource1;

        public Producer(Resource1 resource1) {
            this.resource1 = resource1;
        }

        @Override
        public void run() {
            while (true) {
                resource1.putOne();
            }
        }
    }
}

