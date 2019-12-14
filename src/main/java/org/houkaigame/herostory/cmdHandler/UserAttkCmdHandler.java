package org.houkaigame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.houkaigame.herostory.Broadcast;
import org.houkaigame.herostory.msg.GameMsgProtocol;

public class UserAttkCmdHandler implements ICmdHandler<GameMsgProtocol.UserAttkCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserAttkCmd cmd) {
        if (null==ctx ||null==cmd)return;
        //攻击者ID
        Integer attkUserId = (Integer) ctx.channel().
                attr(AttributeKey.valueOf("userId")).get();
        if (null==attkUserId)return;
        //获取被攻击者ID
        int targetUserId = cmd.getTargetUserId();
        //组装广播参数,攻击者和被攻击者
        GameMsgProtocol.UserAttkResult.Builder builder = GameMsgProtocol.UserAttkResult.newBuilder();
        builder.setAttkUserId(attkUserId);
        builder.setTargetUserId(targetUserId);
        GameMsgProtocol.UserAttkResult build = builder.build();
        //广播
        Broadcast.broadcast(build);
        // 减血消息, 可以根据自己的喜好写...
        // 例如加上装备加成, 暴击等等.
        // 这些都属于游戏的业务逻辑了!
        GameMsgProtocol.UserSubtractHpResult.Builder builder1 = GameMsgProtocol.UserSubtractHpResult.newBuilder();
        builder1.setTargetUserId(targetUserId);
        builder1.setSubtractHp(10);

        GameMsgProtocol.UserSubtractHpResult build1 = builder1.build();
        Broadcast.broadcast(build1);


    }
}
