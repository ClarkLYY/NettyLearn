package com.clarklyy.jucLearn.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InterruptLearn {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){
                //阶段2 终止执行阶段
                if(Thread.currentThread().isInterrupted()){
                    log.debug("终止处理操作");
                    break;
                }
                //业务处理
                try {
                    Thread.sleep(1000);
                    log.debug("业务处理");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //sleep时打断，需要手动加打断标记
                    Thread.currentThread().interrupt();
                }
            }
        });


        t1.start();

        Thread.sleep(3000);
        //阶段1 终止准备阶段
        t1.interrupt();
    }
}
