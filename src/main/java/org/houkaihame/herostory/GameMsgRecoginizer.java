package org.houkaihame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.houkaihame.herostory.msg.GameMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class GameMsgRecoginizer {
    /**
     * 日志对象
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgRecoginizer.class);
    /**
     * 消息代码消息体字典
     */
    static private final Map<Integer, GeneratedMessageV3> _msgCodeAndMsgBodyMap=new HashMap<>();
    /**
     * 消息类型和消息编号字典
     */
    static private final Map<Class<?>, Integer> _msgClazzAndMsgCodeMap=new HashMap<>();
    /**
     * 私有化类默认构造器
     */
    private GameMsgRecoginizer(){

    }
    static  public void init(){
        Class<?>[] innerClazzArray=GameMsgProtocol.class.getDeclaredClasses();
        for(Class<?> innerClazz: innerClazzArray ){
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClazz)){
                continue;
            }
            String clazzName=innerClazz.getSimpleName();
            clazzName=clazzName.toLowerCase();
            for(GameMsgProtocol.MsgCode msgCode:GameMsgProtocol.MsgCode.values()){
                String strMsgCode=msgCode.name();
                strMsgCode=strMsgCode.replaceAll("_", "");
                strMsgCode=strMsgCode.toLowerCase();

                if (!strMsgCode.startsWith(clazzName)){
                    continue;
                }
                try {
                    Object returnObj = innerClazz.getDeclaredMethod("getDefaultInstance").invoke(innerClazz);
                    LOGGER.info("{}<==>{}",innerClazz.getName(),msgCode.getNumber());
                    _msgCodeAndMsgBodyMap.put(msgCode.getNumber(), (GeneratedMessageV3) returnObj);
                    _msgClazzAndMsgCodeMap.put(innerClazz,msgCode.getNumber());
                }catch (Exception e){
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

        /*_msgCodeAndMsgBodyMap.put(GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE,GameMsgProtocol.UserEntryCmd.getDefaultInstance());
        _msgCodeAndMsgBodyMap.put(GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE,GameMsgProtocol.WhoElseIsHereCmd.getDefaultInstance());
        _msgCodeAndMsgBodyMap.put(GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE,GameMsgProtocol.UserMoveToCmd.getDefaultInstance());
*/
//        _msgClazzAndMsgCodeMap.put(GameMsgProtocol.UserEntryResult.class,GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE);
//        _msgClazzAndMsgCodeMap.put(GameMsgProtocol.WhoElseIsHereResult.class,GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE);
//        _msgClazzAndMsgCodeMap.put(GameMsgProtocol.UserMoveToResult.class,GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE);
//        _msgClazzAndMsgCodeMap.put(GameMsgProtocol.UserQuitResult.class,GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE);
    }

    /**
     * 根据消息编号获取构建者
     *
     * @param msgCode
     * @return
     */
    static  public Message.Builder getBuilderByMsgCode(int msgCode){
        if (msgCode < 0) {
            return null;
        }

        GeneratedMessageV3 msg = _msgCodeAndMsgBodyMap.get(msgCode);
        if (null == msg) {
            return null;
        }
        return msg.newBuilderForType();
    }
    /**
     * 根据消息类获取消息编号
     *
     * @param msgClazz
     * @return
     */
    static public int getMsgCodeByMsgClazz(Class<?> msgClazz){
        if (null==msgClazz)return -1;
        Integer msgCode = _msgClazzAndMsgCodeMap.get(msgClazz);
        if (null!=msgCode){
            return msgCode.intValue();
        }else {
            return -1;
        }
    }
}
