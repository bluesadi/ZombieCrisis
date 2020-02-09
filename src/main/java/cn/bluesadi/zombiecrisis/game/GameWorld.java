package cn.bluesadi.zombiecrisis.game;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;

public class GameWorld {

    private World world;
    private Map<String,Zone> safeZones = Maps.newHashMap();

    public GameWorld(String world){
        this.world = Bukkit.getWorld(world);
    }

    public World getWorld() {
        return world;
    }

    public String getName(){
        return world.getName();
    }

    public boolean createSafeZone(Zone zone){
        if(safeZones.containsKey(zone.getId())) return false;
        safeZones.put(zone.getId(),zone);
        return true;
    }

    public void addSafeZone(Zone zone){
        safeZones.put(zone.getId(),zone);
    }

    public boolean removeSafeZone(String name){
        if(safeZones.containsKey(name)){
            safeZones.remove(name);
            return true;
        }
        return false;
    }

    public Collection<Zone> getSafeZones(){
        return safeZones.values();
    }

    public boolean inSafeZone(Location loc){
        return getSafeZone(loc) != null;
    }

    public Zone getSafeZone(Location loc){
        for(Zone zone : safeZones.values()){
            if(zone.inZone(loc)) return zone;
        }
        return null;
    }

    public Zone getSafeZone(String name){
        for(Zone zone : safeZones.values()){
            if(zone.getName().equals(name)) return zone;
        }
        return null;
    }

    public void broadcast(String msg,Object... arg){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getWorld().equals(world))player.sendMessage(String.format(msg,arg));
        }
    }
}
