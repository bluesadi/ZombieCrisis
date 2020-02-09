package cn.bluesadi.zombiecrisis.util;

import java.util.Random;

public class RandomUtil {

    public static final Random RANDOM = new Random();

    public static boolean coin(double possibility){
        return RANDOM.nextDouble() <= possibility;
    }
}
