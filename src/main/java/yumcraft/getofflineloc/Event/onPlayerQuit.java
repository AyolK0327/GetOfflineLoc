package yumcraft.getofflineloc.Event;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import yumcraft.getofflineloc.Unity.serializeUnity;
import yumcraft.getofflineloc.Redis.redis;

import java.io.*;
import java.util.Map;

/**
 * @author: Ayolk
 * @create: 2023-03-16 21:36
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class onPlayerQuit implements Listener {
    private Plugin plugin;
    public onPlayerQuit(Plugin plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(plugin.getConfig().getBoolean("Switch")){
            return;
        }

        Location location = event.getPlayer().getLocation();
        serializeUnity serializeUnity = new serializeUnity();
        String ServerName = plugin.getConfig().getString("ServerName");
        String Key = "GetOfflineLoc:"+event.getPlayer().getUniqueId();
        // 转换成json

        serializeUnity.setLocation(location);
        serializeUnity.serialize(ServerName);

        new redis(plugin).UpdateData(Key,serializeUnity.getValue());

    }






    public byte[] serializeToByte(Location location){
        byte[] bytes = new byte[0];
        Map<String, Object> locData = location.serialize();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(locData);
            bytes = byteArrayOutputStream.toByteArray();
        }catch (Exception ignored){

        }
        return bytes;
    }

    public Location DeserializeLoc(byte[] bytes){
        Map<String, Object> table = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            table = (Map<String, Object>) objectInputStream.readObject();
        }catch (Exception ignored){

        }
        return Location.deserialize(table);
    }
}
