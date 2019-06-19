package org.chain.current.demo.parallelpattern;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 流水线demo
 * 计算（B+C)*B/2
 */
public class FlowLineDemo {
    private static BlockingQueue<Msg> qOne = new LinkedBlockingQueue<>();
    private static BlockingQueue<Msg> qTwo = new LinkedBlockingQueue<>();
    private static BlockingQueue<Msg> qThree = new LinkedBlockingQueue<>();

    static class Msg {
        float a;
        float b;
    }

    static class ProcessOne implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Msg temp = qOne.take();
                    Msg result = new Msg();
                    result.a = (temp.a + temp.b);
                    result.b = temp.a;
                    qTwo.put(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ProcessTwo implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Msg temp = qTwo.take();
                    Msg result = new Msg();
                    result.a = temp.a * temp.b;
                    qThree.put(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ProcessThree implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Msg temp = qThree.take();
                    Msg result = new Msg();
                    result.a = temp.a / 2;
                    System.out.println("flow end,result = " + result.a);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                Random random = new Random();
                Msg msg = new Msg();
                msg.a = random.nextInt(10);
                msg.b = random.nextInt(10);
                try {
                    qOne.put(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Producer());
        Thread t2 = new Thread(new ProcessOne());
        Thread t3 = new Thread(new ProcessTwo());
        Thread t4 = new Thread(new ProcessThree());
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }


}
