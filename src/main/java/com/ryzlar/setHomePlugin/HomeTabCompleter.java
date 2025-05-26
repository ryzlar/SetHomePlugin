package com.ryzlar.setHomePlugin;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.util.*;

public class HomeTabCompleter implements TabCompleter {
    private final SetHomePlugin plugin;

    public HomeTabCompleter(SetHomePlugin plugin) {
        this.plugin = plugin;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return null;
        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        JSONObject data = plugin.getHomesData();
        JSONObject homes = (JSONObject) data.getOrDefault(uuid, new JSONObject());

        if (args.length == 1 && Arrays.asList("home", "delhome", "renamehome").contains(cmd.getName().toLowerCase())) {
            List<String> homeNames = new ArrayList<>();
            for (Object key : homes.keySet()) homeNames.add((String) key);
            return homeNames;
        }

        return Collections.emptyList();
    }
}
