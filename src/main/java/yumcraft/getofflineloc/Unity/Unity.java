package yumcraft.getofflineloc.Unity;

import com.xbaimiao.server.teleport.api.TeleportAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import yumcraft.getofflineloc.API.GetOfflineLocApi;

/**
 * @author: Ayolk
 * @create: 2023-03-16 22:53
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class Unity implements GetOfflineLocApi {
    @Override
    public void teleport(Player player, String ServerName, Location location) {
        TeleportAPI.Factory.getTeleportAPI().teleport(player,ServerName,location);
    }
    public void teleport(Player player, String ServerName,String world, double x,double y,double z) {
        TeleportAPI.Factory.getTeleportAPI().teleport(player,ServerName,world,x,y,z);
    }
}
