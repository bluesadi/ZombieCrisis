package cn.bluesadi.zombiecrisis.util;

import cn.bluesadi.commonlib.logging.Logger;
import cn.bluesadi.zombiecrisis.ZombieCrisis;

public enum DayNightCircle {

    DAY(0,12210,1.0),
    SUNSET(12210,13800,1.5),
    NIGHT(13800,18000,2.0),
    MID_NIGHT(18000,22000,3.0),
    SUN_RISE(22000,24000,1.5);


    private long start,end;
    private double scale;

    DayNightCircle(long start,long end,double scale){
        this.start = start;
        this.end = end;
        this.scale = scale;
    }

    public boolean inRange(long time){
        time %= 24000;
        return time > start && time <= end;
    }

    public double getScale() {
        return scale;
    }

    public static DayNightCircle getTime(long time){
        time %= 24000;
        for(DayNightCircle circle : values()){
            if(circle.inRange(time)) return circle;
        }
        Logger.debug(ZombieCrisis.ID,"无法准备获取时间:"+time);
        return DAY;
    }
}
