package org.houkaihame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.houkaihame.herostory.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 消息编码器
 */
public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {
    /**
     * 日志对象
     */
    static  private  final Logger Logger= LoggerFactory.getLogger(GameMsgEncoder.class);
    @Override
    public void write(ChannelHandlerContext ctx , Object msg, ChannelPromise promise) throws Exception {
        if(null==msg || !(msg instanceof GeneratedMessageV3)){
            super.write(ctx,msg,promise);
            return;
        }
        // 获取消息编码
        int msgCode=GameMsgRecoginizer.getMsgCodeByMsgClazz(msg.getClass());
        if (msgCode<=-1){
            Logger.error("无法识别得消息,msgClass={}",msg.getClass().getName());
            return;
        }

        byte[] byteArr = ((GeneratedMessageV3) msg).toByteArray();
        ByteBuf byteBuf=ctx.alloc().buffer();
        byteBuf.writeShort((short)0);// 写出消息长度, 目前写出 0 只是为了占位
        byteBuf.writeShort((short)msgCode);// 写出消息编号
        byteBuf.writeBytes(byteArr);// 写出消息体

        BinaryWebSocketFrame frame= new BinaryWebSocketFrame(byteBuf);
        super.write(ctx, frame,promise);
    }
}
