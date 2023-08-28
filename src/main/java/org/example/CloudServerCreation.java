package org.example;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CloudServerCreation {

    public static void createServer(String name) throws IOException {
        ProxyServer server = ProxyServer.getInstance();

        int port = (int) (Math.random() * 10000 + 10000);
        ServerInfo info = server.constructServerInfo(name, new InetSocketAddress("localhost", port), "  ", false);
        server.getServers().put(name, info);
        loadServer(name, port);
    }






    public static void loadServer(String name, int port) throws IOException {
        File defaultServerFile = new File("../DEFAULT_SERVER");
        File serverFile = new File("../" + name);
        //check if server already exists
        if(!serverFile.exists()) {
            serverFile.mkdir();
        }

       try {
            // Clone default server to new server
            for(File file : defaultServerFile.listFiles()) {
                Files.copy(file.toPath(), new File(serverFile, file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // The next steps will be:
            // 1. Change server.properties
            // 2. Change server name
            // ...
            File serverPropertiesFile = new File(serverFile, "server.properties");
            String serverProperties = new String(Files.readAllBytes(serverPropertiesFile.toPath()));
            serverProperties = serverProperties.replace("REPLACEBLE_SERVER_PORT_DO_NOT_CHANGE", String.valueOf(port));
            Files.write(serverPropertiesFile.toPath(), serverProperties.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }
        CreateStartFile.createStartScript(name, serverFile.getAbsolutePath(), 2);
        //StartSever.executeCommand("chmod_+x_" + serverFile.getAbsolutePath()+"start.sh");
        //StartSever.startServer(name, serverFile.getAbsolutePath()+"start.sh");
        StartSever.startServer(name,serverFile.getAbsolutePath()+"/start.sh");


    }


}
