package org.houkaigame.herostory;

import org.houkaigame.herostory.mq.MQConsumer;
import org.houkaigame.herostory.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排行榜应用程序
 */
public class RankApp {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RankApp.class);
    /**
     * 应用主函数
     *
     * @param args 命令行参数数组
     */
    public static void main(String[] args) {
        RedisUtil.init();
        MQConsumer.init();

        LOGGER.info("排行榜应用程序启动成功");
    }
}
