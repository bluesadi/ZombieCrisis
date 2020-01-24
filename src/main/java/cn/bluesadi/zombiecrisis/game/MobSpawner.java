package cn.bluesadi.zombiecrisis.game;

import cn.bluesadi.commonlib.logging.Logger;
import cn.bluesadi.zombiecrisis.ZombieCrisis;
import cn.bluesadi.zombiecrisis.api.MobModel;
import cn.bluesadi.zombiecrisis.config.MobData;
import com.google.common.collect.Maps;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Map;
import java.util.Random;

public class MobSpawner {

    private static final Random RANDOM = new Random();
    private static Map<String,MobModel> registeredMobs = Maps.newHashMap();
    private GameWorld world;
    private int maxMobsPerChunk;
    private int period;

    public static void registerMob(MobModel model){
        registeredMobs.put(model.getID(),model);
    }

    public MobSpawner(GameWorld world){
        this.world = world;
    }

    public void enable(){
        new BukkitRunnable(){

            @Override
            public void run() {
                spawn();
            }

        }.runTaskTimer(ZombieCrisis.getInstance(),period * 20L,period * 20L);
    }

   public void setMaxMobsPerChunk(int maxMobsPerChunk) {
        if(maxMobsPerChunk >= 0) {
            this.maxMobsPerChunk = maxMobsPerChunk;
        }
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    private boolean isLocationValid(Location loc){
        Location under = loc.clone().add(0,- 1,0);
        Location above = loc.clone().add(0, 1,0);
        return !under.getBlock().isEmpty() && above.getBlock().isEmpty() && loc.getBlock().isEmpty();
    }

    private Location getValidLocation(Location loc){
        for(int i = 0;i<=30;i++){
            Location newLoc1 = loc.clone().add(0,i,0);
            Location newLoc2 = loc.clone().add(0,-i,0);
            if(isLocationValid(newLoc1)){
                return newLoc1;
            }
            if(isLocationValid(newLoc2)){
                return newLoc2;
            }
        }
        Logger.debug(ZombieCrisis.ID,String.format("无法生成怪物，因为无法找到合适的坐标，原坐标(%s,%s,%s)",loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()));
        return null;
    }

    private Location randomLocation(Chunk chunk){
        Location loc = getAverageLocation(chunk);
        int r = RANDOM.nextInt(100);
        double sin = RANDOM.nextDouble() * 2 - 1;
        double cosA = Math.sqrt(1 - sin * sin);
        double cos = RANDOM.nextDouble() >= 0.5 ? cosA : -cosA;
        double scale = RANDOM.nextDouble() * 0.7 + 0.3;
        loc.add(r * sin * scale,0,r * cos * scale);
        return getValidLocation(loc);
    }

    private boolean hasPlayer(Chunk chunk){
        boolean hasPlayer = false;
        for(Entity entity : chunk.getEntities()){
            if(entity instanceof Player && ((Player)entity).hasPlayedBefore()){
                hasPlayer = true;
                break;
            }
        }
        return hasPlayer;
    }

    private int getMobAmount(Chunk chunk){
        int amount = 0;
        for(Entity entity : chunk.getEntities()){
            if(!entity.isDead() && entity instanceof LivingEntity){
                amount ++;
            }
        }
        return amount;
    }

    private Location getAverageLocation(Chunk chunk){
        int playerNumber = 0;
        double x = 0,y = 0,z = 0;
        for(Entity entity : chunk.getEntities()){
            if(entity instanceof Player){
                Player player = (Player)entity;
                playerNumber ++;
                x += player.getLocation().getX();
                y += player.getLocation().getY();
                z += player.getLocation().getZ();
            }
        }
        return new Location(chunk.getWorld(),x / playerNumber,y / playerNumber,z / playerNumber);
    }

    private void spawn(){
        for (Chunk chunk : world.getWorld().getLoadedChunks()) {
            if (hasPlayer(chunk)) {
                int mobAmount = getMobAmount(chunk);
                for (int i = 0; i < maxMobsPerChunk - mobAmount; i++) {
                    MobData data = MobData.random();
                    if(data != null) {
                        Logger.debug(ZombieCrisis.ID,data.getId());
                        MobModel model = registeredMobs.get(data.getId());
                        Location loc = randomLocation(chunk);
                        if(loc != null) {
                            model.spawn(loc);
                            loc.getWorld().strikeLightningEffect(loc);
                            Logger.debug(ZombieCrisis.ID,String.format("产生了一个怪物(%s,%s,%s)", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                        }
                    }else{
                        Logger.debug(ZombieCrisis.ID,"无法随机获取怪物!");
                    }
                }
            }
        }
    }

}
