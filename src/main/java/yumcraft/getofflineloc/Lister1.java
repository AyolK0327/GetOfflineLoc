package yumcraft.getofflineloc;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: Ayolk
 * @create: 2023-03-16 21:36
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class Lister1 implements Listener {
    private Plugin plugin;
    Lister1(Plugin plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerLe(PlayerQuitEvent event){
        if(plugin.getConfig().getBoolean("Switch")){
            return;
        }
        Location location = event.getPlayer().getLocation();
        String Key = "GetOfflineLoc1:"+event.getPlayer().getUniqueId();
        String value = new String(serializeToByte(location), StandardCharsets.ISO_8859_1);
        new redis(plugin).UpdateData(Key,value);
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
