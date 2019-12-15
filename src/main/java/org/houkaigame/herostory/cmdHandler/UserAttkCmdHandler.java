package org.houkaigame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.houkaigame.herostory.Broadcast;
import org.houkaigame.herostory.model.User;
import org.houkaigame.herostory.model.UserManager;
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

        //获取被攻击用户
        User targetUser = UserManager.getUserById(targetUserId);
        if (null==targetUser)return;
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
        int subtractHp=10;
        targetUser.currHp=targetUser.currHp-subtractHp;

        //广播减血消息
        broadcastSubtracHp(targetUserId,subtractHp);

        //广播死亡消息
        if (targetUser.currHp<=0) broadcastDie(targetUserId);


    }

    /**
     * 广播减血消息
     * @param targetUserId 目标用户ID
     * @param subtractHp 减血量
     */
    static private void broadcastSubtracHp(int targetUserId,int subtractHp){
        if (targetUserId<=0||subtractHp<=0)return;
        GameMsgProtocol.UserSubtractHpResult.Builder builder1 = GameMsgProtocol.UserSubtractHpResult.newBuilder();
        builder1.setTargetUserId(targetUserId);
        builder1.setSubtractHp(subtractHp);

        GameMsgProtocol.UserSubtractHpResult newResult = builder1.build();
        Broadcast.broadcast(newResult);
    }
    /**
     * 广播死亡消息
     * @param targetUserId 目标用户ID
     */
    static private void broadcastDie(int targetUserId){
        if (targetUserId<=0)return;
        GameMsgProtocol.UserDieResult.Builder builder1 = GameMsgProtocol.UserDieResult.newBuilder();
        builder1.setTargetUserId(targetUserId);

        GameMsgProtocol.UserDieResult newResult = builder1.build();
        Broadcast.broadcast(newResult);
    }
}
