package yumcraft.getofflineloc.API;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import yumcraft.getofflineloc.Unity.Unity;

/**
 * @author: Ayolk
 * @create: 2023-03-16 22:49
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public interface GetOfflineLocApi {

    void teleport(Player player, String ServerName, Location location);



    public static class Factory {
        private static GetOfflineLocApi instance = null;

        public Factory() {
        }

        public static GetOfflineLocApi getTeleportAPI() {
            if (instance == null) {
                instance = new Unity();
            }

            return instance;
        }
    }

}
