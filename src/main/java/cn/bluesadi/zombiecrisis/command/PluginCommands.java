package cn.bluesadi.zombiecrisis.command;

import cn.bluesadi.commonlib.i18n.Language;
import cn.bluesadi.commonlib.plugin.CommandHandler;
import cn.bluesadi.zombiecrisis.ZombieCrisis;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommands extends CommandHandler {

    private static final String PREFIX = "zombiecrisis.";
    private static final String RELOAD_PERMISSION = PREFIX + "reload";

    public PluginCommands(){
        super("zombie");
    }

    private boolean validate(Language lang,String permission,int size){
        return validate(permission,size,lang.getMessage("permission_denied"),
                lang.getMessage("arguments_wrong"));
    }

    @Override
    protected void handleCommand(CommandSender sender, Player player, String[] args, int size) {
        if(size >= 1){
            Language lang = ZombieCrisis.getInstance().getI18nManager().getPluginLanguage();
            if(args[0].equalsIgnoreCase("reload") && validate(lang,RELOAD_PERMISSION,1)){
                sendMessage(lang.getMessage("reload_success"));
            }
        }
    }

}
