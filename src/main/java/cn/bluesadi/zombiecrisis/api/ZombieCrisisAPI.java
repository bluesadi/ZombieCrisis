package cn.bluesadi.zombiecrisis.api;

import cn.bluesadi.zombiecrisis.world.MobSpawner;

public class ZombieCrisisAPI {

    /**
     * 向插件中注册一个怪物类型
     * 这个怪物在游戏中生成
     * @param model 怪物的模板
     * */
    public static void registerMob(MobModel model){
        MobSpawner.registerMob(model);
    }

}
