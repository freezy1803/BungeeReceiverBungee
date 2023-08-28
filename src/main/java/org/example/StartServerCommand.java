package org.example;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StartServerCommand extends Command {

    public StartServerCommand() {
        super("startserver", "serverstarter.use", "copyserver");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /startserver <serverName>");
            return;
        }

        String serverName = args[0];
        File sourceFile = new File("/home/Network/", "DEFAULT_SERVER");
        File targetDir = new File("/home/Network/", serverName);
        System.out.println("Source: " + sourceFile);
        System.out.println("Target: " + targetDir);

        try {
            // Copy source server directory
            copyDirectory(sourceFile.toPath(), targetDir.toPath());

            // Execute start script
            String scriptPath = "sudo "+targetDir + "/start.sh";
            System.out.println("Script path: " + scriptPath);
            ProcessBuilder processBuilder = new ProcessBuilder(scriptPath);
            processBuilder.start();

            sender.sendMessage(ChatColor.GREEN + "Server started successfully!");

        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void copyDirectory(Path source, Path target) throws IOException {
        Files.walk(source).forEach(sourcePath -> {
            Path targetPath = target.resolve(source.relativize(sourcePath));
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}