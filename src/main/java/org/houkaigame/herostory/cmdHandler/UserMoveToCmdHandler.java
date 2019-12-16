package org.houkaigame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import org.houkaigame.herostory.Broadcaster;
import org.houkaigame.herostory.model.MoveState;
import org.houkaigame.herostory.model.User;
import org.houkaigame.herostory.model.UserManager;
import org.houkaigame.herostory.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户移动指令处理器
 */
public class UserMoveToCmdHandler implements ICmdHandler<GameMsgProtocol.UserMoveToCmd> {
    /**
     * 日志对象
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(UserMoveToCmdHandler.class);
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserMoveToCmd cmd) {
        if (null==ctx||null==cmd)return;
        //获取用户ID
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if(null ==userId)return;
        //获取移动用户
        User moveUser= UserManager.getUserById(userId);
        if (null == moveUser) {
            LOGGER.error("未找到用户, userId = {}", userId);
            return;
        }
        //获取移动状态
        MoveState mvState = moveUser.moveState;
        // 设置位置和开始时间
        mvState.fromPosX = cmd.getMoveFromPosX();
        mvState.fromPosY = cmd.getMoveFromPosY();
        mvState.toPosX = cmd.getMoveToPosX();
        mvState.toPosY = cmd.getMoveToPosY();
        mvState.startTime = System.currentTimeMillis();
        GameMsgProtocol.UserMoveToResult.Builder resultBuilder = GameMsgProtocol.UserMoveToResult.newBuilder();
        resultBuilder.setMoveUserId(userId);
        resultBuilder.setMoveFromPosX(mvState.fromPosX);
        resultBuilder.setMoveFromPosY(mvState.fromPosY);
        resultBuilder.setMoveToPosX(mvState.toPosX);
        resultBuilder.setMoveToPosY(mvState.toPosY);
        resultBuilder.setMoveStartTime(mvState.startTime);
        GameMsgProtocol.UserMoveToResult build = resultBuilder.build();
        Broadcaster.broadcast(build);
    }
}
