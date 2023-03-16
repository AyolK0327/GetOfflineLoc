package yumcraft.getofflineloc;

import com.xbaimiao.server.teleport.ServerTeleport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.java.JavaPlugin;

public final class GetOfflineLoc extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        FileConfiguration config = this.getConfig();
        saveDefaultConfig();

        //
        redis redis = new redis(this);
        redis.ConnectRedis();
        this.getCommand("GetOfflineLoc").setExecutor(new command(this));
        //
        this.getServer().getPluginManager().registerEvents(new Lister1(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
