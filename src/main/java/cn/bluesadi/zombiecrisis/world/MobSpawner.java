package cn.bluesadi.zombiecrisis.world;

import cn.bluesadi.zombiecrisis.api.MobModel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MobSpawner {

    private static final Random RANDOM = new Random();
    private static List<MobModel> registeredMobs = Lists.newArrayList();
    private static Map<GameRegion,MobSpawner> mobSpawnerMap = Maps.newHashMap();
    private GameRegion region;

    public static MobSpawner getMobSpawner(GameRegion region){
        return mobSpawnerMap.getOrDefault(region,null);
    }

    public static MobSpawner newMobSpawner(GameRegion region){
        MobSpawner spawner = new MobSpawner(region);
        mobSpawnerMap.put(region,spawner);
        return spawner;
    }

    public static void registerMob(MobModel model){
        registeredMobs.add(model);
    }

    private MobSpawner(GameRegion region){
        this.region = region;
    }

    private MobModel randomMob(){
        int totalWeight = 0;
        for(MobModel model : registeredMobs){
            //totalWeight += model.getWeight();
        }
        int randomWeight = (int)(RANDOM.nextDouble() * totalWeight);
        int tempWeight = 0;
        for(MobModel model : registeredMobs){
            //tempWeight += model.getWeight();
            if(tempWeight >=  randomWeight){
                return model;
            }
        }
        return null;
    }

    private boolean isLocationValid(Location loc){
        Location under = loc.clone().add(0,- 1,0);
        Location above = loc.clone().add(0, 1,0);
        return !under.getBlock().isEmpty() && above.getBlock().isEmpty() && loc.getBlock().isEmpty();
    }

    private Location getValidLocation(Location loc){
        loc.setY(getAverageHeight(loc.getChunk()));
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
        //Logger.info("§4警告>>无法生成怪物，因为无法找到合适的坐标");
        return null;
    }

    private Location randomLocation(Chunk chunk){
        /*Location loc = new Location(world,chunk.getX() * 16.5,0,chunk.getZ() * 16.5);
        int r = RANDOM.nextInt(RANGE);
        double sin = RANDOM.nextDouble() * 2 - 1;
        double cosA = Math.sqrt(1 - sin * sin);
        double cos = RANDOM.nextDouble() >= 0.5 ? cosA : -cosA;
        double scale = RANDOM.nextDouble();
        loc.add(r * sin * scale,0,r * cos * scale);
        return getValidLocation(loc);*/
        return null;
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

    private double getAverageHeight(Chunk chunk){
        double totalHeight = 0;
        int playerNumber = 0;
        for(Entity entity : chunk.getEntities()){
            if(entity instanceof Player){
                Player player = (Player)entity;
                playerNumber ++;
                totalHeight += player.getLocation().getY();
            }
        }
        return totalHeight / playerNumber;
    }

    private void spawn(){
        MobModel model = randomMob();
    }


}
