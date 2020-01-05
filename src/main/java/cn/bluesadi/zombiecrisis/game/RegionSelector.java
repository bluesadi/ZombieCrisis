package cn.bluesadi.zombiecrisis.game;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public class RegionSelector {

    private static Map<String,RegionSelector> selectorMap = Maps.newHashMap();
    private Location pos1;
    private Location pos2;

    private RegionSelector(){

    }

    public static RegionSelector getSelector(Player player){
        if(selectorMap.containsKey(player.getName())){
            return selectorMap.get(player.getName());
        }else{
            RegionSelector selector = new RegionSelector();
            selectorMap.put(player.getName(),selector);
            return selector;
        }
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

}
