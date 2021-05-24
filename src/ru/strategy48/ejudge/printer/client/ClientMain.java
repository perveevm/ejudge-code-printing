package ru.strategy48.ejudge.printer.client;

import ru.strategy48.ejudge.printer.client.objects.ClientConfig;
import ru.strategy48.ejudge.printer.client.utils.XMLUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

public class ClientMain {
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Expected one argument: config file path");
            return;
        }

        Path configPath = Path.of(args[0]);
        ClientConfig config;
        try {
            config = XMLUtils.loadClientConfig(configPath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error happened while parsing config file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        try (PrintingClient client = new PrintingClient(config)) {
            client.startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
