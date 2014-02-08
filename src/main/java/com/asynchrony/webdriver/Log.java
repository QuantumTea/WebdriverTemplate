package com.asynchrony.webdriver;

import java.io.*;

public class Log {
    private static final StringBuilder output = new StringBuilder();
    private static String logFilePath = "";
    private static String logFileName = "";

    public static void setLogFilePath(String path) {
        logFilePath = path;
    }

    public static void setLogFileName(String name) {
        logFileName = name;
    }

    public static void info(String log) {
        output.append(log).append(System.getProperty("line.separator"));
        System.out.println(log);
    }

    public static void writeFinalLog() {
        String path = logFilePath + System.getProperty("file.separator") + logFileName;

        try {
            File logFile = new File(path);
            FileWriter fw = new FileWriter(logFile, logFile.exists());
            PrintWriter out = new PrintWriter(fw);
            out.println(output);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("\nIOException, log file not written");
            System.out.println("Path was: " + path);
            System.out.println(output);
        }
    }
}
