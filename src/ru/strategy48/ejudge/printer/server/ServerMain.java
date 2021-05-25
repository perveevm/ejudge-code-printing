package ru.strategy48.ejudge.printer.server;

import ru.strategy48.ejudge.printer.server.web.WebPrinterServer;

import java.nio.file.Path;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Main class that starts {@link PrintingServer}
 */
public class ServerMain {
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Expected one argument: config file path");
            return;
        }

        Path configPath = Path.of(args[0]);
        WebPrinterServer server;
        try {
            server = new WebPrinterServer(configPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
