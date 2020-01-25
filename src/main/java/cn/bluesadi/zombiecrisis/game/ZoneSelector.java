package cn.bluesadi.zombiecrisis.game;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public class ZoneSelector {

    private static Map<String, ZoneSelector> selectorMap = Maps.newHashMap();
    private Location pos1;
    private Location pos2;

    private ZoneSelector(){

    }

    public static ZoneSelector getSelector(Player player){
        if(selectorMap.containsKey(player.getName())){
            return selectorMap.get(player.getName());
        }else{
            ZoneSelector selector = new ZoneSelector();
            selectorMap.put(player.getName(),selector);
            return selector;
        }
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public Zone newZone(String id){
        if(pos1 != null && pos2 != null && pos1.getWorld().getName().equals(pos2.getWorld().getName())){
            return new Zone(id,pos1,pos2);
        }
        return null;
    }

}
