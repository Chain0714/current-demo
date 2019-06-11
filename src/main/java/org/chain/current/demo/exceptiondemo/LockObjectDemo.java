package org.chain.current.demo.exceptiondemo;

/**
 * 锁应该加在正确的对象上
 */
public class LockObjectDemo {
    static class BadLockOnInteger implements Runnable {

        Integer integer = 0;

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                synchronized (integer) {
                    integer++;
                }
//                synchronized (this) {
//                    integer++;
//                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BadLockOnInteger badLockOnInteger = new BadLockOnInteger();
        Thread t1 = new Thread(badLockOnInteger);
        Thread t2 = new Thread(badLockOnInteger);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("process is end the integer is " + badLockOnInteger.integer);
    }
}
