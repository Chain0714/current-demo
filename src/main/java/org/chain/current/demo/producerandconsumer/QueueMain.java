package org.chain.current.demo.producerandconsumer;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueMain {
    public static void main(String[] args) throws InterruptedException {
        Resource2 resource2 = new Resource2(new LinkedBlockingQueue<>(10));
        ProducerConsumerUtil.start(new Producer(resource2), new Consumer(resource2));
    }

    private static class Consumer implements Runnable {
        private Resource2 resource2;

        public Consumer(Resource2 resource2) {
            this.resource2 = resource2;
        }

        @Override
        public void run() {
            while (true) {
                resource2.getOne();
            }
        }
    }

    private static class Producer implements Runnable {
        private Resource2 resource2;

        public Producer(Resource2 resource2) {
            this.resource2 = resource2;
        }

        @Override
        public void run() {
            while (true) {
                resource2.putOne();
            }
        }
    }
}
