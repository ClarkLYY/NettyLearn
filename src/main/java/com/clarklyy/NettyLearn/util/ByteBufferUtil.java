package com.clarklyy.NettyLearn.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

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

    public static void split(ByteBuffer source) {
        source.flip();
        for(int i=0;i<source.limit();i++){
            if(source.get(i)=='\n'){
                int length = i+1-source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j=0;j<length;j++){
                    target.put(source.get());
                }
                target.flip();
                System.out.println(Charset.defaultCharset().decode(target));
            }
        }
        source.compact();
    }
}
