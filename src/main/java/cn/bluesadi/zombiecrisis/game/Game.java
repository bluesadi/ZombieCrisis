package cn.bluesadi.zombiecrisis.game;

import cn.bluesadi.commonlib.io.YamlData;
import cn.bluesadi.commonlib.logging.Logger;
import cn.bluesadi.zombiecrisis.ZombieCrisis;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Location;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import static cn.bluesadi.commonlib.io.FileUtil.loadConfig;

public class Game {

    private static Map<String,Game> gameMap = Maps.newHashMap();
    private String id;
    private String name;
    private GameWorld gameWorld;
    private MobSpawner mobSpawner;
    private Path path;

    private Game(String id){
        this.id = id;
    }

    public static int loadGames(Path path){
        gameMap.clear();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    YamlData data = loadConfig(path);
                    Game game = new Game(data.getString("Id",null));
                    game.name = data.getString("Name",game.id);
                    game.gameWorld = new GameWorld(data.getString("GameWorld"));
                    game.mobSpawner = new MobSpawner(game.gameWorld);
                    game.mobSpawner.setMaxMobsPerChunk(data.getInt("MobSpawner.MaxMobsPerChunk",5));
                    game.mobSpawner.setPeriod(data.getInt("MobSpawner.Period"));
                    List<String> zonesData = data.getStringList("SafeZones");
                    for(String str : zonesData){
                        game.getGameWorld().addSafeZone(new Zone(JSON.parseObject(str)));
                    }
                    if(game.id != null && game.gameWorld.getWorld() != null) {
                        gameMap.put(game.name, game);
                        game.path = path;
                        game.mobSpawner.setEnable(data.getBoolean("MobSpawner.Enable",true));
                        game.mobSpawner.enable();
                    }else{
                        Logger.warn(ZombieCrisis.getInstance().getPluginLanguage().getMessage("game_load_failed",path.getFileName()));
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        }catch (IOException e){
            Logger.warn(ZombieCrisis.getInstance().getPluginLanguage().getMessage("game_load_failed",path.getFileName()));
        }
        return gameMap.size();
    }

    public static Zone getSafeZone(Location loc){
        for(Game game : gameMap.values()){
            Zone zone = game.getGameWorld().getSafeZone(loc);
            if(zone != null) return zone;
        }
        return null;
    }

    public static boolean existsGame(String name){
        return gameMap.containsKey(name);
    }

    public static Game getGame(String game){
        return gameMap.get(game);
    }

    public void saveData(){
        YamlData data = loadConfig(path);
        data.set("ID",id);
        data.set("Name",name);
        data.set("GameWorld",gameWorld.getName());
        data.set("MobSpawner.MaxMobsPerChunk",mobSpawner.getMaxMobsPerChunk());
        data.set("MobSpawner.Period",mobSpawner.getPeriod());
        data.set("MobSpawner.Enable",mobSpawner.isEnable());
        List<String> safeZones = Lists.newArrayList();
        for(Zone zone : gameWorld.getSafeZones()){
            safeZones.add(zone.toJSON());
        }
        data.set("SafeZones",safeZones);
        data.save();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

}
