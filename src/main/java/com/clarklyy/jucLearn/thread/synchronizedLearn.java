package com.clarklyy.jucLearn.thread;

public class synchronizedLearn {
    static final Object lock = new Object();
    static int count =0;

    public static void main(String[] args) {
        synchronized (lock){
            count++;
        }
    }
}
