package com.clarklyy.jucLearn.thread;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.*;

@Slf4j
public class teaLean {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                log.debug("洗茶壶中。。。");
                Thread.sleep(1);
                log.debug("烧水中。。。");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A");

        Thread t2 = new Thread(()->{
            try {
                log.debug("洗茶叶。。。");
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("泡茶");
        Thread.sleep(10);
    }
}
