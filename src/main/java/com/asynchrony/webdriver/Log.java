package com.asynchrony.webdriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log
{
    private static final StringBuilder output = new StringBuilder();
    private static String logFilePath = "";
    private static String logFileName = "";
    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss:SSSS");

    public static void setLogFilePath(String path)
    {
        logFilePath = path;
    }

    public static void setLogFileName(String name)
    {
        logFileName = name;
    }

    public static void info(String log)
    {
        output.append(timeFormat.format(new Date()))
                .append(" : INFO ")
                .append(log)
                .append(System.getProperty("line.separator"));
        System.out.println(log);
    }

    public static void error(String log)
    {
        output.append(timeFormat.format(new Date()))
                .append(" : ERROR ")
                .append(log)
                .append(System.getProperty("line.separator"));
        System.out.println(log);
    }

    public static void writeFinalLog()
    {
        String path = logFilePath + System.getProperty("file.separator") + logFileName;

        try
        {
            File logFile = new File(path);
            FileWriter fw = new FileWriter(logFile, logFile.exists());
            PrintWriter out = new PrintWriter(fw);
            out.println(output);
            out.flush();
            out.close();
        } catch (IOException e)
        {
            System.out.println("\nIOException, log file not written");
            System.out.println("Path was: " + path);
            System.out.println(output);
        }
    }
}
