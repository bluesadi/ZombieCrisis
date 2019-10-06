package cn.bluesadi.zombiecrisis.api;

import cn.bluesadi.zombiecrisis.world.GameRegion;
import org.bukkit.entity.LivingEntity;

/**
 * 你需要实现MobModel接口中的所有方法
 * 然后将该MobModel构建出的对象注册到插件中
 * 以向MobSpawner提供一种可在游戏中生成的怪物类型
 * */
public interface MobModel {

    /**
     * 获取怪物的ID
     * 该名称一定要是唯一的
     * */
    String getID();

    /**
     * 在游戏区域内随机生成一个怪物
     * */
    LivingEntity spawn(GameRegion region);

}
