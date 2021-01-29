package ru.geekbrains.java_two.chat.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Logger {

    private Path logFile;
    private String user;

    public void putMsgToLogFile(String msg) {
        try {
            if (!Files.exists(logFile.getParent())) {
                Files.createDirectories(logFile.getParent());
            }
            msg += "\n";
            Files.write(logFile, msg.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMessagesFromLogFile(int quantity) {
        try {
            if (!Files.exists(logFile)) {
                return Arrays.asList("Missing log file: " + logFile.toString());
            } else {
                List<String> messages = Files.readAllLines(logFile);
                if (messages.size() == 0) return Arrays.asList("Empty log!");
                int messagesLimit = Math.min(messages.size(), quantity);
                return messages.subList(messages.size() - messagesLimit, messages.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList("Something went wrong");
    }

    public void setUser(String user) {
        this.user = user;
        logFile = Paths.get("log", "history_" + user + ".txt");
    }
}
