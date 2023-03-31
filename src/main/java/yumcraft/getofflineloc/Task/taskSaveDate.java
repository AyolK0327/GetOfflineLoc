package yumcraft.getofflineloc.Task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import yumcraft.getofflineloc.Redis.redisUnity;
import yumcraft.getofflineloc.Sql.sql;

import java.util.HashMap;
import java.util.Set;


/**
 * @author: Ayolk
 * @create: 2023-03-28 19:56
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class taskSaveDate{
    private Plugin plugin;
    public taskSaveDate(Plugin plugin){
        this.plugin = plugin;
    }
    public void taskSaveDate1(){
        redisUnity redisUnity = new redisUnity(plugin);
        sql sql = new sql(plugin);
        sql.getHikari();
        Jedis jedis = redisUnity.getJedis();
        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap< String ,Object> Date =  redisUnity.getDate();
                if(Date == null){
                    plugin.getLogger().info("空数据");
                    return;
                }
                Set<String> setKey = Date.keySet();
                for(String key : setKey){
                    Date.get(key);
                    plugin.getLogger().info(key + "  Value:"+Date.get(key));
                    if(sql.hasValue(key)){
                        sql.Update(Date.get(key),key);
                    }else {
                        sql.Insert(key,Date.get(key));
                    }


                    jedis.del("GetOfflineLoc:"+key);
                }
            }
        }.runTaskTimerAsynchronously(plugin,0 ,5 * 20);
    }
}
