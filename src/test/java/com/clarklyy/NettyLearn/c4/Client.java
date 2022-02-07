package com.clarklyy.NettyLearn.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        while(true){
            sc.write(Charset.defaultCharset().encode("heladsfasdflo!\n"));
            Thread.sleep(2000);
        }
//        sc.write(Charset.defaultCharset().encode("heladsfasdflo!\n"));
//        System.out.println("waiting...");
//        sc.close();
//        System.out.println("waiting...");
    }
}
