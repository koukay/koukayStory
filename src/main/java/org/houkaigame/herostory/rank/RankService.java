package org.houkaigame.herostory.rank;

import com.alibaba.fastjson.JSONObject;
import org.houkaigame.herostory.async.AsyncOperationProcessor;
import org.houkaigame.herostory.async.IAsyncOperation;
import org.houkaigame.herostory.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * 排行榜服务
 */
public class RankService {
    /**
     * 日志对象
     */
    private static  final Logger LOGGER = LoggerFactory.getLogger(RankService.class);
    /**
     * 单例对象
     */
    private static final  RankService _instance=new RankService();

    /**
     * 私有化默认构造器
     */
    private RankService(){

    }

    /**
     * 获取单例对象
     * @return  排行榜服务
     */
    static public RankService getInstance(){
        return _instance;
    }

    /**
     * 获取排行榜
     *
     * @param callback 回调函数
     */
    public void getRank(Function<List<RankItem>, Void> callback) {
        if (null == callback) {
            return;
        }

        IAsyncOperation asyncOp =new AsyncGetRank(){
            @Override
            public void doFinish(){
                callback.apply(this.getRankItemList());
            }
        };
        AsyncOperationProcessor.getInstance().process(asyncOp);
    }
    /**
     * 异步方式获取排行榜
     */
    private class AsyncGetRank implements IAsyncOperation{
        /**
         * 排名条目列表
         */
        private List<RankItem> _rankItemList = null;

        /**
         *  获取排名条目列表
         *
         * @return 排名条目列表
         */
        public  List<RankItem> getRankItemList(){
            return _rankItemList;
        }
        @Override
        public void doAsync() {

            try(Jedis redis= RedisUtil.getJedis()){
                //获取字符串集合
                Set<Tuple> valSet = redis.zrevrangeWithScores("Rank", 0, 9);

                _rankItemList=new ArrayList<>();
                int rankId = 0;
                for (Tuple t : valSet) {
                    //获取用户id
                    int userId = Integer.parseInt(t.getElement());

                    //获取用户基本信息
                    String jsonStr = redis.hget("User_" + userId, "BasicInfo");
                    if (null ==jsonStr)continue;
                    //创建排名条目
                    RankItem rankItem = new RankItem();
                    rankItem.userId=++rankId;
                    rankItem.userId=userId;
                    rankItem.win= (int) t.getScore();

                    JSONObject jsonObject = JSONObject.parseObject(jsonStr);

                    rankItem.userName=jsonObject.getString("userName");
                    rankItem.heroAvatar=jsonObject.getString("heroAvatar");
                    _rankItemList.add(rankItem);
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage(),e);
            }
        }
    }

    /**
     * 刷新排行榜
     * @param winnerId  赢家id
     * @param loserId  输家id
     */
    public void refreshRank(int winnerId,int loserId){

        try (Jedis redis = RedisUtil.getJedis()){
            //增加用户的胜利和失败次数
            redis.hincrBy("User_"+winnerId,"Win",1);
            redis.hincrBy("User_"+loserId,"Lose",1);

            //看看赢家总共赢了多少次
            String winStr = redis.hget("User_" + winnerId, "Win");
            int winInt = Integer.parseInt(winStr);
            //修改排名数据
            redis.zadd("Rank",winInt,String.valueOf(winnerId));
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
    }
}
