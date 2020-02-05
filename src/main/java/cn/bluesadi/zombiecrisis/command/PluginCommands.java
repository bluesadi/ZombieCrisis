package cn.bluesadi.zombiecrisis.command;

import cn.bluesadi.commonlib.i18n.Language;
import cn.bluesadi.commonlib.plugin.CommandHandler;
import cn.bluesadi.zombiecrisis.ZombieCrisis;
import cn.bluesadi.zombiecrisis.config.MobData;
import cn.bluesadi.zombiecrisis.game.Game;
import cn.bluesadi.zombiecrisis.game.Zone;
import cn.bluesadi.zombiecrisis.game.ZoneSelector;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommands extends CommandHandler {

    private static final String PREFIX = "zombiecrisis.";
    private static final String RELOAD_PERMISSION = PREFIX + "reload";
    private static final String CREATE_ZONE_PERMISSION = PREFIX + "zone.create";
    private static final String REMOVE_ZONE_PERMISSION = PREFIX + "zone.remove";
    private static final String IGNORE_H_ZONE_PERMISSION = PREFIX + "zone.infh";

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
            }else if(args[0].equalsIgnoreCase("zone") && validate(null,2)){
                if(args[1].equalsIgnoreCase("create") && validate(CREATE_ZONE_PERMISSION,4)){
                    String game = args[2],zone = args[3];
                    if(Game.existsGame(game)){
                        Game gameObj = Game.getGame(game);
                        Zone zoneObj = ZoneSelector.getSelector(player).newZone(zone);
                        if(zoneObj != null) {
                            if (gameObj.getGameWorld().createSafeZone(zoneObj)){
                                sender.sendMessage(lang.getMessage("create_safe_zone_success",zone));
                            }else{
                                sendMessage(lang.getMessage("zone_exists",zone));
                            }
                        }else{
                            sendMessage(lang.getMessage("create_zone_fail"));
                        }
                    }else{
                        sendMessage(lang.getMessage("game_not_exists",game));
                    }
                }else if(args[1].equalsIgnoreCase("remove") && validate(REMOVE_ZONE_PERMISSION,4)){
                    String game = args[2],zone = args[3];
                    if(Game.existsGame(game)){
                        Game gameObj = Game.getGame(game);
                        if (gameObj.getGameWorld().removeSafeZone(zone)){
                            sender.sendMessage(lang.getMessage("remove_safe_zone_success",zone));
                        }else{
                            sendMessage(lang.getMessage("zone_not_exists",zone));
                        }
                    }else{
                        sendMessage(lang.getMessage("game_not_exists",game));
                    }
                }else if(args[1].equalsIgnoreCase("infh") && validate(IGNORE_H_ZONE_PERMISSION,4)){
                    String game = args[2],zone = args[3];
                    if(Game.existsGame(game)){
                        Game gameObj = Game.getGame(game);
                        Zone zoneObj = gameObj.getGameWorld().getSafeZone(zone);
                        if (zoneObj != null){
                            zoneObj.setInfHeight(!zoneObj.isInfHeight());
                            sender.sendMessage(zoneObj.isInfHeight() ? lang.getMessage("set_inf_height_success",zone)
                                    : lang.getMessage("set_fin_height_success",zone));
                        }else{
                            sendMessage(lang.getMessage("zone_not_exists",zone));
                        }
                    }else{
                        sendMessage(lang.getMessage("game_not_exists",game));
                    }
                }
            }
        }
    }

}
