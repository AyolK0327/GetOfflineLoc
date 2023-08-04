package yumcraft.getofflineloc;

import com.comphenix.protocol.ProtocolLibrary;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import yumcraft.getofflineloc.Command.command;
import yumcraft.getofflineloc.Event.onPlayerQuit;
import yumcraft.getofflineloc.Redis.redisUnity;
import yumcraft.getofflineloc.Sql.sql;
import yumcraft.getofflineloc.Task.autoTeleport;
import yumcraft.getofflineloc.Task.taskSaveDate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class GetOfflineLoc extends JavaPlugin implements PluginMessageListener {
    @Override
    public void onEnable() {
        HashMap<String,Integer> autoTeleport = new HashMap<>();
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        // Plugin startup logic
        FileConfiguration config = this.getConfig();
        saveDefaultConfig();
        //
        sql sql = new sql(this);
        sql.getHikari();
        this.getCommand("GetOfflineLoc").setExecutor(new command(this,sql,autoTeleport));
        //
        this.getServer().getPluginManager().registerEvents(new onPlayerQuit(this,autoTeleport), this);
        //延迟传送
        if(getConfig().getBoolean("autoTeleport.enable")){
            //     玩家名字,倒计时

            //开启
            new autoTeleport(this,autoTeleport,sql).timer();
        }

        if(getConfig().getBoolean("Switch")){
            new taskSaveDate(this,sql).taskSaveDate1();
        }
        if(getConfig().getString("ServerName") != "test"){
        }
    }
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("GetServer")) {
            // 使用下文中的"返回（Response）"一节的代码进行读取
            // 数据处理
            String servername = in.readUTF();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
