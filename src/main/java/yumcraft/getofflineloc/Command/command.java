package yumcraft.getofflineloc.Command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import yumcraft.getofflineloc.API.GetOfflineLocApi;
import yumcraft.getofflineloc.Sql.sql;

/**
 * @author: Ayolk
 * @create: 2023-03-16 22:05
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class command implements CommandExecutor {
    private Plugin plugin;
    public command(Plugin plugin){
        this.plugin = plugin;
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

        Location location = player.getLocation();
        if(args[0].equalsIgnoreCase("tp")){
            if(!plugin.getConfig().getBoolean("Switch")){
                sender.sendMessage("本服未开启传送功能.");
                return false;
            }
            sql sql =new sql(plugin);
            sql.hasValue();
            GetOfflineLocApi.Factory.getTeleportAPI().teleport(player,plugin.getConfig().getString("ServerName"),location);
        }

        return false;
    }
}
