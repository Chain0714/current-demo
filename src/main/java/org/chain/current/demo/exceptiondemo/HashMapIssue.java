package org.chain.current.demo.exceptiondemo;

import java.util.HashMap;

public class HashMapIssue {
    static class HashMapThread implements Runnable {
        int startNum;
        HashMap<String, String> hashMap;

        HashMapThread(int startNum, HashMap<String, String> hashMap) {
            this.startNum = startNum;
            this.hashMap = hashMap;
        }

        @Override
        public void run() {
            for (int i = startNum; i < 10000; i += 2) {
                hashMap.put(String.valueOf(i), "test");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, String> hashMap = new HashMap<>(16);
        Thread t1 = new Thread(new HashMapThread(0, hashMap));
        Thread t2 = new Thread(new HashMapThread(1, hashMap));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("process end the map size is " + hashMap.size());
    }
}
