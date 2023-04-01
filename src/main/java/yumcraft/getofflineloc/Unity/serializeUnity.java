package yumcraft.getofflineloc.Unity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;

/**
 * @author: Ayolk
 * @create: 2023-03-27 22:05
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class serializeUnity {

    private Location location;
    private Double locX;
    private Double locY;
    private Double locZ;
    private float yaw;
    private float pitch;
    private String Value;

    public void setLocation(Location location){
        this.location = location;
        this.locX = location.getX();
        this.locZ = location.getZ();
        this.locY = location.getY();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }
    public String getValue(){
        if(this.Value == null){
            return null;
        }
        return this.Value;
    }

    public void serialize(String ServerName){
        if(location == null){
            return;
        }
        HashMap< String , Object > loc = new HashMap<>();
        loc.put("World",location.getWorld().getName());
        loc.put("X",this.locX);
        loc.put("Y",this.locY);
        loc.put("Z",this.locZ);
        loc.put("yaw", this.yaw);
        loc.put("pitch",this.pitch);
        loc.put("ServerName",ServerName);

        JSONObject jsonObject = new JSONObject(loc);
        this.Value = jsonObject.toJSONString();
    }
    public Location getLocation(String rawLocation){
        JSONObject jsonObject = JSON.parseObject(rawLocation);
        Double X = jsonObject.getDouble("X");
        Double Y = jsonObject.getDouble("Y");
        Double Z = jsonObject.getDouble("Z");
        float yaw = jsonObject.getFloat("yaw");
        float pitch = jsonObject.getFloat("pitch");
        World world = Bukkit.getWorld(jsonObject.getString("World"));
        return new Location(world,X,Y,Z,yaw,pitch);
    }
    public String getServerName(String rawLocation){
        return JSON.parseObject(rawLocation).getString("ServerName");
    }


}
