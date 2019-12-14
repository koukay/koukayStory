package org.houkaigame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;

import org.houkaigame.herostory.constant.Constant;
import org.houkaigame.herostory.msg.GameMsgProtocol;
import org.houkaigame.herostory.util.ClassUtils;
import org.houkaigame.herostory.util.PackageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    /**
     *初始化
     */
    static public void init(){
        LOGGER.info("==== 完成 Cmd 和 Handler 的关联 ====");
        //获取包名称
        final  String packageName = CmdHandlerFactory.class.getPackage().getName();
        //获取所有ICmdhandler的子类
        Set<Class<?>> classSet = PackageUtil.listSubClazz(
                packageName,
                true,
                ICmdHandler.class);
        for (Class<?> aClass : classSet) {
            //如果是抽象类
            if ((aClass.getModifiers()& Modifier.ABSTRACT)!=0)continue;
            //获取方法组
            Method[] declaredMethods = aClass.getDeclaredMethods();
            // 消息类型
            Class<?> msgType = null;
            for (Method declaredMethod : declaredMethods) {
                //如果不是handle方法
                if (!declaredMethod.getName().equals("handle"))continue;
                //获取函数参数类型
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                //如果不是两个参数,或者第二个参数类型为GeneratedMessageV3,
                // 或者第二个人参数不是GeneratedMessageV3的子类则跳出本次循环,执行下次循环
                if (parameterTypes.length<2 ||
                        parameterTypes[1]==GeneratedMessageV3.class||
                        !GeneratedMessageV3.class.isAssignableFrom(parameterTypes[1]))continue;
                msgType=parameterTypes[1];
                break;
            }
            if (null==msgType)continue;

            try {
              ICmdHandler<?> cmdHandler= (ICmdHandler<?>) aClass.newInstance();
                LOGGER.info(
                        "关联 {} <==> {}",
                        msgType.getName(),
                        aClass.getName()
                );
                _handlerMap.put(msgType,cmdHandler);
            } catch (Exception e) {
               LOGGER.error(e.getMessage(), e);
            }
        }
    }
    static public ICmdHandler<? extends  GeneratedMessageV3> create (Class<?> msgClazz){
        if (null == msgClazz) {
            return null;
        }

        return _handlerMap.get(msgClazz);
    }
}
