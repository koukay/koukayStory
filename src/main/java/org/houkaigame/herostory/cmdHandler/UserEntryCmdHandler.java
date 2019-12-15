package org.houkaigame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import org.houkaigame.herostory.Broadcast;
import org.houkaigame.herostory.model.User;
import org.houkaigame.herostory.model.UserManager;
import org.houkaigame.herostory.msg.GameMsgProtocol;
/**
 * 用户入场指令处理器
 */
public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd cmd) {
        if (null==ctx||null==cmd)return;
        //从指令对象中获取userID和英雄形象
        int userId =cmd.getUserId();
        String heroAvatar =cmd.getHeroAvatar();

        GameMsgProtocol.UserEntryResult.Builder  resultBuilder=GameMsgProtocol.UserEntryResult.newBuilder();
        resultBuilder.setUserId(userId);
        resultBuilder.setHeroAvatar(heroAvatar);

        //新建用户
        User user =new User();
        user.userId=userId;
        user.heroAvatar=heroAvatar;
        user.currHp=100;

        // 并将用户加入管理器
        UserManager.addUser(user);
        //将用户ID附着到Channel
        ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);
        //构建结果并发送
        GameMsgProtocol.UserEntryResult newResult = resultBuilder.build();
        Broadcast.broadcast(newResult);
    }


}
