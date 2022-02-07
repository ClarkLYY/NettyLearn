package com.clarklyy.NettyLearn;

import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;


@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while(true){
                int len = channel.read(buffer);
                log.info("读取到的字节{}",len);
                if(len==-1) break;
                buffer.flip();
                while(buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.info("实际的字节{}", (char)b);
                }
                buffer.clear();
            }
        } catch (IOException e) {
        }

    }
}
