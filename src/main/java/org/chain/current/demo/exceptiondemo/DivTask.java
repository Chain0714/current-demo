package org.chain.current.demo.exceptiondemo;

import java.util.concurrent.Callable;

public class DivTask implements Runnable {
    private int a;
    private int b;

    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":" + a / b);
    }

//    @Override
//    public Integer call() throws Exception {
//        System.out.println(a / b);
//        return a / b;
//    }
}
