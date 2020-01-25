package cn.bluesadi.zombiecrisis.listener;

import cn.bluesadi.zombiecrisis.ZombieCrisis;
import cn.bluesadi.zombiecrisis.game.ZoneSelector;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ZoneSelectListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType().equals(Material.STICK)){
            if(player.hasPermission("zombiezrisis.zone.select")){
                Block block = event.getClickedBlock();
                if(block != null) {
                    if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                        ZoneSelector.getSelector(player).setPos1(block.getLocation());
                        ZombieCrisis.getInstance().getPluginLanguage().getMessage("pos1_selected");
                    }else if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                        ZoneSelector.getSelector(player).setPos2(block.getLocation());
                        ZombieCrisis.getInstance().getPluginLanguage().getMessage("pos2_selected");
                    }
                }
            }
        }
    }
}
