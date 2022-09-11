package fr.thetimedev.commands;

import fr.thetimedev.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class CommandSpawn implements CommandExecutor {

    Logger LOGGER = getLogger();

    private final Main explodium;

    public CommandSpawn(Main explodium){
        this.explodium = explodium;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.hasPermission("readspawn.spawn")) {
                String message = explodium.config.getString("messages.prefix").replace('&', '§')  + explodium.config.getString("messages.no_permission").replace('&', '§') ;
                if(message.equals("")) {
                    message = "[§6ReadSpawn§f] §cVous n'avez pas la permission pour execter cette commande !";
                }
                player.sendMessage(message);
                return true;
            }
            String worldName = explodium.config.getString("World");
            if(worldName == null || worldName.equals("")) {
                String error = explodium.config.getString("messages.prefix").replace('&', '§')  + explodium.config.getString("messages.spawn_error").replace('&', '§') ;
                if(error.equals("")) {
                    error = "[§6ReadSpawn§f] §4Aucune configuration n'a été faite pour le spawn.";
                }
                player.sendMessage(error);
               return true;
            }else {
                player.teleport(parseStringToLoc(YamlConfiguration.loadConfiguration(explodium.configfile), player));
                String sucess = explodium.config.getString("messages.prefix").replace('&', '§')  + explodium.config.getString("messages.spawn_success").replace('&', '§') ;
                if(sucess.equals("")) {
                    sucess = "[§6ReadSpawn§f] Vous avez été téléporter au spawn !";
                }
                player.sendMessage(sucess);
            }

        }
    return false;
    }

    public Location parseStringToLoc(FileConfiguration config, Player player) {

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
