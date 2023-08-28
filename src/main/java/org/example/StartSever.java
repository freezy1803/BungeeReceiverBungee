package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartSever {


    /**
     * Starts the specified script in a foreground screen session.
     *
     * @param screenName The name of the screen session.
     * @param scriptPath The path to the script to execute.
     */
    public static void startScriptInForegroundScreen(String screenName, String scriptPath) {
        // Command to check if screen session exists
        String[] checkCommand = {"screen", "-list", "|", "grep", screenName};

        try {
            // Check if the screen session exists
            ProcessBuilder checkProcessBuilder = new ProcessBuilder(checkCommand);
            int exitCode = checkProcessBuilder.start().waitFor();

            // Command to start/reattach to a screen session and run the script
            String[] command;
            if (exitCode != 0) {
                // Session doesn't exist, create it
                command = new String[]{"screen", "-S", screenName, "bash", scriptPath};
            } else {
                // Session exists, attach to it
                command = new String[]{"screen", "-x", screenName, "bash", scriptPath};
            }

            // Start the screen session with the script in foreground
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void executeCommand(String command) throws IOException {
        try {
            Process process = new ProcessBuilder(command.split("_"))
                    .redirectErrorStream(true)
                    .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Command exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void startServer(String sName, String path) {
        try {


            // Create command to run server in a new screen session
            String[] command = {
                    "sudo",
                    "screen",
                    "-dmS",
                    sName,
                    "bash",
                    "-c",
                    path.replace("Proxy/../", "")
            };
/*
            String[] command2 = {
                    "java",
                    "-server",
                    "-Xmx2G",
                    "-Xms2G",
                    "-jar",
                    "/home/Network/"+sName+"/CustomServer.jar",
                    "nogui"
            };
*/
            // Execute the command
            //String[] command = {path};
            System.out.println("Server started in a new screen session! ");
            System.out.println("Command: " + String.join(" ", command));
            new ProcessBuilder(command).start();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}