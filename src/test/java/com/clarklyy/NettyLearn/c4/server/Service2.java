package com.clarklyy.NettyLearn.c4.server;

import com.clarklyy.NettyLearn.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 异步阻塞（java nio用到了selector）
 */
@Slf4j
public class Service2 {
    public static void main(String[] args) throws IOException {
        //1. 建立selector
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//selector要工作在非阻塞模式下

        //2. 建立selector和ssc的联系（注册）
        SelectionKey sscKey = ssc.register(selector, 0, null);

        //3. 设置sscKey感兴趣的事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        while(true){
            selector.select();
            //4. 处理事件，selectorKeys内部包含所有事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

            while(iter.hasNext()){
                SelectionKey key = iter.next();
                //4.1处理事件时，要把key从selectedKeys中删除
                iter.remove();
                log.debug("key: {}",key);
                //5. 区分事件类型
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);//selector要工作在非阻塞模式下
                    ByteBuffer buffer = ByteBuffer.allocate(4);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("sc: {}",sc);
                }else if(key.isReadable()){
                    try{
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);
                        //客户端返回值为-1，表示客户端正常断开
                        if(read==-1){
                            key.cancel();
                        }
//                        buffer.flip();
//                        System.out.println(Charset.defaultCharset().decode(buffer));
//                        ByteBufferUtil.debugRead(buffer);
                        //1. 使用分隔符方式处理消息边界，如果buffer不够大，需要手动扩容
                        ByteBufferUtil.split(buffer);
                        //手动扩容步骤
                        if(buffer.position()==buffer.limit()){
                            ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity()*2);
                            buffer.flip();
                            newBuffer.put(buffer);
                            key.attach(newBuffer);
                        }
                        buffer.clear();
                    }catch (Exception e){
                        //客户端异常断开连接，需要将这个key从selector中取消
                        e.printStackTrace();
                        key.cancel();

                    }
                }

            }
        }
    }
}
