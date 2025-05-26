package com.ryzlar.setHomePlugin;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public final class SetHomePlugin extends JavaPlugin {
    private File homesFile;
    private JSONObject homesData;

    @Override
    public void onEnable() {
        homesFile = new File(getDataFolder(), "homes.json");
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        if (!homesFile.exists()) {
            try {
                homesFile.createNewFile();
                homesData = new JSONObject();
                saveHomes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadHomes();
        }
        getCommand("sethome").setTabCompleter(new HomeTabCompleter(this));
        getCommand("home").setTabCompleter(new HomeTabCompleter(this));
        getCommand("homelist").setTabCompleter(new HomeTabCompleter(this));
        getCommand("delhome").setTabCompleter(new HomeTabCompleter(this));
        getCommand("renamehome").setTabCompleter(new HomeTabCompleter(this));
        getLogger().info("SetHomePlugin ingeschakeld.");
    }

    @Override
    public void onDisable() {
        saveHomes();
        getLogger().info("SetHomePlugin uitgeschakeld.");
    }

    private void loadHomes() {
        try (FileReader reader = new FileReader(homesFile)) {
            homesData = (JSONObject) new JSONParser().parse(reader);
        } catch (IOException | ParseException e) {
            homesData = new JSONObject();
        }
    }

    private void saveHomes() {
        try (FileWriter writer = new FileWriter(homesFile)) {
            writer.write(homesData.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getHomesData() {
        return homesData;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();

        switch (command.getName().toLowerCase()) {
            case "sethome":
                if (args.length != 1) {
                    player.sendMessage("§cGebruik: /sethome <naam>");
                    return true;
                }
                String name = args[0];
                JSONObject playerHomes = (JSONObject) homesData.getOrDefault(uuid, new JSONObject());
                if (playerHomes.containsKey(name)) {
                    player.sendMessage("§cJe hebt al een home met die naam.");
                    return true;
                }
                Location loc = player.getLocation();
                JSONObject locObj = new JSONObject();
                locObj.put("world", loc.getWorld().getName());
                locObj.put("x", loc.getX());
                locObj.put("y", loc.getY());
                locObj.put("z", loc.getZ());
                playerHomes.put(name, locObj);
                homesData.put(uuid, playerHomes);
                saveHomes();
                player.sendMessage("§aHome '" + name + "' opgeslagen!");
                return true;

            case "home":
                if (args.length != 1) {
                    player.sendMessage("§cGebruik: /home <naam>");
                    return true;
                }
                name = args[0];
                playerHomes = (JSONObject) homesData.getOrDefault(uuid, new JSONObject());
                if (!playerHomes.containsKey(name)) {
                    player.sendMessage("§cGeen home gevonden met die naam.");
                    return true;
                }
                JSONObject home = (JSONObject) playerHomes.get(name);
                World world = Bukkit.getWorld((String) home.get("world"));
                double x = (double) home.get("x");
                double y = (double) home.get("y");
                double z = (double) home.get("z");
                player.teleport(new Location(world, x, y, z));
                player.sendMessage("§aTeleporteerd naar home '" + name + "'!");
                return true;

            case "homelist":
                playerHomes = (JSONObject) homesData.getOrDefault(uuid, new JSONObject());
                if (playerHomes.isEmpty()) {
                    player.sendMessage("§cJe hebt nog geen homes ingesteld.");
                } else {
                    player.sendMessage("§aJouw homes: §f" + String.join(", ", playerHomes.keySet()));
                }
                return true;

            case "delhome":
                if (args.length != 1) {
                    player.sendMessage("§cGebruik: /delhome <naam>");
                    return true;
                }
                name = args[0];
                playerHomes = (JSONObject) homesData.getOrDefault(uuid, new JSONObject());
                if (playerHomes.remove(name) != null) {
                    homesData.put(uuid, playerHomes);
                    saveHomes();
                    player.sendMessage("§aHome '" + name + "' verwijderd.");
                } else {
                    player.sendMessage("§cGeen home gevonden met die naam.");
                }
                return true;

            case "renamehome":
                if (args.length != 2) {
                    player.sendMessage("§cGebruik: /renamehome <oude_naam> <nieuwe_naam>");
                    return true;
                }
                String oldName = args[0], newName = args[1];
                playerHomes = (JSONObject) homesData.getOrDefault(uuid, new JSONObject());
                if (!playerHomes.containsKey(oldName)) {
                    player.sendMessage("§cGeen home gevonden met de naam '" + oldName + "'.");
                } else if (playerHomes.containsKey(newName)) {
                    player.sendMessage("§cJe hebt al een home met de naam '" + newName + "'.");
                } else {
                    playerHomes.put(newName, playerHomes.remove(oldName));
                    homesData.put(uuid, playerHomes);
                    saveHomes();
                    player.sendMessage("§aHome '" + oldName + "' hernoemd naar '" + newName + "'.");
                }
                return true;
        }

        return false;
    }
}
