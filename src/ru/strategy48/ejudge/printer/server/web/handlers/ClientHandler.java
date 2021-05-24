package ru.strategy48.ejudge.printer.server.web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.strategy48.ejudge.printer.server.objects.PrinterQuery;
import ru.strategy48.ejudge.printer.server.PrintingServer;
import ru.strategy48.ejudge.printer.server.objects.ServerConfig;
import ru.strategy48.ejudge.printer.server.utils.HandlerUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ClientHandler implements HttpHandler {
    private final ServerConfig config;
    private final PrintingServer printer;

    public ClientHandler(final ServerConfig config, final PrintingServer printer) {
        this.config = config;
        this.printer = printer;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> parameters = HandlerUtils.parseResponseParameters(exchange);
        String token = parameters.getOrDefault("token", null);

        if (token == null || !printer.checkToken(token)) {
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().close();
            exchange.close();
            return;
        }

        PrinterQuery query = printer.getPrintingCode();
        if (query == null) {
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().close();
            exchange.close();
            return;
        }

        String source = query.getSource();

        StringBuilder sourceToPrint = new StringBuilder();
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        sourceToPrint.append("Дата: ").append(format.format(new Date(System.currentTimeMillis())));
        sourceToPrint.append(System.lineSeparator());
        sourceToPrint.append("Логин: ").append(query.getUser().getLogin());
        sourceToPrint.append(System.lineSeparator());
        sourceToPrint.append("Участник: ").append(query.getUser().getName());
        sourceToPrint.append(System.lineSeparator());
        sourceToPrint.append(System.lineSeparator());
        sourceToPrint.append(source);

        source = sourceToPrint.toString();

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, source.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(source.getBytes());
        outputStream.close();

        exchange.close();
    }
}
