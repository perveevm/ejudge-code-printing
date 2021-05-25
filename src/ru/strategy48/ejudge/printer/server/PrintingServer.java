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

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes server, getting source codes to print from web interface and giving them to clients
 */
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

    /**
     * Trying to add source code to the printing queue
     *
     * @param login    user's login
     * @param password user's password
     * @param source   user's source code to print
     * @return <code>true</code> if source was added to queue successfully
     */
    public boolean sendForPrinting(final String login, final String password, final String source) {
        if (login == null || password == null || source == null) {
            return false;
        }
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

    /**
     * Takes source code from a queue
     *
     * @return query for {@link ru.strategy48.ejudge.printer.client.PrintingClient}
     */
    public PrinterQuery getPrintingCode() {
        return printingQueue.poll();
    }

    /**
     * Checks {@link ru.strategy48.ejudge.printer.client.PrintingClient} access token
     *
     * @param token given token
     * @return <code>true</code> if token is correct
     */
    public boolean checkToken(final String token) {
        return tokens.containsKey(token);
    }

    /**
     * Returns {@link PrinterToken} instance by given token value
     *
     * @param token given token
     * @return {@link PrinterToken} if token is correct or <code>null</code> otherwise
     */
    public PrinterToken getToken(final String token) {
        return tokens.getOrDefault(token, null);
    }
}
