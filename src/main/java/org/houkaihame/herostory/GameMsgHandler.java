package org.houkaihame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.houkaihame.herostory.cmdHandler.*;
import org.houkaihame.herostory.model.UserManager;
import org.houkaihame.herostory.msg.GameMsgProtocol;

/**
 * 自定义消息处理器
 */
public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Broadcast.addChannel(ctx.channel());
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        Broadcast.removeChannel(ctx.channel());
        //先拿到userID
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if(null ==userId){
            return;
        }
        UserManager.removeUserById(userId);
        GameMsgProtocol.UserQuitResult.Builder builder = GameMsgProtocol.UserQuitResult.newBuilder();
        builder.setQuitUserId(userId);
        GameMsgProtocol.UserQuitResult build = builder.build();
        Broadcast.broadcast(build);
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到客户端消息,msgClass= "+msg.getClass().getName()+",msg= "+msg);
        ICmdHandler<? extends GeneratedMessageV3> cmdHandler= CmdHandlerFactory.create(msg.getClass());
        if (null !=cmdHandler) cmdHandler.handle(ctx, cast(msg));

    }

    /**
     * 转换消息对象
     * @param msg
     * @param <TCmd>
     * @return
     */
    static  private <TCmd extends GeneratedMessageV3> TCmd cast(Object msg){
        if (null == msg){
            return null;
        }else {
            return (TCmd) msg;
        }
}



}





















