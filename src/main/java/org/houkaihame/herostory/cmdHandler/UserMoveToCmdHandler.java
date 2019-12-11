package org.houkaihame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.houkaihame.herostory.Broadcast;
import org.houkaihame.herostory.msg.GameMsgProtocol;
@CmdHandler(name=GameMsgProtocol.UserMoveToCmd.class)
public final class UserMoveToCmdHandler implements ICmdHandler<GameMsgProtocol.UserMoveToCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserMoveToCmd msg) {
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if(null ==userId){
            return;
        }
        GameMsgProtocol.UserMoveToCmd cmd= msg;
        GameMsgProtocol.UserMoveToResult.Builder builder = GameMsgProtocol.UserMoveToResult.newBuilder();
        builder.setMoveUserId(userId);
        builder.setMoveToPosX(cmd.getMoveToPosX());
        builder.setMoveToPosY(cmd.getMoveToPosY());
        GameMsgProtocol.UserMoveToResult build = builder.build();
        Broadcast.broadcast(build);
    }
}
