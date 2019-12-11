package org.houkaigame.herostory;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public final class Broadcast {
    //客户端信道数组,一定要使用static,否则无发实现群发
    static  private final ChannelGroup _channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private Broadcast(){

    }

    /**
     * 添加信道
     * @param channel
     */
    static public void addChannel(Channel channel){
        _channelGroup.add(channel);
    }

    /**
     * 移除信道
     * @param channel
     */
    static public void removeChannel(Channel channel){
        _channelGroup.remove(channel);
    }

    /**
     * 关闭信道
     * @param msg
     */
    static public void broadcast(Object msg){
        _channelGroup.writeAndFlush(msg);
    }
}
