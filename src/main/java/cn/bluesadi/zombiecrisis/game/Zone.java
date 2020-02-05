package cn.bluesadi.zombiecrisis.game;

import com.alibaba.fastjson.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Zone {

    private String id;
    private String name;
    private Location pos1;
    private Location pos2;
    private boolean infHeight;

    public Zone(String id,Location pos1, Location pos2){
        this.id = id;
        this.name = id;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public Zone(JSONObject jsonObject){
        JSONObject pos1Sub = jsonObject.getJSONObject("pos1"),pos2Sub = jsonObject.getJSONObject("pos2");
        this.pos1 = new Location(Bukkit.getWorld(pos1Sub.getString("world")),pos1Sub.getInteger("x"),
                pos1Sub.getInteger("y"),pos1Sub.getInteger("z"));
        this.pos2 = new Location(Bukkit.getWorld(pos2Sub.getString("world")),pos2Sub.getInteger("x"),
                pos2Sub.getInteger("y"),pos2Sub.getInteger("z"));
        this.infHeight = jsonObject.getBoolean("infHeight");
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
    }

    public String toJSON(){
        JSONObject pos1Sub = new JSONObject(),pos2Sub = new JSONObject(),jsonObject = new JSONObject();
        pos1Sub.put("world",pos1.getWorld().getName());
        pos1Sub.put("x",pos1.getBlockX());
        pos1Sub.put("y",pos1.getBlockY());
        pos1Sub.put("z",pos1.getBlockZ());
        pos2Sub.put("world",pos2.getWorld().getName());
        pos2Sub.put("x",pos2.getBlockX());
        pos2Sub.put("y",pos2.getBlockY());
        pos2Sub.put("z",pos2.getBlockZ());
        jsonObject.put("pos1",pos1Sub);
        jsonObject.put("pos2",pos2Sub);
        jsonObject.put("infHeight",infHeight);
        jsonObject.put("id",id);
        jsonObject.put("name",name);
        return jsonObject.toString();
    }

    public void setInfHeight(boolean ignoreHeight) {
        this.infHeight = ignoreHeight;
    }

    public boolean isInfHeight() {
        return infHeight;
    }

    public boolean inZone(Location loc){
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        return (x - pos1.getBlockX()) * (x - pos2.getBlockX()) <= 0 && (z - pos1.getBlockZ()) * (z - pos2.getBlockZ()) <= 0
                && (infHeight || (y - pos1.getBlockY()) * (y - pos2.getBlockY()) <= 0);
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
