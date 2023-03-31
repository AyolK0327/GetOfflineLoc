package yumcraft.getofflineloc.Redis;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

/**
 * @author: Ayolk
 * @create: 2023-03-16 21:46
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class redisUnity {
    private Plugin plugin;
    private Jedis jedis;
    public redisUnity(Plugin plugin){
        this.plugin = plugin;
    }
   public Jedis getJedis(){
       String host = plugin.getConfig().getString("Redis.host");
       int port = plugin.getConfig().getInt("Redis.port");
       String password = plugin.getConfig().getString("Redis.password");
       JedisPool jedisPool = new JedisPool(host, port);
       //通过连接池对象获取连接redis的连接对象
       jedis = jedisPool.getResource();
       jedis.auth(password);
       if(!jedis.ping().equalsIgnoreCase("pong")){
           plugin.onDisable();
       }
       return this.jedis;
   }
   public HashMap<String,Object> getDate(){
       long startTime = System.currentTimeMillis();
       HashMap<String,Object> Date = new HashMap<>();
       String[] getKeys = jedis.keys("*").toArray(new String[0]);
       String a = "GetOfflineLoc:";
       for(String b : getKeys){
           if(b.contains(a)){
               String Key = b.replace(a,"");
               String value =jedis.get(b);
               Date.put(Key,value);
           }
       }
       long endTime = System.currentTimeMillis();
       float excTime = (float) (endTime - startTime) / 1000;
       System.out.println("执行时间：" + excTime + "s");
       return Date;
   }
   public void UpdateData(String key ,String value){
       new BukkitRunnable() {
           @Override
           public void run() {
               jedis.set(key,value);
           }
       }.runTaskAsynchronously(plugin);
   }

}
