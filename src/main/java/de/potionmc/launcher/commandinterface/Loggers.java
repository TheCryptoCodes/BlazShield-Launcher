package de.potionmc.launcher.commandinterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Loggers {
  public Loggers(LoggersType type, boolean useColor, String msg) {
    if (useColor == true) {
      String TEXT_RESET = "\033[0m";
      String TEXT_RED = "\033[31m";
      String TEXT_GREEN = "\033[32m";
      String TEXT_YELLOW = "\033[33m";
      String TEXT_CYAN = "\033[36m";
      String TEXT_WHITE = "\033[37m";
      String TEXT_PURPLE = "\033[35m";
      String TEXT_ORANGE = "\033[39m";
      String TEXT_BLUE = "\033[34m";
      String TEXT_BRIGHT_BLACK = "\033[90m";
      String TEXT_BRIGHT_RED = "\033[91m";
      String TEXT_BRIGHT_GREEN = "\033[92m";
      String TEXT_BRIGHT_YELLOW = "\033[93m";
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
      if (type == LoggersType.SUCCESS)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + " ] " + "\033[32m" + "SUCCESS" + "\033[0m" + "| " + msg); 
      if (type == LoggersType.INFO)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + "] " + "\033[32m" + "INFO" + "\033[0m" + "| " + msg);
      if (type == LoggersType.LOGO)
      System.out.println("\033[36m" + msg + "\033[0m");
      if (type == LoggersType.WARN)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + "] " + "\033[33m" + "WARN" + "\033[0m" + " | " + msg); 
      if (type == LoggersType.ERROR)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + "] " + "\033[31m" + "ERROR" + "\033[0m" + " | " + msg); 
      if (type == LoggersType.SERVICEADD)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + "] " + "\033[36m" + "SERVICE-ADD" + "\033[0m" + " | " + msg); 
      if (type == LoggersType.SERVICEREM)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + "] " + "\033[36m" + "NETWORKING" + "\033[0m" + " | " + msg);
      if (type == LoggersType.HELP)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + "] " + "\033[36m" + "HELP" + "\033[0m" + " | " + msg);
      if (type == LoggersType.DOWNLOADER)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + "] " + "\033[36m" + "Loader" + "\033[0m" + " | " + msg); 
      if (type == LoggersType.SERVICECON)
        System.out.println("[\033[39m" + sdf.format(Calendar.getInstance().getTime()) + "\033[0m" + "] " + "\033[36m" + "SERVICE-CON" + "\033[0m" + " | " + msg); 
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
      if (type == LoggersType.INFO)
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "] INFO | " + msg); 
      if (type == LoggersType.WARN)
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "] WARN | " + msg); 
      if (type == LoggersType.ERROR)
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "] ERROR | " + msg); 
      if (type == LoggersType.SERVICEADD)
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "] SERVICE-ADD | " + msg); 
      if (type == LoggersType.SERVICEREM)
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "] SERVICE-RM | " + msg); 
      if (type == LoggersType.DOWNLOADER)
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "] Loader | " + msg); 
      if (type == LoggersType.SERVICECON)
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "] SERVICE-CON | " + msg); 
    } 
  }
}
