package yumcraft.getofflineloc.Task;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.xbaimiao.server.teleport.api.TeleportAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import yumcraft.getofflineloc.Sql.sql;
import yumcraft.getofflineloc.Unity.Unity;
import yumcraft.getofflineloc.Unity.serializeUnity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class autoTeleport {
    private Plugin plugin;
    private HashMap<String,Integer> autoTeleport;
    private sql sql;
    public autoTeleport(Plugin plugin,HashMap<String,Integer> autoTeleport,sql sql){
        this.sql = sql;
        this.plugin = plugin;
        this.autoTeleport = autoTeleport;
    }
    public void timer(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if (autoTeleport.isEmpty()){
                    return;
                }
                Collection<? extends Player> Players= plugin.getServer().getOnlinePlayers();
                for(Player p: Players){
                    if( autoTeleport.get(p.getName()) != null ){
                        int a = autoTeleport.get(p.getName());
                        if(a<=plugin.getConfig().getInt("autoTeleport.delay")&& a>0 ){

                            //发消息呗
                            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
                            PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.SET_ACTION_BAR_TEXT);
                            String message = plugin.getConfig().getString("autoTeleport.tips")
                                    .replace("%s%",String.valueOf(a))
                                    .replace("&","§");
                            packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
                            packet.setMeta("GetOfflineLoc", true); // Mark packet as from Aurelium Skills
                            protocolManager.sendServerPacket(p,packet);
                            autoTeleport.put(p.getName(),--a);
                        }else if(a == 0){
                            try {
                                autoTeleport.remove(p.getName());
                                if(!sql.firstJoin(p.getUniqueId().toString())){
                                    //服务器名, world, x,z,y 玩家
                                    String ServerName = plugin.getConfig().getString("autoTeleport.location.server");
                                    String World = plugin.getConfig().getString("autoTeleport.location.world");
                                    double x = plugin.getConfig().getDouble("autoTeleport.location.x");
                                    double z = plugin.getConfig().getDouble("autoTeleport.location.z");;
                                    double y = plugin.getConfig().getDouble("autoTeleport.location.y");;
                                    TeleportAPI.Factory.getTeleportAPI().teleport(p,ServerName,World,x,y,z);

                                }else{
                                    String LocValue = sql.getLocation(p.getUniqueId().toString());
                                    Location location = new serializeUnity().getLocation(LocValue);
                                    String ServerName = new serializeUnity().getServerName(LocValue);
                                    TeleportAPI.Factory.getTeleportAPI().teleport(p,ServerName,location);
                                }

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }



            }
        }.runTaskTimerAsynchronously(plugin,0,20);
    }
}
