package ru.strategy48.ejudge.printer.server.web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.strategy48.ejudge.printer.server.objects.ServerConfig;

import java.io.*;

public class MainHandler implements HttpHandler {
    private final ServerConfig config;

    public MainHandler(final ServerConfig config) {
        this.config = config;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuilder html = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(config.getPrintPageHtmlPath().toFile())))) {
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line);
            }
        }

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, html.toString().getBytes().length);
        OutputStream stream = exchange.getResponseBody();
        stream.write(html.toString().getBytes());
        stream.close();
        exchange.close();
    }
}
