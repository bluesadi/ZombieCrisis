package cn.bluesadi.zombiecrisis.command;

import cn.bluesadi.commonlib.i18n.Language;
import cn.bluesadi.commonlib.plugin.CommandHandler;
import cn.bluesadi.zombiecrisis.ZombieCrisis;
import cn.bluesadi.zombiecrisis.config.MobData;
import cn.bluesadi.zombiecrisis.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommands extends CommandHandler {

    private static final String PREFIX = "zombiecrisis.";
    private static final String RELOAD_PERMISSION = PREFIX + "reload";

    public PluginCommands(){
        super("zombie",ZombieCrisis.getInstance());
    }

    @Override
    protected void handleCommand(CommandSender sender, Player player, String[] args, int size) {
        if(size >= 1){
            Language lang = ZombieCrisis.getInstance().getI18nManager().getPluginLanguage();
            if(args[0].equalsIgnoreCase("reload") && validate(RELOAD_PERMISSION,1)){
                ZombieCrisis.getInstance().getI18nManager().reload();
                MobData.loadMobs(ZombieCrisis.getInstance().getPluginFolder().resolve("Mobs"));
                Game.loadGames(ZombieCrisis.getInstance().getPluginFolder().resolve("Games"));
                sendMessage(lang.getMessage("reload_success"));
            }
        }
    }

}
