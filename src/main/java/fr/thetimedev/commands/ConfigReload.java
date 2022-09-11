package fr.thetimedev.commands;

import fr.thetimedev.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class ConfigReload implements CommandExecutor {

    Logger LOGGER = getLogger();

    private Main main;

    public ConfigReload(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!sender.hasPermission("readspawn.reload")) {
            String message = main.config.getString("messages.prefix") + main.config.getString("messages.no_permission");
            if (message.equals("")) {
                message = "[§6ReadSpawn§f] §cVous n'avez pas la permission pour execter cette commande !";
            }
            sender.sendMessage(message);
            return true;
        }

        try {
            main.config.load(main.configfile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        String message = main.config.getString("messages.prefix") + main.config.getString("messages.reload_success");
        if(message.equals("")) {
            message = "[§6ReadSpawn§f] §aConfig Reloaded!";
        }
        sender.sendMessage(message);
        return true;
    }
}
