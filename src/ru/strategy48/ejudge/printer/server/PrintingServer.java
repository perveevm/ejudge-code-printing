package ru.strategy48.ejudge.printer.server;

import org.xml.sax.SAXException;
import ru.strategy48.ejudge.printer.server.exceptions.PrinterServerException;
import ru.strategy48.ejudge.printer.server.objects.PrinterQuery;
import ru.strategy48.ejudge.printer.server.objects.PrinterToken;
import ru.strategy48.ejudge.printer.server.objects.ServerConfig;
import ru.strategy48.ejudge.printer.server.objects.User;
import ru.strategy48.ejudge.printer.server.utils.CSVUtils;
import ru.strategy48.ejudge.printer.server.utils.XMLUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PrintingServer {
    private final ServerConfig config;

    private final Map<String, User> users;
    private final Map<String, PrinterToken> tokens;

    private final ConcurrentLinkedQueue<PrinterQuery> printingQueue = new ConcurrentLinkedQueue<>();

    public PrintingServer(final ServerConfig config) throws PrinterServerException {
        this.config = config;
        this.users = CSVUtils.parseUsersTable(config.getUsersListPath());

        try {
            this.tokens = XMLUtils.loadPrinterTokens(config.getTokensListPath());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new PrinterServerException("Cannot parse tokens file: " + e.getMessage(), e);
        }
    }

    public boolean sendForPrinting(final String login, final String password, final String source) {
        if (!users.containsKey(login)) {
            return false;
        }

        User curUser = users.get(login);
        if (!curUser.getPassword().equals(password)) {
            return false;
        }

        printingQueue.add(new PrinterQuery(curUser, source));
        return true;
    }

    public PrinterQuery getPrintingCode() {
        return printingQueue.poll();
    }

    public boolean checkToken(final String token) {
        return tokens.containsKey(token);
    }

    public PrinterToken getToken(final String token) {
        return tokens.getOrDefault(token, null);
    }
}
