package org.example;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BungeeCreateServer extends Command {


    public BungeeCreateServer() {
        super("bcs");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("bungee.create.server")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.GOLD + "/bcs <server-name>");
                return;
            }

            try {
                CloudServerCreation.createServer(args[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage(ChatColor.GREEN + "Server: " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " started creation!");

        }
    }

}



