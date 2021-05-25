package ru.strategy48.ejudge.printer.server.objects;

import java.nio.file.Path;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes configuration of {@link ru.strategy48.ejudge.printer.server.PrintingServer}
 *
 * <code>host</code> is hostname where web server is located
 * <code>port</code> is TCP port of web server
 *
 * <code>printPageHtmlPath</code> is a {@link Path} to HTML printer page
 * <code>successHtmlPath</code> is a {@link Path} to HTML page that is returned if source code was printed successfully
 * <code>failHtmlPath</code> is a {@link Path} to HTML page that is returned if an error happened while printing source
 *
 * <code>usersListPath</code> is a {@link Path} to CSV file with permitted users list
 * <code>tokensListPath</code> is a {@link Path} to XML file with access tokens list
 */
public class ServerConfig {
    private final String host;
    private final int port;

    private final Path printPageHtmlPath;
    private final Path successHtmlPath;
    private final Path failHtmlPath;

    private final Path usersListPath;
    private final Path tokensListPath;

    public ServerConfig(final String host, final int port,
                        final Path printPageHtmlPath, final Path successHtmlPath, final Path failHtmlPath,
                        final Path usersListPath, final Path tokensListPath) {
        this.host = host;
        this.port = port;

        this.printPageHtmlPath = printPageHtmlPath;
        this.successHtmlPath = successHtmlPath;
        this.failHtmlPath = failHtmlPath;

        this.usersListPath = usersListPath;
        this.tokensListPath = tokensListPath;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Path getPrintPageHtmlPath() {
        return printPageHtmlPath;
    }

    public Path getUsersListPath() {
        return usersListPath;
    }

    public Path getSuccessHtmlPath() {
        return successHtmlPath;
    }

    public Path getFailHtmlPath() {
        return failHtmlPath;
    }

    public Path getTokensListPath() {
        return tokensListPath;
    }
}
