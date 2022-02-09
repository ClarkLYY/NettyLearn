package com.clarklyy.NettyLearn.nio.bufferChannel;

import java.nio.ByteBuffer;

import static com.clarklyy.NettyLearn.util.ByteBufferUtil.debugAll;

/**
    粘包半包问题
 **/
public class TestByteBufferExam {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(100);
        source.put("hello world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for(int i=0;i<source.limit();i++){
            if(source.get(i)=='\n'){
                int length = i+1-source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j=0;j<length;j++){
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
}
