package org.houkaigame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;

import org.houkaigame.herostory.constant.Constant;
import org.houkaigame.herostory.msg.GameMsgProtocol;
import org.houkaigame.herostory.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class CmdHandlerFactory {
    private static final Logger LOGGER= LoggerFactory.getLogger(CmdHandlerFactory.class);
    /**
     * 处理器字典
     */
    static  private Map<Class<?>,ICmdHandler<? extends GeneratedMessageV3>> _handlerMap =new HashMap<>();

    /**
     * 私有化类默认构造器
     */
    private  CmdHandlerFactory(){

    }
    static public void init(){
        try{
            for(Class<?> clazz: ClassUtils.getAllClassByInterface(ICmdHandler.class) ){
                _handlerMap.put(clazz.getAnnotation(CmdHandler.class).name(),(ICmdHandler<? extends GeneratedMessageV3>)clazz.newInstance());
//                _handlerMap.put(clazz.getAnnotation(CmdHandler.class),(ICmdHandler<? extends GeneratedMessageV3>)clazz.newInstance());
                LOGGER.info("{} <=init=> {}",clazz.getAnnotation(CmdHandler.class).name(),clazz.getSimpleName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       /* Class<?>[] innerClazzArray=GameMsgProtocol.class.getDeclaredClasses();
        for(Class<?> innerClazz: innerClazzArray ){
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClazz)){
                continue;
            }
            String clazzName=innerClazz.getSimpleName();
            String className=clazzName;
            clazzName=clazzName.toLowerCase();
            for(GameMsgProtocol.MsgCode msgCode:GameMsgProtocol.MsgCode.values()){
                String strMsgCode=msgCode.name();
                strMsgCode=strMsgCode.replaceAll("_", "");
                strMsgCode=strMsgCode.toLowerCase();

                if (!strMsgCode.startsWith(clazzName)){
                    continue;
                }
                try {
                    String[] handlerArr = Constant.HANDLER_NAMES.split(",");
                    for(String handler:handlerArr){
                        if (handler.equals(className)){
//                    Object returnObj = innerClazz.getDeclaredMethod("getDefaultInstance").invoke(innerClazz);
                        Class clazz = Class.forName("org.houkaihame.herostory.cmdHandler."+className+"Handler");
                        LOGGER.info("{}<==>{}",innerClazz.getName(),clazz.getName());
                        Object obj=clazz.newInstance();
                        _handlerMap.put(innerClazz, (ICmdHandler<? extends GeneratedMessageV3>) obj);
                        }

                    }
                }catch (Exception e){
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }*/
       /* _handlerMap.put(GameMsgProtocol.UserEntryCmd.class, new UserEntryCmdHandler());
        _handlerMap.put(GameMsgProtocol.WhoElseIsHereCmd.class, new WhoElseIsHereCmdHandler());
        _handlerMap.put(GameMsgProtocol.UserMoveToCmd.class, new UserMoveToCmdHandler());*/
    }
    static public ICmdHandler<? extends  GeneratedMessageV3> create (Class<?> msgClazz){
        /*if (msg instanceof GameMsgProtocol.UserEntryCmd){
            return new UserEntryCmdHandler();
        }else if(msg instanceof GameMsgProtocol.WhoElseIsHereCmd){
            return new WhoElseIsHereCmdHandler();
        }else if(msg instanceof GameMsgProtocol.UserMoveToCmd){
            return new UserMoveToCmdHandler();
        }else {
            return null;
        }*/
        if (null == msgClazz) {
            return null;
        }

        return _handlerMap.get(msgClazz);
    }
}
