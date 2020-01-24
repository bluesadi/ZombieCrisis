package cn.bluesadi.zombiecrisis;

import static cn.bluesadi.commonlib.logging.Logger.*;

import cn.bluesadi.commonlib.CommonLib;
import cn.bluesadi.commonlib.i18n.Languages;
import cn.bluesadi.commonlib.plugin.BukkitPlugin;
import cn.bluesadi.zombiecrisis.config.MobData;
import cn.bluesadi.zombiecrisis.game.Game;

public final class ZombieCrisis extends BukkitPlugin {

    public static final String ID = "ZOMBIE_CRISIS";
    private static ZombieCrisis instance;

    @Override
    public void onEnable() {
        instance = this;
        info("§8>>>>>>>>§6§l【§a§l欢迎使用 §c§lZombieCrisis§6§l】§8<<<<<<<<");
        info("> 正在初始化插件...");
        setDebugMode(true);
        CommonLib.init(this);
        info("> 正在导出资源文件");
        saveResource("Games/ExampleGame.yml");
        saveResource("config.yml");
        saveResource("Language/zh_cn.yml");
        info("> 正在加载语言文件");
        getI18nManager().registerLanguage(Languages.SIMPLIFIED_CHINESE,"zh_cn.yml");
        info("> 正在加载怪物配置...");
        info("- 导入了 §e" + MobData.loadMobs(getPluginFolder().resolve("Mobs")) + " §a个有效的怪物");
        info("> 正在加载游戏配置...");
        info("- 导入了 §e" + Game.loadGames(getPluginFolder().resolve("Games")) + "§a个有效的游戏");
        info("§8>>>>>>>>§6§l【§c§lZombieCrisis §a§l加载完毕!§6§l】§8<<<<<<<<");
    }

    @Override
    public void onDisable() {
        info("§8>>>>>>>>§6§l【§a§l感谢使用 §c§lZombieCrisis§6§a】§8<<<<<<<<");
    }

    public static ZombieCrisis getInstance() {
        return instance;
    }

    @Override
    public String getPluginId() {
        return ID;
    }

}
