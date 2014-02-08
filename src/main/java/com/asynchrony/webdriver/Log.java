package com.asynchrony.webdriver;

import java.io.*;
import java.util.Properties;

class Log {
    private static final StringBuilder output = new StringBuilder();
    private static String logFilePath = "";
    private static String logFileName = "";

    Log()
    {
        Properties logProperties = new Properties();
        InputStream input;

        try
        {
            input = new FileInputStream("config.properties");
            logProperties.load(input);
            logFilePath = logProperties.getProperty("logfilepath", "C:\\Logs");
            logFileName = logProperties.getProperty("logfilename", "log.txt");
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println(ex.toString());
        }
    }

    public static void Info(String log) {
        output.append(log).append(System.getProperty("line.separator"));
    }

    public static void WriteFinalLog() {
        String path = logFilePath + System.getProperty("file.separator") + logFileName;
        //System.out.println("Path is: " + path);

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
            out.println(output);
            out.close();
        } catch (IOException e) {
            System.out.println("\nIOException, log file not written");
            System.out.println("Path was: " + path);
            System.out.println(output);
        }
    }
}
