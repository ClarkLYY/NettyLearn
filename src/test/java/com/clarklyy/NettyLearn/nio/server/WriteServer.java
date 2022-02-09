package com.clarklyy.NettyLearn.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * io多路复用。使用写事件处理大buffer场景
 */
public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));

        while(true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
//                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ);

                    StringBuilder sb = new StringBuilder();
                    for(int i=0;i<5000000;i++){
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    //当buffer很大，一次write写不完，buffer还有剩余的内容，有2个解决方法
//                    //方法一: 直接循环写，直到buffer为空，缺点是对于其它的sc会阻塞
//                    while(buffer.hasRemaining()){
//                        int write = sc.write(buffer);
//                        System.out.println(write);
//                    }

                    //方法二：关注写事件
                    int write = sc.write(buffer);
                    System.out.println(write);
                    if(buffer.hasRemaining()){
                        //可能sc还关注了读事件，需要同时关注写+读事件
                        scKey.interestOps(scKey.interestOps()+SelectionKey.OP_WRITE);
                        //将没写完的buffer放进去
                        scKey.attach(buffer);
                    }
                }else if(key.isWritable()){
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    System.out.println(write);
                    //buffer已经写完
                    if(!buffer.hasRemaining()){
                        //去掉写事件
                        key.interestOps(key.interestOps()-SelectionKey.OP_WRITE);
                        //清掉buffer附件
                        key.attach(null);
                    }

                }

            }
        }
    }
}
