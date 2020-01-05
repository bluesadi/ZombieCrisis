package cn.bluesadi.zombiecrisis.game;

import cn.bluesadi.commonlib.io.YamlData;
import cn.bluesadi.commonlib.logging.Logger;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import static cn.bluesadi.commonlib.io.FileUtil.loadConfig;
import static cn.bluesadi.zombiecrisis.ZombieCrisis.ID;

public class Game {

    private static Map<String,Game> gameMap = Maps.newHashMap();
    private String id;
    private String name;
    private GameWorld gameWorld;
    private MobSpawner mobSpawner;

    public Game(String id){
        this.id = id;
    }

    public static int loadGames(Path path){
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    YamlData data = loadConfig(path);
                    Game game = new Game(data.getString("Id","Unknown"));
                    game.name = data.getString("Name","Undefined");
                    game.gameWorld = new GameWorld(data.getString("GameWorld"));
                    game.mobSpawner = new MobSpawner(game.gameWorld);
                    game.mobSpawner.setMaxMobsPerChunk(data.getInt("MobSpawner.MaxMobsPerChunk",5));
                    game.mobSpawner.setPeriod(data.getInt("MobSpawner.Period"));
                    if(data.getBoolean("MobSpawner.Enable")){
                        game.mobSpawner.enable();
                    }
                    if(game.validate()) {
                        gameMap.put(game.name, game);
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        }catch (IOException e){
            Logger.error(ID,"加载怪物失败!",e);
        }
        return gameMap.size();
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

    private boolean validate(){
        return !this.id.equals("Unknown") && this.gameWorld.getWorld() != null;
    }
}
