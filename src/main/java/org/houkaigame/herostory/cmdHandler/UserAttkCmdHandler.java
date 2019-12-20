package org.houkaigame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.houkaigame.herostory.Broadcaster;
import org.houkaigame.herostory.model.User;
import org.houkaigame.herostory.model.UserManager;
import org.houkaigame.herostory.mq.MQProducer;
import org.houkaigame.herostory.mq.VictorMsg;
import org.houkaigame.herostory.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户攻击指令处理器
 */
public class UserAttkCmdHandler implements ICmdHandler<GameMsgProtocol.UserAttkCmd> {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAttkCmdHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserAttkCmd cmd) {
        if (null == ctx ||
                null == cmd) {
            return;
        }

        // 获取攻击者 Id
        Integer attkUserId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (null == attkUserId) {
            return;
        }

        // 获取被攻击者 Id
        int targetUserId = cmd.getTargetUserId();

        GameMsgProtocol.UserAttkResult.Builder resultBuilder = GameMsgProtocol.UserAttkResult.newBuilder();
        resultBuilder.setAttkUserId(attkUserId);
        resultBuilder.setTargetUserId(targetUserId);

        GameMsgProtocol.UserAttkResult newResult = resultBuilder.build();
        Broadcaster.broadcast(newResult);

        // 获取被攻击者
        User targetUser = UserManager.getUserById(targetUserId);
        if (null == targetUser) {
            return;
        }

        // 在此打印线程名称
        LOGGER.info("当前线程 = {}", Thread.currentThread().getName());
        // 我们可以看到不相同的线程名称...
        // 用户 A 在攻击用户 C 的时候, 是在线程 1 里,
        // 用户 B 在攻击用户 C 的时候, 是在线程 2 里,
        // 线程 1 和线程 2 同时修改用户 C 的血量...
        // 这是要出事的节奏啊!

        // 可以根据自己的喜好写,
        // 例如加上装备加成、躲避、格挡、暴击等等...
        // 这些都属于游戏的业务逻辑了!
        int subtractHp = 10;
        targetUser.currHp = targetUser.currHp - subtractHp;

        // 广播减血消息
        broadcastSubtractHp(targetUserId, subtractHp);

        if (targetUser.currHp <= 0) {
            // 广播死亡消息
            broadcastDie(targetUserId);
            if (!targetUser.died){
                //设置死亡标志
                targetUser.died=true;

                // 发送消息到 MQ
                VictorMsg mqMsg = new VictorMsg();
                mqMsg.winnerId = attkUserId;
                mqMsg.loserId = targetUserId;
                MQProducer.sendMsg("Victor", mqMsg);
            }
        }
    }

    /**
     * 广播减血消息
     *
     * @param targetUserId 被攻击者 Id
     * @param subtractHp   减血量
     */
    static private void broadcastSubtractHp(int targetUserId, int subtractHp) {
        if (targetUserId <= 0 ||
                subtractHp <= 0) {
            return;
        }

        GameMsgProtocol.UserSubtractHpResult.Builder resultBuilder = GameMsgProtocol.UserSubtractHpResult.newBuilder();
        resultBuilder.setTargetUserId(targetUserId);
        resultBuilder.setSubtractHp(subtractHp);

        GameMsgProtocol.UserSubtractHpResult newResult = resultBuilder.build();
        Broadcaster.broadcast(newResult);
    }

    /**
     * 广播死亡消息
     *
     * @param targetUserId 被攻击者 Id
     */
    static private void broadcastDie(int targetUserId) {
        if (targetUserId <= 0) {
            return;
        }

        GameMsgProtocol.UserDieResult.Builder resultBuilder = GameMsgProtocol.UserDieResult.newBuilder();
        resultBuilder.setTargetUserId(targetUserId);

        GameMsgProtocol.UserDieResult newResult = resultBuilder.build();
        Broadcaster.broadcast(newResult);
    }
}