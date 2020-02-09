package cn.bluesadi.zombiecrisis;

import static cn.bluesadi.commonlib.logging.Logger.*;
import cn.bluesadi.commonlib.CommonLib;
import cn.bluesadi.commonlib.i18n.Languages;
import cn.bluesadi.commonlib.io.FileUtil;
import cn.bluesadi.commonlib.plugin.BukkitPlugin;
import cn.bluesadi.zombiecrisis.command.PluginCommands;
import cn.bluesadi.zombiecrisis.config.MobData;
import cn.bluesadi.zombiecrisis.game.Game;
import cn.bluesadi.zombiecrisis.listener.PlayerMoveListener;
import cn.bluesadi.zombiecrisis.listener.ZoneSelectListener;

public final class ZombieCrisis extends BukkitPlugin {

    public static final String ID = "ZOMBIE_CRISIS";
    private static ZombieCrisis instance;

    public void saveDefaultResource() {
        FileUtil.mkdirs(getPluginFolder().resolve("Games"));
        if(FileUtil.isEmpty(getPluginFolder().resolve("Games"))) {
            saveResource("Games/ExampleGame.yml");
        }
        saveResource("config.yml");
        FileUtil.mkdirs(getPluginFolder().resolve("Mobs"));
        saveResource("Language/zh_cn.yml");
    }

    @Override
    public void onEnable() {
        instance = this;
        info("§8>>>>>>>>§6§l【§a§l欢迎使用 §c§lZombieCrisis§6§l】§8<<<<<<<<");
        info("> 正在初始化插件...");
        setDebugMode(true);
        CommonLib.init(this);
        info("> 正在导出资源文件");
        saveDefaultResource();
        info("> 正在加载语言文件");
        getI18nManager().registerLanguage(Languages.SIMPLIFIED_CHINESE,"zh_cn.yml");
        info("> 正在注册插件指令");
        registerCommand(new PluginCommands());
        info("> 正在注册插件监听器");
        registerListener(new PlayerMoveListener());
        registerListener(new ZoneSelectListener());
        info("> 正在加载怪物配置...");
        info("- 导入了 §e" + MobData.loadMobs(getPluginFolder().resolve("Mobs")) + " §a个有效的怪物");
        info("> 正在加载游戏配置...");
        info("- 导入了 §e" + Game.loadGames(getPluginFolder().resolve("Games")) + "§a个有效的游戏");
        info("§8>>>>>>>>§6§l【§c§lZombieCrisis §a§l加载完毕!§6§l】§8<<<<<<<<");
    }

    @Override
    public void onDisable() {
        info("§8>>>>>>>>§6§l【§a§l感谢使用 §c§lZombieCrisis§6§a】§8<<<<<<<<");
        info("> 正在保存插件数据...");
        Game.saveGames();
        info("§8>>>>>>>>§6§l【§c§lZombieCrisis §a§l卸载完毕!§6§l】§8<<<<<<<<");
    }

    public static ZombieCrisis getInstance() {
        return instance;
    }

    @Override
    public String getPluginId() {
        return ID;
    }

}
