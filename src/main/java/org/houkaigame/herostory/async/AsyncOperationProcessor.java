package org.houkaigame.herostory.async;

import org.houkaigame.herostory.MainThreadProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步操作处理器
 */
public class AsyncOperationProcessor {
    /**
     * 日志对象
     */
    private static final Logger LOGGER=LoggerFactory.getLogger(AsyncOperationProcessor.class);
    /**
     * 线程数组
     */
    private static ExecutorService[] executorService=new ExecutorService[8];
    /**
     * 单例对象
     */
    private static final AsyncOperationProcessor _instance=new AsyncOperationProcessor();

    /**
     * 私有化类默认构造器
     */
    private AsyncOperationProcessor(){

        for (int i = 0; i < executorService.length; i++) {
            //线程名称
            final String threadName="AsyncOperationProcessor_"+i;
            //创建单线程服务
            executorService[i]= Executors.newSingleThreadExecutor((newRunnable)->{
                Thread thread=new Thread(newRunnable);
                thread.setName(threadName);
                return thread;
            });
        }
    }
    /**
     * 获取单例对象
     *
     * @return 异步操作处理器
     */
    static public AsyncOperationProcessor getInstance(){
        return _instance;
    }

    /**
     *处理异步操纵
     * @param asyncOp 异步操纵
     */
    public void process(IAsyncOperation asyncOp){
        if (null==asyncOp)return;

        //根据绑定id获取线程索引
        int bindId = Math.abs(asyncOp.getBindId());
        int esIndex = bindId % executorService.length;
        executorService[esIndex].submit(()->{
            try {
                //执行异步操作
                asyncOp.doAsync();

                //回到主消息处理器执行完成逻辑
                MainThreadProcessor.getInstance().process(asyncOp::doFinish);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
