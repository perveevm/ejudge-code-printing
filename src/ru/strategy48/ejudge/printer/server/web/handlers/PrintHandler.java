package ru.strategy48.ejudge.printer.server.web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.strategy48.ejudge.printer.server.PrintingServer;
import ru.strategy48.ejudge.printer.server.objects.ServerConfig;
import ru.strategy48.ejudge.printer.server.utils.HandlerUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes {@link HttpHandler} processing query to print source code
 */
public class PrintHandler implements HttpHandler {
    private final ServerConfig config;
    private final PrintingServer printer;

    public PrintHandler(final ServerConfig config, final PrintingServer printer) {
        this.config = config;
        this.printer = printer;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> parameters = HandlerUtils.parseResponseParameters(exchange);
        String login = parameters.getOrDefault("login", null);
        String password = parameters.getOrDefault("password", null);
        String source = parameters.getOrDefault("source", null);

        boolean result = printer.sendForPrinting(login, password, source);
        Path responseHtmlPath;
        if (result) {
            responseHtmlPath = config.getSuccessHtmlPath();
        } else {
            responseHtmlPath = config.getFailHtmlPath();
        }

        StringBuilder html = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(responseHtmlPath.toFile())))) {
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, html.toString().getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(html.toString().getBytes());
        outputStream.close();

        exchange.close();
    }
}
