package com.clarklyy.jucLearn.thread;

import org.openjdk.jol.info.ClassLayout;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class synchronizedLearn {
    static final Object obj = new Object();
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Condition condition = lock.newCondition();
        synchronized (obj){
            Thread t1 = new  Thread(()->{
                synchronized (obj){
                    System.out.println("t1获得锁");
                }
            }, "t1");

            t1.start();
            t1.interrupt();
            Thread.sleep(10000);
        }
    }
}
