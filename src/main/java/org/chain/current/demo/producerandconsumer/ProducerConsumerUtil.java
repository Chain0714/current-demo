package org.chain.current.demo.producerandconsumer;

public class ProducerConsumerUtil {
    public static void start(Runnable producer, Runnable consumer) throws InterruptedException {
        ThreadGroup consumerGroup = new ThreadGroup("consumer");
        ThreadGroup producerGroup = new ThreadGroup("producer");
        Thread c1 = new Thread(consumerGroup, consumer, "consumer1");
        Thread c2 = new Thread(consumerGroup, consumer, "consumer2");
        Thread c3 = new Thread(consumerGroup, consumer, "consumer3");
        Thread c4 = new Thread(consumerGroup, consumer, "consumer4");

        Thread p = new Thread(producerGroup, producer, "producer1");

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        p.start();
        c1.join();
        c2.join();
        c3.join();
        c4.join();
        p.join();
    }
}
