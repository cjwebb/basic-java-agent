package com.github.cjwebb.basicjavaagent;

/**
 * This is the class that will be instrumented
 */
public class Sleeping {

    public void randomSleep() throws InterruptedException {
        // randomly sleeps between 500ms and 1200s
        long randomSleepDuration = (long) (500 + Math.random() * 700);
        System.out.printf("Sleeping for %d ms ..\n", randomSleepDuration);
        Thread.sleep(randomSleepDuration);
    }
}