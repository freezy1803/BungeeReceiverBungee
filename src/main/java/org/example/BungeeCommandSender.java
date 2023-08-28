package org.example;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class BungeeCommandSender extends Plugin {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new SendCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new BungeeCreateServer());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StartServerCommand());

    }

    public class SendCommand extends Command implements TabExecutor {

        public SendCommand() {
            super("cos");
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            if (sender.hasPermission("bungee.send.servercommand")) {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.GOLD + "/cos <server-name> <command>");
                    return;
                }

                String serverName = args[0];
                String command = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("RunCommand");
                out.writeUTF(command);

                //get all servers


                ProxyServer.getInstance().getServerInfo(serverName).sendData("BungeeCord", out.toByteArray());
                sender.sendMessage(ChatColor.GREEN + "Command: " + ChatColor.GOLD + command + ChatColor.GREEN + " has been sent to server: " + ChatColor.GOLD + serverName + ChatColor.GREEN + "!");
            }
        }

        @Override
        public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
            Set<String> match = new HashSet<>();
            if (args.length == 1) {
                match.addAll(ProxyServer.getInstance().getServers().keySet());
            }
            return match;
        }
    }


}
