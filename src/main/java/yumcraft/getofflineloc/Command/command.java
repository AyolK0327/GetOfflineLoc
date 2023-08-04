package yumcraft.getofflineloc.Command;

import com.xbaimiao.server.teleport.api.TeleportAPI;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import yumcraft.getofflineloc.Sql.sql;
import yumcraft.getofflineloc.Unity.serializeUnity;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author: Ayolk
 * @create: 2023-03-16 22:05
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class command implements CommandExecutor {
    private Plugin plugin;
    private sql sql;
    private HashMap<String,Integer> autoTeleport;
    public command(Plugin plugin,sql sql,HashMap<String,Integer> autoTeleport){
        this.plugin = plugin;
        this.sql = sql;
        this.autoTeleport = autoTeleport;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if(args[0].equalsIgnoreCase("reload")){
            plugin.reloadConfig();
            plugin.saveConfig();
            sender.sendMessage("重载配置成功." + plugin.getConfig().getString("ServerName"));
        }
        if(!(sender instanceof Player)){
            return false;
        }
        if(args[0].equalsIgnoreCase("tps")){
            plugin.getLogger().info("玩家"+player.getName()+"加入传送队列.");
            autoTeleport.put(player.getName(),plugin.getConfig().getInt("autoTeleport.delay"));
        }
        if(args[0].equalsIgnoreCase("tp")){
            try {
                String LocValue = sql.getLocation(player.getUniqueId().toString());
                Location location = new serializeUnity().getLocation(LocValue);
                String ServerName = new serializeUnity().getServerName(LocValue);
                TeleportAPI.Factory.getTeleportAPI().teleport(player,ServerName,location);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(args[0].equalsIgnoreCase("tp")){
            if(!plugin.getConfig().getBoolean("Switch")){
                sender.sendMessage("本服未开启传送功能.");
                return false;
            }
        }
        return false;
    }
}
