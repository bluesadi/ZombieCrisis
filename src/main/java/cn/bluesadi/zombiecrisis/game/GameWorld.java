package cn.bluesadi.zombiecrisis.game;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class GameWorld {

    private World world;

    public GameWorld(String world){
        this.world = Bukkit.getWorld(world);
    }

    public World getWorld() {
        return world;
    }

    public String getName(){
        return world.getName();
    }
}
