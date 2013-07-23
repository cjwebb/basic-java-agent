package com.github.cjwebb.basicjavaagent;

import org.junit.Test;

public class AgentTest {

    @Test
    public void shouldInstantiateSleepingInstance() throws InterruptedException {
        Sleeping sleeping = new Sleeping();
        sleeping.randomSleep();
    }
}