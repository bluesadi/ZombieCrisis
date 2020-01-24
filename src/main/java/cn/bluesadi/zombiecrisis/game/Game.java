package cn.bluesadi.zombiecrisis.game;

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
import static cn.bluesadi.commonlib.io.FileUtil.loadConfig;

public class Game {

    private static Map<String,Game> gameMap = Maps.newHashMap();
    private String id;
    private String name;
    private GameWorld gameWorld;
    private MobSpawner mobSpawner;

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
                    if(game.id != null && game.gameWorld.getWorld() != null) {
                        gameMap.put(game.name, game);
                        if(data.getBoolean("MobSpawner.Enable")){
                            game.mobSpawner.enable();
                        }
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
