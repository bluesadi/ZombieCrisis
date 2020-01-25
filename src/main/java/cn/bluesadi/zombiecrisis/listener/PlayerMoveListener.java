package cn.bluesadi.zombiecrisis.listener;

import cn.bluesadi.commonlib.i18n.Language;
import cn.bluesadi.zombiecrisis.ZombieCrisis;
import cn.bluesadi.zombiecrisis.game.Game;
import cn.bluesadi.zombiecrisis.game.Zone;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Language lang = ZombieCrisis.getInstance().getPluginLanguage();
        Player player = event.getPlayer();
        Location to = event.getTo(),from = event.getFrom();
        Zone fromZone = Game.getSafeZone(from),toZone = Game.getSafeZone(to);
        if(fromZone != null && toZone == null){
            player.sendActionBar(lang.getMessage("leave_safezone"));
        }else if(fromZone == null && toZone != null){
            player.sendActionBar(lang.getMessage("enter_safezone"));
        }
    }
}
