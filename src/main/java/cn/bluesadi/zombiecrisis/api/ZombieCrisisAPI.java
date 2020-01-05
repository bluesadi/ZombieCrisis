package cn.bluesadi.zombiecrisis.api;

import cn.bluesadi.zombiecrisis.config.MobData;
import cn.bluesadi.zombiecrisis.game.MobSpawner;

public class ZombieCrisisAPI {

    /**
     * 向插件中注册一个怪物类型
     * 这个怪物在游戏中生成
     * @param model 怪物的模板
     * */
    public static void registerMob(MobModel model){
        MobSpawner.registerMob(model);
    }

    /**
     * 获取某个怪物的配置信息
     * @param id 怪物的id
     * */
    public static MobData getMobData(String id){
        return MobData.getMobData(id);
    }

}
