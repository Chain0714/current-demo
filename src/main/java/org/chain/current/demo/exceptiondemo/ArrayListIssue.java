package org.chain.current.demo.exceptiondemo;

import java.util.ArrayList;

public class ArrayListIssue {
    static class ArrayListThread implements Runnable {

        private ArrayList<Integer> list;

        ArrayListThread(ArrayList<Integer> lsit) {
            this.list = lsit;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++) {
                list.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> list = new ArrayList<>(10);
        Thread t1 = new Thread(new ArrayListThread(list));
        Thread t2 = new Thread(new ArrayListThread(list));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("process end...,the list size is " + list.size());
    }
}
