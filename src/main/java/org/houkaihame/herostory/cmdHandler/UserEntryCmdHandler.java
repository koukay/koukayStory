package org.houkaihame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.houkaihame.herostory.Broadcast;
import org.houkaihame.herostory.anno.CmdHandler;
import org.houkaihame.herostory.model.User;
import org.houkaihame.herostory.model.UserManager;
import org.houkaihame.herostory.msg.GameMsgProtocol;
@CmdHandler(name=GameMsgProtocol.UserEntryCmd.class)
public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd msg) {
        GameMsgProtocol.UserEntryCmd cmd = msg;
        //从指令对象中获取userID和英雄形象
        int userId =cmd.getUserId();
        String heroAvatar =cmd.getHeroAvatar();
        GameMsgProtocol.UserEntryResult.Builder  builder=GameMsgProtocol.UserEntryResult.newBuilder();
        builder.setUserId(userId);
        builder.setHeroAvatar(heroAvatar);
        //将用户加入字典
        User user =new User();
        user.userId=userId;
        user.heroAvatar=heroAvatar;
        UserManager.addUser(user);
        //将用户ID附着到Channel
        ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);
        //构建结果并发送
        GameMsgProtocol.UserEntryResult build = builder.build();
        Broadcast.broadcast(build);
    }


}
