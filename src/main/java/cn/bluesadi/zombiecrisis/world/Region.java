package cn.bluesadi.zombiecrisis.world;

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
        double minX = Math.min(pos1.getX(),pos2.getX());
        double minY = Math.min(pos1.getY(),pos2.getY());
        double minZ = Math.min(pos1.getZ(),pos2.getZ());
        double maxX = Math.max(pos1.getX(),pos2.getX());
        double maxY = Math.max(pos1.getY(),pos2.getY());
        double maxZ = Math.max(pos1.getZ(),pos2.getZ());
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ && (ignoreHeight || (y >= minY && y <= maxY));
    }
}
