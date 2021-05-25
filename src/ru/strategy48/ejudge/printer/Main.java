package ru.strategy48.ejudge.printer;

import ru.strategy48.ejudge.printer.client.ClientMain;
import ru.strategy48.ejudge.printer.server.ServerMain;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * The main class that starts tool in server or client mode
 */
public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Expected first argument [server/client] and second argument [config path]");
            return;
        }

        String[] newArgs = new String[1];
        newArgs[0] = args[1];
        if (args[0].equals("server")) {
            ServerMain.main(newArgs);
        } else if (args[0].equals("client")) {
            ClientMain.main(newArgs);
        } else {
            System.out.println("Expected first argument [server/client] and second argument [config path]");
        }
    }
}
