package cn.bluesadi.zombiecrisis.game;

import org.bukkit.Location;

public class Region {

    private Location pos1;
    private Location pos2;
    private boolean ignoreHeight;

    public Region(Location pos1, Location pos2){
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public void setIgnoreHeight(boolean ignoreHeight) {
        this.ignoreHeight = ignoreHeight;
    }

    public boolean inRegion(Location loc){
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        return (x - pos1.getX()) * (x - pos2.getX()) < 0 && (z - pos1.getZ()) * (z - pos2.getZ()) < 0
                && (ignoreHeight || (y - pos1.getY()) * (y - pos2.getY()) < 0);
    }

}
