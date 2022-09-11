package fr.thetimedev.commands;

import fr.thetimedev.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class SetSpawn implements CommandExecutor {

    Logger LOGGER = getLogger();

    private Main explodium;

    public SetSpawn(Main explodium){
        this.explodium = explodium;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            try {
                explodium.config.load(explodium.configfile);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
            Player player = (Player)sender;
            if(!player.hasPermission("readspawn.setspawn")) {
                String message = explodium.config.getString("messages.prefix") + explodium.config.getString("messages.no_permission");
                if(message.equals("")) {
                    message = "[§6ReadSpawn§f] §cVous n'avez pas la permission pour execter cette commande !";
                }
                player.sendMessage(message);
                return true;
            }
            Location location = player.getLocation();
            double X = location.getX();
            double Y = location.getY();
            double Z = location.getZ();
            float pitch = location.getPitch();
            float yaw = location.getYaw();
            World world = location.getWorld();
            String worldName;
            if (world != null) {
                worldName = world.getName();
            } else {
                worldName = getServer().getWorlds().get(0).getName();
                LOGGER.warning("Unknown World! Using \"" + worldName + "\".");
            }
            explodium.config.set("X", X);
            explodium.config.set("Y", Y);
            explodium.config.set("Z", Z);
            explodium.config.set("pitch", pitch);
            explodium.config.set("yaw", yaw);
            explodium.config.set("World", worldName);
            try {
                explodium.config.save(explodium.configfile);
                String sucess = explodium.config.getString("messages.prefix").replace('&', '§')  + explodium.config.getString("messages.setspawn_success").replace('&', '§') ;
                if(sucess.equals("")) {
                    sucess = "[§6ReadSpawn§f] §aLa position du spawn à bien été modifié !";
                }
                player.sendMessage(sucess);
            } catch (IOException e) {
                String error = explodium.config.getString("messages.prefix").replace('&', '§') + explodium.config.getString("messages.setspawn_success").replace('&', '§') ;
                if(error.equals("")) {
                    error = "[§6ReadSpawn§f] §aLa position du spawn à bien été modifié !";
                }
                player.sendMessage(error);
                e.printStackTrace();
            }
        }
        return false;
    }

}
