package com.clarklyy.NettyLearn.nio.server;

import com.clarklyy.NettyLearn.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

@Slf4j
/**
 * 同步非阻塞（java nio）
 */
public class Service {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//设置ssc非阻塞
        ssc.bind(new InetSocketAddress(8080));
        List<SocketChannel> list = new ArrayList<>();
        while(true){
            SocketChannel sc = ssc.accept();// ssc.configureBlocking=true时是阻塞;false是非阻塞；非阻塞没连接时为null
            //当sc不为空，即有连接时才会将sc加入list中
            if(sc!=null){
                log.debug("connected: {}",sc);
                sc.configureBlocking(false);
                list.add(sc);
            }
            //一直遍历list中的sc
            for(SocketChannel channel:list){
                int read = channel.read(buffer);//sc.configureBlocking=true时是阻塞;false是非阻塞；非阻塞没读到数据是返回0
                //当read>0，即sc有写入时，才会去读buffer
                if(read>0){
                    log.debug("after read...{}", channel);
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();
                }

            }
        }
    }
}
