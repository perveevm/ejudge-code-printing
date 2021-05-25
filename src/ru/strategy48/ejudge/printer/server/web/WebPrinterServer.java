package ru.strategy48.ejudge.printer.server.web;

import com.sun.net.httpserver.HttpServer;
import org.xml.sax.SAXException;
import ru.strategy48.ejudge.printer.server.PrintingServer;
import ru.strategy48.ejudge.printer.server.exceptions.InvalidServerConfigException;
import ru.strategy48.ejudge.printer.server.exceptions.PrinterServerException;
import ru.strategy48.ejudge.printer.server.objects.ServerConfig;
import ru.strategy48.ejudge.printer.server.exceptions.WebPrinterServerException;
import ru.strategy48.ejudge.printer.server.utils.XMLUtils;
import ru.strategy48.ejudge.printer.server.web.handlers.ClientHandler;
import ru.strategy48.ejudge.printer.server.web.handlers.MainHandler;
import ru.strategy48.ejudge.printer.server.web.handlers.PrintHandler;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Web server for {@link PrintingServer}
 */
public class WebPrinterServer implements AutoCloseable {
    private final HttpServer server;

    public WebPrinterServer(final Path configPath) throws PrinterServerException {
        ServerConfig config;
        try {
            config = XMLUtils.loadServerConfig(configPath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new InvalidServerConfigException("Cannot parse web server config", e);
        }

        PrintingServer printer = new PrintingServer(config);
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(config.getHost(), config.getPort()), 10000);

            server.createContext("/", new MainHandler(config));
            server.createContext("/print", new PrintHandler(config, printer));
            server.createContext("/client", new ClientHandler(config, printer));

            server.start();
        } catch (Exception e) {
            throw new WebPrinterServerException("Cannot create web server", e);
        }
    }

    @Override
    public void close() {
        server.stop(0);
    }
}
