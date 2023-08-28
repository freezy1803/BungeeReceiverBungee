package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateStartFile {


    public static void createStartScript(String serverName, String savePath, int memory) {
        String scriptContent =
                "#!/bin/bash\n\n" +
                        "SCREEN=\"" + serverName + "\"\n" +
                        "SERVICE=\"CustomServer.jar\"\n" +
                        "INITMEM=\"2G\"\n" +
                        "MAXMEM=\"2G\"\n\n" +
                        "if [ \"$#\" -eq 1 ]; then\n" +
                        "    if [ \"$1\" == \"inscreen\" ]; then\n" +
                        "        while true\n" +
                        "        do\n" +
                        "            java -server -Xmx$MAXMEM -Xms$INITMEM -jar $SERVICE nogui\n" +
                        "            echo \"Server started ... Press CTRL+C to stop!\"\n" +
                        "            echo \"Restart in:\"\n" +
                        "            for i in 5 4 3 2 1\n" +
                        "            do\n" +
                        "                echo \"$i...\"\n" +
                        "                sleep 1\n" +
                        "            done\n" +
                        "            echo \"-- Server started --\"\n" +
                        "        done\n" +
                        "    fi\n" +
                        "else\n" +
                        "    screen -S $SCREEN bash $0 inscreen\n" +
                        "fi";

        Path fullPath = Paths.get(savePath, "start.sh");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath.toString()))) {
            writer.write(scriptContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
