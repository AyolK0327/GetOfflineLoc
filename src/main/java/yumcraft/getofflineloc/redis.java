package yumcraft.getofflineloc;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author: Ayolk
 * @create: 2023-03-16 21:46
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class redis {
    private Plugin plugin;
    redis(Plugin plugin){
        this.plugin = plugin;
    }
   public Jedis ConnectRedis(){
       String host = plugin.getConfig().getString("Redis.host");
       int port = plugin.getConfig().getInt("Redis.port");
       String password = plugin.getConfig().getString("Redis.password");
       JedisPool jedisPool = new JedisPool(host, port);
       jedisPool.getResource();
       //通过连接池对象获取连接redis的连接对象
       Jedis jedis = jedisPool.getResource();
       jedis.auth(password);
       if(jedis.ping().equalsIgnoreCase("pong")){
           plugin.getLogger().info("redis加载成功.");
       }else{
           plugin.getLogger().info("redis.加载失败.");
           plugin.onDisable();
       }
       return jedis;
   }
   public void UpdateData(String key ,String value){
       new BukkitRunnable() {
           @Override
           public void run() {
               ConnectRedis().set(key,value);
           }
       }.runTaskAsynchronously(plugin);
   }

}
