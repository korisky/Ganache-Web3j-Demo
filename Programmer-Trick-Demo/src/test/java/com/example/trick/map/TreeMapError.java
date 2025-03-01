package com.example.trick.map;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ThreadFactory;

@Slf4j
public class TreeMapError {

    // 多线程修改TreeMap操作时(主要针对红黑树), 当两个线程同时对树进行相反的扭转操作时, 可能导致CPU 100%
    // https://josephmate.github.io/2025-02-26-3200p-cpu-util/


    // 同时作者建议对Thread进行Uncaught抓取
    @Test
    public void threadWithCatching() {
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("xxx-");
                thread.setUncaughtExceptionHandler(
                        (dyingThread, throwable) -> {
                            log.error("uncaught exception:", throwable);
                        });
                return thread;
            }
        };
    }

}
