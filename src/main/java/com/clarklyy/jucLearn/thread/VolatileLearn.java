package com.clarklyy.jucLearn.thread;

public class VolatileLearn {
    static int i=0;
    static  boolean  flag=false;
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            i=1;

            flag=true;
        },"t1");


        Thread t2 = new Thread(()->{
            if(flag){
                System.out.println(i);
            }else{
                System.out.println(false);
            }
        },"t2");

        t1.start();
        t2.start();



    }
}
