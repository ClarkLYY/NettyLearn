package com.clarklyy.NettyLearn.nio.bufferChannel;

import com.clarklyy.NettyLearn.util.ByteBufferUtil;

import java.nio.ByteBuffer;

public class TestByteBufferWriteRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
        buffer.put((byte) 97);
        ByteBufferUtil.debugAll(buffer);
        buffer.put(new byte[]{98, 99, 100});
        ByteBufferUtil.debugAll(buffer);
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);
        buffer.compact();
        ByteBufferUtil.debugAll(buffer);
        buffer.put((byte)101);
        ByteBufferUtil.debugAll(buffer);
    }
}
