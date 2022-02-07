package com.clarklyy.NettyLearn.util;

import java.nio.ByteBuffer;

public class ByteBufferUtil {
    public static void debugAll(ByteBuffer byteBuffer){
        System.out.println("========================================");
        System.out.println("position: "+byteBuffer.position() + "       limit: "+byteBuffer.limit());
        StringBuilder sb = new StringBuilder();
        byte[] bytes = byteBuffer.array();
        for(byte b:bytes){
            System.out.print(b+" ");
            sb.append((char)b);
        }
        System.out.println(sb.toString());
    }

    public static void debugRead(ByteBuffer buffer){
        while(buffer.hasRemaining()){
            System.out.print((char) buffer.get());
        }
        System.out.println();
    }
}
