package org.houkaigame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import org.houkaigame.herostory.Broadcaster;
import org.houkaigame.herostory.model.User;
import org.houkaigame.herostory.model.UserManager;
import org.houkaigame.herostory.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户入场指令处理器
 */
public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd> {
    /**
     * 日志对象
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(UserEntryCmdHandler.class);
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd cmd) {
        if (null==ctx||null==cmd)return;
        //从指令对象中获取userID和英雄形象
        Integer userId= (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (null == userId) return;
        //获取已有用户
        User userById = UserManager.getUserById(userId);
        if (null==userById){
            LOGGER.error("用户不存在, userId = {}", userId);
            return;
        }
        //获取英雄形象
        String heroAvatar =userById.heroAvatar;

        GameMsgProtocol.UserEntryResult.Builder  resultBuilder=GameMsgProtocol.UserEntryResult.newBuilder();
        resultBuilder.setUserId(userId);
        resultBuilder.setHeroAvatar(heroAvatar);

        //构建结果并发送
        GameMsgProtocol.UserEntryResult newResult = resultBuilder.build();
        Broadcaster.broadcast(newResult);
    }


}
