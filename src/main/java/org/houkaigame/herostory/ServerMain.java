package org.houkaigame.herostory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import org.houkaigame.herostory.cmdHandler.CmdHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMain {
    static private final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);
    public static void main(String[] args) {
        CmdHandlerFactory.init();
        GameMsgRecoginizer.init();
        //处理客户端连接线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();// 拉客的, 也就是故事中的美女
        //处理客户端连接线程池
        EventLoopGroup workerGroup=new NioEventLoopGroup();// 干活的, 也就是故事中的服务生
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workerGroup);
        //通过NioServerSocketChannel连接netty
        b.channel(NioServerSocketChannel.class);// 服务器信道的处理方式
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        new HttpServerCodec(),//Http服务器编解码器
                        new HttpObjectAggregator(65535),//内容长度限制
                        new WebSocketServerProtocolHandler("/websocket"),//WebSocket协议处理器,在这里处理握手,ping,pong等消息
                        new GameMsgDecoder(),//自定义消息解码器
                        new GameMsgEncoder(),//自定义消息编码器
                        new GameMsgHandler()//自定义消息处理器
                );
            }
        });
        try {
            // 绑定 12345 端口,
            // 注意: 实际项目中会使用 argArray 中的参数来指定端口号
            ChannelFuture f = b.bind(12345).sync();
            if (f.isSuccess())   LOGGER.info("服务器启动成功!");
            // 等待服务器信道关闭,
            // 也就是不要立即退出应用程序, 让应用程序可以一直提供服务
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

    }
}
