package fr.thetimedev;

import fr.thetimedev.Listener.PlayerJoint;
import fr.thetimedev.commands.CommandSpawn;
import fr.thetimedev.commands.ConfigReload;
import fr.thetimedev.commands.SetSpawn;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static Main instance;

    Logger LOGGER = getLogger();

    public static Main getInstance(){return instance;}

    public File configfile = new File(getDataFolder(), "config.yml");
    public FileConfiguration config = YamlConfiguration.loadConfiguration(configfile);

    @Override
    public void onEnable() {
        LOGGER.info("Loading config files...");

        config.addDefault("messages.prefix", "[&6ReadSpawn&f] ");
        config.addDefault("messages.spawn_success", "&aVous avez été téléporté au spawn.");
        config.addDefault("messages.spawn_error", "&4Aucune configuration n'a été faite pour le spawn.");
        config.addDefault("messages.no_permission", "&cVous n'avez pas la permission pour execter cette commande !");
        config.addDefault("messages.no_player", "&cVous devez être connecter pour utiliser cette commande !");
        config.addDefault("messages.setspawn_success", "&aLa position du spawn à bien été modifié !");
        config.addDefault("messages.setspawn_error", "&4Une erreur est survenue lors de la modification du spawn !");
        config.addDefault("messages.reload_success", "&aLa configuration à bien été rechargé !");

        config.options().copyDefaults(true);

        if(!configfile.exists()) {
            try {
                config.save(configfile);
                LOGGER.info("Config file create !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("Config files loaded!");

        instance = this;
        LOGGER.info("Plugin up !");

        getCommand("spawn").setExecutor(new CommandSpawn(this));
        getCommand("setspawn").setExecutor(new SetSpawn(this));
        getCommand("readspawn-reload").setExecutor(new ConfigReload(this));
        this.getServer().getPluginManager().registerEvents(new PlayerJoint(this), this);
    }

    @Override
    public void onDisable() {
        LOGGER.info("Plugin down !");
    }

}