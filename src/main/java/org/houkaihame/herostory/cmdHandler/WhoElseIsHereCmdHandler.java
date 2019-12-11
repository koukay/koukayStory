package org.houkaihame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import org.houkaihame.herostory.anno.CmdHandler;
import org.houkaihame.herostory.model.User;
import org.houkaihame.herostory.model.UserManager;
import org.houkaihame.herostory.msg.GameMsgProtocol;
@CmdHandler(name=GameMsgProtocol.WhoElseIsHereCmd.class)
public final class WhoElseIsHereCmdHandler implements ICmdHandler<GameMsgProtocol.WhoElseIsHereCmd> {

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.WhoElseIsHereCmd msg) {
        GameMsgProtocol.WhoElseIsHereResult.Builder builder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();
        for (User currUser:UserManager.listUser()){
            if(null ==currUser){
                continue;
            }
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder newBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            newBuilder.setUserId(currUser.userId);
            newBuilder.setHeroAvatar(currUser.heroAvatar);
            builder.addUserInfo(newBuilder);
        }
        GameMsgProtocol.WhoElseIsHereResult build = builder.build();
        ctx.writeAndFlush(build);
    }
}
