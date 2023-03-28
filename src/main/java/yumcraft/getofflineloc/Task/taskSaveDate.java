package yumcraft.getofflineloc.Task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import yumcraft.getofflineloc.Redis.redis;

/**
 * @author: Ayolk
 * @create: 2023-03-28 19:56
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class taskSaveDate{
    private Plugin plugin;
    taskSaveDate(Plugin plugin){
        this.plugin = plugin;
    }
    public void taskSaveDate1(){
        new BukkitRunnable() {
            @Override
            public void run() {
                redis redis1 = new redis(plugin);
                redis1.ConnectRedis();
                redis1.getDate();
            }
        }.runTaskTimerAsynchronously(plugin,20 ,20);
    }
}
