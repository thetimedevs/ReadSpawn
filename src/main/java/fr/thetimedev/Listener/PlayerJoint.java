package fr.thetimedev.Listener;

import fr.thetimedev.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;


public class PlayerJoint implements Listener {

    Logger LOGGER = getLogger();

    private Main main;

    public PlayerJoint(Main main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String worldName = main.config.getString("World");
        if(worldName != null) {
            if (!worldName.equals("")) {
                Location Spawn = getSpawn(YamlConfiguration.loadConfiguration(main.configfile));
                event.getPlayer().teleport(Spawn);
            }
        }

    }

    private Location getSpawn(FileConfiguration config) {
        double X = config.getDouble("X");
        double Y = config.getDouble("Y");
        double Z = config.getDouble("Z");

        String pitchS = config.getString("pitch");
        String yawS = config.getString("yaw");

        if (pitchS== null || pitchS.equals("")) {
            LOGGER.warning("Pitch is not set, using 0.");
            pitchS = "0";
        }
        if (yawS == null || yawS.equals("")) {
            LOGGER.warning("Yaw is not set, using 0.");
            yawS = "0";
        }
        String worldName = config.getString("World");
        if (worldName == null || worldName.equals("")) {
            World newWorld = getServer().getWorlds().get(0);
            LOGGER.warning("World is not set, using \"" + newWorld.getName() +"\".");
            worldName = newWorld.getName();
        }
        World world = Bukkit.getWorld(worldName);
        float pitch = Float.parseFloat(pitchS);
        float yaw = Float.parseFloat(yawS);

        return new Location(world, X, Y, Z, yaw, pitch);
    }

}
