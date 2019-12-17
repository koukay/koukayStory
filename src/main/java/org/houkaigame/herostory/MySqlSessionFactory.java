package org.houkaigame.herostory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;


/**
 * MySql 会话工厂
 */
public class MySqlSessionFactory {
    /**
     * Mysql会话工厂
     */
    static private SqlSessionFactory sqlSessionFactory;
    //私有化类默认构造器
    private MySqlSessionFactory(){

    }

    /**
     * 初始化
     */
    static public void init(){
        try {
            sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(
                    Resources.getResourceAsReader("MyBatisConfig.xml"));
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }

    /**
     * 开启mysql会话
     * @return mysql会话
     */
    static public SqlSession openSession(){
        if (null==sqlSessionFactory) throw new RuntimeException("sessionFactory 尚未初始化");
        return sqlSessionFactory.openSession(true);
    }
}
