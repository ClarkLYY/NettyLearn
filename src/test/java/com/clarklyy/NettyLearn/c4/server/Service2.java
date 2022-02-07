package com.clarklyy.NettyLearn.c4.server;

import com.clarklyy.NettyLearn.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
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
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("sc: {}",sc);
                }else if(key.isReadable()){
                    try{
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(32);
                        int read = channel.read(buffer);
                        //客户端返回值为-1，表示客户端正常断开
                        if(read==-1){
                            key.cancel();
                        }
                        buffer.flip();
                        ByteBufferUtil.debugRead(buffer);
                        buffer.clear();
                    }catch (Exception e){
                        //客户端异常断开连接，需要将这个key从selector中取消
                        key.cancel();
                    }
                }

            }
        }
    }
}
