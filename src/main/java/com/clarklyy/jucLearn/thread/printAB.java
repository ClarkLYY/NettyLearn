package com.clarklyy.jucLearn.thread;

/**
 * 交替打印AB
 */
public class printAB {
    static final Object lock = new Object();
    static void printA(){
        System.out.println("A");
    }
    static void printB(){
        System.out.println("B");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            int count=100;
            while(count-->0){
                synchronized (lock){
                    printA();
                    lock.notifyAll();
                    try {
                        if(count>0){
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "ThreadA");

        Thread t2 = new Thread(()->{
            int count=100;
            while(count-->0){
                synchronized (lock){
                    printB();
                    lock.notifyAll();
                    try {
                        if(count>0){
                            lock.wait();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "ThreadB");
        t1.start();
        t2.start();
    }
}
