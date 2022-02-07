package com.clarklyy.NettyLearn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("data.txt").getChannel();
                FileChannel to = new FileOutputStream("to.txt").getChannel()
        ) {
            long size = from.size();
            for(long left = size;left>0;){
                left -= from.transferTo(0,from.size(), to);
            }
        } catch (IOException e) {
        }
    }
}
