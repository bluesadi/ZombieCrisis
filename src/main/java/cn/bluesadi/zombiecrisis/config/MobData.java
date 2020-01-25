package cn.bluesadi.zombiecrisis.config;

import cn.bluesadi.commonlib.io.YamlData;
import cn.bluesadi.commonlib.logging.Logger;
import cn.bluesadi.zombiecrisis.ZombieCrisis;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Random;
import static cn.bluesadi.commonlib.io.FileUtil.*;

public class MobData {

    private static Map<String, MobData> mobDataMap = Maps.newHashMap();
    private static final Random random = new Random();
    private static int totalWeight;
    private String id;
    private String customName;
    private double maxHealth;
    private double speed;
    private double damage;
    private double resistance;
    private int weight;

    private MobData(String id){
        this.id = id;
    }

    public static int loadMobs(Path path){
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs){
                    YamlData data = loadConfig(path);
                    MobData mob = new MobData(data.getString("Id",null));
                    mob.customName = data.getString("Name");
                    mob.maxHealth = data.getDouble("MaxHealth",10);
                    mob.speed = data.getDouble("Speed",0.2);
                    mob.damage = data.getDouble("Damage",3);
                    mob.resistance = data.getDouble("Resistance",0);
                    mob.weight = data.getInt("Weight",1);
                    totalWeight += mob.weight;
                    if(mob.id != null) {
                        mobDataMap.put(mob.id, mob);
                    }else{
                        Logger.warn(ZombieCrisis.getInstance().getPluginLanguage().getMessage("mob_load_failed",path.getFileName()));
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        }catch (IOException e){
            Logger.warn(ZombieCrisis.getInstance().getPluginLanguage().getMessage("mob_load_failed",path.getFileName()));
        }
        return mobDataMap.size();
    }

    public static MobData random(){
        double r = totalWeight * random.nextDouble();
        int cur = 0;
        for(MobData data : mobDataMap.values()){
            cur += data.getWeight();
            if(cur >= r){
                return data;
            }
        }
        return null;
    }

    public static MobData getMobData(String id){
        return mobDataMap.get(id);
    }

    public String getCustomName() {
        return customName;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDamage() {
        return damage;
    }

    public double getResistance() {
        return resistance;
    }

    public int getWeight() {
        return weight;
    }

    public String getId() {
        return id;
    }
}