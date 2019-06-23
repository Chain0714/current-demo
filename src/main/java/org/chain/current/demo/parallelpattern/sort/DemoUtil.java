package org.chain.current.demo.parallelpattern.sort;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Random;

public class DemoUtil {
    static int[] init(int length, int max) {
        assert max > 0;
        Random random = new Random();
        int[] intArr = new int[length];
        for (int i = 0; i < length; i++) {
            intArr[i] = random.nextInt(max);
        }
        return intArr;
    }

    private static StopWatch sw = new StopWatch();

    public static void startPoint() {
        sw.reset();
        sw.start();
    }

    public static void endPoint() {
        sw.stop();
        System.out.println("耗时：" + sw.getTime());
    }
}
