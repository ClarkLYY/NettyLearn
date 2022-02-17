package com.clarklyy.jucLearn.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class plusAndMinus {
    static int i=0;
    static Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            synchronized (lock){
                for(int j=0;j<5000;j++){
                    i++;
                }
            }
        });
        Thread t2 = new Thread(()->{
            synchronized (lock){
                for(int j=0;j<5000;j++){
                    i--;
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}",i);
    }
}
